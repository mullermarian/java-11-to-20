package com.serli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Java14 {

    public Java14() throws IOException {

        // Helpful Null Pointers
        System.out.println("A helpful null-pointer exception error message.");
        Tree tree1 = new Tree(new Branch(new Leaf("OK")));
        System.out.println("Value in tree 1: " + tree1.branch.leaf.value);
        Tree tree2 = new Tree(new Branch(null));
        try {
            System.out.println("Value in tree 2: " + tree2.branch.leaf.value);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Value in tree 2: " + tree2.branch.leaf.getValue());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        // Switch Expressions
        System.out.println("Switch expressions");
        System.out.println("Heat of color Blue: " + colorHeat(Color.Blue));
        System.out.println("Heat of color Orange: " + colorHeat(Color.Orange));
        System.out.println("Heat of color Purple: " + colorHeat(Color.Purple));
        
        // Files.mismatch()
        Path filePath1 = Files.createTempFile("file1", ".txt");
        Path filePath2 = Files.createTempFile("file2", ".txt");
        Files.writeString(filePath1, "Contenu du fichier");
        Files.writeString(filePath2, "Contenu du fichier");
        if(Files.mismatch(filePath1, filePath2) != -1) {
            System.err.println("Files should be identical.");
        } else {
            System.out.println("Files are identical");
        }
        Path filePath3 = Files.createTempFile("file3", ".txt");
        Path filePath4 = Files.createTempFile("file4", ".txt");
        Files.writeString(filePath3, "Contenu du fichier #3");
        Files.writeString(filePath4, "Contenu du fichier #4");
        long mismatch = Files.mismatch(filePath3, filePath4);
        if(mismatch == -1) {
            System.err.println("Files should be different.");
        } else {
            System.out.println("Files are different: first different bytes at address " + mismatch);
        }

        // Teeing Collector
        double mean = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .collect(Collectors.teeing(
                Collectors.summingDouble(i -> i),
                Collectors.counting(),
                (sum, count) -> sum / count
            ));
        System.out.println(mean);

        // Compact Number Formatting
        NumberFormat formatShort = NumberFormat.getCompactNumberInstance(Locale.of("fr", "FR"), NumberFormat.Style.SHORT);
        formatShort.setMaximumFractionDigits(2);
        System.out.println(formatShort.format(2592));   // 2,59 k
        NumberFormat formatLong = NumberFormat.getCompactNumberInstance(Locale.of("fr", "FR"), NumberFormat.Style.LONG);
        formatLong.setMaximumFractionDigits(0);
        System.out.println(formatLong.format(2592));    // 3 mille
        NumberFormat formatLongUS = NumberFormat.getCompactNumberInstance(Locale.of("en", "US"), NumberFormat.Style.LONG);
        formatLongUS.setMaximumFractionDigits(2);
        System.out.println(formatLongUS.format(2592));    // 2.59 thousand
        
    }
    
    static String colorHeat(Color color) { 
        return switch (color) {
            case Blue, Green            -> "cold";
            case Red, Yellow, Orange    -> "hot";
            default                     -> {
                System.out.println("Cannot determine color heat.");
                yield "unknown";
            }
        };
    }
}

enum Color {
    Green,
    Blue,
    Red,
    Yellow,
    Orange,
    Purple
}

class ATree {
    ABranch branch;
    ATree(ABranch branch) {
        this.branch = branch;
    }
}

class ABranch {
    ALeaf leaf;
    ABranch(ALeaf leaf) {
        this.leaf = leaf;
    }
}

class ALeaf {
    String value;
    ALeaf(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
