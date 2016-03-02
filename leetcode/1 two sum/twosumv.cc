//Runtime:12ms,  beats 85.58%
//������Ȼ����β����ָ�룬�����ƶ�,��Ҫע��Ҫ����ԭʼ������±�����������������±�,����Ҫ��map<index, value>���Ҷ�value����
class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
    
    vector<PAIR> result;
	
	for (unsigned i = 0; i < nums.size(); ++i)
	{
		result.push_back(PAIR(i,nums[i]));
		
	}

	
	sort(result.begin(), result.end(),cmp);

	unsigned i = 0, j= result.size()-1;
	vector<int> outcome;
	while (i < j)
	{
		
		if ((result[i].value + result[j].value) == target)
		{
			int min = (result[i].index < result[j].index) ? result[i].index : result[j].index;//�±��С�������
			int max = (result[i].index < result[j].index) ? result[j].index : result[i].index;
			outcome.push_back(min);
			outcome.push_back(max);
			return outcome;
		}
		else if ((result[i].value + result[j].value) < target)
			++i;
		else {
			--j;
		
		}
		
	}
        
    }
    struct PAIR {
	    int index;
	    int value;
	    PAIR(int a, int b) :index(a), value(b) {}
    };
    static bool cmp(PAIR a, PAIR b) //
    {
	    return a.value < b.value;
    }
};



//�ܼ򵥵�˼�룬ʹ��unordered_map 17ms, ʹ��map 24ms,

class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
    
	unsigned size = nums.size();
	unordered_map<int, int> imap;
	
	vector<int> result;
	for (unsigned i = 0; i < size; ++i)
	{
		if (imap.find(target - nums[i]) != imap.end())
		{
			result.push_back(imap[target - nums[i]]);
			result.push_back(i);
			return result;
		}
		imap[nums[i]] = i;
	}
    }

};


