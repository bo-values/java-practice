import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        System.out.print("テスト\n");
        System.out.println("テスト2");
    }
}

class Variable {
     public static void main(String[] args) {
        int x = 123;
        double y = 123.45;
        boolean z = true;
        char symbol = '@';
        String name = "Bro";



        System.out.println(x);
        System.out.println("My Number is"+x);
        System.out.println("Hello "+name);
     }
}


class Temp {
    public static void main(String[] args) {
        /**
         * 値の入れ替え
         */
        String x = "x";
        String y = "y";
        String temp;

        temp=x;
        x=y;
        y=temp;

        System.out.println("x: "+x);
        System.out.println("y: "+y);
    }
}

class UserInput {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("what is your name? :");
            String name = scanner.nextLine();
            System.out.println("How old are you? ");
            int age = scanner.nextInt();

            System.out.println("Hello "+name);
            System.out.println("your age is "+age);
        }
    }
}

class Calc {
    public static void main(String[] args) {
        double friends = 10;

        friends = (double) friends / 3;
        System.out.println(friends);
    }
}

class IfClass {
    public static void main(String[] args) {
        int age = 20;

        if(age < 20) {
            System.out.println("age not more");
        } else if(age == 20) {
            System.out.println("age same");
        } else {
            System.out.println("age more");
        }
    }
}

class SwitchClass {
    public static void main(String[] args) {
        String day = "Sunday";
        
        switch(day) {
            case "Sunday": System.out.println("Sunday");
            break;
            default: System.out.println("Others");
        }
    }
}