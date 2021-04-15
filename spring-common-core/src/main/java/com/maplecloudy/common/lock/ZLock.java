package com.maplecloudy.common.lock;

/**
 * 锁对象抽象
 * 
 */
public class ZLock implements AutoCloseable {
  private final Object lock;
  
  private final DistributedLock locker;
  
  public ZLock(Object lock, DistributedLock locker) {
    super();
    this.lock = lock;
    this.locker = locker;
  }

  @Override
  public void close() throws Exception {
    locker.unlock(lock);
  }

  public Object getLock() {
    return lock;
  }

  public DistributedLock getLocker() {
    return locker;
  }
}
