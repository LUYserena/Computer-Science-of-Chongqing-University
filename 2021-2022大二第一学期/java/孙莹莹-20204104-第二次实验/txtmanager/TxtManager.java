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

	
	private JMenuBar menuBar;  //�˵���
	
	private JMenu me_File,me_Edit,me_Help,me_Format; //�˵���
	
	private JMenuItem it_new,it_newwindow,it_open,it_save,it_exit; //file�˵�������
	
	private JMenuItem it_undo,it_redo,it_cut,it_copy,it_stick,it_delete; //edit�˵�������
	
	private JMenuItem it_about;  //help�˵�������
	
	private JMenuItem it_word_format;  //format�˵�������
	
	//�ı�����
	private static JTextArea edit_text_area;	// JTestArea()����һ��JTextArea����ʹ��Ԥ���ģʽ�����ַ�����0�С�0�С�
	//�ı�������
	private JScrollPane scroll_bar;
	//��������
	private UndoManager um;
	//ϵͳ���а�
	private Clipboard clipboard;
	//�ļ�ѡ����
	private JFileChooser fileChooser;
	
	public TxtManager()
	{
		initMenuBar();
		initEditText();
		initListener();
		 
		this.setJMenuBar(menuBar);
		this.setSize(700,500); //���ô����С
		this.add(scroll_bar);
		this.setTitle("Serena's TXT.Manager"); //���ô������
		this.setVisible(true); //���ô����Ƿ�ɼ���true���ɼ���false�����ɼ�
		this.setLocationRelativeTo(null);	//����λ�����м�λ��
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	// //���ô���رշ�ʽ
	}
	
	private void initMenuBar()  //��ʼ���˵���
	{
		menuBar = new JMenuBar();   
		
		//file�˵���
		me_File = new JMenu("File(F)");
		me_File.setMnemonic(KeyEvent.VK_F);		// ���ÿ�ݼ�-Alt+f:�� 
		it_new = new JMenuItem("new"); //�½�
		it_newwindow = new JMenuItem("new window"); //�´���
		it_open = new JMenuItem("open");  //��
		it_save = new JMenuItem("preserve");  //����
		it_exit = new JMenuItem("exit");
		me_File.add(it_new);  //�������˵������˵���
		me_File.add(it_newwindow);
		me_File.add(it_open);
		me_File.add(it_save);
		me_File.add(it_exit);

		//edit�˵���
		me_Edit = new JMenu("Edit(E)");
		me_Edit.setMnemonic('e'); // ���ÿ�ݼ�-Alt+e:�� 
		it_undo = new JMenuItem("cancel");  //����
		it_redo = new JMenuItem("recover");  //�ָ�
		it_cut = new JMenuItem("cut"); //����
		it_copy = new JMenuItem("copy");  //����
		it_stick = new JMenuItem("paste");  //ճ��
		it_delete = new JMenuItem("delete");  //ɾ��
		me_Edit.add(it_undo);
		me_Edit.add(it_redo);
		me_Edit.add(it_cut);
		me_Edit.add(it_copy);
		me_Edit.add(it_stick);
		me_Edit.add(it_delete);
		 
		//help�˵���
		me_Help = new JMenu("Help(H)");  //����
		me_Help.setMnemonic('h');  // ���ÿ�ݼ�-Alt+h:�� 
		it_about = new JMenuItem("about");  //����
		me_Help.add(it_about);

		//format�˵���
		me_Format = new JMenu("Format(O)");  //��ʽ
		me_Format.setMnemonic('o');
		it_word_format = new JMenuItem("wordformat(F)");	//	����	CTRL_MASK = ctrl��
		me_Format.add(it_word_format);

		//����˵���
		menuBar.add(me_File);
		menuBar.add(me_Edit);
		menuBar.add(me_Format);
		menuBar.add(me_Help);
	}

		private void initEditText() {
			//���´��뽫�ı��������ڹ�����壬�������Ĺ�����������ı���
			edit_text_area = new JTextArea();
			scroll_bar = new JScrollPane(edit_text_area);
			
			scroll_bar.setRowHeaderView(new ShowLine());		//��ʾ�к�
	        
			scroll_bar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll_bar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);  //ȷ��ˮƽ��������ʱ��ʾ�ڹ��������ϡ�ѡ���У�ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED ��Ҫʱ����
			
			um = new UndoManager();
			clipboard = this.getToolkit().getSystemClipboard();
		}

		
		private void saveFile() 
		{

			File file =null;
			int result;		//int��ֵ��showSaveDialog����ֵ�����ж��û��Ƿ�ѡ�����ļ���·����
			fileChooser = new JFileChooser("C:\\Users\\62473\\Desktop\\");	//���û�Ĭ��Ŀ¼
			result = fileChooser.showSaveDialog(rootPane); // ����Dialog�ĸ�View������	��ʾ�Ի���
			
			if(result == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile(); // �������ȷ����ť����file���ļ�·��
			}
			else return;	//��ȡ�����󲻻������������
			
			try{
				OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(file),"UTF-8"); // ���ַ����б���ת�� �����
				BufferedWriter writer = new BufferedWriter(write);
				for (String value : edit_text_area.getText().split("\n")) {		// ʵ����JTextArea�������ת����ʵ���ı�����
					writer.write(value);
					writer.newLine();//����
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
				file = fileChooser.getSelectedFile(); // �������ȷ����ť����file���ļ�·��
			}
			else return;	//��ȡ�����󲻻������������
		
			if(file.isFile() && file.exists()) {
				try {
					edit_text_area.setText("");		//���Ŀǰ�ı�����
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
			//�ӿ�ActionListener���涨���һ�����󷽷�������ʵ������ӿڵ��඼Ҫ��д���������
			//getSource ���ط����¼����¼�Դ���������
			if(e.getSource()==it_about)
			{
				new ShowHelp();
			}
			else if (e.getSource()==it_word_format) {
				new ShowFormat();
			}
			else if (e.getSource()==it_new) {
				this.saveFile();		//�½��ļ�ǰ����ԭ�ļ�
				new TxtManager();
				this.dispose();		
			}
			else if (e.getSource()==it_newwindow) {
				new TxtManager();		//����´��ڣ������ڲ����˳�
			}
			else if (e.getSource()==it_exit){
				this.saveFile();
				this.dispose();			//�˳�ѯ��(Ŀǰ���޸��Ķ��ᵯ�����洰��)
			}
			else if (e.getSource()==it_save) {
				this.saveFile();
			}
			else if (e.getSource()==it_open) {
				this.openFile();
			}
			else if (e.getSource()==it_undo&&um.canUndo()) {		//�������Գ����ǳ�����һ���ı�����
				um.undo();
			}
			else if (e.getSource()==it_redo&&um.canRedo()) {		//�ָ����ǻָ���һ���ı�����(��Ҫ������)
				um.redo();
			}
			else if (e.getSource()==it_copy) {
				String temptext = edit_text_area.getSelectedText();		//�õ�ѡȡ�ı�
				StringSelection editText = new StringSelection(temptext);	//�����ܴ���ָ�� String �� Transferable
				clipboard.setContents(editText,null);		// ��������ĵ�ǰ�������õ�ָ���� transferable ����
															// ����ָ���ļ�������������Ϊ�����ݵ�������ע�ᡣ
			}
			else if (e.getSource()==it_cut) {
				String temptext = edit_text_area.getSelectedText();
				StringSelection editText = new StringSelection(temptext);  //����String��������
				clipboard.setContents(editText,null);  //����ı���ϵͳ���а�
				int startes = edit_text_area.getSelectionStart();		//����+ɾ��
				int endes  = edit_text_area.getSelectionEnd(); 
				edit_text_area.replaceRange("",startes,endes); // �滻�����ı�
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
				    	edit_text_area.append(str); // ׷���ı����ĵ�ĩβ
				    }
				    catch(Exception ex)
				    {
				    	ex.printStackTrace();
				    }
			    }
			}
			else if (e.getSource()==it_delete) {
				int start= edit_text_area.getSelectionStart();		//ɾ��
				int end  = edit_text_area.getSelectionEnd(); 
				edit_text_area.replaceRange("",start,end);
			}
		}

		
		public void initListener()
		{
			// �˵�iter����
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
			
			//ע�᳷��/�ָ��ɱ༭������
			edit_text_area.getDocument().addUndoableEditListener(new UndoableEditListener(){		
	            public void undoableEditHappened(UndoableEditEvent e) {
	                um.addEdit(e.getEdit());
	            }
	        });
			
		}

		
		public static JTextArea getEdit_text_area() {
			//�����ı��༭������������
				return edit_text_area;
			}

		
		public static void main(String[] args) {
			
			TxtManager createone = new TxtManager();
		
		}
		
	}

