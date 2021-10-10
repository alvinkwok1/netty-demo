package cn.alvinkwok.async_sync;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultFuture implements SyncFuture<Object> {
    // 超时时间
    private final long timeout;
    // 响应对象
    private Object response;
    // 请求响应是否完成
    private boolean done = false;
    // 同步工具
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private static final Map<Channel, DefaultFuture> FUTURES = new ConcurrentHashMap<>();

    public DefaultFuture(Channel channel, long timeout) {
        this.timeout = timeout;
        FUTURES.put(channel, this);
    }

    public Object get() throws InterruptedException {
        lock.lock();
        try {
            long start = System.currentTimeMillis();
            while (!isDone()) {
                condition.await(timeout, TimeUnit.MILLISECONDS);
                boolean isTimeout = System.currentTimeMillis() - start > timeout;
                if (isDone() || isTimeout) {
                    if (isTimeout) {
                        System.out.println("接收数据超时");
                    }
                    break;
                }
            }

        } finally {
            lock.unlock();
        }

        return response;
    }

    public static void receive(Channel channel, Object msg) {
        DefaultFuture future = FUTURES.remove(channel);
        if (future != null) {
            future.doReceive(msg);
        }
    }

    public void doReceive(Object msg) {
        lock.lock();
        try {
            done = true;
            response = msg;
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean isDone() {
        return done;
    }

}
