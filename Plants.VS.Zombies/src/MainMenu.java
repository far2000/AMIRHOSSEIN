import java.util.Scanner;

public class MainMenu {
    private String[] orders = {"Help", "Play","Profile","Shop","Exit"};
    public void commands(CommandScanner scanner) {
        System.out.println("Welcome to MainMenu!!");

        while (true) {
            scanner.scanCommands();
            String string = scanner.getCommand();
            int validitionflag = 0;
            for (String t : orders)
                if (t.equals(string))
                    validitionflag = 1;
            if (validitionflag == 0)
                System.out.println("Invalid command");
            validitionflag = 0;
            if (string.equals("Help")) {
                showcommands();
                commands(scanner);
            }
            if (string.equals("Play")){
                StartGame startgame = new StartGame();
                startgame.start(scanner);
            }
            if(string.equals("Profile")){
                Game.profilemenu.commands(scanner);
            }
            if(string.equals("Shop")){
                Game.shopmenu.commands(scanner);
            }
            if (string.equals("Exit")){
                Game.loginmenu.commands(scanner);
            }

        }

    }
    public void showcommands(){
        for (String s : orders)
            if (s != "Help")
                System.out.println(s);
    }
}
