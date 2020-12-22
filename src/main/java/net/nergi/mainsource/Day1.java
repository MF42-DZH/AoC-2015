package net.nergi.mainsource;

import java.util.ArrayList;
import net.nergi.utils.*;

@SuppressWarnings("unused")
public class Day1 implements Day {
    @Override
    public void exec() {
        // Pre
        ArrayList<String> inp = FileLoader.getInputFile("day1.txt");
        assert inp != null && inp.size() >= 1;
        String s = inp.get(0);

        // Part 1
        int depth = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                ++depth;
            } else {
                --depth;
            }
        }

        System.out.println(depth);

        // Part 2
        depth = 0;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '(') {
                ++depth;
            } else {
                if (--depth <= -1) {
                    System.out.println(i + 1);
                    break;
                }
            }
        }
    }
}
