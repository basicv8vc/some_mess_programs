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

	void colorCheck(Node *p)
	//插入一个Node，红色，看看是不是要调整RedBlackTree结构
	{
		//两个边界情况：p是root p是root儿子
		if (p->parent == NULL)
		{
			p->color = black;
			return;
		}
		if (p->grandparent() == NULL)
		{
			return;
		}

		//看p的父节点，如果是黑色，不用操作，如果是红色，看叔叔
		if (p->parent->color == red)
		{
			//看叔叔
			if (p->uncle()->color == red)//父辈和祖父换颜色，然后对祖父colorCheck()
			{
				p->parent->color = black;
				p->uncle()->color = black;
				p->grandparent()->color = red;

				colorCheck(p->grandparent());
			}
			else //叔叔是黑色，旋转吧小摩托
			{
				//一共四种情况:左左，右右，左右，右左.从祖父-父节点-p 的排序方式分为这四种情况,
				if ((p->parent == p->grandparent()->leftTree) &&
					(p==p->parent->leftTree)                )//左左
				{
					//只需要右旋转一次,以p->grandparent()为root
					leftRotation(p->grandparent());

				}
				else if ((p->parent == p->grandparent()->leftTree) &&
					(p == p->parent->rightTree))//左右
				{
					//旋转两次，先以p->parent为root左旋，再以p->grandparent()为root，右旋
					leftRotation(p->parent);
					rightRotation(p->grandparent());
				}
				else if ((p->parent == p->grandparent()->rightTree) &&
					(p == p->parent->rightTree))//右右
				{
					//只需要一次左旋，以p->grandparent()为root
					leftRotation(p->grandparent());
				}
				else
				{
					//旋转两次，先以p->parent为root右旋，再以p->grandparent()为root，左旋
					rightRotation(p->parent);
					leftRotation(p->grandparent());
				}

			}
		}



	}

	void leftRotation(Node *p)
	//左旋转,以p为root, 最多共需设置6个指针（p可能是root，有的可能是NIL,最少4个指针设置）,
	//这六个指针(三组) 的设置顺序再看看
	{

		//先看p是不是root,这涉及到p->parent的变化，如果p是root,就不用管p->parent
		if (p->parent != NULL)
		{
			//判断p是父节点的左孩子还是右孩子, 设置p右孩子的父节点
			if (p == p->parent->leftTree)
			{
				p->rightTree->parent = p->parent;
				p->parent->leftTree = p->rightTree;
			}
			else
			{
				p->rightTree->parent = p->parent;
				p->parent->rightTree = p->rightTree;
			}

		}
		else
		{
			p->rightTree->parent = NULL;
		}

		p->parent = p->rightTree;//设置p的父节点

		if (p->rightTree->leftTree != NIL)
		{
			p->rightTree->leftTree->parent = p;
			p->rightTree = p->parent->leftTree;

		}
		else
		{
			p->rightTree = p->parent->leftTree;
		}


		p->parent->leftTree = p;


	}

	void rightRotation(Node *p)
	//右旋转和左旋转，写出一个，另一个就出来了，
	{
		if (p->parent != NULL)
		{
			//判断p是父节点的左孩子还是右孩子
			if (p == p->parent->leftTree)
			{
				p->leftTree->parent = p->parent;
				p->parent->leftTree = p->leftTree;
			}
			else
			{
				p->leftTree->parent = p->parent;
				p->parent->rightTree = p->leftTree;
			}
		}
		else
		{
			p->leftTree->parent = NULL;
		}
		p->parent = p->leftTree;
		if (p->leftTree->rightTree != NIL)
		{
			p->leftTree->rightTree->parent = p;
			p->leftTree = p->parent->rightTree;
		}
		else
		{
			p->leftTree = p->parent->rightTree;
		}
		p->parent->rightTree = p;


	}
	void insert(Node *p, T x)
	//find the place, then insert x
	{
		if (p->value >= x)//p的value>=x,将x插入到p左边
		{
			if (p->leftTree == NIL)//找到x的位置，插入
			{
				Node* tmp = new Node();
				tmp->value = x;
				tmp->parent = p;
				tmp->leftTree = tmp->rightTree = NIL;
				p->leftTree = tmp;//新的儿子
				colorCheck(tmp);

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
				colorCheck(tmp);
			}
			else //p->rightTree !=NIL
			{
				insert(p->rightTree, x);

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
