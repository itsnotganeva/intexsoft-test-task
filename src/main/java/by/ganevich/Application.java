package by.ganevich;


import by.ganevich.io.ConsoleInterpreter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
public class Application {

    private static ConsoleInterpreter interpreter;

    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(Application.class);
        interpreter = applicationContext.getBean(ConsoleInterpreter.class);

        interpreter.invokeCommand();

    }
}
