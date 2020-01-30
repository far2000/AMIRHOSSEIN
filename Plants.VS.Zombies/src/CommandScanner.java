import java.util.Scanner;

class CommandScanner {
    Scanner scanner = new Scanner(System.in);
    String command;
    int com;
    public void scanCommands(){
        command = scanner.nextLine();
    }
    public String getCommand(){
        return command;
    }
}
