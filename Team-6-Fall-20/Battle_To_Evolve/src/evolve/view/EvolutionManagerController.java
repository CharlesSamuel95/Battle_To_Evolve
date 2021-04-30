package evolve.view;

import evolve.MainApp;
import evolve.model.Character;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;


public class EvolutionManagerController {
	
	@SuppressWarnings("unused")
	private MainApp mainApp;
	@FXML
	private Label lblPlayerHealth;	
	@FXML
	private Label lblPlayerArmor;	
	@FXML
	private Label lblPlayerAttack;	
	@FXML
	private Label lblPlayerStamina;	
	@FXML
	private Label lblPlayerSpeed;	
	@FXML
	private Label lblPlayerLuck;	
	@FXML
	private Label lblLosingHealth;	
	@FXML
	private Label lblLosingArmor;	
	@FXML
	private Label lblLosingAttack;	
	@FXML
	private Label lblLosingStamina;	
	@FXML
	private Label lblLosingSpeed;
	@FXML
	private Label lblLosingLuck;	
	@FXML
	private Label lblRangeHealth;	
	@FXML
	private Label lblRangeArmor;	
	@FXML
	private Label lblRangeAttack;	
	@FXML
	private Label lblRangeStamina;	
	@FXML
	private Label lblRangeSpeed;
	@FXML
	private Label lblRangeLuck;
	
	private Character playerCharacter;
	private Character losingCharacter;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	public void initData(Character playerCharacter, Character losingCharacter) {
		this.playerCharacter = playerCharacter;
		this.losingCharacter = losingCharacter;
		setEvolutionManager();
	}
	public void setEvolutionManager() {
		setPlayerLabels();
		setLosingLabels();
		setRangeLabels();
	}
	@FXML
	/*private void initialize() {
		setPlayerLabels();
		setLosingLabels();
		setRangeLabels();
	}*/
	private void setPlayerLabels() {
		lblPlayerHealth.setText(Integer.toString(playerCharacter.getHealth()));
		lblPlayerArmor.setText(Integer.toString(playerCharacter.getArmor()));
		lblPlayerAttack.setText(Integer.toString(playerCharacter.getAttack()));
		lblPlayerStamina.setText(Integer.toString(playerCharacter.getStamina()));
		lblPlayerSpeed.setText(Integer.toString(playerCharacter.getSpeed()));
		lblPlayerLuck.setText(Integer.toString(playerCharacter.getLuck()));
		
	}
	private void setLosingLabels() {
		lblLosingHealth.setText(Integer.toString(losingCharacter.getHealth()));
		lblLosingArmor.setText(Integer.toString(losingCharacter.getArmor()));
		lblLosingAttack.setText(Integer.toString(losingCharacter.getAttack()));
		lblLosingStamina.setText(Integer.toString(losingCharacter.getStamina()));
		lblLosingSpeed.setText(Integer.toString(losingCharacter.getSpeed()));
		lblLosingLuck.setText(Integer.toString(losingCharacter.getLuck()));
		
	}
	private void setRangeLabels() {
		lblRangeHealth.setText( (playerCharacter.getHealth() < losingCharacter.getHealth()) ? "1 - " + Integer.toString(losingCharacter.getHealth() - playerCharacter.getHealth()) : "0" );
		lblRangeArmor.setText( (playerCharacter.getArmor() < losingCharacter.getArmor()) ? "1 - " + Integer.toString(losingCharacter.getArmor() - playerCharacter.getArmor()) : "0" );
		lblRangeAttack.setText( (playerCharacter.getAttack() < losingCharacter.getAttack()) ? "1 - " + Integer.toString(losingCharacter.getAttack() - playerCharacter.getAttack()) : "0" );
		lblRangeStamina.setText( (playerCharacter.getStamina() < losingCharacter.getStamina()) ? "1 - " + Integer.toString(losingCharacter.getStamina() - playerCharacter.getStamina()) : "0" );
		lblRangeSpeed.setText( (playerCharacter.getSpeed() < losingCharacter.getSpeed()) ? "1 - " + Integer.toString(losingCharacter.getSpeed() - playerCharacter.getSpeed()) : "0" );
		lblRangeLuck.setText( (playerCharacter.getLuck() < losingCharacter.getLuck()) ? "1 - " + Integer.toString(losingCharacter.getLuck() - playerCharacter.getLuck()) : "0" );
		
	}
	
	public void handleEvolve() {
		playerCharacter.updatePlayerCharacterStats(losingCharacter);
		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Evolved successfully");
        alert.setContentText("Your character has been evolved with new stats!");        
        alert.showAndWait();
		mainApp.showManagerOverView();
	}
	// TEMP METHOD
		public void handleBack() {
			mainApp.showManagerOverView();
		}
}
