package by.ganevich.io;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class CommandDescriptor {

    private String commandName;

    private HashMap<Integer, String> parameters;

}
