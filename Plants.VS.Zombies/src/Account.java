import java.io.Serializable;
import java.util.ArrayList;

public class Account implements Comparable, Serializable {
    private String username;
//    fgfggf
    private String password;
    private int numberofKilledZombies = 0;
    private int extremecoins = 0;
    public ArrayList<Cards> reachedPlantCards = new ArrayList<Cards>();
    public ArrayList<Cards> reachedZombieCards = new ArrayList<Cards>();
    Cards Sunflower = new Cards("Sunflower", 1, 2);
    Cards CherryBomb = new Cards("CherryBomb", 2, 4);
    Cards PeaShooter = new Cards("PeaShooter", 2, 2);
    Cards SnowPea = new Cards("SnowPea", 3, 3);
    Cards ScaredyShroom = new Cards("Scaredy_Shroom", 1, 2);
    Cards Kernelplut = new Cards("KernelPlut", 3, 3);
    Cards WallNut = new Cards("Wall_Nut", 2, 4);
    Cards LilyPad = new Cards("LilyPad", 0, 1);

    {
        reachedPlantCards.add(CherryBomb);
        reachedPlantCards.add(PeaShooter);
        reachedPlantCards.add(SnowPea);
        reachedPlantCards.add(ScaredyShroom);
        reachedPlantCards.add(Kernelplut);
        reachedPlantCards.add(WallNut);
        reachedPlantCards.add(Sunflower);
        reachedPlantCards.add(LilyPad);
    }

    Cards Zombie = new Cards(2, 2, "Zombie");
    Cards FootballZombie = new Cards(3, 4, "FootballZombie");
    Cards ConeheadZombie = new Cards(2, 3, "ConeheadZombie");
    Cards BungeeZombie = new Cards(0, 3, "BungeeZombie");
    Cards BalloonZombie = new Cards(2, 3, "BalloonZombie");
    Cards Zomboni = new Cards(2, 3, "Zomboni");
    Cards ScreenDoorZombie = new Cards(2, 2, "ScreenDoorZombie");

    {
        reachedZombieCards.add(Zombie);
        reachedZombieCards.add(FootballZombie);
        reachedZombieCards.add(ConeheadZombie);
        reachedZombieCards.add(BungeeZombie);
        reachedZombieCards.add(BalloonZombie);
        reachedZombieCards.add(Zomboni);
        reachedZombieCards.add(ScreenDoorZombie);
    }

    // these arraylists should initialize by seven primarycards
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        Game.filehandler.save();
    }

    @Override
    public int compareTo(Object account) {
        int compareage = ((Account) account).getNumberofKilledZombies();
        /* For Ascending order*/
        return -(this.numberofKilledZombies - compareage);

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }

    public int getExtremecoins() {
        return extremecoins;
    }

    public void setExtremecoins(int extremecoins) {
        this.extremecoins += extremecoins;
        Game.filehandler.save();

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        Game.filehandler.save();

    }

    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
        Game.filehandler.save();

    }

    public int getNumberofKilledZombies() {
        return numberofKilledZombies;
    }

    public void setNumberofKilledZombies(int numberofKilledZombies) {
        this.numberofKilledZombies += numberofKilledZombies;
        Game.filehandler.save();
    }


}
