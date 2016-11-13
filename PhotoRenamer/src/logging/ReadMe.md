allNames.txt 记录每个图片（被chooser选中过的图片)的URL，current name, old name (注意可能多次命名，所以有多个old name），
URL&&currentname&&oldname1&&oldname2 // 这里的name都不需要有路径，URL里面包含路径即可

photoRenamer.log 记录所有的操作
oldName&&newName&&TimeStamp // 注意这里的oldname是带有完全路径的图片名
每次有改名操作,直接 添加到文件
try {
            Files.write(Paths.get("test.txt"), "第二行\n".getBytes(), StandardOpenOption.APPEND);
        }catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
        
        
        
createLog 类中public Map<String, Names> photoNames; 表示所有图片的路径和名字。Names类中包含两个域:
private ArrayList<String> oldName;
private photoRenamer.Image image; // 记录当前名字和当前名字中的tag
其中Image表示当前名字和名字中的tag。
	
	


每次启动程序，先加载logging，用map还是list表示所有的图片？？？


每次选中新图片，查看是否在logging中，如果在，记录新的变化，如果不在，则添加到logging中，











///////////////////////////////////////////////////////////////
GUI

分三部分,
最左边显示图片,中间是控制栏,包含删除tag,添加tag等操作,这些操作仅针对显示的图片,所以实际上是对图片进行的命名操作
右边分上下三部分:显示OS所有的tag;显示当前图片的tag,显示当前图片的所有历史名字和当前名字








