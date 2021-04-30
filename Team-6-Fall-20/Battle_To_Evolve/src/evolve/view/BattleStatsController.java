package evolve.view;

import evolve.MainApp;
import evolve.model.BattleStats;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BattleStatsController {
	
	private MainApp mainApp;
	private BattleStats battleStats; 

	
	
	
	@FXML
	Label lblUserRemainingHp;
	@FXML
	Label lblUserTotalDamageDealt;
	@FXML
	Label lblUserTotalRounds;
	@FXML
	Label lblUserNumberOfCriticals;
	@FXML
	Label lblUserNumberOfDodges;
	@FXML
	Label lblUserWinStatus;
	@FXML
	Label lblOpponentRemainingHp;
	@FXML
	Label lblOpponentTotalDamageDealt;
	@FXML
	Label lblOpponentTotalRounds;
	@FXML
	Label lblOpponentNumberOfCriticals;
	@FXML
	Label lblOpponentNumberOfDodges;
	@FXML
	Label lblUserBattleTime;
	@FXML
	Label lblOpponentBattleTime;
	@FXML
	Label lblOpponentWinStatus;
	
	@FXML
	Button btnShowStats;
	@FXML
	private Button btnBattleStatsEvolve;
	
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	@FXML
	private void initialize() {

	}

	public void getBattleInformation() {
		
	}
	

	
	public void initData(BattleStats battleStats) {
		btnShowStats.setVisible(false);
		btnBattleStatsEvolve.setVisible(false);
		this.battleStats = battleStats;
		setLabels();
	}
	
	public void setRemainingHpLabels(){
		lblUserRemainingHp.setText(Integer.toString(battleStats.getUserHP()));
		lblOpponentRemainingHp.setText(Integer.toString(battleStats.getEnemyHealth()));

		
	}
	
	public void setTotalDamageDealtLabels(){
		lblUserTotalDamageDealt.setText(Integer.toString(battleStats.getUserTotalDamageDealt()));
		lblOpponentTotalDamageDealt.setText(Integer.toString(battleStats.getEnemyTotalDamageDealt()));

	}
	
	public void setTotalRoundsLabels(){
		lblUserTotalRounds.setText(Integer.toString(battleStats.getTotalRounds()));
		lblOpponentTotalRounds.setText(Integer.toString(battleStats.getTotalRounds()));
		
		
	}
	
	public void setNumberOfCriticalsLabels(){
		lblUserNumberOfCriticals.setText(Integer.toString(battleStats.getUserCrit()));
		lblOpponentNumberOfCriticals.setText(Integer.toString(battleStats.getEnemyCrit()));
		
	}
	
	public void setNumberOfDodgesLabels(){
		lblUserNumberOfDodges.setText(Integer.toString(battleStats.getUserDodge()));
		lblOpponentNumberOfDodges.setText(Integer.toString(battleStats.getEnemyDodge()));

	}
	
	
	public void setTimeOfBattleLabels(){
	
		lblUserBattleTime.setText(battleStats.getTotalTime() + " Seconds");
		lblOpponentBattleTime.setText(battleStats.getTotalTime() + " Seconds");

	}
	
	public void setWinStatusLabels(){
		if(battleStats.getDidWin() == true) {
			btnBattleStatsEvolve.setVisible(true);
			lblUserWinStatus.setText("Win");
			lblOpponentWinStatus.setText("Lose");
		}
		else {
			lblUserWinStatus.setText("Lose");
			lblOpponentWinStatus.setText("Win");
		}

	}
	
	public void handleCancel() {
		mainApp.showManagerOverView();
	}
	
	public void handleEvolution() {
		mainApp.showEvolutionManagerOverView();
	}
	
	
	public void setLabels() {
		
		setRemainingHpLabels();
		setTotalDamageDealtLabels();
		setTotalRoundsLabels();
		setNumberOfCriticalsLabels();
		setNumberOfDodgesLabels();
		setWinStatusLabels();
		setTimeOfBattleLabels();
		
	}
	
	//Debugging
	public void showStats() {
		setLabels();
	}
	
}
