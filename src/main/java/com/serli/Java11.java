package com.serli;

public class Java11 {

    public static void main(String[] args) {
        new Java11();
    }

    public Java11() {

        // Helpful Null Pointers
        System.out.println("A not-so-helpful null-pointer exception error message.");
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
    }
}

class Tree {
    Branch branch;
    Tree(Branch branch) {
        this.branch = branch;
    }
}

class Branch {
    Leaf leaf;
    Branch(Leaf leaf) {
        this.leaf = leaf;
    }
}

class Leaf {
    String value;
    Leaf(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
