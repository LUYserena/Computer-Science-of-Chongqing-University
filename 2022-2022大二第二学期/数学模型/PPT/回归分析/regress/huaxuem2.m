function yhat=huaxuem2(beta,x)
x1=x(:,1);x2=x(:,2);
yhat=(beta(1)+beta(3)*x2).*x1./((beta(2)+beta(4)*x2).*x1); 