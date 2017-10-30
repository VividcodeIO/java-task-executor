package io.vividcode.taskexecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class SimpleExecutor {

  public static void main(String[] args) {
    int numOfTasks = 10;
    CountDownLatch latch = new CountDownLatch(numOfTasks);
    IntStream.range(0, numOfTasks)
        .mapToObj(count -> new Thread(new Task(latch, count)))
        .forEach(Thread::start);
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("Done!");
  }

  private static class Task implements Runnable {

    private final CountDownLatch latch;
    private final int count;

    private Task(CountDownLatch latch, int count) {
      this.latch = latch;
      this.count = count;
    }

    @Override
    public void run() {
      try {
        Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
        System.out.println(String.format("[%s] Run %d", Thread.currentThread().getName(), count));
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        latch.countDown();
      }
    }
  }

}
