
import jieba
import re


# 待替代符号
punc = "\n ！？｡＂＃《》＄％＆＇（）＊＋－／：；＜＝＞＠［＼］＾＿｀｛｜｝～｟｠｢｣〃》「」『』【】〔〕〖〗〘〙〚〛〜〝〞〟〰〾〿–—‘’‛“”„‟…‧﹏#$%&'()*+-/;<=>?@[\]^_`{|}~“”？！【】（）。’‘……￥"


def Pretreatment(file_path):
    intab = "０１２３４５６７８９"
    outtab = "0123456789"
    trantab = str.maketrans(intab, outtab)  # 将所有的in字符与对应的out字符建立映射
    f1 = open("filter.txt",
              "w", encoding='utf-8')  # 构造过滤后的文件
    for line in open(file_path, encoding='gbk'):
        new_line = re.sub(
            r" |/t|/n|/m|/v|/u|/a|/w|/q|r|t|/k|/f|/p|n|/c|s|/|d|i|b|l|j|e|v|g|N|V|R|T|y|o|A|D|h|z|x|A|B|M|a|Y|\d{8}-\d{2}-\d{3}-\d{3}", "", line)
        new_line = new_line.translate(trantab)  # 将所有的in字符用对应的out字符替代
        f1.write(new_line)
    f1.close()


def preDiv(filename):
    listSen = []
    with open(filename, 'r', encoding='UTF-8') as f:
        for line in f.readlines():
            listTmp = list(jieba.cut(line))
            listSen.append("BOS")
            listSen.extend(listTmp)
            listSen.append("EOS")
    f.close()
    return listSen


def statistic(lists):
    dictSen = {}
    for word in lists:
        if word not in punc:
            if word not in dictSen:
                dictSen[word] = 1
            else:
                dictSen[word] += 1
    dictSen = sorted(dictSen.items(), key=lambda x: x[1], reverse=False)
    dictSen = dict(dictSen)
    dictLen = len(dictSen)
    return dictSen, dictLen


def cutWord(sen):
    cutResult = list(jieba.cut(sen))
    tmp = []
    for word in cutResult:
        if word not in punc:
            tmp.append(word)
    cutResult = tmp
    cutResult.insert(0, "BOS")
    cutResult.append("EOS")
    print("分词结果为：{}\n".format(cutResult))
    return cutResult


def biGram(listSen, dicSen, dicLen, cutResult):
    fenzi = [0]*(len(cutResult)-1)
    fenmu = []
    for i in range(1, len(cutResult)):
        # 计算分母
        if cutResult[i-1] in dicSen:
            fenmu.append(dicSen[cutResult[i-1]])
        else:
            fenmu.append(0)
        # 计算分子
        for j in range(len(listSen)):
            if (listSen[j] == cutResult[i-1]) and listSen[j+1] == cutResult[i]:
                fenzi[i-1] += 1

    end = len(cutResult) - 1;
    timesDict = {}
    for key, value in dicSen.items():
        if key[0] == cutResult[end - 1]:
            if len(key)==2:
                timesDict[key[1]] = value

    newfenmu = fenmu
    newfenzi = fenzi
    timesDict = sorted(timesDict.items(), key = lambda kv:(kv[1], kv[0]),reverse=True)
    count = 0
    for word in timesDict:
        key = word[0]
        count+=1
        if count == 6:
            break
        k = 1
        b = k * (dicLen+1)

        if key in dicSen:
            newfenmu.append(dicSen[key])
        else:
            newfenmu.append(0)
        newfenzi.append(1)
        for j in range(len(listSen) - 1):
            if (listSen[j] == cutResult[end]) and listSen[j + 1] == key:
                newfenzi[end+1] += 1
        # 使用加法数据平滑
        newfenzi = [i + k for i in newfenzi]
        newfenmu = [i + b for i in newfenmu]

        print("下一个字可能为 %s" % key)
        result = 1.0
        for s in range(len(newfenzi)):
            if newfenmu[s] != 0:
                tmp = newfenzi[s] / newfenmu[s]
                result *= tmp
        print("概率为 {}\n".format(result))
        # print(result)


if __name__ == "__main__":
    Pretreatment("训练语料.txt")
    filename = "filter.txt"
    listSen = preDiv(filename)
    sen1 = "鲁迅的一生有着了不起的作"
    sen3 = "我们伟大的祖。"
    sen4 = "坚持中国共产党的领"
    testData = [sen1, sen3, sen4]
    cnt = 0  # 计数
    dictSen, dictLen = statistic(listSen)
    for ss in testData:
        print("Sentence {}: \n".format(ss))
        cnt += 1
        cutResult = cutWord(ss)
        biGram(listSen, dictSen, dictLen, cutResult)
