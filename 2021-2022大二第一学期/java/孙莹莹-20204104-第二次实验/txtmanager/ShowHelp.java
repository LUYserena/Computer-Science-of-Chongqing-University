package txtmanager;


import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ShowHelp extends JFrame {

	private JButton button;
	private JLabel Alabel;
 
	private JPanel panel ;
	private BoxLayout layout;	//ָ�����������Ƿ�Կؼ�����ˮƽ���ߴ�ֱ����

	
	public ShowHelp() {
		panel = new JPanel();	//�������������
		layout = new BoxLayout(panel,BoxLayout.Y_AXIS);	//Y_AXIS - �ؼ���ֱ����
		panel.setLayout(layout);
		
		button = new JButton("OK");
		button.setAlignmentX(CENTER_ALIGNMENT);	//alignment����
		Alabel = new JLabel("<html><br /><h3 style='text-align:center;color:brown;'>created by lovely Serena</h3>"	//JLabel�������Ƕ��html
				+ "Blog:https://blog.csdn.net/weixin_51964133/<br />"
				+ "Come here and learn coding with me<br />"
				+ "Use this one just like the original TXT.<br />"
				+ "Wish you can have a good time!<br />"
				+ "<br /><br />"
				+ "</html>",JLabel.CENTER);
		Alabel.setAlignmentX(CENTER_ALIGNMENT);
 
		panel.add(Alabel);
		panel.add(button);
		
		this.add(panel);
		this.setSize(400,300);
		this.setTitle("about");
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		button.addActionListener(e->{	//���ڹر�
			this.dispose();
		});
	}
}

