import numpy as np
import matplotlib.pyplot as plt

# 目标函数:y=x^2
def func(x):
    return np.square(x)


# 目标函数一阶导数:dy/dx=2*x
def dfunc(x):
    return 2 * x

# Gradient Descent
def GD(x_start, df, epochs, lr):
    """
    梯度下降法。给定起始点与目标函数的一阶导函数，求在epochs次迭代中x的更新值
    :param x_start: x的起始点
    :param df: 目标函数的一阶导函数
    :param epochs: 迭代周期
    :param lr: 学习率
    :return: x在每次迭代后的位置（包括起始点），长度为epochs+1
    """
    xs = np.zeros(epochs+1)
    x = x_start
    xs[0] = x
    for i in range(epochs):
        dx = df(x)
        # v表示x要改变的幅度
        v = - dx * lr
        x += v
        xs[i+1] = x
    return xs

def demo1_GD_lr():
    # 函数图像
    line_x = np.linspace(-5, 5, 100)
    line_y = func(line_x)
    plt.figure('Gradient Desent: Learning Rate')

    x_start = -5
    epochs = 5

    lr = [0.1, 0.3, 0.9]

    color = ['r', 'g', 'y']
    size = np.ones(epochs+1) * 10
    size[-1] = 70
    for i in range(len(lr)):
        x = GD(x_start, dfunc, epochs, lr=lr[i])
        plt.subplot(1, 3, i+1)
        plt.plot(line_x, line_y, c='b')
        plt.plot(x, func(x), c=color[i], label='lr={}'.format(lr[i]))
        plt.scatter(x, func(x), c=color[i])
        plt.legend()
    plt.show()
demo1_GD_lr()
