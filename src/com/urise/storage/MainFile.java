package com.urise.storage;

import java.io.File;

public class MainFile {
    public static void showAllFileInDirectory(File directory, String space) {
        File[] root = directory.listFiles();

        if (root != null) {
            for (File next : root) {
                if (next.isFile()) {
                    System.out.println(space + "Next file is : " + next.getName());
                } else if (next.isDirectory()) {
                    System.out.println(space + "The current directory is " + next.getName());
                    showAllFileInDirectory(next, space + "  ");
                }
            }
        }
    }
}
