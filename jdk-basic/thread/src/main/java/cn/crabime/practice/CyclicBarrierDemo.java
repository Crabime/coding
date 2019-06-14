package cn.crabime.practice;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by crabime on 1/24/18.
 * 使用CyclicBarrier
 */
public class CyclicBarrierDemo {

    public static class Soldier implements Runnable{
        private String soldierName;
        private final CyclicBarrier barrier;

        public Soldier(String soldierName, CyclicBarrier barrier) {
            this.soldierName = soldierName;
            this.barrier = barrier;
        }

        public void run() {
            try {
                barrier.await();
                doWork();
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

        void doWork(){
            try {
                Thread.sleep(Math.abs(new Random().nextInt()%10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.soldierName + "任务执行完毕!");
        }
    }

    public static class BarrierRun implements Runnable{
        boolean flag;
        int N;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            N = n;
        }

        public void run() {
            if (flag){
                System.out.println("司令:[士兵" + N + "个,任务完成!]");
            }else {
                System.out.println("司令:[士兵" + N + "个,集合完成!]");
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        Thread[] allSoldiers = new Thread[10];
        boolean flag = false;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(flag, N));
        System.out.println("集合队伍");
        for (int i = 0; i < N; i++){
            System.out.println("士兵" + i + "来报道!");
            allSoldiers[i] = new Thread(new Soldier("士兵" + i, cyclicBarrier));
            allSoldiers[i].start();
        }
    }
}
