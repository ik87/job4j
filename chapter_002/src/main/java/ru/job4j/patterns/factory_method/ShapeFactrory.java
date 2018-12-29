package ru.job4j.patterns.factory_method;

public class ShapeFactrory {

    public Shape getShape(String shapeType) {
        if (shapeType.equals("circle")) {
            return new Circle();
        } else if (shapeType.equals("square")) {
            return new Square();
        } else if (shapeType.equals("rectangle")) {
            return  new Rectangle();
        } else {
            throw new RuntimeException("The shape: " + shapeType + " didn't found ");
        }
    }

    public static void main(String[] args) {
        ShapeFactrory shapeFactroy = new ShapeFactrory();
        Shape shape = shapeFactroy.getShape("rectanglse");
        shape.draw();
    }
}

interface Shape {
    void draw();
}

class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println("Circle");
    }
}

class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Square");
    }
}

class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Rectangle");
    }
}