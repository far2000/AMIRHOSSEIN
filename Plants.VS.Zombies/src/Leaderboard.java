import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Leaderboard {
    LoginMenu loginmenu;
    public Leaderboard(LoginMenu loginmenu) {
        this.loginmenu = loginmenu;
    }

    public void showLeaderboard(CommandScanner scanner) {
        System.out.println("Welcome to LeaderBoard!");
        Collections.sort(Game.Accounts);
        for(Account acc :Game.Accounts)
            System.out.println("Username : " + acc.getUsername()+"   ***    Number of killed zombies : "+acc.getNumberofKilledZombies());
        commands(scanner);
    }
    private void commands(CommandScanner scanner){
        scanner.scanCommands();
        String string=scanner.getCommand();
        if (string.equals("Exit"))
            loginmenu.commands(scanner);
        else{
            System.out.println("Invalid command");
            commands(scanner);
        }
    }

}
