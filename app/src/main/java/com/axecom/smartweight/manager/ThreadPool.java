package com.axecom.smartweight.manager;

import com.luofx.utils.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * Created by Administrator
 *
 * @author 猿史森林
 * Date: 2017/11/2
 * Class description:
 */
public class ThreadPool {

    private static ThreadPool threadPool;
    /**
     * java线程池
     */
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 最大线程数
     */
    private final static int MAX_POOL_COUNTS = 10;

    /**
     * 线程存活时间
     */
    private final static long ALIVETIME = 200L;

    /**
     * 核心线程数
     */
    private final static int CORE_POOL_SIZE = 5;

    /**
     * 线程池缓存队列
     */

    private ThreadPool() {
        BlockingQueue<Runnable> mWorkQueue = new ArrayBlockingQueue<>(CORE_POOL_SIZE);
        ThreadFactory threadFactory = new ThreadFactoryBuilder("ThreadPool");
        threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_COUNTS, ALIVETIME, TimeUnit.SECONDS, mWorkQueue, threadFactory);

    }

    public static ThreadPool getInstantiation() {
        if (threadPool == null) {
            threadPool = new ThreadPool();
        }
        return threadPool;
    }

    public void addTask(Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("addTask(Runnable runnable)传入参数为空");
        }
        if (threadPoolExecutor != null && threadPoolExecutor.getActiveCount() < MAX_POOL_COUNTS) {
            threadPoolExecutor.execute(runnable);
        }
    }

    public void stopThreadPool() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
            threadPoolExecutor = null;
        }
    }
}
