import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

class FindAndReplace extends JDialog implements ActionListener {
    JLabel findLabel = new JLabel("查找内容：");
    JLabel repLabel = new JLabel("    替换为：");
    JTextField findTf = new JTextField(8);
    JTextField repTf = new JTextField(8);
    JButton findBtn = new JButton("查找");
    JButton repBtn = new JButton("替换");
    JPanel findPn = new JPanel();
    JPanel repPn = new JPanel();
    Container con = this.getContentPane();
    JTextArea textarea;

    String text;
    boolean flg = false;
    int len;
    int start = 0;
    int k = 0;

    public FindAndReplace(JTextArea textarea) {

        this.textarea = textarea;

        findPn.add(findLabel);
        findPn.add(findTf);
        findPn.add(findBtn);
        repPn.add(repLabel);
        repPn.add(repTf);
        repPn.add(repBtn);
        con.add(findPn);
        con.add(repPn);

        findBtn.addActionListener(this);
        repBtn.addActionListener(this);

        this.setTitle("查找和替换");
        this.setLayout(new GridLayout(2, 1));
        this.setSize(300, 140);
        //this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @SuppressWarnings("deprecation")
    public void actionPerformed(ActionEvent e) {
        String findText = findTf.getText();
        String repText = repTf.getText();
        text = textarea.getText();
        if (e.getSource() == findBtn) {
            findBtn.setLabel("下一个");
            if (findText != null) {
                len = findText.length();
                start = text.indexOf(findText, k);
                k = start + len;
                textarea.select(start, start + len);
                flg = true;
                if (start == -1) {
                    JOptionPane.showMessageDialog(null, "已到文件尾部！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    start = 0;
                    k = 0;
                    flg = false;
                }
            }
        } else if (e.getSource() == repBtn) {
            if (flg) {
                textarea.replaceRange(repText, start, start + len);
                flg = false;
            }
        }
    }
}