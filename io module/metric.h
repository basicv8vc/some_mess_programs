/*!
* Copyright 2016 by basicv8vc
* \file metric.h
* \brief interface of evaluation metric function supported in exsvm.
*/

#ifndef EXSVM_METRIC_H_
#define EXSVM_METRIC_H_

#include <dmlc/registry.h>
#include <vector>
#include <string>
#include <functional>
#include "./data.h"
#include "./base.h"

namespace exsvm {
	/*! \brief interface of evaluation metric used to evaluate model performance.
	* This has nothing to do with training, but merely act as evaluation purpose.
	*/
	class Metric {
	public:
		/*! \brief evaluate a specific metric
		* \param preds prediction
		* \param info information, including label etc.
		*/
		virtual float Eval(const std::vector<float> &preds,
			const MetaInfo &info)const = 0;
		/*! \return name of metric */
		virtual const char* Name()const = 0;
		/*! \brief virtual destructor */
		virtual ~Metric(){}
		/*!
		* \brief create a metric according to name.
		* \param name name of the metric.
		*  name can be in form metric[@]param
		*  and the name will be matched in the registry.
		* \return the created metric.
		*/
		static Metric* Create(const std::string &name);
	};

	/*!
	*/
	struct MetricReg: public dmlc::FunctionRegEntryBase<MetricReg, std::function<Metric*(const char*)> >{};
	

}


#endif
