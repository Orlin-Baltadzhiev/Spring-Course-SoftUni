import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        Scanner scan = new Scanner(System.in);
        Homework homework = new Homework();
        homework.setConnection("root", "12345");

        System.out.println("Choose exercise from 2 to 9");
        System.out.println("When you write \"End\" the program will stop.");
        String input = scan.nextLine();
        while(!input.equals("End")) {


            switch (input) {
                case "2":
                    homework.getVillainsNamesEx2();
                    break;
                case "3":
                    homework.getMinionNamesEx3();
                    break;
                case "4":
                    homework.addMinionEx4();
                    break;
                case "5":
                    homework.changeTownNameCasingEx5();
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    homework.increaseAgeWithStoreProcedureEx9();
                    break;
            }
            System.out.println("Choose exercise from 2 to 9");
            input = scan.nextLine();
        }


    }
}
