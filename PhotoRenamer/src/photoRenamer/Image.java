package photoRenamer;
import java.util.ArrayList;


// 只保存当前名字，和当前名字中含有的tag
public class Image {
	public ArrayList<String> tags;
	public String name;
	
	public Image(String name, ArrayList<String> tags) {
		this.tags = tags;
		this.name = name;
	}
	
	public void addTag(String tag) {
		this.tags.add(tag);
	}
	public void addTag(ArrayList<String> tagArray) {
		this.tags.addAll(tagArray);
	}
	public void delTag(String tag) {
		this.tags.remove(tag);
	}
	public void delTag(ArrayList<String> tagArray) {
		this.tags.removeAll(tagArray);
	}
	
}
