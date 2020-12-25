package net.nergi.mainsource;

import net.nergi.utils.FileLoader;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Day8 implements Day {
    private static int countStrMemSize(String str) {
        boolean escapeNext = false;
        int res = 0;

        for (int i = 1; i < str.length() - 1; ++i) {
            char c = str.charAt(i);

            if (c == '\\' && !escapeNext) {
                escapeNext = true;
            } else {
                if (escapeNext && c == 'x') {
                    i += 2;
                }
                ++res;

                escapeNext = false;
            }
        }

        return res;
    }

    private static String convertToCode(String str) {
        final StringBuilder sb = new StringBuilder("\"");

        for (char c : str.toCharArray()) {
            switch (c) {
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\"':
                    sb.append("\\\"");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }

        sb.append("\"");
        return sb.toString();
    }

    @Override
    public void exec() {
        // Pre
        final ArrayList<String> inputLines = FileLoader.getInputFile("day8.txt");
        assert inputLines != null;

        // Test strings
        assert countStrMemSize("\\n") == 1;
        assert countStrMemSize("\\x20") == 1;
        assert countStrMemSize("a\\x20") == 2;
        assert countStrMemSize("\\\\n") == 2;

        // Part 1
        final int rawCharCount = inputLines.stream().mapToInt(String::length).sum();
        final int memCharCount = inputLines.stream().mapToInt(Day8::countStrMemSize).sum();

        System.out.println(rawCharCount - memCharCount);

        // Part 2
        assert convertToCode("").equals("\"\\\"\\\"\"");
        assert convertToCode("\\x27").equals("\"\\\\x27\"");

        final int recodeCharCount = inputLines.stream().map(Day8::convertToCode).mapToInt(String::length).sum();

        System.out.println(recodeCharCount - rawCharCount);
    }
}
