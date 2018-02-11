package us.pinaple.mathworks;

import javafx.application.Preloader;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author Totom3
 */
public class MWPreloader extends Preloader {

	Stage stage;

	private Scene createPreloaderScene() {
		BorderPane p = new BorderPane();
		p.setCenter(new Label("Loading..."));
		return new Scene(p, 300, 150);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.setScene(createPreloaderScene());
		stage.show();
	}

	@Override
	public void handleStateChangeNotification(StateChangeNotification scn) {
		if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
			stage.hide();
		}
	}

	@Override
	public void handleProgressNotification(ProgressNotification pn) {
	}

}