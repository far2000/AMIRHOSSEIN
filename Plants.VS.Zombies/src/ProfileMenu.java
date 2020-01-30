import java.util.Scanner;

public class ProfileMenu {
    private String[] orders = {"Help", "Change", "Delete", "Rename", "Create", "Show","Exit"};

    public void commands(CommandScanner scanner) {
        System.out.println("Welcome to ProfileMenu!!");

        while (true) {
            scanner.scanCommands();
            String string = scanner.getCommand();
            int validitionflag = 0;
            for (String t : orders)
                if (t.equals(string))
                    validitionflag = 1;
            if (validitionflag == 0)
                System.out.println("Invalid command");
            if (string.equals("Help")) {
                showcommands();
                commands(scanner);
            }

            if (string.equals("Change")) {
                System.out.println("Enter your new Username:");
                scanner.scanCommands();
                String username = scanner.getCommand();
                System.out.println("Enter your new Password:");
                scanner.scanCommands();
                String password = scanner.getCommand();
                Game.mainaccount.setUsername(username);
                Game.mainaccount.setPassword(password);
                Game.filehandler.save();
            }
            if (string.equals("Delete")) {
                Game.Accounts.remove(Game.mainaccount);
                Game.filehandler.save();
                Game.loginmenu.commands(scanner);
            }
            if (string.equals("Rename")) {
                System.out.println("Enter your new Username:");

                scanner.scanCommands();
                String username = scanner.getCommand();
                Game.mainaccount.setUsername(username);
                Game.filehandler.save();

            }
            if (string.equals("Create")) {
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
            if (string.equals("Show")) {
                System.out.println(Game.mainaccount.getUsername()+" extremecoins= "+Game.mainaccount.getExtremecoins());
                System.out.println();
            }
            if (string.equals("Exit")) {
                Game.mainmenu.commands(scanner);
            }

        }

    }
    public void showcommands(){
        for (String s : orders)
            if (s != "Help")
                System.out.println(s);
    }
}
