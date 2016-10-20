package demo.reflection.com;

/**
 * Created by lj on 16/10/20.
 */
public class GetClassDemo {

    public static void main(String[] args) throws ClassNotFoundException {

        // 获取类
        // First Method to get Class
        Class classOne = GetClassDemo.class;
        System.out.println(classOne.getName()); // demo.reflection.com.GetClassDemo
        // Second Method to get Class
        Class classTwo = Class.forName("demo.reflection.com.GetClassDemo");
        System.out.println(classTwo.getName()); // demo.reflection.com.GetClassDemo
        // Third Method to get Class
        GetClassDemo getClassDemo = new GetClassDemo();
        System.out.println(getClassDemo.getClass().getName());



    }
}
