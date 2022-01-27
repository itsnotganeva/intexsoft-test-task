package by.ganevich.io.inputmanager;

import java.util.Scanner;

public class InputManager {

    public static String inputString() {
        Scanner in = new Scanner(System.in);

        return in.nextLine();
    }

    public static Integer inputInt() {
        Integer retVal;

        try{
            retVal = Integer.parseInt(inputString());

            return retVal;
        } catch (Exception ex) {
            System.out.println("Wrong input!");
            throw new RuntimeException();
        }
    }

    public static Double inputDouble() {
        Double retVal;

        try{
            retVal = Double.parseDouble(inputString());

            return retVal;
        } catch (Exception ex) {
            System.out.println("Wrong input!");
            throw new RuntimeException();
        }
    }
}
