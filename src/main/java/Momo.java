import java.util.ArrayList;
import java.util.Scanner;

public class Momo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> list = new ArrayList<>();

        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm Momo");
        System.out.println("What can I do for you?");
        System.out.println("____________________________________________________________");

        String input;
        while(true) {
            input = sc.nextLine();
            if(input.equalsIgnoreCase("bye")) {
                bye();
                break;
            } else if (input.equalsIgnoreCase("list")) {
                for (int i = 0; i < list.size(); i++) {
                    System.out.println((i + 1) + "." + list.get(i));
                }
                System.out.println("____________________________________________________________");
            } else {
                if (input.startsWith("mark")) {
                    int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                    list.get(taskIndex).markAsDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(list.get(taskIndex).toString());
                    System.out.println("____________________________________________________________");
                } else if (input.startsWith("unmark")) {
                    int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                    list.get(taskIndex).markAsNotDone();
                    System.out.println("____________________________________________________________");
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(list.get(taskIndex).toString());
                    System.out.println("____________________________________________________________");
                } else {
                    list.add(new Task(input));
                    System.out.println("____________________________________________________________");
                    System.out.println("added: " + input);
                    System.out.println("____________________________________________________________");
                }
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
