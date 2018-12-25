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
		Button file = new Button("�ļ�(F)");
		Button compile = new Button("�༭(E)");
		Button format = new Button("��ʽ(O)");
		Button check = new Button("�鿴(V)");
		Button help = new Button("����(H)");
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
