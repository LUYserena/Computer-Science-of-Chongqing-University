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
	private BoxLayout layout;	//指定在容器中是否对控件进行水平或者垂直放置

	
	public ShowHelp() {
		panel = new JPanel();	//轻量级面板容器
		layout = new BoxLayout(panel,BoxLayout.Y_AXIS);	//Y_AXIS - 控件垂直放置
		panel.setLayout(layout);
		
		button = new JButton("OK");
		button.setAlignmentX(CENTER_ALIGNMENT);	//alignment对齐
		Alabel = new JLabel("<html><br /><h3 style='text-align:center;color:brown;'>created by lovely Serena</h3>"	//JLabel里面可以嵌入html
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
		
		button.addActionListener(e->{	//窗口关闭
			this.dispose();
		});
	}
}

