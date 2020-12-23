package net.nergi.mainsource;

import net.nergi.utils.FileLoader;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day6 implements Day {
    private static final Pattern INST_PATTERN = Pattern.compile("(turn on|toggle|turn off) (\\d+),(\\d+) through (\\d+),(\\d+)");

    private static class Instruction {
        public String inst;
        public int[] range1;
        public int[] range2;

        public Instruction(String i, int[] r1, int[] r2) {
            inst = i;
            range1 = r1.clone();
            range2 = r2.clone();
        }
    }

    private static int[][] generateLights() {
        int[][] bs = new int[1000][];
        for (int i = 0; i < 1000; ++i) {
            bs[i] = new int[1000];
        }

        return bs;
    }

    private Instruction parseInstruction(String instLine) {
        final Matcher m = INST_PATTERN.matcher(instLine);

        if (m.find()) {
            final String inst = m.group(1);
            final String r1x = m.group(2);
            final String r1y = m.group(3);
            final String r2x = m.group(4);
            final String r2y = m.group(5);

            return new Instruction(
                inst,
                new int[] {
                    Integer.parseInt(r1x),
                    Integer.parseInt(r1y)
                },
                new int[] {
                    Integer.parseInt(r2x),
                    Integer.parseInt(r2y)
                }
            );
        } else {
            return null;
        }
    }

    private void executeProgram(ArrayList<String> instLines) {
        int[][] lights = generateLights();
        Stream<Instruction> instructions = instLines.stream().map(this::parseInstruction);

        instructions.forEach((inst) -> {
            for (int y = inst.range1[1]; y <= inst.range2[1]; ++y) {
                for (int x = inst.range1[0]; x <= inst.range2[0]; ++x) {
                    switch (inst.inst) {
                        case "turn on":
                            lights[y][x] += 1;
                            break;
                        case "toggle":
                            lights[y][x] += 2;
                            break;
                        case "turn off":
                            lights[y][x] = Math.max(0, lights[y][x] - 1);
                            break;
                    }
                }
            }
        });

        // Counting and brightness
        int cnt = 0;
        long brg = 0L;
        for (int y = 0; y < 1000; ++y) {
            for (int x = 0; x < 1000; ++x) {
                brg += lights[y][x];
                if (lights[y][x] > 0) {
                    ++cnt;
                }
            }
        }

        System.out.println("Lights left on: " + cnt);
        System.out.println("Total brightness: " + brg);
    }

    @Override
    public void exec() {
        // Part 1
        ArrayList<String> program = FileLoader.getInputFile("day6.txt");
        assert program != null;

        executeProgram(program);
    }
}
