//import javafx.scene.Group;

//import com.sun.tools.jconsole.JConsoleContext;

import org.w3c.dom.Element;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.zip.ZipOutputStream;

public class Handler implements Handlable{
    /**
     * @ this class will execute the commands and communicate with other class
     */

    /**
     * this method will execute commands in the "turn"
     *
     * @ param command
     */
    String[] plantsName = {"PeaShooter", "SnowPea", "Cabbage_Plut", "Repeater", "Threepeater", "Cactus", "GatlingPea",
            "Scaredy_Shroom", "KernelPlut", "SplitPea", "Explode_O_Nut", "Melon_Pult", "LilyPad", "Winter_Melon", "Wall_Nut"
            , "TangleKelp", "Tall_Nut", "Cattail", "PotatoMine", "CherryBomb", "Magnet_Shroom", "Sunflower", "TwinSunflower"
            , "Jalapeno"};
   String[] zombiesName = {"Zombie","FootballZombie","BucketheadZombie","DolphinRiderZombie","Giga-gargantuarZombie",
            "SnorkelZombie","PogoZombie","ScreenDoorZombie","TargetZombie","NewspaperZombie","ConeheadZombie","Zomboni",
            "CatapultZombie","BungeeZombie","BalloonZombie"};
    String[] zombiesNameForDay = {"Zombie","FootballZombie","BucketheadZombie","Giga-gargantuarZombie"
            ,"PogoZombie","ScreenDoorZombie","TargetZombie","NewspaperZombie","ConeheadZombie","Zomboni",
            "CatapultZombie","BungeeZombie","BalloonZombie"};
    boolean plantSide = false;
    boolean zombieSide = false;
    // this variable indicates number of waves which has been made
    int numberOfWaves = 0;
    // this data member will determine the turns that needs to be passed for generating puting another zombie in Rail game
    int currentZombieGenerationPeriodForRail = generateRandom(1,3);
    // this data member will determine the number of last turn in which a zombie has been put in rail game
    int lastTurnZombieGeneratedForRail = 0;
    // this data member will determine the turns that needs to be passed for generating another sun
    int currentSunGenerationPeriod = generateRandom(1,2);
    // this data member will determine the number of last turn in which a sun has been generated
    int lastTurnSunGenerated = 0;
    // this boolean data member will indicate whether the game is continued or not;
    boolean gameIsFinished;
    ArrayList<Zombie> killedZombies = new ArrayList<Zombie>();
    ArrayList<Cards> railCards = new ArrayList<Cards>();
    ArrayList<Cards> reachedPlantCards = Game.mainaccount.reachedPlantCards;
    ArrayList<Cards> selectedPlantCards = new ArrayList<Cards>();
    //should be implement the rest time and price

    ArrayList<Cards> reachedZombieCards = Game.mainaccount.reachedZombieCards;
    ArrayList<Cards> selectedZombieCards = new ArrayList<Cards>();


    Cards currentCard = new Cards(); // THIS IS THE CARD WHICH PLAYER SELECT TO PLANT IN GARDEN
    String name; // the name of gametype

    public Handler(String name) {
        this.name = name;
    }

    private int turn;
    static int Di = 0;
    static int Dj = 0;
    static int Zj = 0;
    static int Rj = 0;
    static int nextTurn = 0;
    int random;
    int anotherRandom;
    public void execute(String command, ArrayList<Plant> plants, Garden garden, ArrayList<Zombie> zombies, Console console, CommandScanner scanner, int turn, ArrayList<Sun> suns, ArrayList<Coin> internalCoin) {
        this.turn = turn;
        String[] splitedCommand = new String[10];
        splitedCommand = command.split(" ");
        ArrayList<Cards> reachedCards = new ArrayList<Cards>(); // this arraylist is for the all cards that player has
        if (!this.name.equals("Water")){
         //   reachedPlantCards.remove(7);
        }
        if (this.name.equals("Day")) {
            // this part is "Collection" for "Day"
            if (Dj == 0) {
                collectionPartOfDay(scanner, console);
                suns.add(new Sun());
                suns.add(new Sun());
                Dj = 1;
            }
            dayAndWaterCommandHandler(command, splitedCommand, plants, zombies, console, garden, suns);
        }
        else if (this.name.equals("Water")) {
            if (Dj == 0) {
                collectionPartOfDay(scanner, console);
                suns.add(new Sun());
                suns.add(new Sun());
                Dj += 1;
            }

            dayAndWaterCommandHandler(command, splitedCommand, plants, zombies, console, garden, suns);
        }
        else if (this.name.equals("Rail")) {
            addNewCard(railCards, this.turn, console);
            railCommandHandler(command, splitedCommand, railCards, plants, zombies, console, garden);

        }
        else if (this.name.equals("Zombie")) {
            if (Zj == 0) {
                collectionPartOfZombie(scanner, console);
                Zj = 1;
                for (int counter = 0 ; counter < 50 ; counter++){
                    internalCoin.add(new Coin());
                }
            }
            zombieCommandHandler(command, splitedCommand, plants, zombies, console, garden, internalCoin);
        }
        else if (this.name.equals("PvP")) {
             boolean plantSide = true;
             boolean zombieSide = false;
            if (Dj == 0) {
                plantSide = true;
                zombieSide = false;
                suns.add(new Sun());
                suns.add(new Sun());
                for (int counter = 0 ; counter < 50 ; counter++){
                    internalCoin.add(new Coin());
                }
                console.printCommand("Choose your opponent name : ");
                for (Account account:Game.Accounts)
                    if(account != Game.mainaccount)
                        console.printCommand(account.getUsername());
                boolean accountIsValid = false;
                while (!accountIsValid) {
                    scanner.scanCommands();
                    String opponentName = scanner.getCommand();
                    for (Account account : Game.Accounts) {
                        if (account.getUsername().equals(opponentName)) {
                            Game.secondaccount = account;
                            accountIsValid = true;
                        }
                    }
                    if (!accountIsValid) {
                        console.printCommand("Account name is invalid :( choose another one :");
                    }
                }
                collectionPartOfDay(scanner, console);
                collectionPartOfZombie(scanner, console);
               Dj = 1;
            }
            if (command.equals("Ready")){
                plantSide = false;
                zombieSide = true;
            }
           if (plantSide == true){
               if (splitedCommand[0].equals("select")) {
                   for (int counter = 0; counter < selectedPlantCards.size(); counter++) {
                       if (splitedCommand[1].equals(selectedPlantCards.get(counter).getName())){
                           currentCard = selectedPlantCards.get(counter);
                           break;
                       }
                   }
               }
               System.out.println("Plant turn");
               dayAndWaterCommandHandler(command, splitedCommand, plants, zombies, console, garden, suns);
               zombieSide = false;
           }
           else if (zombieSide == true){
               System.out.println("Zombie turn");
               zombieCommandHandler(command, splitedCommand, plants, zombies, console, garden, internalCoin);
               plantSide = false;
           }



        }
    }
        public void zombieCommandHandler(String command, String[] splitedCommand,ArrayList<Plant> plants, ArrayList<Zombie> zombies, Console console, Garden garden, ArrayList<Coin> internalCoin){
            if (splitedCommand[0].equals("Put")) {
                String[] zombieproperty = splitedCommand[1].split(",");
                String zombieName = zombieproperty[0];
                int row = Integer.parseInt(zombieproperty[1]);
                boolean moneyIsEnough = true;
                boolean isInSelected = false;
                int zombieCounterOnRow = 0;
                boolean twoZombieIsOnRow = false; // this boolean used for checking that two zombie is on row or not
                for (int counter = 0; counter < garden.getGarden()[row].length; counter++) {
                    if (garden.getGarden()[row][counter].contains("Zomb")) {
                        zombieCounterOnRow += 1;
                    }
                    if (zombieCounterOnRow == 2) {
                        twoZombieIsOnRow = true;
                        break;
                    }
                }
                for (int counter = 0 ; counter < selectedZombieCards.size() ; counter++){
                    if (zombieName.equals(selectedZombieCards.get(counter).getName())){
                        isInSelected = true;
                       // System.out.println("im in some where that the health of the zombie is : " + selectedZombieCards.get(counter).getName() +  " "+ selectedZombieCards.get(counter).getHealth());
                        if (internalCoin.size() > 10 * selectedZombieCards.get(counter).getHealth()){
                            moneyIsEnough = true;
                            for (int counter2 = 0 ; counter2 < 10 * selectedZombieCards.get(counter).getHealth() ; counter2++){
                                internalCoin.remove(internalCoin.size() - 1);
                            }
                        }
                        else{
                            moneyIsEnough = false;
                        }
                        break;
                    }
                }
                if (!moneyIsEnough){
                    console.printCommand("You have no enough money :( ");
                }
                if (twoZombieIsOnRow) {
                    console.printCommand("there is two zombie in this row :(");
                }
                else if (!isInSelected){
                    console.printCommand("This card is not in your Selected cards");
                }

                if (!twoZombieIsOnRow && moneyIsEnough && isInSelected) {
                    if (garden.getGarden()[row][20].equals("mt") && isInSelected) {
                        putZombie(row, 20, garden, console, zombies, selectedZombieCards, zombieName, turn);
                    } else if (garden.getGarden()[row][21].equals("mt") && isInSelected) {
                        putZombie(row, 21, garden, console, zombies, selectedZombieCards, zombieName, turn);
                    } else {
                        console.printCommand("can not put :( ");
                    }

                }
            }
        }
        public void putZombie(int row, int column, Garden garden, Console console, ArrayList<Zombie> zombies, ArrayList<Cards> selectedZombieCards, String zombieName, int turn){
        Zombie zombie = new Zombie(zombieName, turn);
        zombie.setLocation_x(column);
        zombie.setLocation_y(row);
        zombies.add(zombie);
        if (garden.getGarden()[row][column] == "mt"){
            garden.getGarden()[row][column] = zombieName;
        }
        else {
            garden.getGarden()[row][column].concat(zombieName);
        }
        currentCard = null;
        console.printCommand("You put " + zombieName + " in (" + row +", " + column +") successfully!!!");

    }
    public void addNewCard(ArrayList<Cards> railCards,int turn, Console console){
        if(railCards.size() < 10){
            if (Rj == 0){
                random = generateRandom(2, 4);
                nextTurn = random + turn;
                console.printCommand(" the next turn that the card will add is  : " +  nextTurn);
                Rj = 1;
                System.out.println(Rj);
            }
            if (turn == nextTurn){
                anotherRandom = (int) ((Math.random() * 100) % 25) - 1 ;
                Cards cards = new Cards();
                cards.setName(plantsName[anotherRandom]);//implemennt tne price and
                cards.setPrice(new Plant(plantsName[anotherRandom] , 0).getPrice());
                cards.setHealth(new Plant(plantsName[anotherRandom] , 0).getHealth());
                railCards.add(cards);
                console.printCommand("The " + cards.getName() +" has been added to the rail" );
                /*random = generateRandom(2, 4);
                nextTurn = random + turn;
                System.out.println(" the next turn that the card will add is  : " +  nextTurn);
                //Rj = 1;*/
                Rj = 0;
                System.out.println(Rj);
            }

        }
        else{
            console.printCommand("Rail is full");
        }
    }
    public void railCommandHandler(String command, String[] splitedCommand, ArrayList<Cards> railCards, ArrayList<Plant> plants, ArrayList<Zombie> zombies, Console console, Garden garden){
        if (command.equals("List")) {
            if (railCards.size() == 0){
                console.printCommand("The Rail is empty :( ");
            }
            for (int counter = 0; counter < railCards.size(); counter++) {
                console.printCommand(" */*/* " + railCards.get(counter).getName());
            }
        } else if (splitedCommand[0].equals("select")) {
            currentCard = railCards.get(Integer.parseInt(splitedCommand[1]) - 1);
            //railCards.remove(Integer.parseInt(splitedCommand[1]) - 1);
            console.printCommand("You select " + currentCard.getName() + " successfully :)");
        } else if (command.equals("Record")) {
            console.printCommand("Number of killed Zombies : ");

            console.printCommand(String.valueOf(killedZombies.size()));
        }
        else if (splitedCommand[0].equals("Plant")){
            int row = Integer.parseInt(splitedCommand[1]);
            int column = Integer.parseInt(splitedCommand[2]);
            console.printCommand("im going to plant it");
            planting(row, column, garden, console, plants,  currentCard , 1000000000);
        }
        else if (splitedCommand[0].equals("Remove")){
            int row = Integer.parseInt(splitedCommand[1]);
            int column = Integer.parseInt(splitedCommand[2]);
            remove(row, column, plants, zombies, garden, "p", console);
        }
        else if (command.equals("Show lawn")) {
            console.printCommand("EXISTED PLANTS");
            System.out.println("NO." + "        " + "PLANT NAME" + "        " + "HEALTH");
            for (int counter = 0; counter < plants.size(); counter++) {
                console.printCommand(counter + 1 + "      " + plants.get(counter).getName() + "       " + plants.get(counter).getHealth());
            }
            console.printCommand("EXISTED ZOMBIES");
            console.printCommand("NO." + "        " + "ZOMBIE NAME" + "        " + "HEALTH");
            for (int counter = 0; counter < plants.size(); counter++) {
                console.printCommand(counter + 1 + "      " + zombies.get(counter).getName() + "       " + zombies.get(counter).getHealth());
            }
        }
    }


    public void dayAndWaterCommandHandler(String command, String[] splitedCommand, ArrayList<Plant> plants, ArrayList<Zombie> zombies, Console console, Garden garden, ArrayList<Sun> suns) {
        boolean sunIsEnough = false;
        boolean restIsEnough = false;
        if (command.equals("Show hand")) {
            getSelectedCard(selectedPlantCards, console);
        } else if (splitedCommand[0].equals("select") && !this.name.equals("Rail")) {
            //int sunsValue = suns.size();
            boolean isExist = false;
            if (plants.size() > 0 ){
                for (int counter = plants.size() - 1 ; counter >= 0 ; counter--){
                    if (plants.get(counter).getName().equals(currentCard.getName())){
                        isExist = true;
                       // System.out.println(turn -  plants.get(counter).getInsertionTurn() + "  " + currentCard.getRestTime());
                        if (turn -  plants.get(counter).getInsertionTurn() >= currentCard.getRestTime()){
                            restIsEnough = true;
                            break;
                        }
                        else{
                            restIsEnough = false;
                            console.printCommand("rest time is not enough :(");
                            return;
                        }
                    }
                }
                if (isExist == false){
                    if (turn  >= currentCard.getRestTime()){
                        restIsEnough = true;
                    }
                    else{
                        restIsEnough = false;
                        console.printCommand("rest time is not enough :(");
                        return;
                    }
                }

            }
            else if (plants.size() == 0){
                if (turn  >= currentCard.getRestTime()){
                    restIsEnough = true;
                }
                else{
                    restIsEnough = false;
                    console.printCommand("rest time is not enough :(");
                    return;
                }
            }
            if (suns.size() >= currentCard.getPrice()){
                for (int counter = 0 ; counter < currentCard.getPrice(); counter++){
                    suns.remove(suns.size() - 1);
                }
                sunIsEnough = true;
               console.printCommand("sun is enough :)" + suns.size()  + " name " + currentCard.getName() + currentCard.getPrice());
            }
            else if(suns.size() < currentCard.getPrice()){
                console.printCommand("sun is not enough :(" + suns.size() + " name " + currentCard.getName() +"  "+ currentCard.getPrice());
                return;
            }
            boolean cardIsValid = false;
            for (int counter = 0; counter < selectedPlantCards.size(); counter++) {
                if (splitedCommand[1].equals(selectedPlantCards.get(counter).getName()) && sunIsEnough && sunIsEnough) {
                    // implement a method to check out the price and rest time of plants against the sunsvalue
                    currentCard = selectedPlantCards.get(counter);
                    console.printCommand("You select " + currentCard.getName() + " successfully!!!");
                    cardIsValid = true;
                }
                if (!cardIsValid){
                    console.printCommand("Invalid name");
                }
            }
        } else if (splitedCommand[0].equals("Plant") && this.name.equals("Day")) {
            //System.out.println("salam az planting1");
            if (splitedCommand.length < 3){
                console.printCommand("Input type is wrong ... the input should have this format (( Plant #row_number# #column_number#))");
            }
            else{
                int row = Integer.parseInt(splitedCommand[1]);
                int column = Integer.parseInt(splitedCommand[2]);
                planting(row, column, garden, console, plants, currentCard, suns.size());
            }
        } else if (splitedCommand[0].equals("Plant") && this.name.equals("Water")) {
            int row = Integer.parseInt(splitedCommand[1]);
            int column = Integer.parseInt(splitedCommand[2]);
            boolean thereIsLilypad = false;
            boolean thisIsMarinePlant = false;
            for (int counter = 0; counter < plants.size(); counter++) {
                if (plants.get(counter).getName().equals("LilyPad")) {
                    thereIsLilypad = true;
                }
            }
            if (currentCard.getName().equals("LilyPad") || currentCard.getName().equals("TangleKelp")) {
                thisIsMarinePlant = true;
            }
            if ((row == 2 || row == 3) && !thereIsLilypad) {
                console.printCommand("There is not Lily Pad there :( ");
            }
            if ((row == 2 || row == 3) && thereIsLilypad) {
                planting(row, column, garden, console, plants, currentCard, suns.size());
            }
            if ((row != 2 && row != 3) && thisIsMarinePlant) {
                console.printCommand("You can not plant marine plant in garden :(");
            }
            if ((row == 2 || row == 3) && thisIsMarinePlant) {
                planting(row, column, garden, console, plants, currentCard , suns.size());
            }
            if ((row != 2 && row != 3) && !thisIsMarinePlant) {
                planting(row, column, garden, console, plants, currentCard, suns.size());
            }

        } else if (splitedCommand[0].equals("Remove")) {
            int row = Integer.parseInt(splitedCommand[1]);
            int column = Integer.parseInt(splitedCommand[2]);
            remove(row, column, plants, zombies, garden, "p", console);
        } else if (command.equals("Show lawn")) {
            console.printCommand("EXISTED PLANTS");
            System.out.println("NO." + "        " + "PLANT NAME" + "        " + "HEALTH");
            for (int counter = 0; counter < plants.size(); counter++) {
                console.printCommand(counter + 1 + "      " + plants.get(counter).getName() + "       " + plants.get(counter).getHealth());
            }
            console.printCommand("EXISTED ZOMBIES");
            console.printCommand("NO." + "        " + "ZOMBIE NAME" + "        " + "HEALTH");
            for (int counter = 0; counter < plants.size(); counter++) {
                console.printCommand(counter + 1 + "      " + zombies.get(counter).getName() + "       " + zombies.get(counter).getHealth());
            }
        }

    }

    public void collectionPartOfDay(CommandScanner scanner, Console console) {
        String tempCommand = new String();
        String[] tempCommandSplit = new String[10];
        int i = 0;
        if (i == 0) {
            console.printCommand("You are in Day/Water Collection part *+*");
            i = 1;
        }
        scanner.scanCommands();
        tempCommand = scanner.getCommand();

        while (selectedPlantCards.size() < 8 || !tempCommand.equals("Play")) {

            tempCommandSplit = tempCommand.split(" ");

            if (tempCommand.equals("Show hand")) {
                collectionShowHand(selectedPlantCards, console);
            } else if (tempCommand.equals("Show collection")) {
                collectionShowCollection(selectedPlantCards, reachedPlantCards, console);
            } else if (tempCommandSplit[0].equals("Select")) {
                String cardName = new String();

                    cardName = tempCommandSplit[1];
                    setCollectionPlants(selectedPlantCards, reachedPlantCards, cardName, console);

            }
            else if (tempCommand.equals("Play")) {
                console.printCommand("Lets Start Game!!!");
                break;
            }
           /* else {
                console.printCommand("Invalid Command");
            }*/

            scanner.scanCommands();
            tempCommand = scanner.getCommand();

        }

    }

    public void collectionPartOfZombie(CommandScanner scanner, Console console) {
        String tempCommand = new String();
        String[] tempCommandSplit = new String[10];
        if (Di == 0) {
            console.printCommand("You are in Zombie Collection part *+*");
            Di = 1;
        }
        scanner.scanCommands();
        tempCommand = scanner.getCommand();

        while (selectedZombieCards.size() < 8 || !tempCommand.equals("Play")) {

            tempCommandSplit = tempCommand.split(" ");

            if (tempCommand.equals("Show hand")) {
                collectionShowHand(selectedZombieCards, console);
            } else if (tempCommand.equals("Show collection")) {
                if (this.name.equals("PvP")){
                    collectionShowCollection(selectedZombieCards, Game.secondaccount.reachedZombieCards, console);
                }
                else if (this.name.equals("Zombie")){
                    collectionShowCollection(selectedZombieCards, reachedZombieCards, console);
                }
                collectionShowCollection(selectedZombieCards, Game.secondaccount.reachedZombieCards, console);
            } else if (tempCommandSplit[0].equals("Select")) {
                String cardName = new String();
                cardName = tempCommandSplit[1];
                if (this.name.equals("PvP")){
                    setCollectionZombies(selectedZombieCards, Game.secondaccount.reachedZombieCards, cardName, console);
                    currentCard = null;
                }
                else if (this.name.equals("Zombie")){
                    setCollectionZombies(selectedZombieCards, reachedZombieCards, cardName, console);
                }
                }

            scanner.scanCommands();
            tempCommand = scanner.getCommand();
            if (tempCommand.equals("Play")){
                console.printCommand("Lets start the Game!!!");
                break;
            }
        }

    }

    // this method is for showing the unselectedCards
    public void collectionShowCollection(ArrayList<Cards> selectedPlantCards, ArrayList<Cards> reachedPlantCards, Console console) {
        ArrayList<Cards> tempreached = new ArrayList<Cards>();
        ArrayList<Cards> unselected = new ArrayList<Cards>();
        for (int counter = 0; counter < reachedPlantCards.size(); counter++) {
            tempreached.add(reachedPlantCards.get(counter));
        }
        for (int counter = 0; counter < tempreached.size(); counter++) {
            if (!tempreachedIsselected(tempreached.get(counter), selectedPlantCards)) {
                unselected.add(tempreached.get(counter));
            }
        }
        console.printCommand("Unselected cards are : ");
        for (int counter = 0; counter < unselected.size(); counter++) {
            console.printCommand((counter + 1) + "  #*#  " + unselected.get(counter).getName());
        }
    }

    // this method is used in collectionShowCollection
    public boolean tempreachedIsselected(Cards card, ArrayList<Cards> selectedPlantCards) {
        for (int counter = 0; counter < selectedPlantCards.size(); counter++) {
            if (card.getName().equals(selectedPlantCards.get(counter).getName())) {
                return true;
            }
        }
        return false;
    }

    // this method is for showing the selectedPlantCard
    public void collectionShowHand(ArrayList<Cards> selectedPlantCards, Console console) {
        if (selectedPlantCards.size() == 0)
            console.printCommand("there is no card here");
        for (int counter = 0; counter < selectedPlantCards.size(); counter++) {
            console.printCommand("#*#  " + selectedPlantCards.get(counter).getName());
        }
    }

    // the common commands between game :
    boolean locationIsFilled(Garden garden, int row, int column) {
        if (garden.getGarden()[row][column] != "mt") {
            return true;
        }
        return false;
    }

    void setCollectionZombies(ArrayList<Cards> selectedZombieCards, ArrayList<Cards> reachedZombieCards, String cardName, Console console) {
        boolean cardIsExist = false;
        boolean cardWasSelected = false;
        for (int counter = 0; counter < reachedZombieCards.size(); counter++) {
            if (cardName.equals(reachedZombieCards.get(counter).getName())) {
                cardIsExist = true;
                currentCard = new Cards(reachedZombieCards.get(counter).getSpeed(), reachedZombieCards.get(counter).getHealth(), reachedZombieCards.get(counter).getName());
                break;
            }
        }
        for (int counter = 0; counter < selectedZombieCards.size(); counter++) {
            if (cardName.equals(selectedZombieCards.get(counter).getName())) {
                cardWasSelected = true;
                break;
            }
        }
        if (!cardIsExist) {
            console.printCommand("You do not have this card :( ");
        } else if (cardWasSelected) {
            console.printCommand("You select this card previously :( ");
        } else if (cardIsExist && !cardWasSelected) {
            selectedZombieCards.add(currentCard);
            System.out.println("You select " + currentCard.getName() + " successfully");
        }
    }

    void setCollectionPlants(ArrayList<Cards> selectedPlantCards, ArrayList<Cards> reachedPlantCards, String cardName, Console console) {
        boolean cardIsExist = false;
        boolean cardWasSelected = false;
        for (int counter = 0; counter < reachedPlantCards.size(); counter++) {
            if (cardName.equals(reachedPlantCards.get(counter).getName())) {
                cardIsExist = true;
                currentCard = new Cards(reachedPlantCards.get(counter).getName(), reachedPlantCards.get(counter).getPrice(), reachedPlantCards.get(counter).getRestTime());
                break;
            }
        }
        for (int counter = 0; counter < selectedPlantCards.size(); counter++) {
            if (cardName.equals(selectedPlantCards.get(counter).getName())) {
                cardWasSelected = true;
                break;
            }
        }
        if (!cardIsExist) {
            console.printCommand("You do not have this card :( ");
        } else if (cardWasSelected) {
            console.printCommand("You select this card previously :( ");
        } else if (cardIsExist && !cardWasSelected) {
            selectedPlantCards.add(currentCard);
            System.out.println("You select " + currentCard.getName() + " successfully");
        }
    }


    void getSelectedCard(ArrayList<Cards> cards, Console console) {
        if (cards.size() == 0){
            console.printCommand("there is no card here");
        }
        else{
            for (int counter = 0; counter < cards.size(); counter++) {
                console.printCommand("Card name :  " + cards.get(counter).getName() + "// needed suns :  " + cards.get(counter).getPrice() + "//  rest time :  " + cards.get(counter).getRestTime());
            }
        }
    }

    public void remove(int row, int column, ArrayList<Plant> plants, ArrayList<Zombie> zombies, Garden garden, String type, Console console) {
        if (type.equals("p")) {
            for (int counter = 0; counter < plants.size(); counter++) {
                if (row == plants.get(counter).getLocation_y() && column == plants.get(counter).getLocation_x()) {
                    plants.remove(counter);
                    garden.getGarden()[row][column] = "mt";
                    console.printCommand("Plant removed successfully!!!");
                    break;
                }
            }
        } else if (type.equals("z")) {
            for (int counter = 0; counter < zombies.size(); counter++) {
                if (row == zombies.get(counter).getLocation_x() && column == zombies.get(counter).getLocation_y()) {
                    zombies.remove(counter);
                    garden.getGarden()[row][column] = "mt";
                    console.printCommand("Zombie removed successfully!!!");
                    break;
                }
            }
        }

    }

    public void planting(int row, int column, Garden garden, Console console, ArrayList<Plant> plants, Cards currentCard, int sunsValue) {
      //  System.out.println("salam az planting");
        boolean cardIsNULL = false;
        boolean locationIsFilled = false;
        if (currentCard == null) {
            cardIsNULL = true;
        }
        if (locationIsFilled(garden, row, column)) {
            locationIsFilled = true;
        }
        if (cardIsNULL == true) {
            console.printCommand("YOU DID NOT SELECT ANY CARD :(");
            return;
        } else if (locationIsFilled == true) {
            console.printCommand("location Is Filled :( ");
            return;
        }
        if (column != 0 && column % 2 == 0 && this.name.equals("Rail")) {
            //System.out.println("salam az dakhele planting  " + currentCard.getName());
            for (int counter = 0; counter < railCards.size(); counter++) {
                if (currentCard.getName().equals(railCards.get(counter).getName())) {
                    Plant newPlant = new Plant(currentCard.getName(), turn);
                    newPlant.setLocation_x(column);
                    newPlant.setLocation_y(row);
                    plants.add(newPlant);
                    railCards.remove(counter);
                    if (garden.getGarden()[row][column] == "mt") {
                        garden.getGarden()[row][column] = (currentCard.getName());
                    } else {
                        garden.getGarden()[row][column].concat(currentCard.getName());
                    }
                    console.printCommand("You plant" + currentCard.getName() + "  in location  (" + row + ", " + column + ")");
                    currentCard = null;
                    break;
                }
            }
        }
        else if (column != 0 && column % 2 == 0) {
            //System.out.println("salam az dakhele planting");
            for (int counter = 0; counter < selectedPlantCards.size(); counter++) {
                if (currentCard.getName().equals(selectedPlantCards.get(counter).getName())) {
                   // System.out.println("i found it !!!");
                    Plant newPlant = new Plant(currentCard.getName(), turn);
                    newPlant.setLocation_x(column);
                    newPlant.setLocation_y(row);
                    plants.add(newPlant);
                    if (garden.getGarden()[row][column] == "mt") {
                        garden.getGarden()[row][column] = (currentCard.getName());
                    } else {
                        garden.getGarden()[row][column].concat(currentCard.getName());
                    }
                    console.printCommand("You plant" + currentCard.getName() + "  in location  (" + row + ", " + column + ")");
                    currentCard = null;
                    break;
                }
            }
        }


        if (column % 2 != 0) {
            console.printCommand("Choose the fucking even column number :(");
        }

    }



    /**
     * this method will update garden after the "turn" is over
     *
     * @param garden
     */
    public void updateGarden(Garden garden , ArrayList<Plant> plants, ArrayList<Zombie> zombies , ArrayList<Bullet> bullets , ArrayList<LawnMower> lawnMowers , ArrayList<Sun> suns , int turn , ArrayList<Coin> internalCoin){
        if (this.name.equals("Zombie")){
            for (LawnMower lawnMower : lawnMowers){
                lawnMower.setIsUsed(true);
            }
        }
        // this loop every  plant that have a special activity will do it's activity
        for (int i = 0 ; i < plants.size() ; i++){
            plants.get(i).activity(turn , plants.get(i).getName() , zombies , suns);
        }
        // this loop every  zombie that have a special activity will do it's activity
        for (int i = 0 ; i < zombies.size() ; i++)
            zombies.get(i).activity(zombies , plants , turn);
        // in this loop every plant will shoot when it should
        for (Plant plant : plants)
            plant.shoot(turn , bullets , zombies);
        for (int i = 0 ; i < zombies.size() ; i++)
            zombies.get(i).walk(bullets , plants , zombies , turn , lawnMowers);
        // in this loop every zombie that has been frozen will be back to normal speed
        for (Zombie zombie : zombies){
            if(zombie.isFrozen())
                zombie.setFrozen(false);
        }
        // in this loop every bullet will move in its own direction
        for (int i = 0 ; i < bullets.size() ; i++){
            if (bullets.get(i).getIsForward())
                bullets.get(i).moveForward(zombies);
            else
                bullets.get(i).moveBackward(zombies);
            if (bullets.get(i).getLocation_x() >= 22)
                bullets.remove(bullets.get(i));
        }
        for (int i = 0 ; i < bullets.size() ; i++){
            if (bullets.get(i).getLocation_x() > 21 || bullets.get(i).getLocation_x() < 0){
                bullets.remove(i);
            }
        }
        // this loop reduces the health of plants
        for (int i = 0 ; i< plants.size() ; i++){
            for (int j = 0 ; j < zombies.size() ; j++){
                if (locationIsEqual(plants.get(i) , zombies.get(j))){
                    plants.get(i).hurt(zombies.get(j) , zombies , turn);
                    if (plants.get(i).getName().equals("TangleKelp"))
                        plants.remove(plants.get(i));
                    else if (plants.get(i).getName().equals("PotatoMine") && turn - plants.get(i).getInsertionTurn() >= 2)
                        plants.remove(plants.get(i));
                }
            }
        }
        // this loop reduces the health of zombies
        for (int i = 0 ; i < bullets.size() ; i++){
            for (int j = 0 ; j < zombies.size() ; j++){
                if (locationIsEqual(bullets.get(i) , zombies.get(j))) {
                    zombies.get(j).hurt(bullets.get(i) , bullets);
                    bullets.remove(bullets.get(i));
                }
            }
        }
        // this loop will kill the dead plants
        for (int i = 0 ; i< plants.size() ; i++){
            if (plants.get(i).getHealth() <= 0) {
                for (int j = 0 ; j < (new Plant(plants.get(i).getName() , turn)).getHealth() ; j++) {
                    internalCoin.add(new Coin());
                }
                plants.remove(plants.get(i));
            }
        }
        // this loop will kill the dead zombies except zombonie
        for (int j = 0 ; j < zombies.size() ; j++){
            if (zombies.get(j).getHealth() <= 0){
                if (!zombies.get(j).getName().contains("Zombon")) {
                    killedZombies.add(zombies.get(j));
                    zombies.remove(zombies.get(j));
                }
                else {
                    zombies.add(new Zombie("Zombie" , turn));
                    zombies.get(zombies.size()-1).setLocation_x(zombies.get(j).getLocation_x());
                    zombies.get(zombies.size()-1).setLocation_y(zombies.get(j).getLocation_y());
                    zombies.remove(zombies.get(j));
                }
            }
        }
        for (int i = 0 ; i < bullets.size() ; i++){
            if (bullets.get(i).getLocation_x() > 22)
                bullets.remove(bullets.get(i));
        }
        // this method will generate suns randomly
        makeSun(suns , turn , lastTurnSunGenerated , currentSunGenerationPeriod, this.name);
        // this method will check the lawnmowers and if it is necessary will use them
        //checkLawnMower(lawnMowers , zombies);
        gameIsFinished = checkGameIsFinished(zombies , lawnMowers);
        for (Zombie zombie : zombies){
            if (zombie.getDefenceIsOn() == false){
                if (zombie.getPeriodOfDefenceDisability() == 3) {
                    zombie.setPeriodOfDefenceDisability(0);
                    zombie.setDefenceIsOn(true);
                }
                else
                    zombie.addPeriodOfDefenceDisability();
            }

        }
        //updateLawn(garden , plants , zombies);
        if (this.name.equals("Rail")){
            makeWaveForRail(zombiesNameForDay , turn , zombies , garden);
        }
        if (this.name.equals("Day")){
            if (turn == 3) {
                makeWave(zombiesNameForDay, turn, zombies, garden);
                numberOfWaves++;
            }
            if (turn > 3 && numberOfWaves < 3 && zombies.size() == 0){
                makeWave(zombiesNameForDay, turn, zombies, garden);
                numberOfWaves++;
            }
        }
        if (this.name.equals("Water")){
            if (turn == 3) {
                makeWaveForWater(zombiesName, turn, zombies, garden);
                numberOfWaves++;
            }
            if (turn > 3 && numberOfWaves < 3 && zombies.size() == 0){
                makeWaveForWater(zombiesName, turn, zombies, garden);
                numberOfWaves++;
            }
        }
        if (this.name.equals("Zombie")){
            if (turn == 1) {
                makeWaveForZombie(plantsName, turn, plants, garden);
                numberOfWaves++;
            }
            if (turn > 3 && numberOfWaves < 3 && zombies.size() == 0 && plants.size() == 0){
                makeWaveForZombie(plantsName, turn, plants, garden);
                numberOfWaves++;
            }
        }
    }
    private void makeWaveForRail(String[] zombiesNameForDay,int turn,ArrayList<Zombie> zombies,Garden garden){
        if (turn - lastTurnZombieGeneratedForRail == currentZombieGenerationPeriodForRail){
            String randomZombieName = zombiesNameForDay[generateRandom(0,(zombiesNameForDay.length-1))];
            int row = generateRandom(0,5);
            int column = generateRandom(20,21);
            if (garden.getGarden()[row][column].equals("mt")){
                zombies.add(new Zombie(randomZombieName, turn));
                zombies.get(zombies.size()-1).setLocation_y(row);
                zombies.get(zombies.size()-1).setLocation_x(column);
                garden.getGarden()[row][column] = randomZombieName;
            }
            lastTurnZombieGeneratedForRail = turn;
            currentZombieGenerationPeriodForRail = generateRandom(3 , 5);
        }

    }
    private void makeWaveForZombie(String[] plantsName , int turn , ArrayList<Plant> plants , Garden garden){
        int numOfPlants = generateRandom(8 , 12);
        for (int i = 0 ; i < numOfPlants ; i++){
            int row = generateRandom(0,5);
            int column = generateRandom(1,3);
            String randomPlantName = plantsName[generateRandom(0,(plantsName.length-1))];
            if (garden.getGarden()[row][column].equals("mt")){
                plants.add(new Plant(randomPlantName , turn));
                plants.get(plants.size()-1).setLocation_y(row);
                plants.get(plants.size()-1).setLocation_x(column);
                garden.getGarden()[row][column] = randomPlantName;
            }
        }
    }
    private void makeWaveForWater(String[] zombiesNameForDay , int turn , ArrayList<Zombie>zombies , Garden garden ) {
        int numOfZombies = generateRandom(4 , 10);
        int row = 0;
        for (int i = 0 ; i < numOfZombies ; i++){
            String randomZombieName = zombiesNameForDay[generateRandom(0,(zombiesNameForDay.length-1))];
            if (randomZombieName.equals("SnorkelZombie") || randomZombieName.equals("DolphinRiderZombie")){
                if (garden.getGarden()[2][20].equals("mt") || garden.getGarden()[2][21].equals("mt")){
                    row = 2;
                    zombies.add(new Zombie(randomZombieName , turn));
                } else if (garden.getGarden()[3][20].equals("mt") || garden.getGarden()[3][21].equals("mt")){
                    row = 3;
                    zombies.add(new Zombie(randomZombieName , turn));
                }
            }
            else{
                zombies.add(new Zombie(randomZombieName , turn));
                if (row == 2 || row == 3){
                    row = 4;
                }
            }
                zombies.add(new Zombie(randomZombieName , turn));
            if (garden.getGarden()[row][20].equals("mt")) {
                zombies.get(zombies.size()-1).setLocation_x(20);
                zombies.get(zombies.size()-1).setLocation_y(row);
            } else if (garden.getGarden()[row][21].equals("mt")) {
                zombies.get(zombies.size()-1).setLocation_x(21);
                zombies.get(zombies.size()-1).setLocation_y(row);
            }
            if (row == 5)
                row = 0;
            else
                row++;
        }
    }
    private void makeWave(String[] zombiesNameForDay , int turn , ArrayList<Zombie>zombies , Garden garden ) {
        int numOfZombies = generateRandom(4 , 10);
        int randomrow = 0;
        for (int i = 0 ; i < numOfZombies ; i++){
            String randomZombieName = zombiesNameForDay[generateRandom(0,(zombiesNameForDay.length-1))];
            zombies.add(new Zombie(randomZombieName , turn));
            if (garden.getGarden()[randomrow][20].equals("mt")) {
                zombies.get(zombies.size()-1).setLocation_x(20);
                zombies.get(zombies.size()-1).setLocation_y(randomrow);
            } else if (garden.getGarden()[randomrow][21].equals("mt")) {
                zombies.get(zombies.size()-1).setLocation_x(21);
                zombies.get(zombies.size()-1).setLocation_y(randomrow);
            }
            if (randomrow == 5){
                randomrow = 0;
            }
            else
                randomrow++;

        }
    }
    private boolean thereIsZombieInRow(Plant plant, ArrayList<Zombie> zombies) {
        for (Zombie zombie : zombies){
            if (zombie.getLocation_y() == plant.getLocation_y())
                return true;
        }
        return false;
    }

    private void makeSun(ArrayList<Sun> suns, int turn , int lastTurnSunGenerated , int currentSunGenerationPeriod, String name) {
        if (turn - lastTurnSunGenerated == currentSunGenerationPeriod && (name.equals("Day") || name.equals("Water") || name.equals("PvP"))){
            for (int counter = 0 ; counter < generateRandom(2, 5) ; counter++){
                suns.add(new Sun());
            }
            this.lastTurnSunGenerated = turn;
            this.currentSunGenerationPeriod = generateRandom(1 , 2);
        }
    }
    // this method will generate random numbers between min adn max
    private int generateRandom(int min, int max) {
        double x = Math.random()*((max - min)+1)+min;
        int randomNumber = (int) x;
        return randomNumber;
    }
    private void checkLawnMower(ArrayList<LawnMower> lawnMowers, ArrayList<Zombie> zombies) {
        for (LawnMower lawnMower : lawnMowers){
            if (!lawnMower.getIsUsed() && findZombie(lawnMower.getRow(),lawnMower.getColumn(),zombies)!=null)
                lawnMower.killAllZombiesInRow(zombies);
        }
    }
    public boolean checkGameIsFinished(ArrayList<Zombie> zombies , ArrayList<LawnMower> lawnMowers) {
            for (Zombie zombie : zombies){
                if (zombie.getLocation_x() == 0){
                    if (findLawnMower(zombie.getLocation_y() , lawnMowers).getIsUsed() == true){
                        if (!this.name.equals("Zombie")){
                            System.out.println("You are fucking expendable LOOSER :| ");

                        }
                        else {
                            System.out.println("You kicked their Asssss");
                            Game.mainaccount.setNumberofKilledZombies(killedZombies.size());
                            Game.mainaccount.setExtremecoins(100);
                        }
                        return  true;
                    }

                }
            }
        return false;
    }
    //this method should update the string array of object
    private void updateLawn(Garden garden , ArrayList<Plant> plants , ArrayList<Zombie> zombies) {
        /*for (int i = 0 ; i < 6 ; i++){
            for (int j = 0 ; j < 22 ; j++){
                garden.getGarden()[i][j] = "mt";
            }
        }*/
        for (int counter = 0 ; counter < plants.size() ; counter++){
            garden.getGarden()[plants.get(counter).getLocation_y()][plants.get(counter).getLocation_x()].concat(plants.get(counter).getName());
        }
        for (int counter = 0 ; counter < zombies.size() ; counter++){
            garden.getGarden()[zombies.get(counter).getLocation_y()][zombies.get(counter).getLocation_x()].concat(zombies.get(counter).getName());
        }
    }
    private boolean locationIsEqual(Plant plant, Zombie zombie) {
        if (plant.getLocation_x() + 1 == zombie.getLocation_x() && plant.getLocation_y() == zombie.getLocation_y())
            return  true;
        return false;
    }
    private boolean locationIsEqual(Bullet bullet, Zombie zombie) {
        if (bullet.getLocation_x() == zombie.getLocation_x() + 1 && bullet.getLocation_y() == zombie.getLocation_y())
            return  true;
        return false;
    }
    public LawnMower findLawnMower(int row , ArrayList<LawnMower> lawnMowers){
        for (LawnMower lawnMower : lawnMowers){
            if (lawnMower.getRow() == row)
                return lawnMower;
        }
        return null;
    }
    public Plant findPlant(String name , ArrayList<Plant> plants){
        for (Plant plant : plants){
            if (plant.getName().equals(name)){
                return plant;
            }
        }
        return null;
    }
    //this method will find the zombie by getting a name
    public Zombie findZombie(String name , ArrayList<Zombie> Zombies){
        for (Zombie zombie : Zombies){
            if (zombie.getName().equals(name)){
                return zombie;
            }
        }
        return null;
    }
    //this method will find zombie by getting a location
    public Zombie findZombie(int x , int y , ArrayList<Zombie> Zombies){
        for (Zombie zombie : Zombies){
            if (zombie.getLocation_y() == y && zombie.getLocation_x() == x){
                return zombie;
            }
        }
        return null;
    }
    public boolean isPlant (String name , String[]plantsName){
        int counter = 0;
        for (int i = 0 ; i <plantsName.length ; i++){
            if (plantsName[i].equals(name))
                counter++;
        }
        if (counter != 0)
            return true;
        return false;
    }
    public boolean isZombie (String name){
        if (name.contains("Zomb"))
            return true;
        return false;
    }
}
