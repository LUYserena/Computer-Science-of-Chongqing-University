n=10;
x1=[120 140 190 130 155 175 125 145 180 150];
x2=[100 110  90 150 210 150 250 270 300 250];
y =[102 100 120  77  46  93  26  69  65  85];
x=[ones(n,1) x1' x2'];
[b,bi,r,ri,s]=regress(y',x);
s2=sum(r.^2)/(n-2);
b,bi,s,s2
x=[x1 ; x2]';
pause
rstool(x,y','purequadratic')
