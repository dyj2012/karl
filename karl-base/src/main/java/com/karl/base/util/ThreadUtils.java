package com.karl.base.util;


import com.karl.base.aspect.ThreadLocalCacheAspect;

import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

/**
 * 多线程测试工具类
 *
 * @author 杜永军
 * @date 2019/8/5
 */
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
                System.out.println(String.format("第%sThread: ", index) + (System.currentTimeMillis() - s));
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
