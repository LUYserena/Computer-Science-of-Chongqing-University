package txtmanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;


public class TxtManager extends JFrame implements ActionListener{

	
	private JMenuBar menuBar;  //菜单栏
	
	private JMenu me_File,me_Edit,me_Help,me_Format; //菜单项
	
	private JMenuItem it_new,it_newwindow,it_open,it_save,it_exit; //file菜单项内容
	
	private JMenuItem it_undo,it_redo,it_cut,it_copy,it_stick,it_delete; //edit菜单项内容
	
	private JMenuItem it_about;  //help菜单项内容
	
	private JMenuItem it_word_format;  //format菜单项内容
	
	//文本区域
	private static JTextArea edit_text_area;	// JTestArea()建立一个JTextArea对象，使用预设的模式、空字符串、0行、0列。
	//文本滚动条
	private JScrollPane scroll_bar;
	//撤销管理
	private UndoManager um;
	//系统剪切板
	private Clipboard clipboard;
	//文件选择器
	private JFileChooser fileChooser;
	
	public TxtManager()
	{
		initMenuBar();
		initEditText();
		initListener();
		 
		this.setJMenuBar(menuBar);
		this.setSize(700,500); //设置窗体大小
		this.add(scroll_bar);
		this.setTitle("Serena's TXT.Manager"); //设置窗体标题
		this.setVisible(true); //设置窗体是否可见，true：可见，false：不可见
		this.setLocationRelativeTo(null);	//窗体位于正中间位置
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	// //设置窗体关闭方式
	}
	
	private void initMenuBar()  //初始化菜单栏
	{
		menuBar = new JMenuBar();   
		
		//file菜单项
		me_File = new JMenu("File(F)");
		me_File.setMnemonic(KeyEvent.VK_F);		// 设置快捷键-Alt+f:打开 
		it_new = new JMenuItem("new"); //新建
		it_newwindow = new JMenuItem("new window"); //新窗口
		it_open = new JMenuItem("open");  //打开
		it_save = new JMenuItem("preserve");  //保存
		it_exit = new JMenuItem("exit");
		me_File.add(it_new);  //将上述菜单项加入菜单中
		me_File.add(it_newwindow);
		me_File.add(it_open);
		me_File.add(it_save);
		me_File.add(it_exit);

		//edit菜单项
		me_Edit = new JMenu("Edit(E)");
		me_Edit.setMnemonic('e'); // 设置快捷键-Alt+e:打开 
		it_undo = new JMenuItem("cancel");  //撤销
		it_redo = new JMenuItem("recover");  //恢复
		it_cut = new JMenuItem("cut"); //剪切
		it_copy = new JMenuItem("copy");  //复制
		it_stick = new JMenuItem("paste");  //粘贴
		it_delete = new JMenuItem("delete");  //删除
		me_Edit.add(it_undo);
		me_Edit.add(it_redo);
		me_Edit.add(it_cut);
		me_Edit.add(it_copy);
		me_Edit.add(it_stick);
		me_Edit.add(it_delete);
		 
		//help菜单项
		me_Help = new JMenu("Help(H)");  //帮助
		me_Help.setMnemonic('h');  // 设置快捷键-Alt+h:打开 
		it_about = new JMenuItem("about");  //关于
		me_Help.add(it_about);

		//format菜单项
		me_Format = new JMenu("Format(O)");  //格式
		me_Format.setMnemonic('o');
		it_word_format = new JMenuItem("wordformat(F)");	//	字体	CTRL_MASK = ctrl键
		me_Format.add(it_word_format);

		//加入菜单栏
		menuBar.add(me_File);
		menuBar.add(me_Edit);
		menuBar.add(me_Format);
		menuBar.add(me_Help);
	}

		private void initEditText() {
			//以下代码将文本区放置于滚动面板，滑动面板的滚动条能浏览文本区
			edit_text_area = new JTextArea();
			scroll_bar = new JScrollPane(edit_text_area);
			
			scroll_bar.setRowHeaderView(new ShowLine());		//显示行号
	        
			scroll_bar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll_bar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //确定水平滚动条何时显示在滚动窗格上。选项有：ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED 需要时出现
			
			um = new UndoManager();
			clipboard = this.getToolkit().getSystemClipboard();
		}

		
		private void saveFile() 
		{

			File file =null;
			int result;		//int型值，showSaveDialog返回值用来判断用户是否选择了文件或路径。
			fileChooser = new JFileChooser("C:\\Users\\62473\\Desktop\\");	//打开用户默认目录
			result = fileChooser.showSaveDialog(rootPane); // 设置Dialog的根View根布局	显示对话框
			
			if(result == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
			}
			else return;	//按取消键后不会运行下面代码
			
			try{
				OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8"); // 对字符进行编码转换 输出流
				BufferedWriter writer = new BufferedWriter(write);
				for (String value : edit_text_area.getText().split("\n")) {		// 实现在JTextArea多行如何转换到实际文本多行
					writer.write(value);
					writer.newLine();//换行
	            }
				writer.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}

		
		private void openFile() {
			
			File file =null;
			int result;
			fileChooser =new JFileChooser("C:\\Users\\62473\\Desktop\\");
			result = fileChooser.showOpenDialog(rootPane);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile(); // 若点击了确定按钮，给file填文件路径
			}
			else return;	//按取消键后不会运行下面代码
		
			if(file.isFile() && file.exists()) {
				try {
					edit_text_area.setText("");		//清空目前文本内容
					InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file),"UTF-8");
					BufferedReader reader = new BufferedReader(inputStreamReader);
					String readLine;
					while ((readLine = reader.readLine()) != null) {
						edit_text_area.append(readLine+'\n');
					}
					reader.close();
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		}

		
		public void actionPerformed(ActionEvent e) {
			//接口ActionListener里面定义的一个抽象方法，所有实现这个接口的类都要重写这个方法。
			//getSource 返回发生事件的事件源对象的引用
			if(e.getSource()==it_about)
			{
				new ShowHelp();
			}
			else if (e.getSource()==it_word_format) {
				new ShowFormat();
			}
			else if (e.getSource()==it_new) {
				this.saveFile();		//新建文件前保存原文件
				new TxtManager();
				this.dispose();		
			}
			else if (e.getSource()==it_newwindow) {
				new TxtManager();		//添加新窗口，父窗口不会退出
			}
			else if (e.getSource()==it_exit){
				this.saveFile();
				this.dispose();			//退出询问(目前有无更改都会弹出保存窗口)
			}
			else if (e.getSource()==it_save) {
				this.saveFile();
			}
			else if (e.getSource()==it_open) {
				this.openFile();
			}
			else if (e.getSource()==it_undo&&um.canUndo()) {		//撤销可以撤销是撤销上一步文本操作
				um.undo();
			}
			else if (e.getSource()==it_redo&&um.canRedo()) {		//恢复就是恢复上一步文本操作(需要被撤销)
				um.redo();
			}
			else if (e.getSource()==it_copy) {
				String temptext = edit_text_area.getSelectedText();		//得到选取文本
				StringSelection editText = new StringSelection(temptext);	//创建能传输指定 String 的 Transferable
				clipboard.setContents(editText,null);		// 将剪贴板的当前内容设置到指定的 transferable 对象，
															// 并将指定的剪贴板所有者作为新内容的所有者注册。
			}
			else if (e.getSource()==it_cut) {
				String temptext = edit_text_area.getSelectedText();
				StringSelection editText = new StringSelection(temptext);  //构建String数据类型
				clipboard.setContents(editText,null);  //添加文本到系统剪切板
				int startes = edit_text_area.getSelectionStart();		//复制+删除
				int endes  = edit_text_area.getSelectionEnd(); 
				edit_text_area.replaceRange("",startes,endes); // 替换部分文本
			}
			else if (e.getSource()==it_stick) {
				Transferable contents = clipboard.getContents(this);
				DataFlavor  flavor= DataFlavor.stringFlavor;
			    if( contents.isDataFlavorSupported(flavor))
			    {
				    try
				    {  
				    	String str;
				    	str = (String)contents.getTransferData(flavor);
				    	edit_text_area.append(str); // 追加文本到文档末尾
				    }
				    catch(Exception ex)
				    {
				    	ex.printStackTrace();
				    }
			    }
			}
			else if (e.getSource()==it_delete) {
				int start= edit_text_area.getSelectionStart();		//删除
				int end  = edit_text_area.getSelectionEnd(); 
				edit_text_area.replaceRange("",start,end);
			}
		}

		
		public void initListener()
		{
			// 菜单iter监听
			it_new.addActionListener(this);
			it_newwindow.addActionListener(this);
			it_open.addActionListener(this);
			it_save.addActionListener(this);
			it_exit.addActionListener(this);
			it_undo.addActionListener(this);
			it_redo.addActionListener(this);
			it_cut.addActionListener(this);
			it_copy.addActionListener(this);
			it_stick.addActionListener(this);
			it_delete.addActionListener(this);
			it_word_format.addActionListener(this);
			it_about.addActionListener(this);
			
			//注册撤销/恢复可编辑监听器
			edit_text_area.getDocument().addUndoableEditListener(new UndoableEditListener(){		
	            public void undoableEditHappened(UndoableEditEvent e) {
	                um.addEdit(e.getEdit());
	            }
	        });
			
		}

		
		public static JTextArea getEdit_text_area() {
			//返回文本编辑区给其他窗口
				return edit_text_area;
			}

		
		public static void main(String[] args) {
			
			TxtManager createone = new TxtManager();
		
		}
		
	}

