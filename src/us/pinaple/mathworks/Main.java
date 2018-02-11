package us.pinaple.mathworks;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import us.pinaple.mathworks.gui.CompetitionModePane;
import us.pinaple.mathworks.gui.EditModePane;
import us.pinaple.mathworks.gui.MainMenuPane;
import us.pinaple.mathworks.gui.MathWorksIcons;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 800;
	private static final int SCREEN_HEIGHT = 800;

	private static Main instance;

	public static Main getInstance() {
		return instance;
	}

	public static MathWorksDataFile getDataFile() {
		return instance.dataFile;
	}

	public static void setDataFile(MathWorksDataFile dataFile) {
		instance.dataFile = dataFile;
	}

	private Stage stage;
	private final Scene mainMenu;
	private final Scene editMode;
	private Scene launchMode;

	private MathWorksDataFile dataFile;

	public void createNewAndEditMode() {
		this.dataFile = new MathWorksDataFile(null);
		this.stage.setScene(editMode);
	}

	public void lauuuuUUUUUUNNNNCHHHHH(boolean returnToMain) {
		this.launchMode = new Scene(new CompetitionModePane());
		CompetitionModePane root = (CompetitionModePane) launchMode.getRoot();

		this.stage.setScene(launchMode);
		this.stage.setFullScreen(true);

		stage.fullScreenProperty().addListener((observable, oldValue, newValue) -> {
			if (!oldValue || newValue) {
				return;
			}

			if (returnToMain) {
				this.stage.setScene(mainMenu);
			} else {
				this.stage.setScene(editMode);
			}
		});
		
		root.updateProblems();
		root.showInstructions(0.8 * stage.getWidth(), 0.8 * stage.getHeight());
	}

	public Stage getStage() {
		return stage;
	}

	public Main() {
		instance = this;

		this.mainMenu = new Scene(new MainMenuPane(), SCREEN_WIDTH, SCREEN_HEIGHT);
		this.editMode = new Scene(new EditModePane(), SCREEN_WIDTH, SCREEN_HEIGHT);
		this.launchMode = new Scene(new CompetitionModePane());

		FormulaParser.parse("2*x - 1 = 5");
		MathWorksIcons.ayye();
	}

	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;

		primaryStage.setTitle("Math Works");
		primaryStage.setScene(mainMenu);
		primaryStage.show();
	}

	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, MWPreloader.class, args);
	}

}
