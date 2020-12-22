package net.nergi.mainsource;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("unused")
public class Day4 implements Day {
    private static class BWrapper {
        public boolean b;

        public BWrapper(boolean b) {
            this.b = b;
        }
    }

    private static class HashBruteForcer implements Runnable {
        private static int ZERO_COUNT = 1;

        private final long increment;
        private final long start;
        private final BWrapper wrp;

        public HashBruteForcer(long start, long inc, BWrapper wrp) {
            increment = inc;
            this.start = start;
            this.wrp = wrp;
        }

        @Override
        public void run() {
            final MessageDigest md5Hash;
            final String input = "bgvyzdsv";
            byte[] output;

            // Part 1
            try {
                md5Hash = MessageDigest.getInstance("MD5");
                long current = -start;

                do {
                    current = current + increment;
                    output = md5Hash.digest((input + current).getBytes(StandardCharsets.UTF_8));

                    String hf = hexFromMD5(output);
                    if (hf.startsWith(new String(new char[ZERO_COUNT]).replace('\u0000', '0'))) {
                        synchronized (System.out) {
                            System.out.println("[" + start + "] " + current + ": " + hf);
                        }

                        if (hf.charAt(ZERO_COUNT + 1) == '0') {
                            ++ZERO_COUNT;
                        }
                    }
                } while (!testArr(output) && !wrp.b);

                wrp.b = true;

                synchronized (System.out) {
                    if (testArr(output)) {
                        System.out.println("[FOUND] " + current + ": " + hexFromMD5(output));
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

        private boolean testArr(byte[] arr) {
            final String patternPart1 = "00000";
            final String patternPart2 = "000000";

            return hexFromMD5(arr).startsWith(patternPart2);
        }

        private String hexFromMD5(byte[] arr) {
            final BigInteger bgint = new BigInteger(1, arr);
            final StringBuilder htext = new StringBuilder(bgint.toString(16));
            while(htext.length() < 32) {
                htext.insert(0, "0");
            }

            return htext.toString();
        }
    }

    @Override
    public void exec() {
        final BWrapper shouldKill = new BWrapper(false);

        // Part 1 & 2 (& the bonus 3)
        Thread[] threads = new Thread[] {
            new Thread(new HashBruteForcer(0, 10, shouldKill)),
            new Thread(new HashBruteForcer(1, 10, shouldKill)),
            new Thread(new HashBruteForcer(2, 10, shouldKill)),
            new Thread(new HashBruteForcer(3, 10, shouldKill)),
            new Thread(new HashBruteForcer(4, 10, shouldKill)),
            new Thread(new HashBruteForcer(5, 10, shouldKill)),
            new Thread(new HashBruteForcer(6, 10, shouldKill)),
            new Thread(new HashBruteForcer(7, 10, shouldKill)),
            new Thread(new HashBruteForcer(8, 10, shouldKill)),
            new Thread(new HashBruteForcer(9, 10, shouldKill))
        };

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
