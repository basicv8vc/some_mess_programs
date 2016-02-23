#include<iostream>
using namespace std;

/*����ʱ����׳�*/
/*
int factorial(int n)
{
return (n == 0 ? 1 : n*factorial(n - 1));
}
*/
/*�ݹ�ʽ��ģ��ʵ����*/
template <int n>							    //ģ�����������������ռλ��������int����
struct factorial
{
	enum { ret = factorial<n - 1>::ret*n };		//���ģ�����û������Ҳû�г�Ա����.
};
template <>										//����ģ����ػ����ṩ�ݹ��յ�									
struct factorial<0>
{
	enum { ret = 1 };
};



/*����ʱ����ƽ����*/
template<size_t N, size_t low = 1, size_t Upp = N>
struct Root
{
	static const size_t ret = Root<N, (down ? low : mean + 1),
		(down ? mean : Upp)>::ret;
	static const size_t mean = (low + Upp) / 2;
	static const bool down = ((mean*mean) >= N);
};





/*��ˣ�����*/
template <typename T> class DotProduct
{
public:
	virtual ~DotProduct() {}
	virtual T eval() = 0;
};

/*Ҷ�ڵ㣬������*/
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

/*����壬������1*/
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



/*��������*/
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
	cout << sizeof a << endl;						//sizeof�������
	cout << factorial<4>::ret << endl;
	return 0;
}