package net.nergi;

import net.nergi.mainsource.Day;
import net.nergi.utils.FileLoader;
import java.lang.reflect.*;

public class AoC2015 {
    public static void main(String[] args) {
        System.out.println("Working from: " + FileLoader.INPUT_DIR);
        if (args.length >= 1) {
            System.out.println("Received day input: " + args[0]);
        } else {
            System.out.println("Please give the day number as an argument.");
            return;
        }

        try {
            Class<?> clazz = Class.forName("net.nergi.mainsource.Day" + args[0]);
            Constructor<?> ctor = clazz.getConstructor();

            Object dayClass = ctor.newInstance();
            if (dayClass instanceof Day) {
                ((Day) dayClass).exec();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Invalid day number! [" + args[0] + "]");
        } catch (NoSuchMethodException
            | IllegalAccessException
            | InstantiationException
            | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
