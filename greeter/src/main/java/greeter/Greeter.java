package greeter;

public class Greeter {
    public static void main(String[] args) {
        final String output;
        if (args.length >0)
            output = GreetingFormatter.greeting(args[0]);
        else
            output = "Hello and Greetings from Greeter";
        System.out.println(output);
    }
}