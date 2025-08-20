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
        while (true) {
            input = sc.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                bye();
                break;
            } else if (input.equalsIgnoreCase("list")) {
                printList(list);
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
                    if (input.startsWith("todo ")) {
                        String desc = input.substring(5);
                        Task todo = new ToDo(desc);
                        printAddedTask(list, todo);
                    } else if (input.startsWith("deadline ")) {
                        String[] parts = input.substring(9).split(" /by ");
                        Task ddl = new Deadline(parts[0], parts[1]);
                        printAddedTask(list, ddl);
                    } else if (input.startsWith("event ")) {
                        String[] parts = input.substring(6).split(" /from ");
                        String desc = parts[0];
                        String[] period = parts[1].split(" /to");
                        Task event = new Events(desc, period[0], period[1]);
                        printAddedTask(list, event);
                    }
                }
            }
        }
        sc.close();

    }

    private static void printAddedTask(ArrayList<Task> list, Task ddl) {
        list.add(ddl);

        System.out.println("____________________________________________________________");
        System.out.println("Got it. I've added this task:");
        System.out.println(ddl.toString());
        System.out.println("Now you have " + list.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    public static void bye() {
        System.out.println("____________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    private static void printList(ArrayList<Task> tasks) {
        System.out.println("____________________________________________________________");
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }
}
