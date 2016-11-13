package view;

import java.awt.Dimension;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class ImagePreviewer extends JLabel{  
    public ImagePreviewer(JFileChooser chooser){  
        setPreferredSize(new Dimension(100,100));  
        setBorder(BorderFactory.createEtchedBorder());  
        chooser.addPropertyChangeListener(new PropertyChangeListener() {  
            public void propertyChange(PropertyChangeEvent event) {  
                if(event.getPropertyName()==JFileChooser.SELECTED_FILE_CHANGED_PROPERTY){  
                    File f=(File) event.getNewValue();  
                    if(f==null){  
                        setIcon(null);  
                        return;  
                    }  
                    ImageIcon icon=new ImageIcon(f.getPath());  
                    icon = new ImageIcon(icon.getImage().getScaledInstance(getWidth(), -1, Image.SCALE_DEFAULT));
//                    System.out.println("176: " + getWidth());
                    setIcon(icon);  
                }  
            }  
        });  
    }  
}  