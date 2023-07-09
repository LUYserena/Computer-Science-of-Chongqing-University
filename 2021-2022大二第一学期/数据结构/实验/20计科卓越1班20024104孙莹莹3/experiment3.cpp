
#include <cmath>
#include <iostream>
#include <string>
using namespace std;

template <typename Key, typename E>
class BSTNode
{
private:
    Key k;  //node's key
    E it;   //node's value;
    BSTNode * lc;  //node's leftchild
    BSTNode * rc;   //node's rightchild

public:
    BSTNode() { lc = rc = nullptr; }

    BSTNode(Key K, E e, BSTNode *l = nullptr, BSTNode *r = nullptr)
    {
        k = K;
        it = e;
        lc = l;
        rc = r;
    }

    ~BSTNode() = default; // Destructor

    E & element() {return it;}  //get value
    void setElement(const E & e) {it = e;}  //set value

    Key & key() {return k;}  //get key
    void setKey(const Key & K) {k = K;}  //set key

    inline BSTNode * left() const {return lc;}  //find leftchild
    void setLeft(BSTNode<Key,E> *b) {lc = (BSTNode *)b;}  //set leftchild
    inline BSTNode *right() const {return rc;}  //find rightchild
    void setRight(BSTNode<Key,E>*b){rc = (BSTNode*)b;} //set rightchild

   bool isLeaf() {return (lc==NULL) && (rc==NULL);}  //whether is leaf
};


template <typename Key, typename E>
class BST
{
private:
    BSTNode<Key,E> * root;   //the root of BST
    int nodecount;  //number of nodes in BST

    void clearhelp(BSTNode<Key,E> * root) //delete root and it's descendents
    {
        if(root==NULL)  return;
        clearhelp(root->left());
        clearhelp(root->right());
        delete root;
    }

   
    BSTNode<Key,E> *inserthelp(BSTNode<Key,E>*root, const Key &k, const E & it)
    {
        if(root==NULL)
            return new BSTNode<Key,E>(k,it,NULL,NULL);
        if(root->key()>k)
            root->setLeft(inserthelp(root->left(),k,it));
        else    
            root->setRight(inserthelp(root->right(),k,it));
        return root;
    }

   
    BSTNode<Key,E> * getmin(BSTNode<Key,E> *rt)
    {
        if(rt->left()==NULL)
            return rt;
        else
            return getmin(rt->left());
    }

    
    BSTNode<Key,E> *deletemin(BSTNode<Key,E> *rt)
    {
        if(rt->left()==NULL)
            return rt->right();
        else
        {
            rt->setLeft(deletemin(rt->left()));
            return rt;
        }
    }


    BSTNode<Key,E> * removehelp(BSTNode<Key,E> *rt, const Key &k)
    {
        if(rt==NULL)
            return NULL;
        else if(k<rt->key())
            rt->setLeft(removehelp(rt->left(),k));
        else if(k>rt->key())
            rt->setRight(removehelp(rt->right(),k));
        else
        {
            BSTNode<Key,E> * temp = rt;
            if(rt->left()==NULL)
            {
                rt = rt->right();
                delete temp;
            }
            else if(rt->right()==NULL)
            {
                rt = rt->left();
                delete temp;
            }
            else
            {
                 BSTNode<Key,E> * temp = getmin(rt->right());
                rt->setElement(temp->element());
                rt->setKey(temp->key());
                rt->setRight(deletemin(rt->right()));
                delete temp;
            }
           
        }
        return rt;
    }
    
    E findhelp(BSTNode<Key,E> *root, const Key & k)
    {
        if(root==NULL)
            return (E)NULL;
        if(k<root->key())
            return findhelp(root->left(),k);
        else if(k>root->key())
            return findhelp(root->right(),k);
        else
            return root->element();
    }

   

   
    void printhelp(BSTNode<Key, E> *root, int level) const  //中序输出
    {
        if (root == nullptr)
            return;                         // Empty tree
        printhelp(root->left(), level + 1); // Do left subtree
        cout << root->key() << endl;         // Print node value
        printhelp(root->right(), level + 1); // Do right subtree
    };


public:
    BST()
    {
        root = NULL;
        nodecount = 0;
    }

    ~BST() { clearhelp(root); }
 
    void clear()
    {
        clearhelp(root);
        root = NULL;
        nodecount = 0;
    }

    
   void insert(const Key &k, const E & e)
    {
        root = inserthelp(root,k,e);
        nodecount++;
    }

   
    E remove(const Key & k)
    {
        E temp = findhelp(root,k);
        if(temp!=NULL)
        {
            root = removehelp(root,k);
            nodecount--;
        }
        return temp;
    }

   
    E find(const Key &k) const { return findhelp(root, k); }

    BSTNode<Key,E> * getroot()
    {
        return root;
    }

    
    int size() { return nodecount; }

    void print() const
    {
        if(root==NULL)
            cout << "The BST is empty" << endl;
        else
            printhelp(root, 0);
    }

   
    
};

class Location
{
public:
    int x;
    int y;
    bool isNull;

   
    Location(nullptr_t = nullptr)
    {
        x = 0x3f3f3f3f;
        y = 0x3f3f3f3f;
        isNull = true;
    }

    explicit Location(int xlo, int ylo)
    {
        x = xlo;
        y = ylo;
        isNull = false;
    }

    
    bool operator!=(Location *)
    {
        return !isNull;
    }
    int getx()
    {
        return x;
    }
    int gety()
    {
        return y;
    }

    /** 重载输出*/
    friend ostream &operator<<(ostream &out, const Location &l);
};

ostream &operator<<(ostream &out, const Location &l)
{
    out << l.x << " " << l.y;
    return out;
}

class CityDB
{
private:
    BST<string, Location> *database;

public:
    CityDB()
    {
        database = new BST<string, Location>();
    }

    ~CityDB()
    {
        delete database;
    }

    
    void insert(int number)
    {
        int i;
        string name;
        int lox,loy;
        for(int i=0; i<number; i++)
        {
            cin >> name >> lox >> loy;
            database->insert(name,Location(lox,loy));
        }

    }

    
    void runCmd(string cmd = "cmd")
    {

        if (cmd == "remove")
        { //remove
            string strBuffer;
            cin >> strBuffer;
            database->remove(strBuffer);
        }
        else if (cmd == "insert")
        { //insert
            insert(1);
        }
        else if (cmd == "selectKey")
        { //select key
            char c;
            cin >> c;
            printCaptital(database->getroot(),c);
        
        }
        else if (cmd == "selectLocation")
        {
           int p,q,dis;
            cin >> p >> q >> dis;
            printDistance(database->getroot(),p,q,dis); 
        }
        else if (cmd == "print")
        { 
            database->print();
        }
       
        else
        {
            cerr << "command is unvailable" << cmd << endl;
        }
    }

    void printCaptital(BSTNode<string,Location>*bst,char c)  //检索首字母
    {
        if(bst)
        {
            printCaptital(bst->left(),c);
            if((bst->key())[0]==c)
                cout << bst->key() << " " << bst->element().getx() << " " << bst->element().gety() << endl;
            printCaptital(bst->right(),c);
        }
    }

   void printDistance(BSTNode<string,Location>*bst, int p, int q, int dis) //检索距离
   {
       if(bst)
       {
           printDistance(bst->left(),p,q,dis);
           if((pow(((bst->element()).getx()-p)*1.0,2*1.0)+pow(((bst->element()).gety()-q)*1.0,2*1.0)) <= dis*dis*1.0)
                cout << bst->key() << " " << bst->element().getx() << " " << bst->element().gety() << endl;
            printDistance(bst->right(),p,q,dis);
       }
   }
};

int main()
{
    int N;
    cin >> N;
    CityDB mycity;
    mycity.insert(N); 
    int num;
    while(cin>>num)
    {
        if(num==1)
        {
            mycity.runCmd("insert");
        }
        else if(num==0)
        {
            mycity.runCmd("remove");
        }
        else if(num==2)
        {
            break;
        }
        
    }
    mycity.runCmd("print");
    mycity.runCmd("selectKey");
    mycity.runCmd("selectLocation");
    system("pause");

    return 0;
}

