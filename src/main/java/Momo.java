import java.util.Scanner;

public class Momo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] list = new String[100];
        int count = 0;

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Momo");
        System.out.println("What can I do for you?");


        String input;
        while(true) {
            input = sc.nextLine();
            if(input.equalsIgnoreCase("bye")) {
                bye();
                break;
            } else if (input.equalsIgnoreCase("list")) {
                System.out.println("____________________________________________________________");
                for (int i = 0; i < count; i++) {
                    System.out.println((i+1) + ". " + list[i]);
                }
                System.out.println("____________________________________________________________");
            } else {
                list[count] = input;
                count ++;
                System.out.println("____________________________________________________________");
                System.out.println("added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        sc.close();

    }

    public static void bye() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }
}
