#include <iostream>
#include <iomanip>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <dirent.h>
using namespace std;

class godd
{
public:
    virtual void doit() = 0;
};

class modd : public godd    //创建文件夹
{
private:
    vector<string> mycommandArgs;

public:
    modd(vector<string> commandArgs)
    {
        this->mycommandArgs = commandArgs;
    }
    virtual void doit()
    {
        if (mycommandArgs.size() < 2)
        {
            cout << "error: please enter the name of the folder" << endl;
        }
        else
        {
            string dirName = mycommandArgs[1];
            mode_t mode = 0775;
            if (mkdir(dirName.c_str()) == 0)
            {
                cout << "succeed in creating the folder‘" << dirName << "’" << endl;
            }
            else
            {
                cout << "error:fail in creating the folder‘" << dirName << "’" << endl;
            }
        }
    }
};
class rodd : public godd     //删除文件夹
{
private:
    vector<string> mycommandArgs;

public:
    rodd(vector<string> commandArgs)
    {
        this->mycommandArgs = commandArgs;
    }
    virtual void doit()
    {
        if (mycommandArgs.size() < 2)
        {
            cout << "error:please enter the name of the folder" << endl;
        }
        else
        {
            string dirName = mycommandArgs[1];
            if (rmdir(dirName.c_str()) == 0)
            {
                cout << "succeed in deleting the folder‘" << dirName << "’" << endl;
            }
            else
            {
                cout << "error:fail in deleting the folder‘" << dirName << "’" << endl;
            }
        }
    }
};
class roff : public godd   //删除文件
{
private:
    vector<string> mycommandArgs;

public:
    roff(vector<string> commandArgs)
    {
        this->mycommandArgs = commandArgs;
    }
    virtual void doit()
    {
        if (mycommandArgs.size() < 2)
        {
            cout << "error: please enter the name of the file" << endl;
        }
        else
        {
            string fileName = mycommandArgs[1];
            if (remove(fileName.c_str()) == 0)
            {
                cout << "succeed in deleting the file‘" << fileName << "’" << endl;
            }
            else
            {
                cout << "error: fail in deleting the file‘" << fileName << "’" << endl;
            }
        }
    }
};
class codd : public godd      //更改目录
{
private:
    vector<string> mycommandArgs;

public:
    codd(vector<string> commandArgs)
    {
        this->mycommandArgs = commandArgs;
    }
    virtual void doit()
    {
        if (mycommandArgs.size() < 2)
        {
            cout << "error: please enter the name of the folder" << endl;
        }
        else
        {
            string dirName = mycommandArgs[1];
            if (chdir(dirName.c_str()) == 0)
            {
                char curDir[300];
                getcwd(curDir, 300); // getcwd()会将当前工作目录的绝对路径复制到参数curDir所指的内存空间中。
                cout << "has change the working catalogue to： "<< curDir << endl;
            }
            else
            {
                cout << "error: failure in changeing the working catalogue" << endl;
            }
        }
    }
};
class lodd : public godd  //列出文件夹内容
{
private:
    vector<string> mycommandArgs;

public:
    lodd(vector<string> commandArgs)
    {
        this->mycommandArgs = commandArgs;
    }
    virtual void doit()
    {
        DIR *curDir;
        struct dirent *ent; //目录项结构体
        struct stat entStat; //文件属性结构体
        if ((curDir = opendir(".")) == NULL)   
        {
            cout << "error: failure in opening the folder" << endl;
        }
        else
        {
            while ((ent = readdir(curDir)) != NULL) //读取目录函数
            {
                if (stat(ent->d_name, &entStat) == 0)    //定义函数：int stat(const char * file_name, struct stat *buf);
                {                                         // 函数说明：stat()用来将参数file_name 所指的文件状态, 复制到参数buf 所指的结构中。
                    if (entStat.st_mode & S_IFDIR)        //S_IFDIR判断一个路径是否为目录
                    {
                        cout << setw(6) << left << "d";
                        cout << right << setw(10) << entStat.st_size << left << setw(8) << " bytes";
                        cout << ent->d_name << "/" << endl;
                    }
                    else if (entStat.st_mode & S_IFREG)
                    {
                        cout << setw(6) << left << "-";
                        cout << right << setw(10) << entStat.st_size << left << setw(8) << " bytes";
                        cout << ent->d_name << endl;
                    }
                }
            }
            closedir(curDir);
        }
    }
};
class copp : public godd
{
private:
    vector<string> mycommandArgs;

public:
    copp(vector<string> commandArgs)
    {
        this->mycommandArgs = commandArgs;
    }
    virtual void doit()
    {
        if (mycommandArgs.size() < 3)
        {
            cout << "error: please enter the name of the source document/folder and the name of the target document/folder. " << endl;
        }
        else
        {
            string sourceFileName = mycommandArgs[1];
            string targetFileName = mycommandArgs[2];
            ifstream sourceFile(sourceFileName.c_str(), ios::binary);
            ofstream targetFile(targetFileName.c_str(), ios::binary);
            targetFile << sourceFile.rdbuf();    // rdbuf()可以实现一个流对象指向的内容用另一个流对象来输出
            targetFile.close();
            sourceFile.close();
            cout << "copy successfully" << endl;
        }
    }
};

vector<string> split_str(string s)
{
    vector<string> tokens;
    istringstream iss(s);      //istringstream类用于执行C++风格的串流的输入操作。
    do                             //istringstream::istringstream(string str);它的作用是从string对象str中读取字符。
    {
        string sub;        
        iss >> sub;
        tokens.push_back(sub);
    } while (iss);

    tokens.erase(tokens.end() - 1); // 删除最后的换行回车符

    return tokens;
}
void handle(vector<string> commandss);
void show();

int main()
{
    show();
    string command;
    vector<string> commandArgs;
    getline(cin, command);
    commandArgs = split_str(command);
    while (commandArgs.size() > 0 && "q" != commandArgs[0])
    {
        handle(commandArgs);
        cout << "?";
        getline(cin, command);
        commandArgs = split_str(command);
    }
    return 0;
}

void handle(vector<string> commandArgs)
{
    if ("rf" == commandArgs[0])
    {
        roff p(commandArgs);
        godd *q = &p;
        q->doit();
    }
    else if ("md" == commandArgs[0])
    {
        modd p(commandArgs);
        godd *q = &p;
        q->doit();
    }
    else if ("rd" == commandArgs[0])
    {
        rodd p(commandArgs);
        godd *q = &p;
        q->doit();
    }
    else if ("ld" == commandArgs[0])
    {
        lodd p(commandArgs);
        godd *q = &p;
        q->doit();
    }
    else if ("cd" == commandArgs[0])
    {
        codd p(commandArgs);
        godd *q = &p;
        q->doit();
    }
    else if ("cp" == commandArgs[0])
    {
        copp p(commandArgs);
        godd *q = &p;
        q->doit();
    }
    else
    {
        cout << "error: flase command。" << endl;
        return;
    };
}

void show()
{
   cout << "===================================" << endl;
    cout << " welcome to the Doszip Commander   ver: 1 by LUY" << endl;
    cout << "==============================================" << endl;
    cout << "plese enter with： ? xx1 xx2 …… xxN" << endl;
    cout << "   ? means what you want to do，xx1~xxN means the object you want to work with" << endl;
    cout << "\ncommands are as followed：" << endl;
    cout << "   md  create a folder             rd  delete a folder" << endl;
    cout << "   cd  change the working folder   ld  list the folder" << endl;
    cout << "   cp  copy the document/folder    rf  delete the document" << endl;
    cout << "   q   quit\n"
         << endl;

    cout << "?";
}