import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
//this the test from morteza
public class Game {
    public static ArrayList<Account> Accounts = new ArrayList<Account>();
    static LoginMenu loginmenu = new LoginMenu();
    static MainMenu mainmenu = new MainMenu();
    static ProfileMenu profilemenu = new ProfileMenu();
    static ShopMenu shopmenu = new ShopMenu();
    static CommandScanner commandScanner = new CommandScanner();
    static Filehandler filehandler = new Filehandler();
    static Account mainaccount;
    static Account secondaccount;
    public static void main(String[] args) {
        CommandScanner scanner = new CommandScanner();
        // Start game with this menu
            //Scanner scanner = new Scanner(System.in)
        filehandler.load();
        loginmenu.commands(scanner);
    }


}
