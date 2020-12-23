package net.nergi.mainsource;

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
                default:
                    return null;
            }
        }
    }

    @Override
    public void exec() {
        // TODO: Implement
    }
}
