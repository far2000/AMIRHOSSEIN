public class Cards {
    private String name;
    private int price;
    private int restTime;
    private int speed;
    private int health;
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }
    // the constructor of the Card class
    public Cards(String name, int price, int restTime){
        setName(name);
        setPrice(price);
        setRestTime(restTime);
    }
    //this constructor is used for zombies in Zombie game.
    public Cards(int speed, int health, String name){
        setName(name);
        setHealth(health);
        setPrice(health * 10);
        setSpeed(speed);
    }
    public Cards(){

    }

// this constructor is used for current card
    public Cards(String name){
        setName(name);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getRestTime() {
        return restTime;
    }
}
