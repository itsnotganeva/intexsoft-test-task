package by.ganevich;

import by.ganevich.io.ConsoleInterpreter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private static ConsoleInterpreter interpreter;

    public static void main(String[] args) {

        //        System.out.println("cli - Console app \n"
        //                + "api - Web app");
        //        Scanner in = new Scanner(System.in);
        //
        //        String command = in.nextLine();
        //
        //        if (command.equals("cli")) {
        //            ApplicationContext applicationContext =
        //                    new AnnotationConfigApplicationContext(Application.class);
        //            interpreter = applicationContext.getBean(ConsoleInterpreter.class);
        //
        //            interpreter.invokeCommand();
        //        } else if (command.equals("api")) {
        SpringApplication.run(Application.class);
        //        } else {
        //            throw new CommandNotFoundException("Command entered incorrectly. Enter 'api' or 'cli'");
        //        }

    }
}
