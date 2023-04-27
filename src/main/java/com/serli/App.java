package com.serli;

public class App 
{
    public static void main( String[] args ) throws Throwable {
        switch(args[0]) {
            case "11"   ->  new Java11();
            case "14"   ->  new Java14();
            case "15"   ->  new Java15();
            case "16"   ->  new Java16();
            case "17"   ->  new Java17();
            case "18"   ->  new Java18();
            case "20"   ->  new Java20();
            default     ->  System.err.println("You must provide a valid java version: 11, 14, 15, 16, 17, 18, 20");
        };
    }
}
