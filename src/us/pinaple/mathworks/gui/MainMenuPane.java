package us.pinaple.mathworks.gui;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import us.pinaple.mathworks.Main;
import us.pinaple.mathworks.MathWorksDataFile;

public class MainMenuPane extends BorderPane {

	private final VBox buttonCreateNew;
	private final VBox buttonLoad;

	private final StackPane topPane;

	public MainMenuPane() {
		// 1. Create components
		this.topPane = new StackPane();
		this.buttonCreateNew = buildIcon("Create\n or Edit", MathWorksIcons.BUTTON_CREATE_IMAGE, onCreateNew());
		this.buttonLoad = buildIcon("Launch\nExisting", MathWorksIcons.BUTTON_LAUNCH_IMAGE, onLaunch());

		// 2. Style things up
		topPane.setAlignment(Pos.CENTER);
		topPane.prefWidthProperty().bind(widthProperty());
		topPane.setPadding(new Insets(40, 0, 0, 0));

		ImageView iv = new ImageView(MathWorksIcons.TITLE_IMAGE);
		iv.setFitHeight(100);
		iv.setPreserveRatio(true);
		topPane.getChildren().add(iv);

		// 3. Build GUI
		HBox box = new HBox(buttonCreateNew, buttonLoad);
		box.setSpacing(50);
		box.setAlignment(Pos.CENTER);

		setTop(topPane);
		setCenter(box);

		setBackground(new Background(new BackgroundFill(Color.CADETBLUE, null, null)));
	}

	private Runnable onCreateNew() {
		return () -> {
			Main.getInstance().createNewAndEditMode();
		};
	}

	private Runnable onLaunch() {
		return () -> {
			File f = new FileChooser().showOpenDialog(Main.getInstance().getStage());
			if (f == null) {
				return;
			}

			MathWorksDataFile df = MathWorksDataFile.loadFromFile(f);
			if (df == null) {
				return;
			}

			Main.setDataFile(df);
			Main.getInstance().lauuuuUUUUUUNNNNCHHHHH(true);
		};
	}

	private VBox buildIcon(String name, Image image, Runnable onPress) {
		VBox vbox = new VBox();

		Label label = new Label(name);
		label.setFont(new Font("System Bold", 30));

		label.setAlignment(Pos.CENTER);
		label.setTextAlignment(TextAlignment.CENTER);
		label.prefWidthProperty().bind(vbox.widthProperty());

		ImageView iv = new ImageView(image);
		iv.setFitWidth(100);
		iv.setFitHeight(100);
		HBox b = new HBox(iv);
		b.setAlignment(Pos.CENTER);

		vbox.setPrefSize(300, 300);
		vbox.setMaxSize(300, 300);
		vbox.getChildren().setAll(b, label);
		vbox.setPadding(new Insets(30));

		CornerRadii corners = new CornerRadii(20);

		vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, corners, null)));
		vbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, corners, null)));

		vbox.setOnMouseEntered(event -> {
			vbox.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, corners, null)));
		});

		vbox.setOnMouseExited(event -> {
			vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, corners, null)));
		});

		vbox.setOnMousePressed(event -> {
			onPress.run();
		});

		return vbox;
	}

}
