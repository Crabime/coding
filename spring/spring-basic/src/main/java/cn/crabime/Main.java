package cn.crabime;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.task.support.ExecutorServiceAdapter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) {
//        ApplicationContext atx = new ClassPathXmlApplicationContext("beans.xml");
        ApplicationContext atx = new ClassPathXmlApplicationContext("beans.xml");
        //如果这里不创建任何spring容器时间,那么在spring容器初始化时还是会启动时间监听
//        EmailEvent event = new EmailEvent("email event", "crabime", "crabime@gmail.com");
        //通过spring容器去发布事件
//        atx.publishEvent(event);
        Teacher t = atx.getBean("teacher", Teacher.class);
        Leader l = atx.getBean("leader", Leader.class);
        t.check();
        System.out.println(l.controllNumber());
        String localeMessage = atx.getMessage("hello", new String[]{"JohnSon"}, Locale.CHINA);
        System.out.println("Global message are : " + localeMessage);

        ThreadPoolTaskExecutor threadPool = atx.getBean("threadPool", ThreadPoolTaskExecutor.class);
        ExecutorServiceAdapter adapter = new ExecutorServiceAdapter(threadPool);
        List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
        Callable<Integer> task = null;
        for (int i = 0; i < 10; i++){
            task = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int time = new Random().nextInt(1000);
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " has slept " + time);
                    return time;
                }
            };
            //submit task and wait to execute
            threadPool.submit(task);
            //add all task to list
            tasks.add(task);
        }

        //get the start time of all threads
        long start = System.currentTimeMillis();
        try {
            List<Future<Integer>> result = adapter.invokeAll(tasks);
            for (int i = 0; i < result.size(); i++){
                System.out.println(result.get(i).get());
            }
        } catch (ExecutionException ex){
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Total time is " + (System.currentTimeMillis() - start));
    }


}
