package evolve.view;

import evolve.MainApp;
import javafx.fxml.FXML;

public class IntroScreenController {

	private MainApp mainApp;
	
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	@FXML
	private void initialize() {
		
	}
	public void handleNewAdventure() {
		mainApp.showManagerOverView();
	}
	public void handleOldAdventure() {
		
	}
	public void handleRules() {
		mainApp.showRuleOverview();
	}
}
