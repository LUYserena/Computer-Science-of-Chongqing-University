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

	private JComboBox word_style;	//�����б�
	private JComboBox word_size;
	private JComboBox word_pattern;
	private JComboBox word_color;
	
	private String[] styles = {"����","����","����","΢���ź�","����"};
	private String[] colors = {"��ɫ","��ɫ","��ɫ","��ɫ","��ɫ","��ɫ"};
	private String[] word_big = {"2","4","8","16","24","32","64","72"};
	private String[] pattern = {"����","��б","����"};
	
	private JPanel paneNorth;//����װ�ĸ�ComboBox
	private JPanel paneCenter;//����װ��ʾ��
	private JPanel paneSouth;//����װ��ť
	
	private JTextField showText;//��ʾ�ı�
	
	private JButton butok;		
	private JButton butcancel;
	
	private Font selectedFont = TxtManager.getEdit_text_area().getFont();	//������װ�ı������
	private String selectedStyle = "����";		//Ĭ����������
	private int selectedBig = 32;
	private int selectedPattern = Font.PLAIN;
	private Color selectedColor = TxtManager.getEdit_text_area().getForeground();	//��ɫҪ�������ϸ�
	
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

	private void addBtnListener() { 	//ע�ᰴť����
		butcancel.addActionListener(this);
		butok.addActionListener(this);
	}
	
	private void initListener() {		//ע�����������
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
		showText = new JTextField("show word-format"); //����չʾ
		showText.setHorizontalAlignment(JTextField.CENTER);	//�ı�����
		showText.setFont(selectedFont);
		showText.setEditable(false);	//���ɱ༭
		showText.setSize(300,300);
//		showText.setForeground(Color.black);//������ɫ
	}

	
	/**
	   * ��ʼ������
	   * ��ÿ���ؼ�����һ���ò�������this������
	   */
	  public void initLocation() {
		  paneNorth = new JPanel();  //���������
		  paneNorth.add(new JLabel("word-style:"));	//����
		  paneNorth.add(word_style);
		  paneNorth.add(new JLabel("word-size:"));  //�ֺ�
		  paneNorth.add(word_size);
		  paneNorth.add(new JLabel("word_pattern:"));//����
		  paneNorth.add(word_pattern);
		  paneNorth.add(new JLabel("color:"));  //��ɫ
		  paneNorth.add(word_color);
		  paneNorth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));	//��add��������ö�
		  this.add(paneNorth,BorderLayout.NORTH);
		  
		  paneCenter = new JPanel();	//���չʾ��
		  paneCenter.add(showText);
		  
		  this.add(paneCenter, BorderLayout.CENTER);	//��Ӱ�ťȥ
		  paneSouth = new JPanel();
		  paneSouth.add(butok);
		  paneSouth.add(butcancel);
		  this.add(paneSouth, BorderLayout.SOUTH);
		  
	  }

	  
	  /**
	  	 * ��ʼ������comboBox 
	  	 * ����Ӧ��ѡ�����
	  	 */
	  public void initBox() {
		  word_style = new JComboBox(styles);	//���鴫��Ͳ���addItem
		  word_size = new JComboBox(word_big);
		  word_pattern = new JComboBox(pattern);
		  word_color = new JComboBox(colors);
	  }

	  
	  @Override
		public void actionPerformed(ActionEvent e) {	//�԰�ť�ļ���
			if (e.getSource() == butcancel) {
				  this.dispose();//���ٵ�ǰ����
			}else if (e.getSource() == butok) { // ���ø������ʵ�����õ�text_area������setFont
		    
				TxtManager.getEdit_text_area().setFont(selectedFont);	//������
				TxtManager.getEdit_text_area().setForeground(selectedColor);	//��������ɫ
				this.dispose();
			}
		}

	  
	  @Override
		public void itemStateChanged(ItemEvent e) {	//��������ļ���
			if (e.getItem() == "����") {
				  selectedStyle = "����";
				  renewFont();
			  }else if (e.getItem() == "����") {
				  selectedStyle = "����";
				  renewFont();
			  }else if (e.getItem() == "����") {
				  selectedStyle = "����";
				  renewFont();
			  }else if (e.getItem() == "΢���ź�") {
				  selectedStyle = "΢���ź�";
				  renewFont();
			  }else if (e.getItem() == "����") {
				  selectedStyle = "����";
				  renewFont();
			  }else if (e.getItem() == "����") {
				  selectedPattern = Font.PLAIN;
				  renewFont();
			  }else if (e.getItem() == "��б") {
				  selectedPattern = Font.ITALIC;
				  renewFont();
			  }else if (e.getItem() == "����") {
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
			  }else if (e.getItem() == "��ɫ") {
				  selectedColor = Color.red;
				  renewFont();
			  }else if (e.getItem() == "��ɫ") {
				  selectedColor = Color.black;
				  renewFont();
			  }else if (e.getItem() == "��ɫ") {
				  selectedColor = Color.blue;
				  renewFont();
			  }else if (e.getItem() == "��ɫ") {
				  selectedColor = Color.yellow;
				  renewFont();
			  }else if (e.getItem() == "��ɫ") {
				  selectedColor = Color.green;
				  renewFont();
			  }else if (e.getItem() == "��ɫ") {
				  selectedColor = Color.WHITE;
				  renewFont();
			  }
		}

	  
	  private void renewFont() {  //��������������ʽ
			selectedFont = new Font(selectedStyle,selectedPattern,selectedBig);
			showText.setFont(selectedFont);
			showText.setForeground(selectedColor);
		}

	}



