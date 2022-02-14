package by.ganevich;


import by.ganevich.io.Interactive;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@ComponentScan
public class Application {

    private static Interactive interactive;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Application.class);
        interactive = applicationContext.getBean(Interactive.class);

        interactive.InvokeInteractiveMenu();

    }
}
