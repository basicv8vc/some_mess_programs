/*
* 字符串匹配Sunday算法,  http://www.inf.fh-flensburg.de/lang/algorithmen/pattern/sundayen.htm
* 甚至可以compared in an arbitrary order,不必从左到右或者从右到左，可以任意的顺序！！！比如abcde是pattern，可以先从c比较,
* 对pattern进行预处理的过程同boyer-moore中的坏字符规则,
*/

#include <iostream>
#include <string>
using namespace std;
#define ALPHABETSIZE 256

void makeSunday(const string &needle, int sunday[])
//得到移动字符映射表
{
	

	for (int i = 0; i < ALPHABETSIZE; ++i)
		sunday[i] = -1;

	char a;
	for (unsigned i = 0; i < needle.size(); ++i)
	{
		a = needle[i];
		sunday[a] = i;//如果有多个相同字符在needle，这里记录的是最后一个字符的位置
	}

}

int sundayMatch(const string &haystack, const string &needle)
//Sunday算法主流程, 这里按照from left to right order,
{
	unsigned hsize = haystack.size();
	unsigned nsize = needle.size();
	if (nsize == 0)
		return 0;
	if (hsize == 0)
		return -1;
	int sunday[ALPHABETSIZE];//每个字符在needle中的位置
	makeSunday(needle, sunday);
	//for (unsigned i = 0; i < nsize; ++i)
		//cout << sunday[needle[i]] << ' ';
	//cout << endl;

	for (unsigned i = 0,k=0; i < hsize;)
	{
		while (haystack[i] == needle[k])
		{
			++k;
			++i;
			//cout << "i,k一起match " << i << ' ' << k << endl;
			if (k == nsize)
				return i - k;

		}
		 
		unsigned start = i - k + nsize;//current text window，后第一个字符的位置
		i = i - k + (nsize - sunday[haystack[start]]); //i-k是当前text window第一个字符位置; 子串右移等价于长串右移,
		k = 0;
		//cout << "i and k " << i << ' ' << k << endl;

		

	}
	return -1;

}
int main()
{
	string haystack = "ABCD";
	string needle = "ABDD";
	cout<<sundayMatch(haystack, needle) << endl;
	return 0;
}




