/*
*  可以使用map，也可以自己用数组表示，map结构<char, int>表示每个字符上一次出现的位置,
*  可以每次仅仅保存当前最长的子串，如果当前字符在最长子串中重复了（假设重复位置为i），将map中0-(i-1)的删掉才行，这种做法效率低
*  第二种方式是不删除，保存所有字符上一次出现位置，但是 length = min(i - alphabet[s[i]], length +1); 因为有形如"abbbbba"这种情况！
*/
class Solution {
public:
	int lengthOfLongestSubstring(string s) {

		unsigned maxLength = 0;
		unsigned length = 0;
		int alphabet[256];

		for (int i = 0; i < 256; ++i)
			alphabet[i] = -1;
		for (unsigned int i = 0; i < s.length(); ++i)
		{
			if (alphabet[s[i]] == -1)
			{
				++length;
				alphabet[s[i]] = i;
			}
			else
			{
				maxLength = length > maxLength ? length : maxLength;
				length = min(i - alphabet[s[i]], length +1); //"abbba"
				alphabet[s[i]] = i;
			}

		}
		
		return length > maxLength ? length : maxLength;;
		


	}

};



class Solution {
public:
	int lengthOfLongestSubstring(string s) {

		unsigned maxLength = 0;
		unsigned length = 0;
		map<char, int> m;

		for (unsigned int i = 0; i < s.length(); ++i)
		{
			if (m.find(s[i]) == m.end())
			{
				++length;
				m[s[i]] = i;
			}
			else
			{
				maxLength = length > maxLength ? length : maxLength;
				for (int j = i - length; j < m[s[i]];++j)
					m.erase(s[j]);
				length = i - m[s[i]];
				
				//for (int j = m[s[i]] + 1; j < i; ++j)
					

				m[s[i]] = i;
			}

		}
		
		return length > maxLength ? length : maxLength;;
		


	}

};
