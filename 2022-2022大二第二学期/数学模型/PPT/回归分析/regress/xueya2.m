y=[144	215	138	145	162	142	170	124	158	154 162	150	140	110	128	130	135	114	116	124 136	142	120	120	160	158	144	130	125	175];
x1=[39	47	45	47	65	46	67	42	67	56 64	56	59	34	42	48	45	18	20	19 36	50	39	21	44	53	63	29	25	69];
x2=[24.2 31.1 22.6 24.0 25.9 25.1 29.5 19.7 27.2 19.3 28.0 25.8 27.3 20.1 21.7 22.2 27.4 18.8 22.6 21.5 25.0 26.2 23.5 20.3 27.1 28.6 28.3 22.0 25.3 27.4];  
x3=[0   1   0   1   1   0   1   0   1   0   1   0   0   0   0   1    0    0   0   0   0    1    0    0    1    1     0     1     0     1];
n=length(y);
X=[ones(n,1), x1',x2',x3'];
[b,bint,r,rint,s]=regress(y',X);
s2=sum(r.^2)/(n-4);
b,bint,s,s2
rcoplot(r,rint)
pause
y=[y(1) y(3:9) y(11:30)];           % 剔除两个异常数据
x1=[x1(1) x1(3:9) x1(11:30)];
x2=[x2(1) x2(3:9) x2(11:30)];
x3=[x3(1) x3(3:9) x3(11:30)];
n=length(y);
X=[ones(n,1), x1',x2',x3'];
[b,bint,r,rint,s]=regress(y',X);
s2=sum(r.^2)/(n-4);
b,bint,s,s2
rcoplot(r,rint)
pause
y0=[1,50,25,1]*b;                   %  预测值
x11=x1-mean(x1);x22=x2-mean(x2);x33=x3-mean(x3);
XX=[x11',x22',x33'];L=inv(XX'*XX);
x=[50,25,1];xb=[mean(x1),mean(x2),mean(x3)];
a=sqrt((x-xb)*L*(x-xb)'+1/n+1);
t=tinv(0.975,n-2);
d=t*a*sqrt(s2);
y1=y0-d;y2=y0+d;
yt=[y1,y0,y2]                    %  预测区间（t分布）              
dd=norminv(0.975)*sqrt(s2);
y3=y0-dd;y4=y0+dd;
yn=[y3,y0,y4]                    %  预测区间（N分布）

