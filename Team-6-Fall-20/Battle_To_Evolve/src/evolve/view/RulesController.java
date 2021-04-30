package evolve.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class RulesController {

	@SuppressWarnings("unused")
	private Stage dialogStage;
	
	@FXML
	private TextArea rulesDisplay;
	@FXML
	private Label selectedItem;
	@FXML
	private MenuButton rulesSelection;
	@FXML
	private void initialize() {
		reset();
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	private void showCharacterRules() {
		selectedItem.setText("Character Rules");
		selectedItem.setVisible(true);
		rulesSelection.setText("Character Rules");
		rulesDisplay.setText(getRules(new File("resources/rules_text_files/characterRules.txt")));
	}
	@FXML
	private void showBattleRules() {
		selectedItem.setText("Battle Rules");
		selectedItem.setVisible(true);
		rulesSelection.setText("Battle Rules");
		rulesDisplay.setText(getRules(new File("resources/rules_text_files/battleRules.txt")));
	}
	@FXML
	private void showEvolutionRules() {
		selectedItem.setText("Evolution Rules");
		selectedItem.setVisible(true);
		rulesSelection.setText("Evolution Rules");
		rulesDisplay.setText(getRules(new File("resources/rules_text_files/evolutionRules.txt")));
	}
	@FXML
	private void handleClear() {
		reset();
	}
	// Resets everything to clear state
	public void reset() {
		selectedItem.setVisible(false);
		rulesSelection.setText("Select a Rule Set");
		rulesDisplay.setText("Hello, please select the category of rules you would like to view");
	}
	public String getRules(File inFile) {
		String rules = "";
		try {
			Scanner scan = new Scanner(inFile);
			while(scan.hasNext()) {
				rules += scan.nextLine();
				rules += "\n";
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return rules;
	}
}
