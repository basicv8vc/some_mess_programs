package view;

// http://www.cnblogs.com/pzy4447/p/4637604.html

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TextComponentFrame extends JFrame {

    int DEFAULT_WIDTH = 600;
    int DEFAULT_HEIGHT = 400;

    public TextComponentFrame() {
        setTitle("TextComponentFrame");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        
        
        //本frame包含三大板块, frame包含panel
        JPanel northPanel = new JPanel();        
        JScrollPane scrollPane = null;
        JPanel southPanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);
        
        //先来构造northPanel，它包含两个控件，panel包含控件        
        final JTextField textField = new JTextField();
        final JPasswordField passwordField = new JPasswordField();
        final JTextField textField2 = new JTextField();
        
//        northPanel.setLayout(new GridLayout(2, 2));
        northPanel.setLayout(new GridLayout(3, 2));
        northPanel.add(new JLabel("UserName:", SwingConstants.RIGHT));//靠右对齐
        northPanel.add(textField);
        northPanel.add(new JLabel("Password:", SwingConstants.RIGHT));//靠右对齐
        northPanel.add(passwordField);
        northPanel.add(new JLabel("测试:", SwingConstants.RIGHT));
        northPanel.add(textField2);

        //再来构造中间板块，它包含一个textArea
        final JTextArea textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);//添加到窗体

        //构造southPanel，它包含一个button        
        southPanel.setLayout(new GridLayout(2, 2));
        JButton insertButton = new JButton("insertButton");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                textArea.append("UserName: " + textField.getText() + "\n"
                        + "Password:" + new String(passwordField.getPassword()) + "\n"
                        + "Test:" + textField2.getText() + "\n" 
                        + "\n");
            }
        });
        southPanel.add(insertButton);
    }

    public static void main(String[] args) {
        //创建窗体并指定标题
        TextComponentFrame frame = new TextComponentFrame();
        //关闭窗体后退出程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //自动适配所有控件大小
        //frame.pack();
        //设置窗体位置在屏幕中央
        frame.setLocationRelativeTo(null);
        //显示窗体
        frame.setVisible(true);
    }
}