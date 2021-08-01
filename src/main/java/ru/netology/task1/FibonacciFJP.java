package ru.netology.task1;

import java.util.concurrent.RecursiveTask;

public class FibonacciFJP extends RecursiveTask<Integer> {
    private final int n;

    public FibonacciFJP(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n < 30) { return fibonacci(n); }
        FibonacciFJP task1 = new FibonacciFJP(n - 1);
        FibonacciFJP task2 = new FibonacciFJP(n - 2);
        invokeAll(task1, task2);
        return task1.join() + task2.join();
    }

    private static int fibonacci(int n) {
        if (n == 0) { return 0; }
        if (n == 1) { return 1; }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
