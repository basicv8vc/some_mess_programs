/*
*  ����ʹ��map��Ҳ�����Լ��������ʾ��map�ṹ<char, int>��ʾÿ���ַ���һ�γ��ֵ�λ��,
*  ����ÿ�ν������浱ǰ����Ӵ��������ǰ�ַ�����Ӵ����ظ��ˣ������ظ�λ��Ϊi������map��0-(i-1)��ɾ�����У���������Ч�ʵ�
*  �ڶ��ַ�ʽ�ǲ�ɾ�������������ַ���һ�γ���λ�ã����� length = min(i - alphabet[s[i]], length +1); ��Ϊ������"abbbbba"���������
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
