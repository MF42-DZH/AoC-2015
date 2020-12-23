package net.nergi.mainsource;

import net.nergi.utils.FileLoader;
import net.nergi.utils.MutablePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Day7 implements Day {
    private interface ShortBitOp {
        short op(short a, short b);
    }

    private static class CToken {
        public String cnt;

        public CToken(String tok) {
            cnt = tok;
        }
    }

    private static class CVal extends CToken {
        public short value;

        public CVal(String id, short val) {
            super(id);
            value = val;
        }
    }

    private static class COp extends CToken {
        public String in1;
        public String in2;
        public String out;

        public COp(String op, String i1, String i2, String o) {
            super(op);
            in1 = i1;
            in2 = i2;
            out = o;
        }

        public ShortBitOp getOp() {
            switch (this.cnt) {
                case "AND":
                    return ((a, b) -> (short) (a & b));
                case "OR":
                    return ((a, b) -> (short) (a | b));
                case "LSHIFT":
                    return ((a, b) -> (short) (a << b));
                case "RSHIFT":
                    return ((a, b) -> (short) (a >> b));
                case "NOT":
                    return ((a, b) -> (short) (~a));
                case "->":
                    return ((a, b) -> a);
                default:
                    return null;
            }
        }
    }

    private boolean isNumber(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    private static final Pattern INST_PATTERN = Pattern.compile("(.+) (.+) (.+) -> (.+)");

    private ArrayList<CToken> tokenise(ArrayList<String> insts) {
        final ArrayList<CToken> tokens = new ArrayList<>();
        for (String line : insts) {
            final Matcher match = INST_PATTERN.matcher(line);
            if (match.find()) {
                // Command
                final String i1 = match.group(1);
                final String i2 = match.group(3);
                final String op = match.group(2);
                final String o = match.group(4);

                tokens.add(new COp(op, i1, i2, o));
            } else {
                if (line.startsWith("NOT")) {
                    final String[] strs = line.split(" ");
                    tokens.add(new COp(strs[0], strs[1], "0", strs[3]));
                    continue;
                }

                // Value
                final String[] strs = line.split(" -> ");

                if (Character.isDigit(strs[0].charAt(0))) {
                    tokens.add(new CVal(strs[1], Short.parseShort(strs[0])));
                } else {
                    tokens.add(new COp("->", strs[0], "0", strs[1]));
                }
            }
        }

        return tokens;
    }

    private void evaluate(ArrayList<CToken> tokens) {
        final HashMap<String, Short> memory = new HashMap<>();
        final ArrayList<MutablePair<COp, Boolean>> instructions = new ArrayList<>();

        // First pass: grab all CVal instances and COp instances
        for (CToken token : tokens) {
            if (token instanceof CVal) {
                final CVal cur = (CVal) token;
                memory.put(cur.cnt, cur.value);
            } else if (token instanceof  COp) {
                final COp cur = (COp) token;
                instructions.add(new MutablePair<>(cur, false));
            }
        }

        System.out.println(memory);

        // While not all of the instructions have been evaluated, run the process
        while (instructions.stream().anyMatch((p) -> !p.second)) {
            System.out.printf("Remaining: %s%n",
                instructions.stream().map((p) -> p.second ? 0 : 1).reduce(0, Integer::sum)
            );

            for (MutablePair<COp, Boolean> inst : instructions) {
                final COp current = inst.first;
                final boolean isI1Number = isNumber(current.in1);
                final boolean isI2Number = isNumber(current.in2);

                // If it should execute, compute the new value
                if (
                    (
                        isI1Number || memory.containsKey(current.in1)
                    ) && (
                        isI2Number || memory.containsKey(current.in2)
                    )
                ) {
                    short i1 = isI1Number ?
                        Short.parseShort(current.in1) :
                        memory.get(current.in1);
                    short i2 = isI2Number ?
                        Short.parseShort(current.in2) :
                        memory.get(current.in2);

                    final ShortBitOp op = current.getOp();
                    memory.put(current.out, op.op(i1, i2));

                    inst.second = true;
                }
            }
        }

        System.out.println(memory.get("a"));

        short sh = memory.get("a");

        Set<String> s = new HashSet<>(memory.keySet());
        for (String k : s) {
            if (!k.equals("b")) {
                memory.remove(k);
            }
        }

        for (CToken token : tokens) {
            if (token instanceof CVal) {
                final CVal cur = (CVal) token;
                memory.put(cur.cnt, cur.value);
            }
        }

        memory.put("b", sh);

        // System.out.println(memory);

        for (MutablePair<COp, Boolean> inst : instructions) {
            inst.second = false;
        }

        while (instructions.stream().anyMatch((p) -> !p.second)) {
            int np = instructions.stream().map((p) -> p.second ? 0 : 1).reduce(0, Integer::sum);

            // System.out.printf("Remaining: %s%n", np);
            // System.out.println(memory);

            for (MutablePair<COp, Boolean> inst : instructions) {
                final COp current = inst.first;
                final boolean isI1Number = isNumber(current.in1);
                final boolean isI2Number = isNumber(current.in2);
                // if (current.out.equals("b")) {
                //     inst.second = true;
                //     continue;
                // }

                // If it should execute, compute the new value
                if (
                    (
                        isI1Number || memory.containsKey(current.in1)
                    ) && (
                        isI2Number || memory.containsKey(current.in2)
                    )
                ) {
                    short i1 = isI1Number ?
                        Short.parseShort(current.in1) :
                        memory.get(current.in1);
                    short i2 = isI2Number ?
                        Short.parseShort(current.in2) :
                        memory.get(current.in2);

                    final ShortBitOp op = current.getOp();
                    memory.put(current.out, op.op(i1, i2));

                    if (memory.get("a") != null) {
                        System.out.println(memory.get("a") & 0xffff);
                    }

                    inst.second = true;
                }
            }
        }

        // Somehow my solution is off by 6 for my input set ???
        System.out.println((memory.get("a") & 0xffff));
    }

    @Override
    public void exec() {
        // Pre
        ArrayList<String> insts = FileLoader.getInputFile("day7.txt");
        assert insts != null;

        // Part 1 & 2
        ArrayList<CToken> tokens = tokenise(insts);
        evaluate(tokens);
    }
}
