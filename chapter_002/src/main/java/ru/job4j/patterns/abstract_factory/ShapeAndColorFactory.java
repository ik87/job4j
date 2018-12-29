package ru.job4j.patterns.abstract_factory;

public class ShapeAndColorFactory {
    public static void main(String[] args) {
        AbstractFactory shapeFactory = FactoryProducer.getFactory("shape");
        AbstractFactory colorFactory = FactoryProducer.getFactory("color");

        Shape shape = shapeFactory.getShape("Circle");
        shape.draw();

        Color color = colorFactory.getColor("Red");
        color.fill();


    }
}

class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        if(choice.equalsIgnoreCase("shape")) {
            return new ShapeFactory();
        } else if (choice.equalsIgnoreCase("color")) {
            return new ColorFactory();
        } else {
            return null;
        }
    }
}

abstract class AbstractFactory {
    abstract Color getColor(String color);

    abstract Shape getShape(String shapeType);
}

class ShapeFactory extends AbstractFactory {
    @Override
    Color getColor(String color) {
        return null;
    }

    @Override
    Shape getShape(String shapeType) {
        if (shapeType.equalsIgnoreCase("circle")) {
            return new Circle();
        } else if (shapeType.equalsIgnoreCase("square")) {
            return new Square();
        } else if (shapeType.equalsIgnoreCase("rectangle")) {
            return new Rectangle();
        } else {
            throw new RuntimeException("The shape: " + shapeType + " didn't found ");
        }
    }
}


class ColorFactory extends AbstractFactory {
    @Override
    Shape getShape(String shapeType) {
        return null;
    }

    @Override
    Color getColor(String color) {
        if (color.equalsIgnoreCase("red")) {
            return new Red();
        } else if (color.equalsIgnoreCase("blue")) {
            return new Blue();
        } else if (color.equalsIgnoreCase("yellow")) {
            return new Yellow();
        } else {
            throw new RuntimeException("The color: " + color + " didn't found ");
        }
    }
}

interface Shape {
    void draw();
}

interface Color {
    void fill();
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

class Yellow implements Color {
    @Override
    public void fill() {
        System.out.println("yellow");
    }
}

class Blue implements Color {
    @Override
    public void fill() {
        System.out.println("Blue");
    }
}

class Red implements Color {
    @Override
    public void fill() {
        System.out.println("Red");
    }
}