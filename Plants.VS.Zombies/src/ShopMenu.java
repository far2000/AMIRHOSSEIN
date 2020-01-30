import java.util.ArrayList;
import java.util.Scanner;

public class ShopMenu {
    private String[] orders = {"Help", "Show shop", "Show collection", "Exit", "Buy[object]","Money"};
    private String[] allplants =    {"PeaShooter", "SnowPea", "Cabbage_Plut", "Repeater", "Threepeater", "Cactus", "GatlingPea",
            "Scaredy_Shroom", "KernelPlut", "SplitPea", "Explode_O_Nut", "Melon_Pult", "LilyPad", "Winter_Melon", "Wall_Nut"
            , "TangleKelp", "Tall_Nut", "Cattail", "PotatoMine", "CherryBomb", "Magnet_Shroom", "Sunflower", "TwinSunflower"
            , "Jalapeno"};
    private String[] allzombies=    {"Zombie","FootballZombie","BucketheadZombie","DolphinRiderZombie","Giga-gargantuarZombie",
            "SnorkelZombie","PogoZombie","ScreenDoorZombie","TargetZombie","NewspaperZombie","ConeheadZombie","Zomboni",
            "CatapultZombie","BungeeZombie","BalloonZombie"};

    int price = -1;
    int restTime = -1;
    int health = -1;
    int speed = -1;

    public void commands(CommandScanner scanner) {

        System.out.println("Welcome to Shop!!");

        while (true) {
            ArrayList<String> plantswehave=new ArrayList<String>();
            ArrayList<String> zombieswehave=new ArrayList<String>();

            for(Cards plant: Game.mainaccount.reachedPlantCards)
                plantswehave.add(plant.getName());
            for(Cards zombie: Game.mainaccount.reachedZombieCards)
                zombieswehave.add(zombie.getName());
            scanner.scanCommands();
            String string = scanner.getCommand();
            int validitionflag = 0;
            for (String t : orders)
                if ((t.equals(string)) || (string.substring(0, 4).equals("Buy ")))
                    validitionflag = 1;
            if (validitionflag == 0)
                System.out.println("Invalid command");
            validitionflag = 0;
            if (string.equals("Help")) {
                showcommands();
            }
            else if (string.equals("Exit")) {
                Game.mainmenu.commands(scanner);
            }
            else if (string.substring(0, 4).equals("Buy ")) {
                buy(string.substring(4));
                Game.filehandler.save();
            }
            else if(string.equals("Show collection")){
                for(String plant:plantswehave)

                    System.out.println(plant);
                for(String zombie :zombieswehave){
                    System.out.println(zombie);}

            }else if(string.equals("Money"))
                System.out.println(Game.mainaccount.getExtremecoins());

            else if(string.equals("Show shop")){
                for(String plant:allplants)
                    if(!plantswehave.contains(plant)){
                        plants(plant);
                        int pricebycoin = (price * restTime * health) + 1;
                        System.out.println(plant +" "+ pricebycoin);}
                for(String zombie:allzombies)
                    if(!zombieswehave.contains(zombie)){
                        zombies(zombie);
                        int pricebycoin = (1 + speed) * health * 10;
                        System.out.println(zombie + " "+pricebycoin);}
            }
            setprimers();
            //to set health,price... to their premier;
        }

    }

    private void buy(String string) {
        int pricebycoin = 0;
        for (Cards card : Game.mainaccount.reachedPlantCards)
            if (string.equals(card.getName())) {
                System.out.println("you had this plant already");
                return;
            }
        for (Cards card : Game.mainaccount.reachedZombieCards)
            if (string.equals(card.getName())) {
                System.out.println("you had this zombie already");
                return;
            }
        if ((!plants(string)) && (!zombies(string))) {
            System.out.println("this card is not existed");
            return;
        }
        int extremecoins = Game.mainaccount.getExtremecoins();
        if (plants(string)) {
            pricebycoin = (price * restTime * health) + 1;
            if (extremecoins >= pricebycoin) {
                Game.mainaccount.setExtremecoins(extremecoins - pricebycoin);
                Cards plant = new Cards(string, price, restTime);
                Game.mainaccount.reachedPlantCards.add(plant);
                return;
            } else {
                System.out.println("you don't have enough coins to buy " + string);
                return;
            }
        }
        if (zombies(string)) {
            pricebycoin = (1 + speed) * health * 10;
            if (extremecoins >= pricebycoin) {
                Game.mainaccount.setExtremecoins(extremecoins - pricebycoin);
                Cards zombie = new Cards(speed, health, string);
                Game.mainaccount.reachedZombieCards.add(zombie);
                return;
            } else {
                System.out.println("you don't have enough coins to buy " + string);
                return;
            }
        }
    }
    private boolean plants(String name) {
        if (name.equals("PeaShooter")) {
            this.price = 2;
            this.restTime = 2;
            this.health = 2;

        } else if (name.equals("SnowPea")) {
            this.price = 3;
            this.restTime = 3;
            this.health = 3;
        } else if (name.equals("Cabbage_Plut")) {
            this.price = 2;
            this.restTime = 3;
            this.health = 2;

        } else if (name.equals("Repeater")) {
            this.price = 3;
            this.restTime = 4;
            this.health = 4;

        } else if (name.equals("Threepeater")) {
            this.price = 4;
            this.restTime = 4;
            this.health = 5;

        } else if (name.equals("Cactus")) {
            this.price = 4;
            this.restTime = 5;
            this.health = 5;


        } else if (name.equals("GatlingPea")) {
            this.price = 5;
            this.restTime = 4;
            this.health = 3;

        } else if (name.equals("Scaredy_Shroom")) {
            this.price = 1;
            this.restTime = 2;
            this.health = 1;

        } else if (name.equals("KernelPlut")) {
            this.price = 3;
            this.restTime = 3;
            this.health = 2;

        } else if (name.equals("SplitPea")) {
            this.price = 4;
            this.restTime = 4;
            this.health = 3;

        } else if (name.equals("Explode_O_Nut")) {
            this.price = 4;
            this.restTime = 5;
            this.health = 3;

        } else if (name.equals("Melon_Plut")) {
            this.price = 3;
            this.restTime = 3;
            this.health = 3;

        } else if (name.equals("LilyPad")) {
            this.price = 0;
            this.restTime = 1;
            this.health = 1;

        } else if (name.equals("Winter_Melon")) {
            this.price = 4;
            this.restTime = 5;
            this.health = 3;

        } else if (name.equals("Wall_Nut")) {
            this.price = 2;
            this.restTime = 4;
            this.health = 4;

        } else if (name.equals("TangleKelp")) {
            this.price = 3;
            this.restTime = 3;
            this.health = 0;

        } else if (name.equals("Tall_Nut")) {
            this.price = 4;
            this.restTime = 6;
            this.health = 6;

        } else if (name.equals("Cattail")) {
            this.price = 5;
            this.restTime = 5;
            this.health = 3;

        } else if (name.equals("PotatoMine")) {
            this.price = 2;
            this.restTime = 3;
            this.health = 1;

        } else if (name.equals("CherryBomb")) {
            this.price = 2;
            this.restTime = 4;
            this.health = 0;

        } else if (name.equals("Magnet_Shroom")) {
            this.price = 4;
            this.restTime = 4;
            this.health = 2;

        } else if (name.equals("Sunflower")) {
            this.price = 1;
            this.restTime = 2;
            this.health = 2;

        } else if (name.equals("TwinSunflower")) {
            this.price = 3;
            this.restTime = 5;
            this.health = 2;

        } else if (name.equals("Jalapeno")) {
            this.price = 4;
            this.restTime = 5;
            this.health = 0;

        } else
            return false;
        return true;
    }
    private boolean zombies(String name) {
        if (name.equals("Zombie")) {
            speed = 2;
            health = 2;
        } else if (name.equals("FootballZombie")) {
            speed = 3;
            health = 4;
        } else if (name.equals("BucketheadZombie")) {
            speed = 2;
            health = 3;
        } else if (name.equals("ConeheadZombie")) {
            speed = 2;
            health = 3;
        } else if (name.equals("Zomboni")) {
            speed = 2;
            health = 3;
        } else if (name.equals("CatapultZombie")) {
            speed = 2;
            health = 3;
        } else if (name.equals("BungeeZombie")) {
            speed = 0;
            health = 3;
        } else if (name.equals("BalloonZombie")) {
            speed = 2;
            health = 3;
        } else if (name.equals("NewspaperZombie")) {
            speed = 2;
            health = 2;

        } else if (name.equals("TargetZombie")) {
            speed = 2;
            health = 3;

        } else if (name.equals("ScreenDoorZombie")) {
            speed = 2;
            health = 2;

        } else if (name.equals("Giga-gargantuarZombie")) {

            speed = 6;
            health = 1;
        } else if (name.equals("PogoZombie")) {
            speed = 2;
            health = 2;
        } else if (name.equals("SnorkelZombie")) {
            speed = 2;
            health = 2;
        } else if (name.equals("DolphinRiderZombie")) {
            speed = 2;
            health = 2;
        } else
            return false;
        return true;
    }

    public void showcommands() {
        for (String s : orders)
            if (s != "Help")
                System.out.println(s);
    }

    private void setprimers() {
        price = -1;
        restTime = -1;
        health = -1;
        speed = -1;
    }
}
