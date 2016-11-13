package syntax;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by lj on 16/11/13.
 */
public class Syntax {

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(date); // Sun Nov 13 14:13:13 CST 2016
//        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test.txt")))) {
//            bw.append("第一行\n");
//        } catch (IOException e) {
//
//        }
//
//        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test.txt")))) {
//            bw.append("第二行\n");
//        } catch (IOException e) {
//
//        }

//        try {
//            Files.write(Paths.get("test.txt"), "第一行\n".getBytes(), StandardOpenOption.APPEND);
//        }catch (IOException e) {
//            //exception handling left as an exercise for the reader
//        }
//
//        try {
//            Files.write(Paths.get("test.txt"), "第二行\n".getBytes(), StandardOpenOption.APPEND);
//        }catch (IOException e) {
//            //exception handling left as an exercise for the reader
//        }
//        String name = "@a@b.png";
//        String[] array = name.split("\\.");


//        String[] array = name.split("\\.")[0].split("@"); // 一个完整的名字是 @a@b.png
//        System.out.println(array.length);
//        String str = "Contents/Home/bin/java.png";
//        String[] res = str.split("/");
//        System.out.println(res[res.length-1]);
//        String photoFormat = "111.png".split("\\.")[1];
//        System.out.println(photoFormat);
//        Path source = Paths.get("./abc.txt");
//        try {
//            Files.move(source, source.resolveSibling("abcde.txt"));
//        } catch (IOException e) {
//
//        }


        HashMap<String, Integer> map = new HashMap<>();
        map.put("1", 1);
        map.put("1", 2);
        System.out.print(map.get("1"));
    }
}
