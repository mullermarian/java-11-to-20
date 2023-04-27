package com.serli;

import java.awt.*;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.util.Arrays;
import java.util.List;

public class Java20 {

    public Java20() throws Throwable {
        
        // Pattern Matching for switch
        System.out.println(getPerimeter(new ARectangle(10, 5)));
        System.out.println(getPerimeter(new ACircle(4)));
        System.out.println(getPerimeter(new ARectangle(5, 5)));

        System.out.println(printValue(123));
        System.out.println(printValue(1024));
        System.out.println(printValue(123456789L));
        System.out.println(printValue(123.45));
        System.out.println(printValue("Chaîne"));
        System.out.println(printValue(List.of("a", "b", "c")));
        System.out.println(printValue(null));
        
        // Record Patterns
        printSum(new Point(1,2));

        // Foreign Function & Memory API
        SymbolLookup stdlib = Linker.nativeLinker().defaultLookup();
        MethodHandle strlen = Linker.nativeLinker().downcallHandle(
            stdlib.lookup("strlen").orElseThrow(),
            FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS)
        );
        MemorySegment str = SegmentAllocator.implicitAllocator().allocateUtf8String("Foreign function !");
        long len = (long) strlen.invoke(str);
        System.out.println("len = " + len);
        
    }
    
    static double getPerimeter(AShape shape) {
        return switch(shape) {
            case ARectangle r when r.w == r.h    -> 4 * r.w;
            case ARectangle r                    -> 2 * r.w + 2 * r.h;
            case ACircle c                       -> 2 * Math.PI * c.r;
            default                             -> 0;
        };
    }
    
    static String printValue(Object obj) {
        return switch (obj) {
            case null                   -> "Oops";
            case Integer i when i < 256 -> String.format("small int %d", i);
            case Integer i              -> String.format("int %d", i);
            case Long l                 -> String.format("long %d", l);
            case Double d               -> String.format("double %f", d);
            case String s               -> String.format("String %s", s);
            default                     -> obj.toString();
        };
    }

    static void printSum(Object obj) {
        if (obj instanceof Point(int x, int y)) {
            System.out.println(x+y);
        }
    }
}

interface AShape {}
class ARectangle implements AShape {
    public double w, h;
    public ARectangle(double w, double h) {
        this.w = w;
        this.h = h;
    }
}
class ACircle implements AShape {
    public double r;
    public ACircle(double r) {
        this.r = r;
    }
}

record Point(int x, int y) {}
