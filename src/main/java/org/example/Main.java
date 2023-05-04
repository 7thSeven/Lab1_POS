package org.example;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File currentDir = new File(".");
        printFiles(currentDir, 0);
    }

    public static void printFiles(File dir, int depth) {
        String prefix = "";
        for (int i = 0; i < depth; i++) {
            prefix += "\t";
        }

        System.out.println(prefix + dir.getName());

        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    printFiles(file, depth + 1);
                }
            }
        }
    }
}