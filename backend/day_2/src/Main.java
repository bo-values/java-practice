import java.util.ArrayList;

class WrapperClass {
    public static void main(String[] args) {
        Boolean a = true;
        Character b = '@';
        Integer c = 123;
        Double d = 3.14;
        String e = "Bro";

        if(a == true) {
            System.out.println("this is true");
        }
    }
}

class ResizableArray {
    public static void main(String[] args) {
        ArrayList<String> food = new ArrayList<String>();

        food.add("pizza");
        food.add("hotdog");

        for(int i=0; i<food.size();i++){
            System.out.println(food.get(i));
        }
    }
}


class Methods {
    public static void main(String[] args) {
        String name = "Bro";
        hello(name);

        int a = 1;
        int b = 4;

        int d = calc(a,b);
        System.err.println(d);
    }

    static void hello(String name) {
        System.out.println("Hello "+name);
    }

    static int calc(int a,int b) {
        int c = a + b;
        return c;
    }
}


class OverloadMethods {
    public static void main(String[] args) {
        int x = add(1,2,3);
        System.err.println(x);
    }

    static int add(int a, int b){
        return a + b;
    }
    static int add(int a, int b, int c){
        return a + b + c;
    }
}

class PrintfClass {
    public static void main(String[] args) {
        boolean boolVal = true;
        char charVal = 'A';
        int intVal = 255;
        double doubleVal = 3.14159;
        String strVal = "Hello";

        System.out.printf("Boolean: %b%n", boolVal);
        System.out.printf("Character: %c%n", charVal);
        System.out.printf("Integer (decimal): %d%n", intVal);
        System.out.printf("Integer (octal): %o%n", intVal);
        System.out.printf("Integer (hex): %x%n", intVal);
        System.out.printf("Float: %f%n", doubleVal);
        System.out.printf("Exponential: %e%n", doubleVal);
        System.out.printf("General: %g%n", doubleVal);
        System.out.printf("Hash code: %h%n", strVal);
        System.out.printf("String: %s%n", strVal);
        System.out.printf("Newline: %n");
    }
}