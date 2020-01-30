import java.util.ArrayList;

public class StartGame {
    public void start(CommandScanner scanner){
        /**
         * @the turn of the game is calculated with "turn"
         */
        // the row and column of garden
        int gardenRow = 6;
        int gardenColumn = 22;// the 20'th and 21st column is for putting zombies
        int turn = 0;
        // the garden is this object
        Garden garden = new Garden(gardenRow, gardenColumn);
        // the existing elements of the game are gather here
        //this is an ArrayList for keeping the mowers;
        ArrayList<LawnMower> lawnMowers = new ArrayList<LawnMower>();
        for (int i = 0 ; i < 6 ; i++){
            lawnMowers.add(new LawnMower(i , 0));
        }
        //this variable determines the last turn that a sun has been generated
        //int lastTurnSunGenerated = 0;
        //this variable determines the number of turn that should be passed to generate another sun
        ArrayList<Sun> suns = new ArrayList<Sun>();
        ArrayList<Coin> internalCoin = new ArrayList<Coin>();
        ArrayList<Plant> plants = new ArrayList<Plant>();
        ArrayList<Zombie> zombies = new ArrayList<Zombie>();
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        // the commands will scan with this object
        // the results will print with this object
        Console console = new Console();
        //the message for select the game type
        console.printCommand("PoWWWW!!!");
        console.printCommand("Enter the Game name : ");
        console.printCommand("Day   or  Water  or  Zombie  or Rail or PvP");
        // the game handler will start with getting a command
        scanner.scanCommands();
        String gameName = scanner.getCommand();
        while (!nameIsValid(gameName)){
            console.printCommand("Inavlid Game Name");
            scanner.scanCommands();
            gameName = scanner.getCommand();
        }
        // object game will execute the commands
        Handler handler = new Handler(gameName);
        // the collection of 7 cards that player has during the game


        /**
         * @ the game will start in this while with first command
         */
        boolean gameIsContinued = true; // when we want end game this parameter will set "false"
        while(gameIsContinued && !handler.checkGameIsFinished(zombies, lawnMowers)){
            while (!scanner.getCommand().equals("End turn")){
                // execute the given commands with object game
                handler.execute(scanner.getCommand(), plants, garden, zombies, console, scanner, turn ,suns, internalCoin);
                // this method will print the garden after updating it
                scanner.scanCommands();
            }
            // update the garden with this method of object game
            handler.updateGarden(garden , plants , zombies , bullets , lawnMowers ,suns, turn , internalCoin);
            for (Zombie zombie : zombies){
                console.printCommand(zombie.getName() + "  x  " + zombie.getLocation_x() + "   y   " + zombie.getLocation_y() + "   Health  " + zombie.getHealth() );
            }
            for (Plant plant : plants){
                console.printCommand(plant.getName()  + "  x  " + plant.getLocation_x() + "   y   " + plant.getLocation_y() + "  Health   " + plant.getHealth() );
            }
            for (Bullet bullet : bullets){
                console.printCommand(bullet.getBulletType() + "  x  " + bullet.getLocation_x() + "   y   " + bullet.getLocation_y());
            }
            //printGarden(garden.getGarden(), gardenRow, gardenColumn);
            turn++;
            String[][] lawn = new String[6][22];
            for (int i = 0 ; i < 6 ; i++){
                for (int j = 0 ; j < 22 ; j++){
                    lawn[i][j] = "mt";
                }
            }
            for (int i = 0 ; i < 6 ; i++){
                if (!lawnMowers.get(i).getIsUsed())
                    lawn[i][0] = "lawnMower";
            }
            for (Plant plant : plants){
                lawn[plant.getLocation_y()][plant.getLocation_x()] = plant.getName();
            }
            for (Zombie zombie : zombies){
                if (lawn[zombie.getLocation_y()][zombie.getLocation_x()].equals("mt")){
                    lawn[zombie.getLocation_y()][zombie.getLocation_x()] = zombie.getName();
                }
                else
                    lawn[zombie.getLocation_y()][zombie.getLocation_x()].concat(zombie.getName());
            }
            for (Bullet bullet : bullets){
                if (lawn[bullet.getLocation_y()][bullet.getLocation_x()].equals("mt")){
                    lawn[bullet.getLocation_y()][bullet.getLocation_x()] = "*";
                }
                else
                    lawn[bullet.getLocation_y()][bullet.getLocation_x()].concat("*");
            }
            for (int i = 0 ; i < 6 ; i++){
                for (int j = 0 ; j < 22 ; j++){
                    System.out.print(lawn[i][j] + " ");
                }
                System.out.println("");
            }
            console.printCommand("turn is " + turn);
            console.printCommand("sun is " + suns.size());
            console.printCommand("Coin is " + internalCoin.size());
            scanner.scanCommands();
            if (scanner.getCommand().equals("end")){
                gameIsContinued = false;
                Game.mainmenu.commands(scanner);

            }
            System.out.println("");
        }

    }

    private boolean nameIsValid(String command) {
        if (command.equals("Day") || command.equals("Water") || command.equals("Zombie") || command.equals("Rail") || command.equals("PvP"))
            return  true;
        return false;
    }

    public static void printGarden(String[][] garden, int row, int column){
        for (int rowCounter = 0 ; rowCounter < row ; rowCounter++){
            for (int columnCounter = 0 ; columnCounter < column ; columnCounter++ ){
                System.out.print(garden[rowCounter][columnCounter] + " ");
            }
            System.out.println("");
        }
    }
    // this method will generate random numbers between min adn max
    public static int generateRandom(int min, int max) {
        double x = Math.random()*((max - min)+1)+min;
        int randomNumber = (int) x;
        return randomNumber;
    }
}
