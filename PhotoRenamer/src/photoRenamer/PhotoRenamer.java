package photoRenamer;

import java.awt.EventQueue;

import javax.swing.JFrame;

import logging.PRLogging;
import view.ImageViewerFrame;


public class PhotoRenamer {

	
	 public static void main(String[] args) { 
	    	// 先加载日志
	    	PRLogging createLog = new PRLogging();
	    	createLog.createLogFile();
	    	createLog.readLogging();
	    	
	        EventQueue.invokeLater(new Runnable() {  
	            public void run() {  
	                ImageViewerFrame frame = new ImageViewerFrame();  
	                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	                //设置窗体位置在屏幕中央
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);  
	            }  
	        });  
	    }  

}