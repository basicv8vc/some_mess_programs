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
		Node<T> *parent, *leftTree, *rightTree; //����ָ�룬�ֱ�ָ�򸸽ڵ㣬���Ҷ���

		Node() 
		{
			color = red;
			parent = leftTree = rightTree = NULL;
		}


		Node* grandparent()
		{
			if (parent != NULL)////�������⣺��ʵҲ��root��parent==NULL? root�ĺ�����grandparents��
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
		//�����Լ����ֵܽ���,�������ֵܣ���������
		{
			if (this == parent->leftTree)
				return parent->rightTree;
			else
				return parent->leftTree;

		}



	};
	
	void insertCheck(Node *p)
	//p���ɺ�ɫ���ɫ���Ƿ��ͻ
	{
		if (p->parent == NULL)//p��root,ֻ�������
			p->color = black;

		else if (p->grandparent()==NULL)//p��root�����ӣ�
		{


		}
	}

	void insert(Node *p, T x)
	//find the place, then insert x
	{
		if (p->value >= x)//p��value>=x,��x�϶����뵽p���
		{
			if (p->leftTree == NIL)
			{
				Node* tmp = new Node();
				tmp->value = x;
				tmp->parent = p;
				tmp->leftTree = tmp->rightTree = NIL;
				p->leftTree = tmp;//�µĶ���
				//��parent����ɫ,����Ǻ�ɫ����end�������������ɫ
				if (tmp->parent->color = red)//tmp->parent==p��ע��root�ڲ���ʱ���ܾ������廻ɫ����Ϊ��ɫ��Ȼ��insertCheck() ������ģ���������root�����Ǻ�ɫ,��p�Ǻ�ɫ��˵������root��tmp����grandparents,

				{
					
					if (tmp->uncle()->color == red)//���������壬�������Ǻ�ɫ,Ĭ�����游
					{
						//���ס�������游����ɫ
						tmp->parent->color = black;
						tmp->uncle()->color = black;
						tmp->grandparent()->color = red;

						//���游���к����
						insertCheck(tmp->grandparent());//insertCheck�������ǣ����Ѿ����뵽���������ɫ�п���������,


					}
					else //���������壬�������Ǻ�ɫ��Ĭ�����游, ��ת����
					{


					}
					

				}
				//���parent �Ǻ�ɫ����������

			}
			else //p->leftTree !=NIL
			{
				insert(p->leftTree, x);

			}

		}
		else //p->value < x,��x���뵽p�ұ�
		{
			if (p->rightTree == NIL)
			{
				Node *tmp = new Node();
				tmp->value = x;
				tmp->parent = p;
				tmp->leftTree = tmp->rightTree = NIL;
				p->rightTree = tmp;//�µĶ���

				if (tmp->parent->color == red)
				{
					if (tmp->uncle()->color == red)//���������壬�������Ǻ�ɫ,Ĭ�����游
					{
						//���ס�������游����ɫ
						tmp->parent->color = black;
						tmp->uncle()->color = black;
						tmp->grandparent()->color = red;

						//���游���к����
						insertCheck(tmp->grandparent());


					}
					else //���������壬�������Ǻ�ɫ��Ĭ�����游, ��ת����
					{


					}


				}
				//���parent �Ǻ�ɫ����������
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
	//һ�ſ���
	{
		NIL = new Node();
		NIL->color = black;//NIL�Ǻ�ɫ�ڵ�,���ù�value
		root = NULL;//ÿ����������ʼ��,

	}

	void insert(T x)
	//����xֵ
	{
		//�ж��ǲ��ǿ���,
		if (root == NULL)
		{
			root = new Node();
			root->color = black;
			root->value = x;
			root->leftTree = root->rightTree = NIL;//root��parent��NULL,

		}
		else
		{
			insert(root, x);
		}

	}






};