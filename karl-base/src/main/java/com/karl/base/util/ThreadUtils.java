package com.karl.base.util;


import com.karl.base.aspect.ThreadLocalCacheAspect;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * 多线程测试工具类
 *
 * @author karl
 * @date 2019/8/5
 */
@Slf4j
public class ThreadUtils {

    public static void run(int count, Consumer<Integer> c) {
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            final int index = i;
            new Thread(() -> {
                long s = System.currentTimeMillis();
                try {
                    ThreadLocalCacheAspect.init();
                    c.accept(index);
                } finally {
                    countDownLatch.countDown();
                    ThreadLocalUtils.clear();
                }
                log.debug(String.format("第%sThread: ", index) + (System.currentTimeMillis() - s));
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
