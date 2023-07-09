#include<iostream>
#include<cmath>
using namespace std;

template<typename E>
void swap(E * a, int i, int j)
{
    E temp = a[i];
    a[i] = a[j];
    a[j] = temp;
    return;
}

int gcd(int a, int b)
{
    if(a % b == 0)
        return b;
    return gcd(b, a%b);
}

class Rational{
friend bool operator<(const Rational& r1, const Rational& r2) 
{  return r1.N * r2.D < r1.D * r2.N;}
friend bool operator<=(const Rational& r1, const Rational& r2) 
{   return r1.N * r2.D <= r1.D * r2.N;}
friend bool operator>(const Rational& r1, const Rational& r2) 
{   return r1.N * r2.D > r1.D * r2.N;}
friend bool operator>=(const Rational& r1, const Rational& r2) 
{   return r1.N * r2.D >= r1.D * r2.N;}
friend bool operator==(const Rational& r1, const Rational& r2) 
{   return r1.N * r2.D == r1.D * r2.N;}
friend bool operator!=(const Rational& r1, const Rational& r2) 
{   return r1.N * r2.D != r1.D * r2.N;}

public:
   int N; //分子
   int D; //分母， 要求大于0
   
   Rational() {}  //default constructor
   Rational(int n){ N=n; D=1;} //constructor for integer value
   Rational(int n, int d) 
   {
        if(n==0)
        {
            N = n;
            D = d;
        }
        else 
        {
            int c;
            c = gcd(abs(n),d);
            N = n/c;
            D = d/c;
        }
    } //normal constructor
   Rational(const Rational& r){N=r.N; D=r.D;} //copy constructor
   Rational& operator=(const Rational& r) {N=r.N; D=r.D; return *this;} // assignment override

    void printhelp()
    {
        if(N==0)
            cout << 0;
        else if(D==1)
            cout << N;
        else    
            cout << N << "/" << D;
        return;
    }
};

template<typename E>
void printhh(E a)
{
    a.printhelp();
}

//最小堆的实现
template<typename E>
class heap
{
private:
    E * Heap;
    int maxsize;
    int n;

    void shifdown(int pos)  //helper to put element at its correct position
    {
        while(!isLeaf(pos))
        {
            int j = leftchild(pos);
            int rc = rightchild(pos);
            if((rc < n) && (Heap[rc]<Heap[j]))
                j = rc;
            if(Heap[pos]<Heap[j])
                return;
            swap(Heap,pos,j);
            pos = j;
        }
    }

public:
    heap(E *h, int num, int max)
    {Heap = h; n = num; maxsize = max; buildHeap();}
    
    int size() const
    {return n;}

    bool isLeaf(int pos) const
    {return (pos>=n/2)&&(pos<n);}

    int leftchild(int pos) const
    {return 2*pos+1;}

    int rightchild(int pos) const
    {return 2*pos+2;}

    int parent(int pos) const
    {return (pos-1)/2;}

    void buildHeap()
    {
        for(int i=n/2-1; i>=0; i--)
        shifdown(i);
    }

    void insert(const E & it)
    {
        if(n>=maxsize)
        {
            cout << "Heap is full";
            return;
        }
        int curr = n++;
        Heap[curr] = it;
        while((curr!=0) && (Heap[curr] < Heap[parent(curr)]))
        {
            swap(Heap,curr,parent(curr));
            curr = parent(curr);
        }
    }

    E removefirst()
    {
        if(n<=0)
        {
            cout << "Heap is empty";
            return NULL;
        }
        swap(Heap,0,--n);  //Swap the first with last value
        if(n!=0)
            shifdown(0);
        return Heap[n];
    }

    E remove(int pos)
    {
        if(pos<0 || pos>=n)
        {
            cout << "Bad position";
            return NULL;
        }   
        if(pos==(n-1))
            n--;
        else
        {
            swap(Heap,pos,--n);
            while((pos!=0) && (Heap[pos] < Heap[parent(pos)]))
            {
                swap(Heap,pos,parent(pos));
                pos = parent(pos);
            }
            if(n!=0)
                shifdown(pos);

        }
        return Heap[n];
    }

    void printheap() {           //print Heap
        for (int i = 0; i < n; i++) {
            printhh(Heap[i]);
            cout << "  ";
        }
        return;
    }

    void heapsort(int n)
    {
        E maxval;
        for(int i=0; i<n; i++)
        {
            maxval = this->removefirst();
        }
        return;
    }
};






int main()
{
    int T;
    cin >> T;
    Rational compute[10];
    for(int i=0; i<T; i++)
    {
        int son;
        int mum;
        cin >> son >> mum;
        compute[i] = Rational(son,mum);
    }
    
    heap<Rational> tryone(compute,T,T);
    tryone.printheap();
    cout << endl;
    tryone.heapsort(T);
    for(int i=T-1; i>=0; i--)
    {
        compute[i].printhelp();
        if(i==0)
            cout << endl;
        else    
            cout << "  ";
    }
    
    system("pause");
    return 0;
}