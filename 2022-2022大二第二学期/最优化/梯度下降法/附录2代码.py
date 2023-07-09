#导入相关依赖包
import math
import numpy as np
from matplotlib import pyplot as plt
plt.rcParams['font.sans-serif'] = ['SimHei'] #用来正常显示中文标签
plt.rcParams['axes.unicode_minus'] = False #用来正常显示负号


#函数定义
def f(x):
    return 1/4*pow(x,4)-7/3*pow(x,3)+7*pow(x,2)-8*x
#函数求导
def f1(x):
    return pow(x,3)-7*pow(x,2)+14*x-8

#函数作图
def drawing():
    x = np.arange(0, 5, 0.01)
    plt.xlabel('x')
    plt.ylabel('y')
    plt.title("函数图像")
    plt.plot(x, f(x))
#最速下降法
#x0 表示初始化
#err 表示误差
#learn 表示学习率
#用一个list把每次找的x和对应的序号记录
xList=[]
countList=[]
def MaxSpeed(x0,err,learn):
    count=0
    xList.append(x0)
    countList.append(count)
    while(count<=100):
        x1=x0-learn*f1(x0)
        xList.append(x1)
        countList.append(count+1)
        if(abs(f1(x0))<=err):
            print("最终迭代结果为%.3f"%(x1))
            print("与最终值得绝对误差为%.3f"%(abs(x1-4)))
            return x1
        else:
            x0=x1
        count=count+1
    print("迭代次数达到，但仍然没有找到")
    print("最终迭代结果为%.3f"%(x1))
    print("与最终值得绝对误差为%.3f"%(abs(x1-4)))
#设置不同参数即可
MaxSpeed(2.5,0.00001,0.4)
plt.plot(countList,xList,color='r')
plt.axhline(y=4, ls='--', c='blue') # 添加水平线
x = np.arange(0, 5, 0.01)
plt.show()