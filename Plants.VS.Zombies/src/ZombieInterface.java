import java.util.ArrayList;

public interface ZombieInterface {
    void activity(ArrayList<Zombie> zombies , ArrayList<Plant> plants , int turn );
    void hurt(Bullet bullet , ArrayList<Bullet> bullets);
    void walk(ArrayList<Bullet> bullets, ArrayList<Plant> plants , ArrayList<Zombie> zombies , int turn , ArrayList<LawnMower> lawnMowers);
    void setProperties(String name);
}
