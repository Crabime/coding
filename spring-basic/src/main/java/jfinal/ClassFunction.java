package jfinal;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by crabime on 6/28/17.
 */
public class ClassFunction {
    private static char dot = '.';
    private static char sprit = '/';

    /**
     * 从package中获取所有的class
     * @return
     */
    public static Set<Class<?>> getClasses(String pack){
        Set<Class<?>> classes = new LinkedHashSet<>();
        boolean recursive = true;
        String packageName = pack;
        String packageDirName = packageName.replace(dot, sprit);
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while(dirs.hasMoreElements()){
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                //如果是以file开头的协议
                if("file".equals(protocol)){
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                }else if ("jar".equals(protocol)){
                    JarFile jar;
                    jar = ((JarURLConnection)url.openConnection()).getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()){
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        if (name.charAt(0) == sprit){
                            name = name.substring(1);
                        }
                        if (name.startsWith(packageDirName)){
                            int idx = name.lastIndexOf(sprit);
                            if (idx != -1){
                                packageName = name.substring(0, idx).replace(sprit, dot);
                            }
                            if ((idx != -1) || recursive){
                                if (name.endsWith(".class") && !entry.isDirectory()){
                                    String className = name.substring(packageName.length() + 1, name.length() - 6);
                                    try {
                                        classes.add(Class.forName(packageName + dot + className));
                                    }catch (ClassNotFoundException e){
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;

    }

    /**
     * 以文件的形式来获取包下的所有class
     */
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, boolean recursive, Set<Class<?>> classes){
        File dir = new File(packagePath);
        if(!dir.exists() || !dir.isDirectory()){
            return;
        }
        File[] dirFiles = dir.listFiles(new FileFilter(){

            @Override
            public boolean accept(File file) {
                //拿到所有目录或者.class文件
                return (recursive && file.isDirectory() || (file.getName().endsWith(".class")));
            }
        });
        //循环列出所有文件
        for (File file : dirFiles){
            if(file.isDirectory()){
                //递归的拿到.class文件(排除空文件夹)
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            }else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                System.out.println("className is : " + className);
                try {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + dot + className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
