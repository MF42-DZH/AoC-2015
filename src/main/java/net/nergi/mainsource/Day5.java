package net.nergi.mainsource;

import net.nergi.utils.FileLoader;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Day5 implements Day {
    private boolean validateString(String str) {
        // Check for disallowed character pairs.
        if (str.contains("ab") || str.contains("cd") || str.contains("pq") || str.contains("xy")) {
            return false;
        }

        // Check for three or more vowels.
        boolean vowels;
        int vs = 0;
        for (char c : str.toCharArray()) {
            if ("aeiou".contains("" + c)) {
                ++vs;
            }
        }

        vowels = vs >= 3;

        // Check for double characters.
        boolean doubled = false;
        for (int i = 0; i < str.length() - 1; ++i) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                doubled = true;
                break;
            }
        }

        return vowels && doubled;
    }

    private boolean validateStringRedo(String str) {
        // Check for pairing.
        boolean hasPairing = false;
        for (int i = 0; i < str.length() - 2; ++i) {
            if (str.charAt(i) == str.charAt(i + 2)) {
                hasPairing = true;
                break;
            }
        }

        boolean hasRepeatingPair = false;
        for (int i = 0; i < str.length() - 1 && !hasRepeatingPair; ++i) {
            String pair = str.charAt(i) + "" + str.charAt(i + 1);
            for (int j = i + 2; j < str.length() - 1; ++j) {
                String np = str.charAt(j) + "" + str.charAt(j + 1);

                if (pair.equals(np)) {
                    hasRepeatingPair = true;
                    break;
                }
            }
        }

        return hasPairing && hasRepeatingPair;
    }

    @Override
    public void exec() {
        // Pre
        ArrayList<String> strings = FileLoader.getInputFile("day5.txt");
        assert strings != null;

        // Part 1
        int cnt = (int) strings.stream().filter(this::validateString).count();
        System.out.println(cnt);

        // Part 2
        System.out.println(validateStringRedo("qjhvhtzxzqqjkmpb"));
        System.out.println(validateStringRedo("xxyxx"));
        System.out.println(validateStringRedo("uurcxstgmygtbstg"));
        System.out.println(validateStringRedo("ieodomkazucvgmuy"));

        cnt = (int) strings.stream().filter(this::validateStringRedo).count();
        System.out.println(cnt);
    }
}
