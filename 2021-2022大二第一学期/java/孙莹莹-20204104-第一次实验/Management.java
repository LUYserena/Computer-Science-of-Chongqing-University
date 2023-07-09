package management;

import java.util.Random;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Iterator;

public class Management {
	
	public static Department myDepartment[] = new Department[5];
	
	public static void main(String args[])
	{
	    Employee myEmployee[] = new Employee[100];
		String namedepart[] = {"Sale", "HR", "Finance", "Law", "Tech"};  //部门
		String manager[] = {"Serena", "Tom", "Lisa", "Jhon", "Sally"};   //部门负责人
		String sexes[] = {"male","female"};
		Map<String, Integer> Query = new HashMap<String, Integer>();     //ͨ用字典来查询员工业绩
		for(int i=0; i<5; i++)        // 对部门进行初始化
		{
			int nums[] = {25,22,18,25,10};
			myDepartment[i] = new Department(i,namedepart[i],nums[i],manager[i],nums[i]); 
		}
		int index = 0;             
		for(int i=0; i<5; i++)              // 初始化员工
		{
			
			for(int j=0;j<myDepartment[i].getTotalworkers();j++)
			{
				Random rnd = new Random();
				int salary = rnd.nextInt(30000);   //产生随机Salary
				int bonus = rnd.nextInt(1000);     //产生随机Bonus
				int allowance = rnd.nextInt(200);  //产生随机Allowance
				Performance me = new Performance(salary,bonus,allowance);
				String tempname = getRandomString(5);  //产生随机名字   
				myEmployee[index] = new Employee(index+1,tempname,sexes[index%2],me,myDepartment[i]); //根据上述信息初始化员工
				myEmployee[index].showinformation();
				Query.put(myEmployee[index].getName(), myEmployee[index].getSalary());    // 将名字和业绩放入字典中
				myDepartment[i].addWorkers(myEmployee[index]);
				index++;
				
			}
		}
		for(int i=0;i<5;i++)  //按业绩排序输出部门员工
		{
			myDepartment[i].showworkerbyPer();
		} 
		
	
		Arrays.sort(myEmployee); //对所有员工按业绩进行排序
		for(Employee e: myEmployee)
		{
			e.showinformation();
		} 
		
		//用字典根据名字查询业绩                                                 
		int output = Query.get(myDepartment[4].getemployee(1).getName());
		System.out.println("The " + myDepartment[4].getemployee(1).getName() + "'s salary is " + output + " " + " = " + myDepartment[4].getemployee(1).getSalary());
	}
	
	public static String getRandomString(int length){  //生成随机字符串
	     String str="abcdefghijklmnopqrstuvwxyz";
	     String begin = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();                                                                                                                                                                                                                                             
	     int num =  random.nextInt(26);
	     sb.append(begin.charAt(num));
	     for(int i=1;i<length;i++){
	       int number= random.nextInt(26);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
}
};

//员工类
class Employee implements Comparable 
{
	private int id;
	private String name;
	private String sex;
	private Performance monthlysalary;
	private Department flat;
	
	public Employee(int m_id, String m_name, String m_sex, Performance m_salary, Department m_department)
	{
		id = m_id;
		name = m_name;
		sex = m_sex;
		monthlysalary = m_salary;
		flat = m_department;
	}
	
	public Employee()
	{
		id = 0;
		name = "Default";
		sex = "male";
		monthlysalary = new Performance();
		flat = new Department();
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	public String getSex()
	{
		return sex;
	}
	public String getDepartment()
	{
		return flat.getDepartmentName();
	}
	public int getSalary()
	{
		return monthlysalary.getTotalsalary();
	}
	public void showinformation()
	{
		System.out.println("Department: " + getDepartment() + " Name:  " + name +" Id: "+ id +" Sex: "+sex + "  Salary: "+ getSalary());
	}
	
	public int compareTo(Object o)  //重写比较方法，按业绩从高到低排序；
	{
		if(o instanceof Employee)
		{
			Employee e = (Employee) o;
			if(this.getSalary() > e.getSalary())
				return -1;
			else
				return 1;
		}
		return 0;
	}
	
};

//部门类
class Department
{
	private int DepartmentNo;
	private String DepartmentName;
	private int totalworkers;
	private String manager;
	private ArrayList<Employee> workers;
	
	public Department(int no, String name, int num, String man, int numbers)
	{
		DepartmentNo = no;
		DepartmentName = name;
		totalworkers = num;
		manager = man;
		workers = new ArrayList<Employee>(numbers);
		
	}
	
	public Department()
	{
		DepartmentNo = 0;
		DepartmentName = "nothing";
		totalworkers = 0;
		manager = "default";
		workers = new ArrayList<Employee>();
	}
	
	public String getDepartmentName()
	{
		return DepartmentName;
	}
	
	public int getDepartmentNo()
	{
		return DepartmentNo;
	}
	
	public int getTotalworkers()
	{
		return totalworkers;
	}
	
	public String getManager()
	{
		return manager;
	}
	public void addWorkers(Employee a)
	{
		workers.add(a);
	}
	public Employee getemployee(int a)
	{
		return workers.get(a);
	}
	public void showworkerbyPer()
	{
		workers.sort(null);
		Iterator<Employee> iterator = workers.iterator();
		while(iterator.hasNext()) {//判断当前“指针”下面是否还有元素
			iterator.next().showinformation();//如果指针下面有元素，则移动指针并获取相应位置的元素
		}
	}

}

//业绩类
class Performance
{
	private int basicsalary;  //基本薪水
	private int allowance;    //津贴
	private int bonus;                //奖金
	
	public Performance(int salary, int allow, int bon)
	{
		basicsalary = salary;
		allowance = allow;
		bonus = bon;
	}
	
	public Performance()
	{
		basicsalary = 0;
		allowance = 0;
		bonus = 0;
	}
	
	public int getBasicsalary()
	{
		return basicsalary;
	}
	
	public int getAllowance()
	{
		return allowance;
	}
	
	public int getBonus()
	{
		return bonus;
	}
	
	public int getTotalsalary()  //返回总薪水
	{
		return basicsalary + bonus + allowance;
	}
	
	public void raiseSalary(int a)  //加工资
	{
		basicsalary += a;
	}
	
	public void raiseAllowance(int a)  //加津贴
	{
		allowance += a;
	}
	
	public void raiseBonus(int a)  // 加奖金
	{
		bonus += a;
	}
}



