package logging;

import java.util.ArrayList;


// 记录每一副图片的名字
public class Names {
	
	private ArrayList<String> oldName;
	private photoRenamer.Image image; // 记录当前名字和当前名字中的tag
	
	public Names() {
		oldName = new ArrayList<String>();
	}
	
	/**
	 *  设置旧名字 oldName
	 * @param name
	 */
	public void setOldName(ArrayList<String> name) {
		for(String str : name)
			oldName.add(str);
	}

    public void setOldName(String name) {
        oldName.add(name);
    }
	/**
	 * 
	 * @param name 初始化image
	 */
	public void setCurrentName(String name) {
		if (name.contains("@")) {
			ArrayList<String> tags = new ArrayList<String>();
			String[] array = name.split("\\.")[0].split("@"); // 一个完整的名字是 @a@b.png, "@a@b"按照@分割是3部分
			for(int j=1;j<array.length;j++) {
                tags.add(array[j]);

            }
			image = new photoRenamer.Image(name, tags);
		}
		else
			image = new photoRenamer.Image(name, new ArrayList<String>()); // 很可能当前的名字不包含标签! WWS.jpg
	}
	
	/**
	 * 
	 * @return 图片的旧名字，可能很多个
	 */
	public ArrayList<String> getOldName() {
		return oldName;
	}
	
	/**
	 * 
	 * @return 图片的名字
	 */
	public String getCurrentName() {
		return image.name;
	}
	
	/**
	 * 
	 * @return 当前图片的标签
	 */
	public ArrayList<String> getCurrentTags() {
		return image.tags;
	}

}
