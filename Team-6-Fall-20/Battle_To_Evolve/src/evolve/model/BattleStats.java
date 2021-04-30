package evolve.model;

public class BattleStats {

	@SuppressWarnings("unused")
	private int totalDamage = 0;
	private int userTotalDamage = 0;
	private int enemyTotalDamage = 0;
	private int totalRounds = 0;
	private int userHealth = 0;
	private int enemyHealth = 0;
	private long startTime = 0;
	private long endTime = 0;
	private int userDodge = 0;
	private int enemyDodge = 0;
	private int userCrit = 0;
	private int enemyCrit = 0;
	private boolean didWin = false;

	
	public BattleStats() {};
	
	public void setStart(long startTime) {
		this.startTime = startTime;
	}
	public void setEnd(long endTime) {
		this.endTime = endTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public int getTotalTime() {

		return (int) (( endTime - startTime) / 1000000000);

	}
	public void addToTotalDamage(int total) {
		totalDamage += total;
	}
	public void addRound() {
		totalRounds++;
	}
	public int getTotalRounds() {
		return totalRounds;
	}

	
	public void setUserHealth(int hp) {
		this.userHealth = hp;
	}
	public void setEnemyHealth(int hp) {
		this.enemyHealth = hp;
	}
	
	public int getUserHP() {
		return userHealth;
	}
	public int getEnemyHealth() {
		return enemyHealth;
	}
	
	public void addUserDodge() {
		userDodge++;
	}
	
	public void addEnemyDodge() {
		enemyDodge++;
	}
	
	public int getUserDodge() {
		return userDodge;
	}
	
	public int getEnemyDodge() {
		return enemyDodge;
	}
	
	public void addUserCrit() {
		userCrit++;

	}
	
	public void addEnemyCrit() {
		enemyCrit++;
	}
	
	public int getUserCrit() {
		return userCrit;
	}
	
	public int getEnemyCrit() {
		return enemyCrit;
	}
	
	public void addUserTotalDamageDealt(int damage) {
		userTotalDamage += damage;
	}
	
	public void addEnemyTotalDamageDealt(int damage) {
		enemyTotalDamage += damage;
	}
	
	public int getUserTotalDamageDealt() {
		return userTotalDamage;
	}
	
	public int getEnemyTotalDamageDealt() {
		return enemyTotalDamage;
	}
	
	public void setUserWon() {
		didWin = true;
	}
	
	public void setEnemyWon() {
		didWin = false;
	}
	
	public boolean getDidWin() {
		return didWin;
	}
	
}
