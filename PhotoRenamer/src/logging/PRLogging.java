package logging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class PRLogging {
	public static volatile Map<String, Names> photoNames; // 每个图片的路径以及所有的名字(当前和旧名字)。所有的名字用Names类表示,而Names中当前名字用Image类表示
	public List<Record> records; // 记录每次命名操作

	public PRLogging() {
		photoNames = new HashMap<String, Names>();
		records = new LinkedList<Record>();

	}

    /**
     *
     * @return 返回photoNames中包含的所有tag, 也就是所有图片现在的名字中包含的tag
     */
	public static String getAllTags() {
	    Set<String> tags = new HashSet<String >();
        for( Map.Entry<String, Names> entry : photoNames.entrySet()) {
            ArrayList<String > ctags = entry.getValue().getCurrentTags(); // 当前图片的tag
            for(String str : ctags)
                tags.add(str);

        }

        String string = "All tags:\n";
        for( String tag: tags) {
            string += tag;
            string += "\n";
        }

        return string;

    }

    /**
     *
     * @return 系统中出现次数最多的tag
     */
    public static String getMostCommomTags() {
        String mostCommomTag = "";
        int mostCounts = 0;

        Map<String, Integer> tag2count = new HashMap<>();
        for (Map.Entry<String, Names> entry : photoNames.entrySet()) {
            ArrayList<String > ctags = entry.getValue().getCurrentTags(); // 当前图片的tag
            for (String tag: ctags) {
                if (tag2count.containsKey(tag)) {
                    tag2count.put(tag, tag2count.get(tag)+1);
                }
                else
                    tag2count.put(tag, 1);
            }

        }// for

        for(Map.Entry<String, Integer> entry : tag2count.entrySet()) {
            if (entry.getValue() > mostCounts) {
                mostCommomTag = entry.getKey();
                mostCounts = entry.getValue();
            }
        }

        return mostCommomTag;

    }


	public void createLogFile() {
		// 创建目录
		File theDir = new File("logs");
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			boolean result = false;

			try{
				theDir.mkdir();
				result = true;
			}
			catch(SecurityException se){
				//handle it
			}
			if(result) {
				System.out.println("DIR created");
			}
		}

		// 检查文件是否存在, 记录每个图片的所有名字
		try {
	        File file = new File("logs/allNames.txt");
	        if (!file.exists()) {
	            file.createNewFile();
	        }
//	        else { // 追加内容
//	            FileOutputStream writer = new FileOutputStream("logs/allNames.txt");
//	            writer.write(("").getBytes());
//	            writer.close();
//	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

		// 后台日志，记录每次修改操作
		try {
	        File file = new File("logs/photoRenamer.log");
	        if (!file.exists()) {
	            file.createNewFile();
	        }
//	        else { // 追加内容
//	            FileOutputStream writer = new FileOutputStream("logs/photoRenamer.log");
//	            writer.write(("").getBytes());
//	            writer.close();
//	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	}
	/**
	 *  读取log，运行时会改变logging
	 */
	public void readLogging() {

		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("logs/allNames.txt"), "UTF-8"))) {
			String line;
			while((line = br.readLine()) != null) { // 将allNames.txt 中的记录保存到photoNames
				String[] array = line.split("&&"); // array[0]是图片的路径;array[1]是图片的现在名字;array[2]往后的是图片以前的名字
				int length = array.length;
				Names names = new Names();
				names.setCurrentName(array[1]); // currentName 可能是含有标签形式,也可能不含有标签,Image表示图片的所有标签
                ArrayList<String> oldnames = new ArrayList<String>();
                for(int i=2;i<length;i++)
                    oldnames.add(array[i]);
                names.setOldName(oldnames); // 设置图片的所有旧名字

				photoNames.put(array[0], names); // URL 和 Names

			}
		} catch (IOException ex) {

		}

	}

}
