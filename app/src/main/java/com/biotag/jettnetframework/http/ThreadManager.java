package com.biotag.jettnetframework.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public  class ThreadManager {
    private static ThreadManager threadManager = new ThreadManager();
    public static ThreadManager getInstance(){
        return threadManager;
    }


    //请求队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();
    //往队列中加入任务
    void addTask(Runnable runnable){
        if(runnable==null){
            return;
        }
        try {
            mQueue.add(runnable);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //需要一个线程池（处理中心）
    private ThreadPoolExecutor threadPoolExecutor;
    private ThreadManager(){

        threadPoolExecutor = new ThreadPoolExecutor(3, 10, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //r就是被丢出来的任务
                addTask(r);
            }
        });
        threadPoolExecutor.execute(runnable);
    }
    /**
     * 核心线程，完成整个运作过程
     */
    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                //从队列中取到任务，再放到处理中心进行执行
                try {
                    Runnable take = mQueue.take();
                    threadPoolExecutor.execute(take);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
}
