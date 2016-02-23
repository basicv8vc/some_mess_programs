#include<iostream>
using namespace std;

/*运行时计算阶乘*/
/*
int factorial(int n)
{
return (n == 0 ? 1 : n*factorial(n - 1));
}
*/
/*递归式的模板实例化*/
template <int n>							    //模板类参数并不是类型占位符，而是int类型
struct factorial
{
	enum { ret = factorial<n - 1>::ret*n };		//这个模板类既没有数据也没有成员函数.
};
template <>										//利用模板的特化来提供递归终点									
struct factorial<0>
{
	enum { ret = 1 };
};



/*编译时计算平方根*/
template<size_t N, size_t low = 1, size_t Upp = N>
struct Root
{
	static const size_t ret = Root<N, (down ? low : mean + 1),
		(down ? mean : Upp)>::ret;
	static const size_t mean = (low + Upp) / 2;
	static const bool down = ((mean*mean) >= N);
};





/*点乘，基类*/
template <typename T> class DotProduct
{
public:
	virtual ~DotProduct() {}
	virtual T eval() = 0;
};

/*叶节点，派生类*/
template<typename T> class SimpleDotProduct :public DotProduct<T>
{
public:
	SimpleDotProduct(T* a, T* b) :v1(a), v2(b)
	{}
	virtual T eval()
	{
		return (*v1)*(*v2);
	}
private:
	T* v1;
	T* v2;

};

/*组合体，派生类1*/
template <typename T> class CompositeDotProduct : public DotProduct<T>
{
public:
	CompositeDotProduct(T *a, T *b, size_t dim) :
		s(new SimpleDotProdct<T>(a, b)),
		c((dim == 1) ? 0 : new CompositeDotProduct<T>(a + 1, b + 1, dim - 1))
	{}

	virtual ~CompositeDotProduct()
	{
		delete c;
		delete s;
	}

	virtual T eval()
	{
		return (s->eval() + ((c) ? c->eval() : 0));
	}
protected:
	SimpleDotProduct<T>* s;
	CompositeDotProduct<T> *c;
};



/*辅助函数*/
template<typename T>
T dot(T* a, T* b, size_t dim)
{
	return (dim == 1) ?
		SimpleDotProduct<T>(a, b).eval() :
		CompositeDotProduct<T>(a, b, dim).eval();
}



int main()
{
	int a = 3;
	cout << sizeof a << endl;						//sizeof是运算符
	cout << factorial<4>::ret << endl;
	return 0;
}