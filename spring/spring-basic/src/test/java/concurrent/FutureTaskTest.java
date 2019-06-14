package concurrent;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by crabime on 9/9/17.
 */
@RunWith(JUnit4.class)
public class FutureTaskTest {

    /**
     * simple futuretask demo
     */
    @Test
    public void testSimpleTaskTest(){
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "hello";
            }
        });

        try {
            task.run();
            String result = task.get();
            assertThat(result).isEqualTo("hello");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSimpleTaskWithExactResult(){
        String result = "结果";
        FutureTask<String> task = new FutureTask<String>(new Runnable() {
            @Override
            public void run() {
                System.out.println("execute process");
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, result);
        task.run();
        System.out.println("whether has been cancelled : " + task.isCancelled());
        if (!task.isCancelled()){
            task.cancel(true);
        }
        try {
            String s = task.get();
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
