package v1ch14.ForkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

/**
 * @program: practice-demos
 * @description: this program demonstrates the fork-join framework
 * @author: xiaoboji
 * @create: 2020-08-20 00:33
 */
public class ForkJoinTest {

  public static void main(String[] args) {
    final int SIZE = 100000000;
    double[] numbers = new double[SIZE];
    for (int i = 0; i < SIZE; i++) {
      numbers[i] = Math.random();
    }
    Counter counter = new Counter(numbers, 0, numbers.length, x -> x > 0.5);
    ForkJoinPool pool = new ForkJoinPool();
    pool.invoke(counter);
    System.out.println(counter.join());
  }
}

class Counter extends RecursiveTask<Integer> {

  public static final int THRESHOLD = 1000;
  private double[] values;
  private int from;
  private int to;
  private DoublePredicate filter;

  public Counter(double[] values, int from, int to, DoublePredicate filter) {
    this.values = values;
    this.from = from;
    this.to = to;
    this.filter = filter;
  }

  @Override
  protected Integer compute() {
    if (to - from < THRESHOLD) {
      int count = 0;
      for (int i = from; i < to; i++) {
        if (filter.test(values[i])) {
          count++;
        }
      }
      return count;
    } else {
      int mid = (from + to) / 2;
      Counter first = new Counter(values, from, mid, filter);
      Counter second = new Counter(values, mid, to, filter);
      invokeAll(first, second);
      return first.join() + second.join();
    }
  }
}