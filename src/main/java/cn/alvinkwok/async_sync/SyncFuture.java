package cn.alvinkwok.async_sync;

public interface SyncFuture<T> {
    /**
     * 没有结果之前进行等待，有结果则返回
     * @throws InterruptedException
     */
    T get() throws InterruptedException;

    /**
     * 当前操作是否已经完成
     * @return true完成，false未完成
     */
    boolean isDone();
}