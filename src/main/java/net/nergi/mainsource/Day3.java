package net.nergi.mainsource;

import net.nergi.utils.FileLoader;
import net.nergi.utils.Pair;
import java.util.HashSet;

@SuppressWarnings("unused")
public class Day3 implements Day {
    private int navigate(String navigation) {
        HashSet<Pair<Integer, Integer>> visited = new HashSet<>();
        visited.add(new Pair<>(0, 0));

        int cx = 0;
        int cy = 0;

        for (char c : navigation.toCharArray()) {
            switch (c) {
                case '^':
                    visited.add(new Pair<>(cx, ++cy));
                    break;
                case '>':
                    visited.add(new Pair<>(++cx, cy));
                    break;
                case 'v':
                    visited.add(new Pair<>(cx, --cy));
                    break;
                case '<':
                    visited.add(new Pair<>(--cx, cy));
                    break;
                default:
                    break;
            }
        }

        return visited.size();
    }

    private int navigateTwo(String navigation) {
        HashSet<Pair<Integer, Integer>> visitedNorm = new HashSet<>();
        HashSet<Pair<Integer, Integer>> visitedRobo = new HashSet<>();
        visitedNorm.add(new Pair<>(0, 0));
        visitedRobo.add(new Pair<>(0, 0));

        int cxn = 0;
        int cyn = 0;
        int cxr = 0;
        int cyr = 0;

        for (int i = 0; i < navigation.length(); ++i) {
            char c = navigation.charAt(i);
            switch (c) {
                case '^':
                    if (i % 2 == 0) {
                        visitedNorm.add(new Pair<>(cxn, ++cyn));
                    } else {
                        visitedRobo.add(new Pair<>(cxr, ++cyr));
                    }
                    break;
                case '>':
                    if (i % 2 == 0) {
                        visitedNorm.add(new Pair<>(++cxn, cyn));
                    } else {
                        visitedRobo.add(new Pair<>(++cxr, cyr));
                    }
                    break;
                case 'v':
                    if (i % 2 == 0) {
                        visitedNorm.add(new Pair<>(cxn, --cyn));
                    } else {
                        visitedRobo.add(new Pair<>(cxr, --cyr));
                    }
                    break;
                case '<':
                    if (i % 2 == 0) {
                        visitedNorm.add(new Pair<>(--cxn, cyn));
                    } else {
                        visitedRobo.add(new Pair<>(--cxr, cyr));
                    }
                    break;
                default:
                    break;
            }
        }

        visitedNorm.addAll(visitedRobo);
        return visitedNorm.size();
    }

    @Override
    public void exec() {
        // Part 1
        String navigation = FileLoader.getRawInputFile("day3.txt");
        assert navigation != null;

        System.out.println(navigate(navigation));

        // Part 2
        System.out.println(navigateTwo(navigation));
    }
}
