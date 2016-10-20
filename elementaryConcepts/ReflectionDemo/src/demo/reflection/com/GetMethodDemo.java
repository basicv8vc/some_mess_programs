package demo.reflection.com;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;



public class GetMethodDemo {

    private String str;
    private int i;

    private void testPrivate() {

    }

    protected void testProtected() {

    }

    public void setString(String str) {
        this.str = str;
    }

    public String getString() {
        return str;
    }

    public void setInt(int i) {
        this.i = i;
    }

    public int getInt() {
        return i;
    }

    public void fun() {

    }

    public void fun(String str, int i) {
        System.out.println("String: " + str + ", int: " + i);

    }
    public static void main(String[] args) {

        try{

            Class c = Class.forName("demo.reflection.com.GetMethodDemo"); // 获取Class
            Object o = c.newInstance(); // 新建对象
            Method method = c.getMethod("fun", String.class, int.class); //获取方法
            method.invoke(o, "abc", 1); // 调用方法


        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


        Class classTwo = null;
        try {
            classTwo = Class.forName("demo.reflection.com.GetMethodDemo");
            Method[] methods = classTwo.getDeclaredMethods();
            for(Method m: methods) {
                String methodName = m.getName();
                System.out.println(methodName);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------");

        Class classThree = null;
        try {
            classThree = Class.forName("demo.reflection.com.GetMethodDemo");
            Method[] methods = classThree.getMethods();
            for(Method m: methods) {

                String methodName = m.getName();
                System.out.println(methodName);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
