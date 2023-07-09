y=[144	215	138	145	162	142	170	124	158	154 162	150	140	110	128	130	135	114	116	124 136	142	120	120	160	158	144	130	125	175];
x=[39	47	45	47	65	46	67	42	67	56 64	56	59	34	42	48	45	18	20	19 36	50	39	21	44	53	63	29	25	69];
n=length(y);
X=[ones(n,1)  x'];
[b,bint,r,rint,s]=regress(y',X);
b,bint,s,s2=sum(r.^2)/(n-2)
rcoplot(r,rint)
pause
y=[y(1) y(3:30)];
x=[x(1) x(3:30)];
n=length(y);
X=[ones(n,1)  x'];
[b,bint,r,rint,s]=regress(y',X);
b,bint,s,s2=sum(r.^2)/(n-2)
rcoplot(r,rint)
pause
y0=b(1)+b(2)*50;              % 预测y(x=50)
xb=mean(x);
sxx=sum((x-xb).^2);
a=sqrt((50-xb)^2/sxx+1/n+1);
t=tinv(0.975,n-2);
d=t*a*sqrt(s2);
y1=y0-d;y2=y0+d;             % 预测y(x=50)区间（t分布）
[y0 y1 y2]
d1=norminv(0.975)*sqrt(s2);
y3=y0-d1;y4=y0+d1;
[y0 y3 y4]                   % 预测y(x=50)区间（N分布）