import java.util.ArrayList;

public interface Handlable {
    void updateGarden(Garden garden , ArrayList<Plant> plants, ArrayList<Zombie> zombies , ArrayList<Bullet> bullets , ArrayList<LawnMower> lawnMowers , ArrayList<Sun> suns , int turn , ArrayList<Coin> internalCoin);
    void execute(String command, ArrayList<Plant> plants, Garden garden, ArrayList<Zombie> zombies, Console console, CommandScanner scanner, int turn, ArrayList<Sun> suns, ArrayList<Coin> internalCoin);


}
