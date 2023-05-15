package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array1 = new int[10_000];
        int[] array2 = new int[10_000];
        int[] arrayResult1 = new int[10_000];

        for (int i = 0; i < 10_000; i++) {
            array1[i] = (int) (Math.random() * 101);
            array2[i] = (int) (Math.random() * 101);
        }

        // Варіант 1.1 рахування всіх елементів масиву зі змінною
        // sleep = 1
        long time = System.currentTimeMillis();

        for (int i = 0; i < 10_000; i++) {
            arrayResult1[i] = array1[i] * array2[i];

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.printf("sync1.1 : %s\n", System.currentTimeMillis() - time);


        // Варіант 1.2 рахування всіх елементів масиву зі змінною
        // sleep = 0
        time = System.currentTimeMillis();

        for (int i = 0; i < 10_000; i++) {
            arrayResult1[i] = array1[i] * array2[i];

            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.printf("sync1.2 : %s\n", System.currentTimeMillis() - time);



        // Варіант 2.1 рахування всіх елементів масиву зі змінною
        // sleep = 1
        int[] arrayResult2 = new int[10_000];
        time = System.currentTimeMillis();

        Arrays.parallelSetAll(arrayResult2, i -> {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return array1[i] * array2[i];
        });

        System.out.printf("sync2.1 : %s\n", System.currentTimeMillis() - time);


        // Варіант 2.2 рахування всіх елементів масиву зі змінною
        // sleep = 0
        time = System.currentTimeMillis();

        Arrays.parallelSetAll(arrayResult2, i -> {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return array1[i] * array2[i];
        });

        System.out.printf("sync2.2 : %s\n", System.currentTimeMillis() - time);


        System.out.println("\nПорівняння результатів після використання двох різних варіантів:");

        for (int i = 0; i < 10_000; i++) {
            System.out.println(arrayResult1[i] + " : " + arrayResult2[i]);
        }
    }
}