package io.vividcode.taskexecutor;

import java.util.concurrent.ThreadLocalRandom;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorExecutor {

  public static void main(String[] args) {
    int numOfTasks = 10;
    Flux.range(0, numOfTasks).flatMap(index -> Mono.fromCallable(() -> {
      Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
      System.out.println(String.format("[%s] Run %d", Thread.currentThread().getName(), index));
      return null;
    }).publishOn(Schedulers.elastic())).blockLast();
  }
}
