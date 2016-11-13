package view;

import logging.Names;
import logging.PRLogging;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import static logging.PRLogging.photoNames;


public class ImageViewerFrame extends JFrame {  
	public static final int WIDTH = 700;  
    public static final int HEIGHT = 500;
    
    String currentPhoto; // 显示在displayPanel的图片, 可以说是连接后台的桥梁
    Image currentImage; //
    
    private JPanel displayPanel;
    private JPanel controlPanel; // 打开图片后，可以进行各种操作的label，是功能模块，包含重命名功能等
    private JPanel tagPanel; // 显示，当前os所有的tag, 程序运行后先加载logging，然后这个label负责显示logging中的tag
    private JLabel label; // 显示图片的panel
    private JTextArea currentPhotoName; // 显示当前图片的名字
    private JFileChooser chooser;  
    
    
    private JTextField textField;
    private JTextArea allTagsTextArea; // 系统所有的标签, 其实可以用一个scrolltext
    private JTextArea currentPhotoTagsTextArea; // 当前display图片含有的tag
    private JTextArea currentPhotoOldNamesTextArea; // 当前display图片的旧名字
    private JScrollPane allTagsScrollPanel;
    private JScrollPane currentPhotoTagsScrollPanel;
    private JScrollPane currentPhotoOldNamesScrollPanel;
    
    
    
    private JTextField mostCommonTagTextField; // 重命名TextField
    private JButton mostCommonTagButton; // 重命名按钮
    private JTextArea addTagField; // 用于添加tag的textField
    private JButton addTagButton; // 用于添加tag的Button
    private JTextArea deleteTagField; // 用于删除tag的textField
    private JButton deleteTagButton; // 用于删除tag的Button
    
      
    public ImageViewerFrame() {  
        super("ImageViewer");  
        setSize(WIDTH, HEIGHT);  
        setLayout(new BorderLayout());
        try {  
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());  
        } catch (Exception e) {  
            //  
        }   
        
        JMenuBar menuBar = new JMenuBar();  
        setJMenuBar(menuBar);  
        JMenu menu = new JMenu("File");  
        JMenuItem openItem = new JMenuItem("open");  
        menu.add(openItem);  
        openItem.addActionListener(new FileOpenListener());  
        JMenuItem exitItem = new JMenuItem("exit");  
        menu.add(exitItem);  
        menuBar.add(menu);  
        exitItem.addActionListener(new ActionListener() {  
            public void actionPerformed(ActionEvent e) {  
                // TODO Auto-generated method stub,// 保存photoNames到allNames.txt
                try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("logs/allNames.txt"), "UTF-8"))) {
                    for(Map.Entry<String, Names> entry : photoNames.entrySet()) {
                        bw.write(entry.getKey()+"&&");
                        bw.write(entry.getValue().getCurrentName()+"&&");
                        ArrayList<String> oldName = entry.getValue().getOldName();
                        for(int i=0;i<oldName.size()-1;i++)
                            bw.write(oldName.get(i)+"&&");
                        bw.write(oldName.get(oldName.size()-1) +"\n");

                    }
                } catch (IOException ex) {

                }

                System.exit(0);  
            }  
        });  
          
        //use a label to display a image
        displayPanel = new JPanel();
        label = new JLabel();  
        currentPhotoName =  new JTextArea();
        label.setPreferredSize(new Dimension(200, 450));
        
        displayPanel.add(label);
        add(displayPanel, BorderLayout.WEST);
      
        // 设置controlLabel
        textField = new JTextField();
        
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 2));
        
        mostCommonTagTextField = new JTextField();
        mostCommonTagButton = new JButton("MostCommonTag");   // 重命名Button

        controlPanel.add(mostCommonTagButton);
        controlPanel.add(mostCommonTagTextField);

        addTagField = new JTextArea();
        addTagButton = new JButton("Rename Photo");
        controlPanel.add(addTagField);
        controlPanel.add(addTagButton);

        deleteTagField = new JTextArea();
        deleteTagButton = new JButton("Delete Tags");

        add(controlPanel, BorderLayout.CENTER);

        // 点击此按钮,显示出现次数最多的tag
        mostCommonTagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                mostCommonTagTextField.setText(PRLogging.getMostCommomTags() + "\n");
            }
        });

        // 添加一个tag为当前图片,实际上也是重命名,
        addTagButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                deleteTagField.setText(addTagField.getText());

                String textContent = addTagField.getText();
                String[] newTags = textContent.split("\n"); // 用户添加的tag

                String[] spliation = currentPhoto.split("/");
                String realName = spliation[spliation.length-1]; // 不包含路径名的图片名
                String[] tmp2 = realName.split("\\.");
                String photoFormat = tmp2[tmp2.length-1]; // 得到图片的格式, png

                String[] tmp = currentPhoto.split(realName); // 得到路径名, tmp[0]

                // 1) 添加photoRenamer.log
                String outputLog = currentPhoto+"&&";
                outputLog += tmp[0];
                for(String sss : newTags) {
                    outputLog += "@";
                    outputLog += sss;
                }
                outputLog += ".";
                outputLog += photoFormat;
                outputLog += "&&";
                outputLog += new Date().toString();
                outputLog += "\n";
                try {
                    Files.write(Paths.get("logs/photoRenamer.log"), outputLog.getBytes(), StandardOpenOption.APPEND);
                }catch (IOException ex) {
                    //exception handling left as an exercise for the reader
                }
                // 2) 在OS重命名这个文件
//                Files.move(currentPhoto, currentPhoto.resolveSibling("newname"));
                Path source = Paths.get(currentPhoto);
                String newName = "";
                for(String sss : newTags) {
                    newName += "@";
                    newName += sss;
                }
                newName += ".";
                newName += photoFormat;
                try {
                    Files.move(source, source.resolveSibling(newName));
                } catch (IOException exx) {

                }

                // 3) 更新photoNames
                // 3.1) 当前名字变成一个旧名字
                // 3.2) 更新当前名字
                // 3.3) 更新标签
                // 3.4) 更新右边所有标签的显示、当前标签的显示和当前图片名字的显示
                Names ctName;
                if (photoNames.containsKey(currentPhoto)) {
                    ctName = photoNames.get(currentPhoto);
                }
                else {
                    ctName = new Names();
                }
                    ctName.setOldName(realName);
                    ctName.setCurrentName(newName);

                // 5) 更改当前图片名字
                currentPhoto = tmp[0] + newName;

                    photoNames.put(currentPhoto, ctName); // 更新photoName

                    allTagsTextArea.setText("All of tags: \n" + PRLogging.getAllTags()); // 设置新的所有的tag

                    ArrayList<String> listCurrenttags = ctName.getCurrentTags();
                    String setNewTag = "This photo's tags:\n";
                    for(String astr : listCurrenttags) {
                        setNewTag += astr;
                        setNewTag += "\n";
                    }

                    currentPhotoTagsTextArea.setText(setNewTag);  // 设置当前图片的tag

                    String ctPhotoNames = "This photo's name and old names:\n";
                    ctPhotoNames += newName;
                    ctPhotoNames += "\n";

                    if(photoNames.containsKey(currentPhoto)) {
                        ArrayList<String> listTag = photoNames.get(currentPhoto).getOldName();
                        for(String str : listTag) {
                            ctPhotoNames += str;
                            ctPhotoNames += "\n";
                        }
                    }
                    currentPhotoOldNamesTextArea.setText(ctPhotoNames); // 设置当前图片的所有名字


                // 4)  清空 addTagField
                addTagField.setText("");





            }
        });

        
        // use two label to display tags, one display all tags, and the other display current photo's tag
        tagPanel = new JPanel();
        allTagsTextArea = new JTextArea();
        currentPhotoTagsTextArea = new JTextArea();
        currentPhotoOldNamesTextArea = new JTextArea();
        currentPhotoOldNamesScrollPanel = new JScrollPane(currentPhotoOldNamesTextArea);
        allTagsScrollPanel = new JScrollPane(allTagsTextArea);
        currentPhotoTagsScrollPanel = new JScrollPane(currentPhotoTagsTextArea);
        tagPanel.setLayout(new GridLayout(3, 1));
        
//        allTagsTextArea.setPreferredSize(new Dimension(150, 100)); // JScrollPanel中如果TextArea设置固定长度,就没有那个进度栏,不是滚动显示
        allTagsTextArea.setText(PRLogging.getAllTags());

        tagPanel.add(allTagsScrollPanel);
        tagPanel.add(currentPhotoOldNamesScrollPanel);
        tagPanel.add(currentPhotoTagsScrollPanel);
        
        add(tagPanel, BorderLayout.EAST);
        
        chooser = new JFileChooser();  
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "jpeg", "png", "gif");  
        chooser.setFileFilter(filter);  
        //预览  
        chooser.setAccessory(new ImagePreviewer(chooser));  
        //accessory 通常用于显示已选中文件的预览图像  
        chooser.setFileView(new FileIconView(filter, new ImageIcon()));  
        //设置用于检索 UI 信息的文件视图，如表示文件的图标或文件的类型描述。   
  
    }  
      
    private class FileOpenListener implements ActionListener{  
        @Override  
        public void actionPerformed(ActionEvent e) {  
            // TODO Auto-generated method stub  
            chooser.setCurrentDirectory(new File("."));  
            int result = chooser.showOpenDialog(ImageViewerFrame.this);  
            if(result==JFileChooser.APPROVE_OPTION){  
                String name = chooser.getSelectedFile().getPath();  // 显示在displayPanel的图片路径
                currentPhoto = name; // 要显示的图片
                // 两个JScrollPanel分别显示当前图片的所有tag和所有names
                String ctPhotoTags = "This photo's tag:\n";
                if (photoNames.containsKey(currentPhoto)) {
                    ArrayList<String> listTag = photoNames.get(currentPhoto).getCurrentTags();
                    for(String str: listTag) {
                        ctPhotoTags += str;
                        ctPhotoTags += "\n";
                    }

                }
                currentPhotoTagsTextArea.setText(ctPhotoTags);

                ////////
                String ctPhotoNames = "This photo's name and old names:\n";
                String[] spliation = currentPhoto.split("/");
                String realName = spliation[spliation.length-1]; // 不包含路径名的图片名
                ctPhotoNames += realName;
                ctPhotoNames += "\n";

                if(photoNames.containsKey(currentPhoto)) {
                    ArrayList<String> listTag = photoNames.get(currentPhoto).getOldName();
                    for(String str : listTag) {
                        ctPhotoNames += str;
                        ctPhotoNames += "\n";
                    }
                }
                currentPhotoOldNamesTextArea.setText(ctPhotoNames);

                System.out.println(currentPhoto);

                ImageIcon icon=new ImageIcon(name);  
                
                //这个是强制缩放到与组件(Label)大小相同  
                icon = new ImageIcon(icon.getImage().getScaledInstance(displayPanel.getWidth(), displayPanel.getHeight(), Image.SCALE_DEFAULT));
  
                label.setIcon(icon);  
                label.setHorizontalAlignment(SwingConstants.CENTER);  
            }  
        }  
    }  
  
    
}  
