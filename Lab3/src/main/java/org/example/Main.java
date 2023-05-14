package org.example;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Яке за рахунком число Фібоначчі ви хочете порахувати?");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // n - номер числа Фібоначчі, яке потрібно обчислити

        System.out.println("Початок обчислення...");
        CompletableFuture<BigInteger> future = CompletableFuture.supplyAsync(() -> fibonacci(n));

        try {
            System.out.println("Очікування завершення обчислень...");
            BigInteger result = future.get();
            System.out.println("Отримане значення: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static BigInteger fibonacci(int n) {
        if (n <= 1)
            return BigInteger.valueOf(n);
        else {
//            return fibonacci(n - 1) + fibonacci(n - 2);

            BigInteger a = BigInteger.ZERO;
            BigInteger b = BigInteger.ONE;

            for (int i = 2; i <= n; i++) {
                BigInteger temp = a.add(b);
                a = b;
                b = temp;
            }

            return b;
        }

    }
}