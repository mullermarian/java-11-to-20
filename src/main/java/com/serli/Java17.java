package com.serli;

public class Java17 {

    public Java17() {

        // Sealed classes
        Shape shape1 = new Triangle();
        System.out.println(shape1.print());
        Shape2 shape2 = new Rectangle2();
        System.out.println(shape2.print());
        Shape square = new Square();
        System.out.println(square.print());
        
    }
}


sealed interface Shape permits Triangle, Rectangle, Circle {
    String print();
}
final class Triangle implements Shape {
    public String print() { return "Tri<a,b,c>"; }
}
non-sealed class Rectangle implements Shape {
    public String print() { return "Rect<w,l>"; }
}
final class Square extends Rectangle {
    public String print() { return "Squ<l>"; }
}
final class Circle implements Shape {
    public String print() { return "Cir<c,r>"; }
}
/* Not allowed
final class SquareBis implements Shape {
    public String print() { return "SquBis<l>"; }
}*/

abstract sealed class Shape2 {
    public abstract String print();
}
final class Triangle2 extends Shape2 {
    public String print() {
        return "Tri<a,b,c>";
    }
}
non-sealed class Rectangle2 extends Shape2 {
    public String print() {
        return "Rect<w,l>";
    }
}
final class Square2 extends Rectangle2 {
    public String print() {
        return "Squ<l>";
    }
}
final class Circle2 extends Shape2 {
    public String print() {
        return "Cir<c,r>";
    }
}
