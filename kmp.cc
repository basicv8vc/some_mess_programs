#include <iostream>
#include <string>
using namespace std;

void makeNext(const string &needle, int next[]) {
	//http://www.cnblogs.com/c-cloud/p/3224788.html
	int q, k;//q:子串下标；k:最大前后缀长度
	int m = needle.size();//子串的长度
	//int *next = new int[needle.size()];
	next[0] = 0;//第一个字符的肯定为0

	for (q = 1, k = 0; q < m; ++q) {//一次计算子串中每一个字符的next值
		while (k > 0 && (needle[q] != needle[k])) {//递归的求出P[0]···P[q]的最大的相同的前后缀长度k
			k = next[k - 1];
		}

		if (needle[q] == needle[k])//如果相等，最大相同前后缀长度加1
			++k;

		next[q] = k;
	}//for

}

//匹配字符串，返回index，或者-1
int strStr(const string &haystack, const string &needle) {

	//对子串构建next数组/前缀数组/部分匹配表(有限状态自动机),
	//对子串要求next数组实际就是自我匹配！和长串匹配子串的过程一样！

	//计算next数组
	int *next = new int[needle.size()];


	makeNext(needle, next);
	for (unsigned i = 0; i < needle.size(); ++i)
		cout << next[i] << ' ';
	cout << endl;
	
	for (unsigned i = 0,k=0; i < haystack.size();) {
		while (haystack[i + k] == needle[k]) {
			++k;
			if (k == needle.size())
				return i;
		}

		//i += k;//长串不动，只需要子串右移,

		if(k>0){
			i += k;
			//k -= next[k - 1];
			k = next[k - 1];//长串不动，所以下标i+=k,而子串右移k-next[k-1]位，所以k=k-(k-next[k-1]),即 k=next[k-1];
		}
		else{
			++i;

		}

	}

	return -1;
}

int main()
{
	string needle = "baba";// "ABCDABD";
	string hackey = "ababda"; "BBC ABCDAB ABCDABCDABDE";
	cout << strStr(hackey, needle) << endl;;
	return 0;
}
