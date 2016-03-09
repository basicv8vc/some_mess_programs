#include <iostream>
#include <functional>

struct Foo {
	Foo(int num):num_(num){}
	void print_add(int i)const
	{
		std::cout << num_ + i << std::endl;
	}
	int num_;
};

void print_num(int i)
{
	std::cout << i << std::endl;
}

struct PrintNum {
	void operator()(int i)const {
		std::cout << i << std::endl;
	}
};

int main()
{
	//store a free function
	std::function<void(int)> f_display = print_num;
	f_display(-9);

	//store a lambda
	std::function<void()> f_display_42 = []() {print_num(42); };
	f_display_42();
	// store a call to a member function，可以看出来struct 和class的成员函数都有一个this，
	std::function<void(const Foo&, int)> f_add_display = &Foo::print_add;
	const Foo foo(314159);
	f_add_display(foo, 1);

	return 0;
}