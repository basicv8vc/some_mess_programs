/*!
* Copyright (c) 2016 by basicv8vc
* \file base.h
* \brief defines configuration macros of exsvm.
*/
#ifndef EXSVM_BASE_H_
#define EXSVM_BASE_H_

#include <dmlc/base.h>
#include <dmlc/omp.h>



/*!
* \brief Whether always log console message with time.
*  It will display like, with timestamp appended to head of the message.
*  "[21:47:50] 6513x126 matrix with 143286 entries loaded from ../data/agaricus.txt.train"
*/
#ifndef EXSVM_LOG_WITH_TIME
#define EXSVM_LOG_WITH_TIME 1
#endif


/*! \brief namespace of exsvm*/
namespace exsvm {
/*!
* \brief unsigned interger type,
*  used for feature index and row index.
*/
//typedef uint32_t svm_unit;
/*! \brief long integers */
typedef unsigned long svm_ulong;
/*! \brief float type, used for storing statistics */
//typedef float svm_float;


/*! \brief define unsigned long for openmp loop */
typedef dmlc::omp_ulong omp_ulong;
/*! \brief define unsigned int for openmp loop */
typedef dmlc::omp_uint bst_omp_uint;

/*!
* \brief define compatible keywords in g++
*  Used to support g++-4.6 and g++4.7
*/
#if DMLC_USE_CXX11 && defined(__GNUC__) && !defined(__clang_version__)
#if __GNUC__ == 4 && __GNUC_MINOR__ < 8
#define override
#define final
#endif
#endif
}//namespace exsvm

#endif
