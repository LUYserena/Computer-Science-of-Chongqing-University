#include <iostream>
#include <stdio.h>
#include <vector>
#include <string>
#include <cstring>
#include <fstream>
#include <sstream>
using namespace std;
ofstream outfile;

struct user
{
    string account, name, ID, key;
    int balance = 0;
    bool state;
};

class ATM
{
private:
    string account;
    string name;
    string ID;
    string password;
    int balance;
    bool state;
public:
    ATM() {};
    void showbalance() const { cout << balance;}
    void showpassword() const {cout << password;}
    void operator=(user a);
    string getaccount() const {return account;}
    string getname() const {return name;}
    string getID() const {return ID;}
    string getpassword() const {return password;}
    int getbalance() const {return balance;}
    bool getstate() const {return state;}
    void changestate(int f) {state = f;}
    void showstate();
    void changepassword();
    void drawmoney(int i);
    void savemoney(int i);
    void transfermoney(string a, int b);

};

void ATM::operator=(user a)
{
    account = a.account;
    name = a.name;
    password = a.key;
    balance = a.balance;
    state = a.state;
    ID = a.ID;

}

void ATM::showstate()
{
    if(state)
        cout << "True" << endl;
    else
        cout << "False" << endl;
}

void ATM::changepassword()
{
    cout << "Please enter your current password(3 times to lock):" << endl;
    string key;
    cin >> key;
    int times = 0;
    while(key != this->password)
    {
        cout << "wrong password, try again";
        times += 1;
        if(times == 3)
        {
            cout << "You have tried 3 times, your account locked";
            return;
        }
        cin >> key;
    }
    cout << "right password, now please enter your new password(6 digits only): " << endl;
    string newkey;
    cin >> newkey;
    while(newkey.size() != 6)
    {
        cout << "please enter only 6 digits! try again!" << endl;
        cin >> newkey;
    }
    cout << "please confirm your password: " << endl;
    string newkey1;
    cin >> newkey1;
    if(newkey == newkey1)
    {
        this->password = newkey;
        outfile << "You have changed the password of " << name <<"'s account" << endl;
        return;
    }
    else 
    {
        cout << "Password cannot match!" << endl;
        return;
    }
}


void ATM::drawmoney(int i)
{
    if(i > balance)
    {
        cout << "You have " << balance << " money only" << endl;
        cout << "withdraw fail" << endl;
        return ;
    }
    else
    {
        balance -= i;
        cout << "success! You have withdraw " << i << " money from your account" << endl;
        cout << "now you have " << balance << " money left" << endl;
        return;
    }
}

void ATM::savemoney(int i)
{
    if(i < 0)
    {
        cout << "money cannot be negative" << endl;
        return ;
    }
    else
    {
        balance += i;
        return ;
    }
}

ATM accns[100];
void regist(int i)
{
    user a;
    cout << "please enter Your account with 19 chars:  " << endl;
    cin >> a.account;
    cout << "please enter Your name: " << endl;
    getchar();
    getline(cin,a.name);
    cout << "please enter your ID:" << endl;
    cin >> a.ID;
    cout << "please set your password with 6 numbers: " << endl;
    cin >> a.key;
    cout << "please confirm your password again: " << endl;
    string b;
    cin >> b;
    if(a.key != b)
    {
        cout << "password not match, register fail" << endl;
        return;
    }
    else
    {
        a.balance = 10000;
        a.state = true;
        accns[i] = a;
        outfile << "You have registered an account " << endl;
    }
}

void split_str(char * s, int i)
{
    int j=0, k;
    user a;
    for(j=0; j<=18; j++)
        a.account = a.account + s[j];
    j = 20;
    while(s[j] != '#')
    {
        a.name = a.name + s[j];
        j += 1;
    }
    for(k=1; k<=18; k++)
        a.ID = a.ID + s[j + k];
    j = j + 19;
    for(k=1; k<=6; k++)
        a.key = a.key + s[j + k];
    j += 8;
    while (s[j] != ' ')
    {
        a.balance =  s[j] = 48 + a.balance * 10;
        j += 1;
    }
    if (s[j + 1] == 49)
        a.state = true;
    else
        a.state = false;
    accns[i] = a;
}

int read()
{
    ifstream fs_in;
    int num = 0;
    fs_in.open("account.txt");
    char info[100] = {0};
    fs_in.getline(info, 100);
    while(strlen(info) > 1)
    {
        split_str(info, num);
        num += 1;
        fs_in.getline(info, 100);
    }
    return num;
}

int plug_in(int i)
{
    string s;
    int j;
    cout << "You have entered your card" << endl;
    cout << "Your card account is:" << endl;
    cin >> s;
    for(j=0; j<i; j++)
    {
        if(accns[j].getaccount() == s)
        {
            s = accns[j].getpassword();
            break;
        }
    }
    if(j==i)
    {
        cout << "Your card haven't registered." << endl;
        return -1;
    }
    if(!accns[j].getstate())
    {
        cout << "Your card has locked. Card withdrawed" << endl;
        return -1;
    }
    cout << "Enter your password(3 times to lock): " << endl;
    string key;
    cin >> key;
    int k = 0;
    while(key != s)
    {
        k += 1;
        if(k==3)
        {
            cout << "3 times tried. Your card has been locked" << endl;
            cout << "Card withdrawed" << endl;
            outfile << accns[j].getname() << "'s card has been locked" << endl;
            accns[j].changestate(false);
            return -1;
        }
        cout << "Wrong password. Try again" << endl;
        cin >> key;
    }
    cout << "Welcome, dearly" << accns[j].getname() << endl;
    outfile << "You have Plug-in a card named " << accns[j].getname() << endl;
    return j;
}

void draw_money(int i)
{
    int money;
    cout << "how much do you want to withdraw" << endl;
    cin >> money;
    bool f = true;
    while(f)
    {
        accns[i].drawmoney(money);
        f = false;
    READ:
        if(f)
            cin >> money;
    }
    return;
}

void transfer_money(int note, int num)
{
    cout << "Please enter the account you what to tranfer to " << endl;
    string s;
    cin >> s;
    int j;
    for( j=0; j<num; j++)
    {
        if(accns[j].getaccount() == s)
            break;
    }
    if(j == num)
    {
        cout << "His or her card hasn't registered " << endl;
        return;
    }
    if(!accns[j].getstate())
    {
        cout << "His or her card has been locked. Transfer fail" << endl;
        return;
    }
    cout << "Please confirm the account you want to transfer to" << endl;
    string accoun;
    cin >> accoun;
    if(s == accoun)
    {
        cout << "You have" << accns[note].getbalance() << " money" << endl;
        cout << "enter the money you want to transfer" << endl;
        int money;
        cin >> money;
        bool f = true;
        while(f)
        {
            if(money < 0)
            {
                cout << "it cannot be negative" << endl;
                goto READ;
            }
            if(money > accns[note].getbalance())
            {
                cout << "You don't have so much money" << endl;
                cout << "try again" << endl;
                goto READ;
            }
            cout << "Transfer successully!" << endl;
            outfile << accns[note].getname() << "have transfer" << money << " $ to account named" << accns[j].getname() << endl;
            accns[note].drawmoney(money);
            accns[j].savemoney(money);
            f = false;
        READ:
            if(f)
                cin >> money;
        }
        return ;
    }
    else
    {
        cout << "Account cannot match" << endl;
        return;
    }
}

void write_infile(int num)
{
    freopen("account.txt", "w", stdout); //输出重定向
    for(int i=0; i<num; i++)
    {
        cout << accns[i].getaccount() << " " << accns[i].getname() << "#" << accns[i].getID() << " " << accns[i].getpassword() << " " << accns[i].getbalance() << " ";
        if(accns[i].getstate())
            cout << "1" <<endl;
        else
            cout << "0" << endl;
    }
    fclose(stdout);  //关闭重定向输出
}

void show()
{
    cout << "what do you want to do?" << endl;
    cout << "your account data will be reserved in account.txt, your log will be reserved in log.txt" << endl;
    cout << "Main menu:" << endl;
    cout << "     [1].register an account" <<endl;
    cout << "     [2] plug in your card" << endl;
    cout << "     [3] view my balance" << endl;
    cout << "     [4] withdraw money" << endl;
    cout << "     [5] transfer money" << endl;
    cout << "     [6] change the password" << endl;
    cout << "     [7] pop out the card" << endl;
    cout << "     [8] quit" << endl;
}

int main()
{
    outfile.open("log.txt", ios::app); //ios::app 追加模式打开文件夹， 以ios::app打开,如果没有文件，那么生成空文件；如果有文件，那么在文件尾 追加。
    int num = read();
    show();
    int choice, note = -1;
    cin >> choice;
    while(choice != 8)
    {
        if(choice == 1)  //register an account
        {
            regist(num);
            num += 1;
            cout << "what do you want to do now?";
            cin >> choice;
            continue;
        }
        if(choice == 2) //plug in the card
        {
            if(note != -1)
            {
                cout << "you cannot insert two cards at a time" << endl;
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
            else
            {
                note = plug_in(num);
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
        }
        if(choice == 3) //view the balance
        {
            if(note != -1 && accns[note].getstate())
            {
                cout << "You balance is " << accns[note].getbalance() << endl;
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
            else
            {
                cout << "You Haven't plug-in," << endl
                     << "Please Plug-in first" << endl;
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
        }
        if(choice == 4) // withdraw money
        {
            if(note != -1 && accns[note].getstate())
            {
                draw_money(note);
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
            else
            {
                cout << "You Haven't plug-in," << endl
                     << "Please Plug-in first" << endl;
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
        }
        if(choice == 5)  //transfer money
        {
            if(note != -1 && accns[note].getstate())
            {
                transfer_money(note, num);
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
             else
            {
                cout << "You Haven't plug-in," << endl
                     << "Please Plug-in first" << endl;
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
        }
        if(choice == 6) //change the password
        {
            if(note != -1 && accns[note].getstate())
            {
                accns[note].changepassword();
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
             else
            { 
                cout << "You Haven't plug-in," << endl
                     << "Please Plug-in first" << endl;
                cout << "what do you want to do" << endl;
                cin >> choice;
                continue;
            }
        }
        if(choice == 7) //pop out the card
        {
            cout << "Your card has been withdrawn." << endl;
            outfile << "card withdrawed" << endl;
            note = -1;
            cout << "what do you want to do" << endl;
            cin >> choice;
            continue;

        }
    }
    cout << "bye!" << endl;
    write_infile(num);
    system("pause");
    return 0;
}