import java.awt.*;
import javax.swing.*;

public class frameMain {
	public static void main(String[] args) {
		new frameMain();
	}
	
	public frameMain(){
		JFrame frame = new JFrame("Windows-Notebook");
		Panel menuPanel;
		menuPanel = new Panel();
		menuPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		Button file = new Button("文件(F)");
		Button compile = new Button("编辑(E)");
		Button format = new Button("格式(O)");
		Button check = new Button("查看(V)");
		Button help = new Button("帮助(H)");
		menuPanel.add(file);
		menuPanel.add(compile);
		menuPanel.add(format);
		menuPanel.add(check);
		menuPanel.add(help);
		frame.add(menuPanel, BorderLayout.NORTH);
		frame.setBounds(50, 50, 500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
	}
}
