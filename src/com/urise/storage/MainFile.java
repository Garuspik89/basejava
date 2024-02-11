package com.urise.storage;

import java.io.File;

public class MainFile {
    public static void showAllFileInDirectory(File directory) {
        File[] root = directory.listFiles();

        if (root != null) {
            for (File next : root) {
                if (next.isFile()) {
                    System.out.println("Next file is : " + next.getName());
                } else if (next.isDirectory()) {
                    showAllFileInDirectory(next);
                }
            }
        }
    }
}
