package net.nergi.mainsource;

import net.nergi.utils.FileLoader;
import net.nergi.utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Day9 implements Day {
    private final static Pattern SEG_REGEX = Pattern.compile("(.+) to (.+) = (-?\\d+)");

    private static class Node {
        public final String name;
        public boolean visted;

        public Node(String name) {
            this.name = name;
            visted = false;
        }

        @Override
        public String toString() {
            return "[" + name + "]";
        }
    }

    private static class TwoWaySegment {
        public final Node endpoint1;
        public final Node endpoint2;
        public final int distance;

        public TwoWaySegment(Node e1, Node e2, int distance) {
            endpoint1 = e1;
            endpoint2 = e2;
            this.distance = distance;
        }

        @Override
        public String toString() {
            return endpoint1 + " <-> " + endpoint2 + " | " + distance;
        }
    }

    // NOTE: The set of nodes in the HashMap and the nodes in the segments are the same instances, be careful when modifying them.
    private static Pair<ArrayList<TwoWaySegment>, HashMap<String, Node>> generateSegments(ArrayList<String> segmentStrs) {
        final ArrayList<TwoWaySegment> segments = new ArrayList<>();
        final HashMap<String, Node> nodes = new HashMap<>();

        // First pass: collect all names of nodes and create a hashmap of names to nodes
        for (String s : segmentStrs) {
            final Matcher match = SEG_REGEX.matcher(s);

            if (match.find()) {
                if (!nodes.containsKey(match.group(1))) {
                    nodes.put(match.group(1), new Node(match.group(1)));
                }

                if (!nodes.containsKey(match.group(2))) {
                    nodes.put(match.group(2), new Node(match.group(2)));
                }
            } else {
                throw new RuntimeException("Invalid input in file: [" + s + "]");
            }
        }

        // Second pass: put together all segments from the node map
        for (String s : segmentStrs) {
            final Matcher match = SEG_REGEX.matcher(s);

            if (match.find()) {
                segments.add(new TwoWaySegment(
                    nodes.get(match.group(1)),
                    nodes.get(match.group(2)),
                    Integer.parseInt(match.group(3))
                ));
            } else {
                throw new RuntimeException("Invalid input in file: [" + s + "]");
            }
        }

        return new Pair<>(segments, nodes);
    }

    @Override
    public void exec() {
        // Pre
        final ArrayList<String> listEdges = FileLoader.getInputFile("day9.txt");
        assert listEdges != null;

        // Verify that the inputs parses correctly
        {
            final Pair<ArrayList<TwoWaySegment>, HashMap<String, Node>> part1Seg = generateSegments(listEdges);
            System.out.println(part1Seg.first);
        }

        // Part 1: TODO
    }
}
