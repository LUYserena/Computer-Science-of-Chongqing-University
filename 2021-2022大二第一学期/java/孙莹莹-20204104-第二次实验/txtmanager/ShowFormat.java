package txtmanager;


import javax.swing.JFrame;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ShowFormat extends JFrame implements ItemListener,ActionListener {

	private JComboBox word_style;	//下拉列表
	private JComboBox word_size;
	private JComboBox word_pattern;
	private JComboBox word_color;
	
	private String[] styles = {"宋体","黑体","楷体","微软雅黑","隶书"};
	private String[] colors = {"黑色","蓝色","绿色","红色","白色","黄色"};
	private String[] word_big = {"2","4","8","16","24","32","64","72"};
	private String[] pattern = {"常规","倾斜","粗体"};
	
	private JPanel paneNorth;//用于装四个ComboBox
	private JPanel paneCenter;//用来装演示区
	private JPanel paneSouth;//用来装按钮
	
	private JTextField showText;//演示文本
	
	private JButton butok;		
	private JButton butcancel;
	
	private Font selectedFont = TxtManager.getEdit_text_area().getFont();	//用来封装改变的属性
	private String selectedStyle = "宋体";		//默认字体属性
	private int selectedBig = 32;
	private int selectedPattern = Font.PLAIN;
	private Color selectedColor = TxtManager.getEdit_text_area().getForeground();	//颜色要单独加上改
	
	public ShowFormat() {
		  initBox();
		  initText();
		  initButton();
		  initLocation();
		  initListener();
		  addBtnListener();
		  
		  this.setSize(550,240);
		  this.setTitle("word-format");
		  this.setVisible(true);
		  this.setLocationRelativeTo(null);
		  this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	  }

	private void addBtnListener() { 	//注册按钮监听
		butcancel.addActionListener(this);
		butok.addActionListener(this);
	}
	
	private void initListener() {		//注册下拉框监听
		word_style.addItemListener(this);
		word_size.addItemListener(this);
		word_pattern.addItemListener(this);
		word_color.addItemListener(this);
	}

	
	private void initButton() {
		butok = new JButton("OK");
		butcancel = new JButton("CANCEL");
	}

	
	private void initText() {
		showText = new JTextField("show word-format"); //字体展示
		showText.setHorizontalAlignment(JTextField.CENTER);	//文本居中
		showText.setFont(selectedFont);
		showText.setEditable(false);	//不可编辑
		showText.setSize(300,300);
//		showText.setForeground(Color.black);//字体颜色
	}

	
	/**
	   * 初始化布局
	   * 将每个控件按照一定得布局排在this窗口中
	   */
	  public void initLocation() {
		  paneNorth = new JPanel();  //添加下拉区
		  paneNorth.add(new JLabel("word-style:"));	//字体
		  paneNorth.add(word_style);
		  paneNorth.add(new JLabel("word-size:"));  //字号
		  paneNorth.add(word_size);
		  paneNorth.add(new JLabel("word_pattern:"));//字形
		  paneNorth.add(word_pattern);
		  paneNorth.add(new JLabel("color:"));  //颜色
		  paneNorth.add(word_color);
		  paneNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));	//让add的组件不置顶
		  this.add(paneNorth,BorderLayout.NORTH);
		  
		  paneCenter = new JPanel();	//添加展示区
		  paneCenter.add(showText);
		  
		  this.add(paneCenter, BorderLayout.CENTER);	//添加按钮去
		  paneSouth = new JPanel();
		  paneSouth.add(butok);
		  paneSouth.add(butcancel);
		  this.add(paneSouth, BorderLayout.SOUTH);
		  
	  }

	  
	  /**
	  	 * 初始化几个comboBox 
	  	 * 把相应的选项加入
	  	 */
	  public void initBox() {
		  word_style = new JComboBox(styles);	//数组传入就不用addItem
		  word_size = new JComboBox(word_big);
		  word_pattern = new JComboBox(pattern);
		  word_color = new JComboBox(colors);
	  }

	  
	  @Override
		public void actionPerformed(ActionEvent e) {	//对按钮的监听
			if (e.getSource() == butcancel) {
				  this.dispose();//销毁当前窗口
			}else if (e.getSource() == butok) { // 调用父窗体的实例，拿到text_area并对其setFont
		    
				TxtManager.getEdit_text_area().setFont(selectedFont);	//改字体
				TxtManager.getEdit_text_area().setForeground(selectedColor);	//改字体颜色
				this.dispose();
			}
		}

	  
	  @Override
		public void itemStateChanged(ItemEvent e) {	//对下拉框的监听
			if (e.getItem() == "宋体") {
				  selectedStyle = "宋体";
				  renewFont();
			  }else if (e.getItem() == "黑体") {
				  selectedStyle = "黑体";
				  renewFont();
			  }else if (e.getItem() == "楷体") {
				  selectedStyle = "楷体";
				  renewFont();
			  }else if (e.getItem() == "微软雅黑") {
				  selectedStyle = "微软雅黑";
				  renewFont();
			  }else if (e.getItem() == "隶书") {
				  selectedStyle = "隶书";
				  renewFont();
			  }else if (e.getItem() == "常规") {
				  selectedPattern = Font.PLAIN;
				  renewFont();
			  }else if (e.getItem() == "倾斜") {
				  selectedPattern = Font.ITALIC;
				  renewFont();
			  }else if (e.getItem() == "粗体") {
				  selectedPattern = Font.BOLD;
				  renewFont();
			  }else if (e.getItem() == "2") {
				  selectedBig = 2;
				  renewFont();
			  }else if (e.getItem() == "4") {
				  selectedBig = 4;
				  renewFont();
			  }else if (e.getItem() == "8") {
				  selectedBig = 8;
				  renewFont();
			  }else if (e.getItem() == "16") {
				  selectedBig = 16;
				  renewFont();
			  }else if (e.getItem() == "24") {
				  selectedBig = 24;
				  renewFont();
			  }else if (e.getItem() == "32") {
				  selectedBig = 32;
				  renewFont();
			  }else if (e.getItem() == "64") {
				  selectedBig = 64;
				  renewFont();
			  }else if (e.getItem() == "72") {
				  selectedBig = 72;
				  renewFont();
			  }else if (e.getItem() == "红色") {
				  selectedColor = Color.red;
				  renewFont();
			  }else if (e.getItem() == "黑色") {
				  selectedColor = Color.black;
				  renewFont();
			  }else if (e.getItem() == "蓝色") {
				  selectedColor = Color.blue;
				  renewFont();
			  }else if (e.getItem() == "黄色") {
				  selectedColor = Color.yellow;
				  renewFont();
			  }else if (e.getItem() == "绿色") {
				  selectedColor = Color.green;
				  renewFont();
			  }else if (e.getItem() == "白色") {
				  selectedColor = Color.WHITE;
				  renewFont();
			  }
		}

	  
	  private void renewFont() {  //重新设置字体样式
			selectedFont = new Font(selectedStyle,selectedPattern,selectedBig);
			showText.setFont(selectedFont);
			showText.setForeground(selectedColor);
		}

	}



