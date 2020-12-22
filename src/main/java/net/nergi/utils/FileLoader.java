package net.nergi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {
    public static final String LB = System.lineSeparator();
    public static final String SP = File.separator;

    public static final String WORK_DIR = Paths.get("").toAbsolutePath().toString();
    public static final String INPUT_DIR = WORK_DIR + SP + "inputs" + SP;

    public static ArrayList<String> getInputFile(String name) {
        File f = new File(INPUT_DIR + name);

        try {
            Scanner scn = new Scanner(f);
            ArrayList<String> contents = new ArrayList<>();

            while (scn.hasNextLine()) {
                contents.add(scn.nextLine());
            }

            scn.close();
            return contents;

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static String getRawInputFile(String name) {
        File f = new File(INPUT_DIR + name);

        try {
            Scanner scn = new Scanner(f);
            StringBuilder contents = new StringBuilder();

            while (scn.hasNextLine()) {
                contents.append(scn.nextLine());
            }

            return contents.toString();
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static ArrayList<String> getGroupedInputFile(String name) {
        File f = new File(INPUT_DIR + name);

        try {
            Scanner scn = new Scanner(f);
            ArrayList<String> groups = new ArrayList<>();

            while (scn.hasNext(LB + LB)) {
                groups.add(scn.next(LB + LB));
            }

            return groups;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static <T> ArrayList<T> getProcessedInputFile(String name, StringProcessor<T> processor) {
        ArrayList<String> lines = getInputFile(name);

        if (lines != null) {
            ArrayList<T> out = new ArrayList<>();
            lines.forEach((str) -> out.add(processor.process(str)));

            return out;
        } else {
            return null;
        }
    }
}
