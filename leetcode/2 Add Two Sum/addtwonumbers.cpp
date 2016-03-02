#include <iostream>
#include <vector>
#include <map>
#include <unordered_map>
using namespace std;


/**
* Definition for singly-linked list.
* struct ListNode {
*     int val;
*     ListNode *next;
*     ListNode(int x) : val(x), next(NULL) {}
* };
*/

//关键： The digits are stored in reverse order,意思是个位数在第一个Node,
//Input: (2 -> 4 -> 3) + (5 -> 6 -> 4), 342+465=807
//Output : 7 -> 0 -> 8

struct ListNode {
	int val;
	ListNode *next;
	ListNode(int x):val(x),next(NULL){}
};


//36 ms， 击败了 63.70%
ListNode* addTwoNumbers1(ListNode* l1, ListNode* l2)
{
	//head和tail指针先指向一个无意义的ListNode,然后利用tail不断追加ListNode,返回head->next
	
	ListNode ln(0);
	ListNode *head= &ln ;
	ListNode *tail = head;
	

	


	int jinwei = 0;
	while (l1 != NULL && l2 != NULL)//同时遍历l1 l2
	{
		int sum = l1->val+l2->val+jinwei;
		jinwei = 0;
		if (sum >= 10) {
			sum -= 10;
			++jinwei;
		}

		ListNode *tmp = new ListNode(sum);
		tail->next=tmp;
		tail = tmp;
		l1 = l1->next;
		l2 = l2->next;
		
	}
	while (l1 != NULL)//l2 end,单独遍历l1
	{
		int sum = l1->val + jinwei;
		jinwei = 0;
		if (sum >= 10) {
			sum -= 10;
			++jinwei;
		}
		ListNode *tmp = new ListNode(sum);
		tail->next = tmp;
		tail = tmp;
		l1 = l1->next;
	}
	while(l2!=NULL)
	{
		int sum = l2->val + jinwei;
		jinwei = 0;
		if (sum >= 10) {
			sum -= 10;
			++jinwei;
		}
		ListNode *tmp = new ListNode(sum);
		tail->next = tmp;
		tail = tmp;
		l2 = l2->next;
	}
	if (jinwei == 1) {
		ListNode *tmp = new ListNode(1);
		tail->next = tmp;
		tail = tmp;
	}
	return head->next;
}

//36ms
ListNode* addTwoNumbers2(ListNode* l1, ListNode* l2)
{
	//利用l1和l2的空间,储存结果,
	ListNode *head = l1;
	ListNode *tail = l1;


	int jinwei = 0;
	while (l1 != NULL && l2 != NULL)//同时遍历l1 l2
	{
		int sum = l1->val + l2->val + jinwei;
		jinwei = 0;
		if (sum >= 10) {
			sum -= 10;
			++jinwei;
		}

		l1->val=sum;
		if(l1->next !=NULL)
			tail = l1->next;

		l1 = l1->next;
		l2 = l2->next;

	}
	while (l1 != NULL)//l2 end,单独遍历l1
	{
		int sum = l1->val + jinwei;
		jinwei = 0;
		if (sum >= 10) {
			sum -= 10;
			++jinwei;
		}
		l1->val=sum;
		if(l1->next!=NULL)
			tail = l1->next;
		l1 = l1->next;
	}
	while(l2!=NULL)
	{
		int sum = l2->val + jinwei;
		jinwei = 0;
		if (sum >= 10) {
			sum -= 10;
			++jinwei;
		}
		l2->val=sum;
		tail->next = l2;
		tail = l2;
		l2 = l2->next;
	}
	//tail指向最后一个 ListNode,
	if (jinwei == 1) {
		ListNode *tmp = new ListNode(1);
		tail->next = tmp;
		tail = tmp;
	}
	return head;

}


//44 ms
ListNode* addTwoNumbers3(ListNode* l1, ListNode* l2)
//优化三个while循环写法，只需要写一个while循环即可，极大缩减了代码量,
{
	ListNode *head = new ListNode(0);
	ListNode *tail = head;

	int plus = 0;
	while (l1 || l2)
	{
		int sum = (l1 ? l1->val : 0) + (l2 ? l2->val : 0) + plus;

		if (sum >= 10)
		{
			sum -= 10;
			plus = 1;
		}
		else
			plus = 0;


		tail->next = new ListNode(sum);
		tail = tail->next;

		l1 = (l1?l1->next : NULL);
		l2 = (l2 ? l2->next : NULL);

	}
	if (plus)
	{
		tail->next = new ListNode(1);
		tail = tail->next;
	}

	return head->next;

}


