import java.util.Scanner;

public class LoginMenu {
    private String[] orders = {"Help", "Create account", "Login", "Leaderboard","Exit"};
    boolean f = true;
    public void commands(CommandScanner scanner) {
        System.out.println("Welcome to LoginMenu!!");

        while (f) {
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
            if (string.equals("Login")) {
                System.out.println("Enter your Username:");
                scanner.scanCommands();
                String username = scanner.getCommand();
                System.out.println("Enter your Password:");
                scanner.scanCommands();
                String password = scanner.getCommand();
                boolean flag = false;
                for (Account account : Game.Accounts) {
                    if ((username.equals(account.getUsername())) && (password.equals(account.getPassword()))) {
                        flag = true;
                        Game.mainaccount = account;
                        Game.mainmenu.commands(scanner);
                        //go to mainmenu.
                    }
                }
                if (!flag)
                    System.out.println("username or password is invalid");
            }
            if (string.equals("Create account")) {
                System.out.println("Enter your Username:");

                scanner.scanCommands();
                String username = scanner.getCommand();
                System.out.println("Enter your Password:");
                scanner.scanCommands();
                String password = scanner.getCommand();
                Account account = new Account(username, password);
                Game.Accounts.add(account);
                Game.filehandler.save();
            }
            if (string.equals("Leaderboard")) {
                Leaderboard leaderboard = new Leaderboard(Game.loginmenu);
                leaderboard.showLeaderboard(scanner);
            }
            if (string.equals("Exit")) {
                f = false;
            }


        }
    }
    public void showcommands(){
        for (String s : orders)
            if (s != "Help")
                System.out.println(s);
    }
}
