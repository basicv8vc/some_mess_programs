/*!
* Copyright (c) 2016 by basicv8vc
* \file data.h
* \brief The input data structure of exsvm.
*/
#ifndef EXSVM_DATA_H_
#define EXSVM_DATA_H_

#include <dmlc/base.h>
#include <dmlc/data.h>
#include <string>
#include <memory>
#include <vector>
#include "./base.h"

namespace exsvm {

	/*! \brief data type accepted by exsvm interface */
	enum DataType {
		kFloat32 = 1,
		kDouble = 2,
		kUInt32 = 3,
		kUInt64 = 4
	};

	/*!
	* \brief Meat information about dataset, always sit in memory.
	*/
	struct MetaInfo {
		/*! \brief number of rows in the data */
		uint32_t num_row;
		/*! \brief number of columns in the data */
		uint32_t num_col;
		/*! \brief number of nonzero entries in the data */
		uint32_t num_nonzero;
		/*! \brief label of each instance */
		std::vector<float> labels;
		/*! \brief weights of each instance, optional */
		std::vector<float> weights;
		/*! \brief default constructor */
		MetaInfo() :num_row(0), num_col(0), num_nonzero(0) {}
		/*!
		* \brief Get weight of each instance.
		* \param i Instance index.
		* \return The weight.
		*/
		inline float GetWeight(size_t i) const {
			return weights.size() != 0 ? weights[i] : 1.0f;
		}
		/*! \brief clear all the information */
		void Clear();
		/*! \brief Load the Meat info from binary stream.
		* \param fi The input stream.
		*/
		void LoadBinary(dmlc::Stream *fi);
		/*! \brief Save the Meta info to binary stream
		* \param fo The output stream
		*/
		void SaveBinary(dmlc::Stream *fo) const;
		/*!
		* \brief Set information in the meta info.
		* \param key The key of the information.
		* \param dptr The data pointer of the source array.
		* \param dtype The type of the source data.
		* \param num Number of elements in the source array.
		*
		*/
		void SetInfo(const char* key, const void* dptr, DataType dtype, size_t num);
	};


	/*! \brief read-only sparse instance batch in CSR format */
	struct SparseBatch
	{
		/*! \brief an entry of sparse vector */
		struct Entry
		{

			/*! \brief feature index */
			uint32_t index;
			/*! \brief feature value */
			float fvalue;
			/*! \brief default constructor */
			Entry() {}
			/*! \brief constructor with index and value
			* \param index The feature or instance.
			* \param fvalue The feature value.
			*/
			Entry(uint32_t index, float fvalue) :index(index), fvalue(fvalue) {}
			/*! \brief reversely compare feature values */
			inline static bool CmpValue(const Entry &a, const Entry &b)
			{
				return a.fvalue < b.fvalue;
			}
		};

		/*! \brief an instance of sparse vector in the batch */
		struct Inst
		{
			/*! \brief pointer to the elements */
			const Entry *data;
			/*! \brief length of the instance */
			uint32_t length;
			/*! \brief constructor */
			Inst(const Entry *data, uint32_t length) :data(data), length(length) {}
			/*! \brief get i-th pair in the sparse vector */
			inline const Entry& operator[](size_t i) const
			{
				return data[i];
			}
		};

		/*! \brief batch size */
		size_t size;
	};


	/*! \brief read-only row batch, used to access row continuously */
	struct RowBatch : public SparseBatch
	{
		/*! \brief the offset of rowid of this batch */
		size_t base_rowid;
		/*! \brief array[size+1], row pointer of each of the elements */
		const size_t *ind_ptr;
		/*! \brief array[ind_ptr.back()], content of the sparse element */
		const Entry *data_ptr;
		/*! \brief get i-th row from the batch */
		inline Inst operator[](size_t i) const
		{
			return Inst(data_ptr + ind_ptr[i], static_cast<uint32_t>(ind_ptr[i + 1] - ind_ptr[i]));
		}
	};

	/*!
	* \brief read-only column batch, used to access columns,
	* the columns are not required to be continuous
	*/
	struct ColBatch : public SparseBatch {
		/*! \brief column index of each columns in the data */
		const uint32_t *col_index;
		/*! \brief pointer to the column data */
		const Inst *col_data;
		/*! \brief get i-th column from the batch */
		inline Inst operator[](size_t i) const {
			return col_data[i];
		}
	};

	/*!
	* \brief This is data structure that user can pass to DMatrix::Create
	*  to create a DMatrix for training, user can create this data structure
	*  for customized Data Loading on single machine.
	*/
	class DataSource :public dmlc::DataIter<RotBatch>
	{
	public:
		/*! \brief Meat information about the dataset
		* The subclass need to be able to load this correctly from data.
		*/
		MetaInfo info;

	};

	/*! \brief A vector-like structure to represent set of rows.
	* But saves the memory when all rows are in the set.
	*/
	struct RowSet
	{
	public:
		/*! \brief \return i-th row index */
		inline uint32_t operator[](size_t i) const;
		/*! \return the size of the set */
		inline size_t size() const;
		/*! \brief push the index back to the set */
		inline void push_back(uint32_t i);
		/*! \brief clear the set */
		inline void clear();
		/*! \brief save rowset to file
		* \param fo The file to be saved.
		*/
		inline void save(dmlc::Stream *fo) const;
		/*!
		* \brief Load rowset from file.
		* \param fi The file to be loaded.
		* \return if read is successful.
		*/
		inline bool load(dmlc::Stream* fi);
		/*! \brief default constructor */
		RowSet():size_(0){}
	private:
		/*! \brief The internal data structure of size */
		size_t size_;
		/*! \brief The internal data structure of row set if not all */
		std::vector<uint32_t> rows_;
	};

	inline uint32_t RowSet::operator[](size_t i) const 
	{
		return rows_.size() == 0 ? i : rows_[i];
	}
	inline size_t RowSet::size() const
	{
		return size_;
	}
	inline void RowSet::clear()
	{
		rows_.clear();
		size_ = 0;
	}
	inline void RowSet::push_back(uint32_t i)
	{
		rows_.push_back(i);
		++size_;
	}
	inline void RowSet::save(dmlc::Stream *fo)const
	{
		fo->Write(rows_);
		fo->Write(&size_, sizeof(size_));
	}
	inline bool RowSet::load(dmlc::Stream *fi)
	{
		if (!fi->Read(&rows_)) return false;
		if (rows_.size() != 0) return true;
		return fi->Read(&size_, sizeof(size_)) == sizeof(size_);
	}

	/*!
	* \brief Internal data structured used by ExSVM during training.
	*  There is only one  ways to create a customized DMatrix that reads in user defined-format in single machine.
	*  Provdie a DataSource, that can be passed to DMatrix::Create
	*      This can be used to re-use inmemory data structure into DMatrix.
	*/
	class DMatrix
	{
	public:
		/*! \brief default constructor */
		DMatrix():cache_learner_ptr_(nullptr){}
		/*! \brief meta information of the dataset */
		virtual MetaInfo& info() = 0;
		/*! \brief meta information of the dataset*/
		virtual const MetaInfo& info() const = 0;
		/*! \brief get the raw iterator, reset to beginning position,
		* \note Only either RowIterator or column Iterator can be active.
		*/
		virtual dmlc::DataIter<RowBatch>* RowIterator() = 0;
		/*! \brief get column iterator, reset to the begging position */
		virtual dmlc::DataIter<ColBatch>* ColIterator() = 0;
		/*! \brief virtual destructor */
		virtual ~DMatrix(){}
		/*! \brief save DMatrix to local file.
		* \param fname The file name to be saved.
		* \return The created DMatrix.
		*/
		virtual void SaveToLocalFile(const std::string& fname);
		/*!
		* \brief Load DMatrix from URI.
		* \param uri The URI of input.
		* \param silent Whether print information during loading.
		* \param load_row_split Flag to read in part of rows, divided among the workers in distributed mode.
		* \param file_format The format type of the file, used for dmlc::Parser::Create.
		*   By default "auto" will be able to load in both local binary file.
		* \return The created DMatrix.
		*/
		static DMatrix* Load(const std::string& uri,
			bool silent,
			bool load_row_split,
			const std::string& file_format = "auto");
		/*!
		* \brief create a new DMatrix, by wrapping a row_iterator, and meta info.
		* \param source The source iterator of the data, the create function takes ownership of the source.
		* \param cache_prefix The path to prefix of temporary cache file of the DMatrix when used in external memory mode.
		*     This can be nullptr for common cases, and in-memory mode will be used.
		* \return a Created DMatrix.
		*/
		static DMatrix* Create(std::unique_ptr<DataSource>&& source,
			const std::string& cache_prefix = "");
	private:
		// allow learner class to access this field.
		friend class LearnerImpl;
		/*! \brief public field to back ref cached matrix. */
		LearnerImpl* cache_learner_ptr_;
	};

}
