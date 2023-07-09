 #include<iostream>
using namespace std;

const int defaultSize = 50;
//队列实现
template<typename E>
class Queue
{
private:
    void operator=(const Queue & ) {}
    Queue(const Queue&) {}
public:
    Queue() {}
    virtual ~Queue() {}
    virtual void enqueue(const E &) = 0;
    virtual E dequeue() = 0;
    virtual void clear() = 0;
    virtual const E & frontValue() const = 0;
    virtual int length() const = 0;
    virtual bool full() const = 0;
    virtual bool empty() const = 0;
};

//顺序队列
template<typename E>
class AQueue: public Queue<E>
{
private:
    int maxSize;
    int front;
    int rear;
    E * listArray;

public:
    AQueue(int size = defaultSize)
    {
        maxSize = size + 1;
        rear = 0;
        front = 1;
        listArray = new E [maxSize];
    }

    ~AQueue() {delete [] listArray;}

    void clear() {rear = 0; front = 1;}

    void enqueue(const E & it)
    {
		if(((rear+2)%maxSize) ==front)
		{
			cout << "Queue is full" << endl;
			return;
		}
        rear = (rear + 1) % maxSize;
        listArray[rear] = it;
    }

    E dequeue()
    {
		if(length()==0)
		{
			cout << "Queue is empty" << endl;
			return -10000;
		}
        E it = listArray[front];
        front = (front+1) % maxSize;
        return it;
    }

    const E & frontValue() const{
        if(length()==0)
		{
			cout << "Queue is empty" << endl;
			return defaultSize;
		}
        return listArray[front];
    }

    int length() const{
        return ((rear+maxSize)-front+1) % maxSize;
    }

    bool full() const
    {
        return ((rear+2)%maxSize==front);
    }

    bool empty() const
    {
        return (front==(rear+1)%maxSize);
    }
};

//栈实现
template<typename E>
class Stack
{
private:
    void operator=(const Stack&) {}
    Stack(const Stack &) {}
public:
    Stack() {}
    virtual ~Stack() {}

    virtual void clear() = 0;
    virtual void push(const E & it) = 0;
    virtual E pop() = 0;
    virtual const E& topValue() const = 0;
    virtual int length() const = 0;
    
};


template <typename E>
class QStack : public Stack<E>{
private:
    int maxSize; //栈的容量
    AQueue<E> QA;
    AQueue<E> QB; //基于数组实现的队列
public:
     QStack(int size = defaultSize): QA(size), QB(size) //初始化队列
     {
        maxSize = size;
     }
     ~QStack() { }
               //完成下列函数的代码
     void clear(){ 
         while(!QA.empty())
         {
             QA.dequeue();
         }
         maxSize = 0;
    }
     void push(const E& it) { 
         if(!QA.full())
         {
             while(!QA.empty())
            {
                E temp = QA.dequeue();
                QB.enqueue(temp);
            }
            QA.enqueue(it);
            while(!QB.empty())
            {
                E temp = QB.dequeue();
                QA.enqueue(temp); 
            }
        }
      }
     E pop() {  
         int s = QA.frontValue();
         QA.dequeue();
         return s;
      }
     const E &topValue() const { 
         return QA.frontValue();
      }
     virtual int length() const { 
         return QA.length();
      }
   
    void check(int num) {   //验证出栈顺序是否有效
        AQueue<E> temp(num);
        int value;
        //读取待测序列
        for(int i=0; i<num; i++)
        {
            cin >> value;
            temp.enqueue(value);
        }
        int curr = 1;
        while(curr<=num)
        {
            while(curr<=temp.frontValue())
            {
                if(length() < maxSize)
                {
                    push(curr);
                    curr++;
                }
                else
                {
                    if(length()==maxSize)
                    {
                        if(curr==temp.frontValue())
                        {
                            //push(curr);
                            curr++;
                            break;
                        }
                        else
                        {
                            cout << "F" << endl;
                            return;
                        }
                    }
                }
            }
            for(int i=1; i<=length();i++)
            {
                if(topValue()==temp.frontValue())
                {
                    pop();
                    temp.dequeue();
                }
            }
        }
        cout << "T" << endl;
        return;
    }
};



int main()
{
    
    int N;  //入栈的最大整数
    int K;  //栈存储整数的数量上限
    int M;  //共有m行序列需要检测
    cin >> N >> K >> M;
    QStack<int> trys(K);
    for(int i=0; i<M; i++)
    {
        trys.check(N);
    }

    system("pause");
    return 0;
}
 
 
 

