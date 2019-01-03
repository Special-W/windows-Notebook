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
    	    	  int result=JOptionPane.showConfirmDialog(null, "是否将更改的文件保存？", "记事本", JOptionPane.YES_NO_CANCEL_OPTION);
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
				statusText = "行数：" + textAreaCount + "\t字数：" + textAreaNum;
				statusLabel.setText(statusText);
			}
		});
		
		statusLabel = new JLabel();
		statusPanel = new JPanel();
		filedialog = new JFileChooser(".");
		
		menubar = new JMenuBar();
		file = new JMenu("文件(F)");
		file.setMnemonic('F');
		
		compile = new JMenu("编辑(E)");
		compile.setMnemonic('E');
		
		format = new JMenu("格式(O)");
		format.setMnemonic('O');
		
		check = new JMenu("查看(V)");
		check.setMnemonic('V');
		
		help = new JMenu("帮助(H)");
		help.setMnemonic('H');
		
		newfile = new JMenuItem("新建(N)");
		newfile.setMnemonic('N');
		newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		newfile.addActionListener(this);
		
		open = new JMenuItem("打开(O)");
		open.setMnemonic('O');
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		open.addActionListener(this);
		
		save = new JMenuItem("保存(S)");
		save.setMnemonic('S');
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
	    save.addActionListener(this);
	    
		othersave = new JMenuItem("另存为(A)");
		othersave.setMnemonic('A');
		othersave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
	    othersave.addActionListener(this);
		
		exit = new JMenuItem("退出(X)");
		exit.setMnemonic('X');
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,InputEvent.CTRL_MASK));
		exit.addActionListener(this);
		
		revocation = new JMenuItem("撤销(U)");
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
		
		cut = new JMenuItem("剪切(T)");
		cut.setMnemonic('T');
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		cut.addActionListener(this);
		
		copy = new JMenuItem("复制(C)");
		copy.setMnemonic('C');
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		copy.addActionListener(this);
		
		paste = new JMenuItem("粘贴(P)");
		paste.setMnemonic('P');
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		paste.addActionListener(this);
		
		delete = new JMenuItem("删除(L)");
		delete.setMnemonic('L');
		delete.addActionListener(this);
		
		find = new JMenuItem("查找(D)");
		find.setMnemonic('D');
		find.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		find.addActionListener(this);
		
		findNext = new JMenuItem("查找下一处(N)");
		findNext.setMnemonic('N');
		findNext.addActionListener(this);
		
		replace = new JMenuItem("替换(R)");
		replace.setMnemonic('R');
		replace.addActionListener(this);
		
		goTo = new JMenuItem("转到(G)");
		goTo.setMnemonic('G');
		goTo.addActionListener(this);
		
		selectall = new JMenuItem("全选(A)");
		selectall.setMnemonic('A');
		selectall.addActionListener(this);
		
		date = new JMenuItem("时间/日期(D)");
		date.setMnemonic('D');
		date.addActionListener(this);
		
		lineWrap = new JMenuItem("自动换行(W)");
		lineWrap.setMnemonic('W');
		lineWrap.addActionListener(this);
		
		font = new JMenuItem("字体(F)");
		font.setMnemonic('F');
		font.addActionListener(this);
		
		status = new JMenuItem("状态栏(S)");
		status.setMnemonic('S');
		status.addActionListener(this);
		
		about = new JMenuItem("关于记事本(A)");
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
		statusText = "行数：" + textAreaCount + "字数：" + textAreaNum;
		statusLabel.setText(statusText);
		statusPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		statusPanel.add(statusLabel);
		statusPanel.setVisible(false);
		
		frame.add(menubar, BorderLayout.NORTH);
		frame.add(new JScrollPane(text), BorderLayout.CENTER);
		frame.add(statusPanel, BorderLayout.SOUTH);
		this.setTitle("未命名.txt");
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
		
		if(e.getSource() == newfile)    //新建文件
		{
			int result = JOptionPane.showConfirmDialog(this, "是否将更改的文件保存？", "记事本", JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(result == JOptionPane.YES_OPTION)
			{
				othersave.doClick();
			} else if(result == JOptionPane.CANCEL_OPTION) 
			{
			   return;
			}
			filesavename = null;
			frameMain.this.setTitle("未命名.txt");
			text.setText(null);
		}
		
		if(e.getSource() == open)		//打开文件
		{
			int result = JOptionPane.showConfirmDialog(this, "是否将更改的文件保存？", "记事本", JOptionPane.YES_NO_CANCEL_OPTION);
			
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
		
		if(e.getSource() == save || e.getSource() == othersave)		//保存
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
		
			if(filesavename == null)		//另存为
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
			int result = JOptionPane.showConfirmDialog(this, "是否将更改的文件保存？", "记事本", JOptionPane.YES_NO_CANCEL_OPTION);
			
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
               JOptionPane.showMessageDialog(null,"系统无内容可以撤销了，导致无法撤销","警告",JOptionPane.WARNING_MESSAGE);  
         	  
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
				JOptionPane.showMessageDialog(null, "没有选择任何内容");
			}
		}
		
		if(e.getSource() == find || e.getSource() == findNext || e.getSource() == replace) {
			new FindAndReplace(text);
		}
		
		if(e.getSource() == goTo) {
			zhuandao z = new zhuandao(frameMain.this,"转到行",true);
			z.setVisible(true);
		}
		
		if(e.getSource() == selectall) {
			text.selectAll();
		}
		
		if(e.getSource() == date) {
			// 获得当前光标位置  
			int cur = text.getCaretPosition();  
			// 得到光标之后的字符串  
			String tailString = text.getText().substring(cur);  
			// 得到光标之前的字符串  
			String headString = text.getText().substring(0,cur);  
			// 拼接字符串 并输出  
			text.setText(headString + new timeinfor().temptime2 + tailString); 
		}
		
		if(e.getSource() == lineWrap)
		{
			text.setLineWrap(true);
      		JOptionPane.showMessageDialog(null, "系统已根据窗口的大小进行换行");
		}
		
		if(e.getSource() == font)
		{
			Fontdialogd di=new Fontdialogd(null,"字体",true);
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
