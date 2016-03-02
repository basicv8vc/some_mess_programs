//Runtime:12ms,  beats 85.58%
//先排序，然后首尾两个指针，向内移动,但要注意要返回原始数组的下标而不是排序后的数组下标,所以要用map<index, value>并且对value排序，
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
			int min = (result[i].index < result[j].index) ? result[i].index : result[j].index;//下标从小到大输出
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



//很简单的思想，使用unordered_map 17ms, 使用map 24ms,

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


