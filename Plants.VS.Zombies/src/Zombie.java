import java.util.ArrayList;

public class Zombie implements ZombieInterface {
	String[][] garden = new String[6][19];
	public int turn;// it should be defined in main
	private String name;
	private int insertionTurn;
	private int health;
	private int location_x;
	private int location_y;
	private int price;
	private int speed;
	private int power = 1;
	private int defendHealth = 0;
	private boolean isFrozen;
	private boolean defenceIsOn = true;
	private int periodOfDefenceDisability = 0;


	public Zombie (String name , int insertionTurn) {
		this.insertionTurn = insertionTurn;
		this.name = name;
		setProperties(name);
	}

	public void setDefenceIsOn(boolean defenceIsOn) {
		this.defenceIsOn = defenceIsOn;
	}

	public void setPeriodOfDefenceDisability(int periodOfDefenceDisability) {
		this.periodOfDefenceDisability = periodOfDefenceDisability;
	}

	public void addPeriodOfDefenceDisability() {
		this.periodOfDefenceDisability++;
	}

	public boolean getDefenceIsOn() {
		return defenceIsOn;
	}

	public int getPeriodOfDefenceDisability() {
		return periodOfDefenceDisability;
	}

	public void decreaseHealth(){
		this.health--;
	}
	public int getHealth() {
		return this.health;
	}

	public String getName() {
		return name;
	}

	public void setFrozen(boolean frozen) {
		this.isFrozen = frozen;
	}

	public boolean isFrozen() {
		return isFrozen;
	}

	public int getLocation_x() {
		return location_x;
	}

	public int getLocation_y() {
		return location_y;
	}

	public int getInsertionTurn() {
		return insertionTurn;
	}
	public int getPrice() {
		return price;
	}

	public int getSpeed() {
		return speed;
	}

	public int getPower() {
		return power;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setLocation_x(int location_x) {
		this.location_x = location_x;
	}

	public void setLocation_y(int location_y) {
		this.location_y = location_y;
	}

	public void activity(ArrayList<Zombie> zombies , ArrayList<Plant> plants , int turn ){
		if (this.name.equals("BungeeZombie")){
			if (turn - insertionTurn == 1){
				int row = generateRandom(0,5);
				int column = generateRandom(1,21);
				this.setLocation_y(row);
				this.setLocation_x(column);
			}
			if ((turn - insertionTurn) > 3 && this.health > 0){
				if (findPlant(this.location_x , this.location_y , plants) != null){
					plants.remove(findPlant(this.location_x , this.location_y , plants));
				}
			}
		}
	}

	public void hurt(Bullet bullet , ArrayList<Bullet> bullets){
		if (bullet.getBulletType() == "partabe"){
			this.health -= bullet.getPower();
			bullets.remove(bullet);
		}
		if (bullet.getBulletType() == "nokhod"){
			if (name.equals("NewspaperZombie")) {
				if (defendHealth == 0 || !defenceIsOn){
					this.health -= bullet.getPower();
					bullets.remove(bullet);
				}
				else {
					this.defendHealth -= bullet.getPower();
					bullets.remove(bullet);
				}
			}
			else if (name.equals("TargetZombie")) {
				if (defendHealth == 0 || !defenceIsOn){
					this.health -= bullet.getPower();
					bullets.remove(bullet);
				}
				else {
					this.defendHealth -= bullet.getPower();
					bullets.remove(bullet);
				}
			}
			else if (name.equals("ScreenDoorZombie")) {
				if (defendHealth == 0 || !defenceIsOn){
					this.health -= bullet.getPower();
					bullets.remove(bullet);
				}
				else {
					this.defendHealth -= bullet.getPower();
					bullets.remove(bullet);
				}
			}
			else{
				this.health -= bullet.getPower();
				bullets.remove(bullet);
			}

		}
		if (bullet.getBulletType() == "yakhi")
			this.isFrozen = true;
	}
	public void walk(ArrayList<Bullet> bullets, ArrayList<Plant> plants , ArrayList<Zombie> zombies , int turn , ArrayList<LawnMower> lawnMowers) {
		// every zombie has a default speed that has been specified in setProperties but if a zombie is frozen its speed will be lower than default speed so we define an actual speed for every zombie that indicates the speed he is walking with in current turn
		int actualSpeed = speed;
		if (this.isFrozen == true)
			actualSpeed /= 2;
		for (int i = 0 ; i < actualSpeed ; i++){
			if (nextIsLawnMower(lawnMowers)){
				findLawnMower(location_y , location_x - 1, lawnMowers).killAllZombiesInRow(zombies);
			}
			else if (this.location_x == 0){
				break;
			}
			else if (nextCellIsEmpty(bullets , plants , zombies))
				this.setLocation_x(location_x - 1);
			else if (nextIsBullet(bullets)  && !nextIsPlant(plants)){
				this.setLocation_x(location_x - 1);
				this.hurt(findBullet(location_x , location_y , bullets) ,bullets);
				bullets.remove(findBullet(location_x , location_y , bullets));
			}
			else if (!nextIsBullet(bullets) && nextIsZombie(zombies) && !nextIsPlant(plants))
				this.setLocation_x(location_x - 1);
			else if (nextIsPlant(plants))
				if (this.name.contains("Pogo"))
					this.setLocation_x(location_x - 2);
				else if (this.name.contains("Zomboni") || this.name.contains("Catapult")) {
					this.setLocation_x(location_x - 1);
					plants.remove(findPlant(location_x, location_y, plants));
				}
				else
					break;
		}
	}

	private LawnMower findLawnMower(int row, int column , ArrayList<LawnMower>lawnMowers) {
		for (LawnMower lawnMower : lawnMowers){
			if (lawnMower.getRow() == row && lawnMower.getColumn() == column)
				return lawnMower;
		}
		return null;
	}

	private boolean nextIsLawnMower(ArrayList<LawnMower> lawnMowers) {
		for (LawnMower lawnMower : lawnMowers){
			if (lawnMower.getColumn() == this.location_x - 1 && lawnMower.getRow() == this.location_y && !lawnMower.getIsUsed())
				return true;
		}
		return false;
	}

	private Plant findPlant(int location_x, int location_y, ArrayList<Plant> plants) {
		for (Plant plant : plants){
			if (plant.getLocation_x() == location_x && plant.getLocation_y() == this.location_y)
				return plant;
		}
		return null;
	}
	private Bullet findBullet(int location_x, int location_y, ArrayList<Bullet> bullets) {
		for (Bullet bullet : bullets){
			if (bullet.getLocation_x() == location_x && bullet.getLocation_y() == this.location_y)
				return bullet;
		}
		return null;
	}

	private boolean nextIsPlant(ArrayList<Plant> plants) {
		for (Plant plant : plants){
			if (plant.getLocation_x() == this.location_x - 1 && plant.getLocation_y() == this.location_y)
				return true;
		}
		return false;
	}

	private boolean nextIsZombie(ArrayList<Zombie> zombies) {
		for (Zombie zombie : zombies){
			if (zombie.getLocation_x() == this.location_x - 1 && zombie.getLocation_y()== this.location_y)
				return true;
		}
		return false;
	}

	private boolean nextIsBullet(ArrayList<Bullet> bullets) {
		for (Bullet bullet : bullets){
			if (bullet.getLocation_x() == this.location_x - 1 && bullet.getLocation_y() == this.location_y)
				return true;
		}
		return false;
	}

	private boolean nextCellIsEmpty(ArrayList<Bullet> bullets, ArrayList<Plant> plants, ArrayList<Zombie> zombies) {
		int counter = 0;
		for (Zombie zombie : zombies){
			if (zombie.getLocation_x() == this.location_x - 1 && zombie.getLocation_y()== this.location_y)
				counter++;
		}
		if (counter > 0)
			return  false;
		for (Plant plant : plants){
			if (plant.getLocation_x() == this.location_x - 1 && plant.getLocation_y() == this.location_y)
				counter++;
		}
		if (counter > 0)
			return  false;
		for (Bullet bullet : bullets){
			if (bullet.getLocation_x() == this.location_x - 1 && bullet.getLocation_y() == this.location_y)
				counter++;
		}
		if (counter > 0)
			return  false;
		return true;
	}


	public void setProperties(String name) {
		if (name.equals("Zombie")) {
			speed = 2;
			health = 2;
		}
		else if (name.equals("FootballZombie")) {
			speed = 3;
			health = 4;
		}
		else if (name.equals("BucketheadZombie")) {
			speed = 2;
			health = 3;
		}
		else if (name.equals("ConeheadZombie")) {
			speed = 2;
			health = 3;
		}
		else if (name.equals("Zomboni)")) {
			speed = 2;
			health = 3;
		}
		else if (name.equals("CatapultZombie")) {
			speed = 2;
			health = 3;
		}
		else if (name.equals("BungeeZombie")) {
			speed = 0;
			health = 3;
		}
		else if (name.equals("BalloonZombie")) {
			speed = 2;
			health = 3;
		}
		else if (name.equals("NewspaperZombie")) {
			speed = 2;
			health = 2;
			defendHealth = 2;
		}
		else if (name.equals("TargetZombie")) {
			speed = 2;
			health = 3;
			defendHealth = 3;
		}
		else if (name.equals("ScreenDoorZombie")) {
			speed = 2;
			health = 2;
			defendHealth = 4;
		}
		else if (name.equals("Giga-gargantuarZombie")) {
			power = 500;
			speed = 6;
			health = 1;
		}
		else if (name.equals("PogoZombie")) {
			speed = 2;
			health = 2;
		}
		else if (name.equals("SnorkelZombie")) {
			speed = 2;
			health = 2;
		}
		else if (name.equals("DolphinRiderZombie")) {
			defendHealth = 2;
			speed = 2;
			health = 2;
		}
	}
	private int generateRandom(int min, int max) {
		double x = Math.random()*((max - min)+1)+min;
		int randomNumber = (int) x;
		return randomNumber;
	}
}
