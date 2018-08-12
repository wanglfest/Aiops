package com.sinosoft.utils;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池工具类 <br/>
 * 默认5个核心线程，10个拓展线程，60秒存活时间，可接受任务没有阻塞
 * @author vampire
 *
 */
public final class ThreadUtils {
    private static ThreadPoolExecutor pool;
    private static final Logger log = LoggerFactory.getLogger(ThreadUtils.class);
    static {
        log.info("init thread pool ...");
        // 创建线程池
        pool = new ThreadPoolExecutor(10, 50, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    }

    /**
     * 停止线程池
     */
    public static void shutdown() {
        if (pool != null) {
            log.info("thread pool shutdown ...");
            pool.shutdown();
        }
    }

    /**
     * 执行一个任务
     * @param task
     */
    public static void execute(Runnable task) {
        pool.execute(task);
    }

    /**
     * 执行一个任务并返回执行结果
     * @param task 任务
     * @param result 执行结果
     * @return
     */
    public static <T> Future<T> submit(Runnable task, T result) {
        return pool.submit(task, result);
    }
}
