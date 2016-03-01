/*!
* Copyright (c) 2016 by basicv8vc
* \file redblack.cc
* \brief implement red black tree.
* https://zh.wikipedia.org/wiki/%E7%BA%A2%E9%BB%91%E6%A0%91
*/

#include <iostream>
#include <algorithm>
#include <vector>
#include <map>
using namespace std;

enum Color {
	red,black
};

template<typename T>
class RedBlackTree {

private:

	struct Node {
		T value;
		Color color;
		Node<T> *parent, *leftTree, *rightTree; //三个指针，分别指向父节点，左右儿子

		Node() 
		{
			color = red;
			parent = leftTree = rightTree = NULL;
		}


		Node* grandparent()
		{
			if (parent != NULL)////两个问题：其实也就root的parent==NULL? root的孩子有grandparents？
				return parent->parent;
			return NULL;
		}

		Node* uncle()
		{
			if (grandparent()==NULL)
				return NULL;
			if(parent==grandparent()->leftTree)
				return grandparent()->rightTree;
			else
				return grandparent()->leftTree;
				
		}

		Node* sibling()
		//返回自己的兄弟姐妹,必须有兄弟，满二叉树
		{
			if (this == parent->leftTree)
				return parent->rightTree;
			else
				return parent->leftTree;

		}



	};
	
	void insertCheck(Node *p)
	//p点由黑色变红色，是否冲突
	{
		if (p->parent == NULL)//p是root,只需红变黑啦
			p->color = black;

		else if (p->grandparent()==NULL)//p是root的孙子，
		{


		}
	}

	void insert(Node *p, T x)
	//find the place, then insert x
	{
		if (p->value >= x)//p的value>=x,将x肯定插入到p左边
		{
			if (p->leftTree == NIL)
			{
				Node* tmp = new Node();
				tmp->value = x;
				tmp->parent = p;
				tmp->leftTree = tmp->rightTree = NIL;
				p->leftTree = tmp;//新的儿子
				//看parent的颜色,如果是黑色，则end，否则看叔叔的颜色
				if (tmp->parent->color = red)//tmp->parent==p；注意root在插入时可能经历祖叔换色，成为红色，然后insertCheck() 会纠正的，所以这里root不会是红色,而p是红色，说明不是root，tmp必有grandparents,

				{
					
					if (tmp->uncle()->color == red)//首先由叔叔，且叔叔是红色,默认有祖父
					{
						//父亲、叔叔和祖父换颜色
						tmp->parent->color = black;
						tmp->uncle()->color = black;
						tmp->grandparent()->color = red;

						//对祖父进行红红检查
						insertCheck(tmp->grandparent());//insertCheck做的事是：点已经插入到数里，但是颜色有可能有问题,


					}
					else //首先有叔叔，且叔叔是黑色，默认有祖父, 旋转操作
					{


					}
					

				}
				//如果parent 是黑色，结束插入

			}
			else //p->leftTree !=NIL
			{
				insert(p->leftTree, x);

			}

		}
		else //p->value < x,将x插入到p右边
		{
			if (p->rightTree == NIL)
			{
				Node *tmp = new Node();
				tmp->value = x;
				tmp->parent = p;
				tmp->leftTree = tmp->rightTree = NIL;
				p->rightTree = tmp;//新的儿子

				if (tmp->parent->color == red)
				{
					if (tmp->uncle()->color == red)//首先由叔叔，且叔叔是红色,默认有祖父
					{
						//父亲、叔叔和祖父换颜色
						tmp->parent->color = black;
						tmp->uncle()->color = black;
						tmp->grandparent()->color = red;

						//对祖父进行红红检查
						insertCheck(tmp->grandparent());


					}
					else //首先有叔叔，且叔叔是黑色，默认有祖父, 旋转操作
					{


					}


				}
				//如果parent 是黑色，结束插入
			}
			else //p->rightTree !=NIL
			{
				insert(p->rightTree, x);

			}


		}
	}


	}
	Node* root, NIL;

public:
	RedBlackTree()
	//一颗空树
	{
		NIL = new Node();
		NIL->color = black;//NIL是黑色节点,不用管value
		root = NULL;//每个变量都初始化,

	}

	void insert(T x)
	//插入x值
	{
		//判断是不是空树,
		if (root == NULL)
		{
			root = new Node();
			root->color = black;
			root->value = x;
			root->leftTree = root->rightTree = NIL;//root的parent是NULL,

		}
		else
		{
			insert(root, x);
		}

	}






};