import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.io.*;

public class frameMain extends JFrame implements ActionListener, MouseListener {
	
	JMenuBar menubar;
	JMenu file, compile, format, check, help;
	JMenuItem newfile, open, save, othersave, exit, revocation, cut, copy,
	 paste, delete, find, findNext, replace, goTo, selectall, date, lineWrap,
	 font, status, about;
	Container frame = this.getContentPane();
	static JTextArea text;
	JPanel statusPanel;
	JLabel statusLabel;
	JFileChooser filedialog;
	String filesavename, cutpaste, statusText;
	int textAreaCount, textAreaNum;
	static UndoManager undomg = new UndoManager();
	
	public static void main(String[] args) {
		new frameMain();
	}
	
	public frameMain(){
		
		this.addWindowListener(new WindowAdapter()
    	{  
    	      public void windowClosing( WindowEvent e )
    	      { 
    	    	  int result=JOptionPane.showConfirmDialog(null, "�Ƿ񽫸��ĵ��ļ����棿", "���±�", JOptionPane.YES_NO_CANCEL_OPTION);
    				if(result==JOptionPane.YES_OPTION)
    				{
    					othersave.doClick();
    				}
    				else if(result==JOptionPane.CANCEL_OPTION)
    				{
    				    return;
    				}
    			
    	     }
    	} );
		
		text = new JTextArea();
		text.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e)
			{
				textAreaCount = text.getLineCount();
				textAreaNum = text.getText().length();
				statusText = "������" + textAreaCount + "\t������" + textAreaNum;
				statusLabel.setText(statusText);
			}
		});
		
		statusLabel = new JLabel();
		statusPanel = new JPanel();
		filedialog = new JFileChooser(".");
		
		menubar = new JMenuBar();
		file = new JMenu("�ļ�(F)");
		file.setMnemonic('F');
		
		compile = new JMenu("�༭(E)");
		compile.setMnemonic('E');
		
		format = new JMenu("��ʽ(O)");
		format.setMnemonic('O');
		
		check = new JMenu("�鿴(V)");
		check.setMnemonic('V');
		
		help = new JMenu("����(H)");
		help.setMnemonic('H');
		
		newfile = new JMenuItem("�½�(N)");
		newfile.setMnemonic('N');
		newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		newfile.addActionListener(this);
		
		open = new JMenuItem("��(O)");
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		open.addActionListener(this);
		
		save = new JMenuItem("����(S)");
		save.setMnemonic('S');
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
	    save.addActionListener(this);
	    
		othersave = new JMenuItem("���Ϊ(A)");
		othersave.setMnemonic('A');
		othersave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
	    othersave.addActionListener(this);
		
		exit = new JMenuItem("�˳�(X)");
		exit.setMnemonic('X');
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,InputEvent.CTRL_MASK));
		exit.addActionListener(this);
		
		revocation = new JMenuItem("����(U)");
		revocation.setMnemonic('U');
		revocation.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		text.getDocument().addUndoableEditListener(new UndoableEditListener()
	     {

			@Override
			public void undoableEditHappened(UndoableEditEvent arg0) {
				// TODO Auto-generated method stub
				undomg.addEdit(arg0.getEdit());
			}
	    	 
	     });
		revocation.addActionListener(this);
		
		cut = new JMenuItem("����(T)");
		cut.setMnemonic('T');
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		cut.addActionListener(this);
		
		copy = new JMenuItem("����(C)");
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		copy.addActionListener(this);
		
		paste = new JMenuItem("ճ��(P)");
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		paste.addActionListener(this);
		
		delete = new JMenuItem("ɾ��(L)");
		delete.setMnemonic('L');
		delete.addActionListener(this);
		
		find = new JMenuItem("����(D)");
		find.setMnemonic('D');
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		find.addActionListener(this);
		
		findNext = new JMenuItem("������һ��(N)");
		findNext.setMnemonic('N');
		findNext.addActionListener(this);
		
		replace = new JMenuItem("�滻(R)");
		replace.setMnemonic('R');
		replace.addActionListener(this);
		
		goTo = new JMenuItem("ת��(G)");
		goTo.setMnemonic('G');
		goTo.addActionListener(this);
		
		selectall = new JMenuItem("ȫѡ(A)");
		selectall.setMnemonic('A');
		selectall.addActionListener(this);
		
		date = new JMenuItem("ʱ��/����(D)");
		date.setMnemonic('D');
		date.addActionListener(this);
		
		lineWrap = new JMenuItem("�Զ�����(W)");
		lineWrap.setMnemonic('W');
		lineWrap.addActionListener(this);
		
		font = new JMenuItem("����(F)");
		font.setMnemonic('F');
		font.addActionListener(this);
		
		status = new JMenuItem("״̬��(S)");
		status.setMnemonic('S');
		status.addActionListener(this);
		
		about = new JMenuItem("���ڼ��±�(A)");
		about.setMnemonic('A');
		about.addActionListener(this);
		
		file.add(newfile);
		file.add(open);
		file.addSeparator();
		file.add(save);
		file.add(othersave);
		file.addSeparator();
		file.add(exit);
		compile.add(revocation);
		compile.addSeparator();
		compile.add(cut);
		compile.add(copy);
		compile.add(paste);
		compile.add(delete);
		compile.addSeparator();
		compile.add(find);
		compile.add(findNext);
		compile.add(replace);
		compile.add(goTo);
		compile.addSeparator();
		compile.add(selectall);
		compile.add(date);
		format.add(lineWrap);
		format.add(font);
		check.add(status);
		help.add(about);
		
		menubar.add(file);
		menubar.add(compile);
		menubar.add(format);
		menubar.add(check);
		menubar.add(help);
		
		textAreaCount = text.getLineCount();
		textAreaNum = text.getText().length();
		statusText = "������" + textAreaCount + "������" + textAreaNum;
		statusLabel.setText(statusText);
		statusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		statusPanel.add(statusLabel);
		statusPanel.setVisible(false);
		
		frame.add(menubar, BorderLayout.NORTH);
		frame.add(new JScrollPane(text), BorderLayout.CENTER);
		frame.add(statusPanel, BorderLayout.SOUTH);
		this.setTitle("δ����.txt");
		this.setBounds(50, 50, 600, 500);
		this.setVisible(true);
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == newfile)    //�½��ļ�
		{
			int result = JOptionPane.showConfirmDialog(this, "�Ƿ񽫸��ĵ��ļ����棿", "���±�", JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(result == JOptionPane.YES_OPTION)
			{
				othersave.doClick();
			} else if(result == JOptionPane.CANCEL_OPTION) 
			{
			   return;
			}
			filesavename = null;
			frameMain.this.setTitle("δ����.txt");
			text.setText(null);
		}
		
		if(e.getSource() == open)		//���ļ�
		{
			int result = JOptionPane.showConfirmDialog(this, "�Ƿ񽫸��ĵ��ļ����棿", "���±�", JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(result == JOptionPane.YES_OPTION)
			{
				othersave.doClick();
			} else if(result == JOptionPane.CANCEL_OPTION) 
			{
			   return;
			}
	
			String s;
	          
	        if(filedialog.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
	        {
	          	try
	          	{
	          		text.setText(null);
	          		
	          		File file = filedialog.getSelectedFile();
	          		
	          		filesavename = file.getName(); 
	          		
	          		FileReader file_reader = new FileReader(file);
	          		
	          		BufferedReader in = new BufferedReader(file_reader);
	          		
	          		while((s=in.readLine())!=null)
	          		{
	          			text.append(s+"\n");
	          		}
	          		
	          		in.close();
	          		
	          		file_reader.close();
	          		
	          		frameMain.this.setTitle(filesavename);
	          	} catch(Exception e1)
	          	{
	          		System.out.println(e.toString());
	          	}
	          }
	  	}
		
		if(e.getSource() == save || e.getSource() == othersave)		//����
        {
			if(filesavename != null) {
				try
				{
					FileWriter fw = new FileWriter(filesavename);
					fw.write(text.getText());                
					fw.close();
					frameMain.this.setTitle(filesavename);
				}catch(Exception  ex)
				{
					ex.printStackTrace();
				}
			}
		
			if(filesavename == null)		//���Ϊ
			{
	  		
				if(filedialog.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						File file = filedialog.getSelectedFile();
	          		
						filesavename = file.getName();
						
						FileWriter tofile = new FileWriter(file);
                    
						String s = text.getText();
                   
						tofile.write(s);
                   
						tofile.close();
                    
						frameMain.this.setTitle(filesavename);
					}
					catch(Exception e2)
					{
						System.out.println(e.toString());
					}
				}
			}
        }
		
		if(e.getSource() == exit)
		{
			int result = JOptionPane.showConfirmDialog(this, "�Ƿ񽫸��ĵ��ļ����棿", "���±�", JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(result == JOptionPane.YES_OPTION)
			{
				othersave.doClick();
			} else if(result == JOptionPane.CANCEL_OPTION) 
			{
			   return;
			}
			System.exit(0);
		}
		
		if(e.getSource() == revocation)
		{
			if(undomg.canUndo()) {  
                undomg.undo();  
            } else {  
               JOptionPane.showMessageDialog(null,"ϵͳ�����ݿ��Գ����ˣ������޷�����","����",JOptionPane.WARNING_MESSAGE);  
         	  
            }
		}
		
		if(e.getSource() == cut)
		{
			text.cut();
		}
		
		if(e.getSource() == copy)
		{
			text.copy();
		}
		
		if(e.getSource() == paste)
		{
			text.paste();
		}
		
		if(e.getSource() == delete)
		{
			if(text.getSelectedText() != null)
			{
				text.replaceSelection("");
			} else {
				JOptionPane.showMessageDialog(null, "û��ѡ���κ�����");
			}
		}
		
		if(e.getSource() == find || e.getSource() == findNext || e.getSource() == replace) {
			new FindAndReplace(text);
		}
		
		if(e.getSource() == goTo) {
			zhuandao z = new zhuandao(frameMain.this,"ת����",true);
			z.setVisible(true);
		}
		
		if(e.getSource() == selectall) {
			text.selectAll();
		}
		
		if(e.getSource() == date) {
			// ��õ�ǰ���λ��  
			int cur = text.getCaretPosition();  
			// �õ����֮����ַ���  
			String tailString = text.getText().substring(cur);  
			// �õ����֮ǰ���ַ���  
			String headString = text.getText().substring(0,cur);  
			// ƴ���ַ��� �����  
			text.setText(headString + new timeinfor().temptime2 + tailString); 
		}
		
		if(e.getSource() == lineWrap)
		{
			text.setLineWrap(true);
      		JOptionPane.showMessageDialog(null, "ϵͳ�Ѹ��ݴ��ڵĴ�С���л���");
		}
		
		if(e.getSource() == font)
		{
			Fontdialogd di=new Fontdialogd(null,"����",true);
			di.setVisible(true);
		}
		
		if(e.getSource() == status)
		{
			statusPanel.setVisible(true);
		}
		
		if(e.getSource() == about)
		{
			String s = "java windows-Notebook \n \t\t20181227";
			JOptionPane.showMessageDialog(null, s, "about", JOptionPane.PLAIN_MESSAGE);
		}
		
	}
}
