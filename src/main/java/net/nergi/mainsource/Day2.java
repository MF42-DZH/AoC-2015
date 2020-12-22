package net.nergi.mainsource;

import net.nergi.utils.FileLoader;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Day2 implements Day {
    private Long calculateArea(String str) {
        String[] splitted = str.replace(FileLoader.LB, "").split("x");

        Long l = Long.parseLong(splitted[0]);
        Long w = Long.parseLong(splitted[1]);
        Long h = Long.parseLong(splitted[2]);

        long lw = l * w;
        long wh = w * h;
        long lh = l * h;

        long slack = Math.min(lw, Math.min(wh, lh));

        return (2L * lw) + (2L * wh) + (2L * lh) + slack;
    }

    private Long calculateRibbon(String str) {
        String[] splitted = str.replace(FileLoader.LB, "").split("x");

        Long l = Long.parseLong(splitted[0]);
        Long w = Long.parseLong(splitted[1]);
        Long h = Long.parseLong(splitted[2]);

        long ribbon = Math.min(l + l + w + w, Math.min(w + w + h + h, l + l + h + h));

        return (l * w * h) + ribbon;
    }

    @Override
    public void exec() {
        // Part 1
        ArrayList<Long> areas = FileLoader.getProcessedInputFile("day2.txt", this::calculateArea);
        assert areas != null;

        long total = 0L;
        for (Long area : areas) {
            total += area;
        }

        System.out.println(total);

        // Part 2
        ArrayList<Long> ribbons = FileLoader.getProcessedInputFile("day2.txt", this::calculateRibbon);
        assert ribbons != null;

        long totalR = 0L;
        for (Long ribbon : ribbons) {
            totalR += ribbon;
        }

        System.out.println(totalR);

    }
}
