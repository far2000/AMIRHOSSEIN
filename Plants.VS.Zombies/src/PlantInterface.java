import java.util.ArrayList;

public interface PlantInterface {
    void hurt(Zombie zombie , ArrayList<Zombie> zombies , int turn);
    void shoot(int turn , ArrayList<Bullet>bullets , ArrayList<Zombie> zombies);
    void setProperties(String name);
    void activity(int turn , String name , ArrayList<Zombie> zombies , ArrayList<Sun> suns);
}
