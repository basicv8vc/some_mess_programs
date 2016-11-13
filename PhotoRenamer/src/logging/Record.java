package logging;

/**
 *  后台记录的每次更改名字的记录
 * @author lj
 *
 */
public class Record {
	
	private String oldName; // 旧名字, 带有完全路径的形式
	private String currentName;
	private String timeStamp;

	public Record(String oldName, String currentName, String date) {
	    this.oldName = oldName;
        this.currentName = currentName;
        this.timeStamp = date;
    }
	

}
