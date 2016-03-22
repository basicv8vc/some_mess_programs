//https://codefights.com/challenge/wwPuroz78u6yA8cGD
template<class T>
T Expand_It(T s, int k) {

	long long c[26] = { 0 };
	for (long long i = 0; i<s.size();){
		c[s[i] - 'a'] += (s[i + 1] - '0');
		i += 2;
	}
	long long a = 0;
	for (int i = 0; i<26; ++i)
	{
		a += c[i];
		if (a >= k)
			return T(1, i + 'a');
	}
	return "-1";

}
