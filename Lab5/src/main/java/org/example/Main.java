package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

    public class Main {
        private static volatile int counter = 0;
        private static final Object lock = new Object();
        private static final String outputPath = "C:\\Users\\tarab\\Documents\\output.txt";

        public static void main(String[] args) {
            Thread thread1 = new Thread(new WorkerThread("Thread 1", 250, true));
            Thread thread2 = new Thread(new WorkerThread("Thread 2", 500, false));
            Thread thread3 = new Thread(new WorkerThread("Thread 3", 1000, false));

            thread1.start();
            thread2.start();
            thread3.start();

            try {
                Thread.sleep(70000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            thread1.interrupt();
            thread2.interrupt();
            thread3.interrupt();
        }

        private static class WorkerThread implements Runnable {
            private final String threadName;
            private final int interval;
            private final boolean isCounterIncrementThread;

            public WorkerThread(String threadName, int interval, boolean isCounterIncrementThread) {
                this.threadName = threadName;
                this.interval = interval;
                this.isCounterIncrementThread = isCounterIncrementThread;
            }

            @Override
            public void run() {
                while (!Thread.interrupted() && counter < 240) {
                    synchronized (lock) {
                        if (isCounterIncrementThread) {
                            counter++;
                        }

                        try {
                            FileWriter fileWriter = new FileWriter(outputPath, true);
                            PrintWriter printWriter = new PrintWriter(fileWriter);

                            printWriter.println(threadName + " - " + LocalDateTime.now() + " - Counter: " + counter);
                            printWriter.close();

                            lock.wait(interval);
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
        }
    }
