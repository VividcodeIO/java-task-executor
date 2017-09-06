package io.vividcode.taskexecutor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CompletableFeatureExecutor {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    CompletableFuture.allOf(
        IntStream.range(0, 10)
            .mapToObj(count -> CompletableFuture.supplyAsync(() -> {
              try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
                System.out.println("Run #" + count);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              return null;
            }))
            .collect(Collectors.toList())
            .toArray(new CompletableFuture[0]))
        .get();
    System.out.println("Done!");
  }
}
