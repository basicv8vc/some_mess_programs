/*!
* Copyright (c) 2016 by basicv8vc
* \file io.h
* \brief defines serializable interface of exsvm.
* �ض�����ostream istream�Լ���Ӧ��streambuf��OutBuf, InBuf
* Stream�е�Read Write������serializer::Handler�е�Write��Read, Stream��ostream��istream�ĵײ㣬
* ��ô�о�InputSplit�Ƕ����ģ�����
* ���̶߳��ļ�������
*/
#ifndef EXSVM_IO_H_
#define EXSVM_IO_H_

#include <cstdio>
#include <string>
#include <vector>
#include <istream>
#include <ostream>
#include <streambuf>
#include <cstdint>

namespace exsvm {
	/*! \brief interface of stream I/O for serialization */
	class Stream {
	public:
		/*! \brief reads data from a stream
		* \param ptr Pointer to a memory buffer
		* \param size Block size
		* \return the size of data read
		*/
		virtual size_t Read(void* ptr, size_t size) = 0;
		/*! \brief writes data to a stream 
		* \param ptr Pointer to a memory buffer
		* \param size Block size.
		*/
		virtual void Write(const void* ptr, size_t size) = 0;
		/*! \brief virtual distructor */
		virtual ~Stream(){}
		/*! \brief generic factory function
		* create an stream, the stream will close the underlying files upon deletion
		*
		* \param uri The uri of the input we support by default file:// will be used.
		* \param flag can be "w", "r", "a"
		* \param allow_null, whether NULL can be returned, or directly report error
		* \return the created stream, can be NULL when allow_null==true and file do not exist
		*/
		static Stream* Create(const char *uri, const char* const flag, bool allow_null = false);
		//helper functions to write/read different data structure
		/*! \brief writes a data to stream
		* Stream support Write/Read of most STL composites and base types.
		* If the data type is not supported, a compile time error will be issued.
		* \param data, data to be written
		* \param T, the data type to be written
		*/
		template<typename T>
		inline void Write(const T &data);
		/*!
		* \brief loads a data from stream.
		*
		* Stream support Write/Read of most STL composites and base types.
		* If the data type is not supported, a compile time error will
		* be issued.
		*
		* \param out_data place holder of data to be deserialized
		* \return whether the load was successful
		*/
		template<typename T>
		inline bool Read(T *out_data);
	};

	/*! \brief interface of input stream that support seek */
	class SeekStream :public Stream {
	public:
		//virtual destructor
		virtual ~SeekStream(){}
		/*! \brief seek to certain position of the file */
		virtual void Seek(size_t pos) = 0;
		/*! \brief tell the position of the stream */
		virtual size_t Tell() = 0;
		/*! \brief generic factory function, create an SeekStream for read only, the stream will close the underlying files upon deletion error 
		* will exit when create failed
		* \param allow_null whether NULL can be returned, or directly report error
		* \return the created stream, can be NULL when allow_null==true and file do not exit
		*/
		static SeekStream *CreateForRead(const char *uri, bool allow_null = false);

	};

	/*! \brief interface for serializable objects */
	class Serializable {
	public:
		/*! \brief virtual destructor */
		virtual ~Serializable(){}
		/*��
		* \brief load the model from a stream
		* \param fi stream where to load the model from
		*/
		virtual void Load(Stream *fi) = 0;
		/*!
		* \brief saves the model to a stream
		* \param fo stream where to save the model to
		*/
		virtual void Save(Stream *fo) const = 0;
	};

	/*!
	* \brief input split creates that allows reading
	*  of records from split of data,
	*  independent part that covers all the dataset
	*
	*  see InputSplit::Create for definition of record,
	*  Raw file is splitted into several splits, each split contains many records.
	*  Each chunk of memory can contain multiple records,
	*/
	class InputSplit {
	public:
		/*! \brief a blob of memory region */
		struct Blob {
			/*! \brief pointer to start of the memory region */
			void *dptr;
			/*! \brief size of the memory region */
			size_t size;
		};

		/*! \brief hint the inputsplit how large the chunk size it should return when implementing NextChunk,
		* this is a hint so may not be enforced,
		* but InputSplit will try adjust its internal buffer
		* size to the hinted value
		* \param chunk_size the chunk size
		*/
		virtual void HinkChunkSize(size_t chunk_size){}
		/*!\brief reset the position of InputSplit to beginning */
		virtual void BeforeFirst() = 0;
		/*!\brief get the next record, the returning value is valid until next call to NextRecord or NextChunk
		* caller can modify the memory content of out_rec
		* 
		* For text, out_rec contains a single line
		* For recordio, out_rec contains one record content(with header striped)
		* \param out_rec used to store the result
		* \return true if we can successfully get next record, false we reached end of split,
		* \sa InputSplit::Create for definition of record.
		*/
		virtual bool NextRecord(Blob *out_rec) = 0;

		/*!
		* \brief get a chunk of memory that can contain multiple records,
		*  the caller needs to parse the content of the resulting chunk,
		*  for text file, out_chunk can contain data of multiple lines
		*  for recordio, out_chunk can contain multiple records(including headers)
		*
		*  This function ensures there won't be partial record in the chunk
		*  caller can modify the memory content of out_chunk,
		*  the memory is valid until next call to NextRecord or NextChunk
		*
		*  Usually NextRecord is sufficient, NextChunk can be used by some
		*  multi-threaded parsers to parse the input content
		*
		* \param out_chunk used to store the result
		* \return true if we can successfully get next record
		*     false if we reached end of split
		* \sa InputSplit::Create for definition of record
		* \sa RecordIOChunkReader to parse recordio content from out_chunk
		*/
		virtual bool NextChunk(Blob *out_chunk) = 0;
		/*! \brief destructor*/
		virtual ~InputSplit() {}

		/*!
		* \brief reset the Input split to a certain part id,
		*  The InputSplit will be pointed to the head of the new specified segment.
		*  This feature may not be supported by every implementation of InputSplit.
		* \param part_index The part id of the new input.
		* \param num_parts The total number of parts.
		*/
		virtual void ResetPartition(unsigned part_index, unsigned num_parts) = 0;

		/*!
		* \brief factory function:
		*  create input split given a uri
		* \param uri the uri of the input
		* \param part_index the part id of current input
		* \param num_parts total number of splits
		* \param type type of record
		*   List of possible types: "text", "recordio"
		*     - "text":
		*         text file, each line is treated as a record
		*         input split will split on '\\n' or '\\r'
		*     - "recordio":
		*         binary recordio file, see recordio.h
		* \return a new input split
		* \sa InputSplit::Type
		*/
		static InputSplit* Create(const char *uri,
			unsigned part_index,
			unsigned num_parts,
			const char *type);

	};
	/*! \brief a std::ostream class that can wrap Stream objects, can use ostream with that output to underlying Stream
	* ostream�Ƕ�Stream�ķ�װ��Stream���Ƕ�Streambuffer�ķ�װ��
	* Usage example:
	* \code
	* Stream *fs = Stream::Create("file:///test.txt","w");
	* exsvm::ostream os(fs);
	* os<<"hello world"<<std::endl;
	* delete fs;
	* \endcode
	*/
	//ostream��std::ostream��������
	class ostream:public std::basic_ostream<char> 
	{
	public:
		/*! \brief construct std::ostream type 
		* \param stream, the Stream output to be used
		* \param buffer_size, internal streambuf size
		*/
		ostream(Stream *stream, size_t buffer_size = (1 << 10)) :std::basic_ostream<char>(NULL), buf_(buffer_size) 
		{
			this->set_stream(stream);
			
		}
		//virtual destructor
		virtual ~ostream()
		{
			buf_.pubsync();
		}
		/*! \brief set internal stream to be stream, reset states
		* \param stream new stream as output
		*/
		inline void set_stream(Stream *stream)
		{
			buf_.set_stream(stream);
			this->rdbuf(&buf_);
		}
		/*! \return how many bytyes we written so far */
		inline size_t bytes_written() const 
		{
			return buf_.bytes_out();
		}

	private:
		//internal streambuf, typedef basic_streambuf<char> streambuf;
		class OutBuf :public std::streambuf {
		public:
			explicit OutBuf(size_t buffer_size) :stream_(NULL), buffer_(buffer_size), bytes_out_(0)
			{
				if (buffer_size == 0)
					buffer_.resize(2);
			}
			//set stream to the buffer
			inline void set_stream(Stream *stream);

			inline size_t bytes_out()const
			{
				return bytes_out_;
			}
		private:
			//internal stream by StreamBuf
			Stream *stream_;
			/*! \brief internal buffer */
			std::vector<char> buffer_;
			/*! \brief number of bytes written so far */
			size_t bytes_out_;
			//override sync
			inline int_type sync();
			//override overflow
			inline int_type overflow(int c);
		};

		/*! \brief buffer of the stream */
		OutBuf buf_;
	};

	/*!
	* \brief a std::istream class that can can wrap Stream objects,
	*  can use istream with that output to underlying Stream
	* istream�Ƕ�Stream�ķ�װ,
	* Usage example:
	* \code
	*
	*   Stream *fs = Stream::Create("hdfs:///test.txt", "r");
	*   exsvm::istream is(fs);
	*   is >> mydata;
	*   delete fs;
	* \endcode
	*/
	class istream :public std::basic_istream<char> {
	public:
		/*!
		* \brief construct std::istream type
		* \param stream, the Stream output to be used
		* \param buffer_size, internal buffer size
		*/
		istream(Stream *stream,
			size_t buffer_size = (1 << 10))//1024���ֽ�
			: std::basic_istream<char>(NULL), buf_(buffer_size) {
			this->set_stream(stream);
		}
		virtual ~istream(){}
		/*! \brief set internal stream to be stream, reset states
		* \param stream, new stream as output
		*/
		inline void set_stream(Stream *stream)
		{
			buf_.set_stream(stream);
			this->rdbuf(&buf_);
		}
		/*! \return how many bytes we read so far */
		inline size_t bytes_read(void) const {
			return buf_.bytes_read();
		}

	private:
		//internal streambuf
		class InBuf : public std::streambuf {
		public:
			explicit InBuf(size_t buffer_size)
				: stream_(NULL), bytes_read_(0),
				buffer_(buffer_size) {
				if (buffer_size == 0) buffer_.resize(2);
			}
			// set stream to the buffer
			inline void set_stream(Stream *stream);
			// return how many bytes read so far
			inline size_t bytes_read(void) const {
				return bytes_read_;
			}
		private:
			/*! \brief internal stream by StreamBuf */
			Stream *stream_;
			/*! \brief how many bytes we read so far */
			size_t bytes_read_;
			/*! \brief internal buffer */
			std::vector<char> buffer_;
			// override underflow
			inline int_type underflow();
		};
		/*! \brief input buffer */
		InBuf buf_;

	};

	//implementations of inline functions
	template<typename T>
	inline void Stream::Write(const T &data)
	{
		serializer::Handler<T>::Write(this, data);
	}
	template<typename T>
	inline bool Stream::Read(T *out_data)
	{
		return serializer::Handler<T>::_ReadBarrier(this, out_data);
	}

	//implementation for ostream
	inline void ostream::OutBuf::set_stream(Stream *stream)
	{
		if (stream_ != NULL)this->pubsync();
		this->stream_ = stream;
		this->setp(&buffer_[0], &buffer_[0] + buffer_.size() - 1);
	}
	inline int ostream::OutBuf::sync()
	{
		if (stream_ == NULL)return -1;
		std::ptrdiff_t n = pptr() - pbase();
		stream_->Write(pbase(), n);
		this->pbump(-static_cast<int>(n));
		bytes_out_ += n;
		return 0;

	}

	inline int ostream::OutBuf::overflow(int c)
	{
		*(this->pptr()) = c;
		std::ptrdiff_t n = pptr() - pbase();
		this->pbump(-static_cast<int>(n));
		if (c == EOF)
		{
			stream_->Write(pbase(), n);
			bytes_out_ += n;
		}
		else
		{
			stream_->Write(pbase(), n + 1);
			bytes_out_ += n + 1;
		}
		return c;
	}

	// implementations for istream
	inline void istream::InBuf::set_stream(Stream *stream) {
		stream_ = stream;
		this->setg(&buffer_[0], &buffer_[0], &buffer_[0]);
	}
	inline int istream::InBuf::underflow() {
		char *bhead = &buffer_[0];
		if (this->gptr() == this->egptr()) {
			size_t sz = stream_->Read(bhead, buffer_.size());
			this->setg(bhead, bhead, bhead + sz);
			bytes_read_ += sz;
		}
		if (this->gptr() == this->egptr()) {
			return traits_type::eof();
		}
		else {
			return traits_type::to_int_type(*gptr());
		}
	}

}




#endif 
