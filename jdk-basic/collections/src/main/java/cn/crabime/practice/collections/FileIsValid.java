package cn.crabime.practice.collections;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class FileIsValid {

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        String str = "D://logs/";
        Constructor<File> constructor = File.class.getConstructor(String.class);
        File f = constructor.newInstance(str);
        Method isInvalidMethod = File.class.getDeclaredMethod("isInvalid");
        isInvalidMethod.setAccessible(true);
        Object result = isInvalidMethod.invoke(f);
        System.out.println(result);

        FileInputStream fileInputStream = new FileInputStream(f);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fileInputStream.read(buffer, 0, buffer.length)) != -1) {
            System.out.println(new String(buffer, 0, len));
        }
        fileInputStream.close();
    }
}
