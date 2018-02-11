package us.pinaple.mathworks.gui;

import java.io.File;
import java.util.List;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import us.pinaple.mathworks.Main;
import us.pinaple.mathworks.MathWorksDataFile;
import us.pinaple.mathworks.problem.DummyProblem;
import us.pinaple.mathworks.problem.MathProblem;

public class EditModePane extends BorderPane {

	private final MenuBar menuBar;
	private final ListView<MathProblem> problems;
	private Pane problemPane;
	private HBox bottomBar;

	public EditModePane() {
		// 1. Init components
		this.menuBar = new MenuBar();
		this.problems = new ListView<>();
		this.problemPane = null;
		this.bottomBar = new HBox();

		initMenu();

		// 2. Style things up
		problems.setCellFactory(list -> new MathListCell());
		problems.getSelectionModel().getSelectedItems().addListener(new ListChangeListener<MathProblem>() {
			@Override
			public void onChanged(ListChangeListener.Change<? extends MathProblem> c) {
				c.next();
				List<? extends MathProblem> l = c.getAddedSubList();
				if (l.isEmpty()) {
					return;
				}

				MathProblem problem = l.get(0);
				if (problem == DummyProblem.getInstance()) {
					// Create new
					problemPane = new NewProblemPane(EditModePane.this);
					setCenter(problemPane);
				} else {
					problemPane = new EditProblemPane(EditModePane.this, problem);
					setCenter(problemPane);
				}
			}
		});
		problems.getItems().add(DummyProblem.getInstance());

		Button launchButton = new Button("Launch!");
		launchButton.setFont(new Font("System Bold", 20));
		launchButton.setAlignment(Pos.CENTER);
		launchButton.setPadding(new Insets(20, 0, 20, 0));
		launchButton.prefWidthProperty().set(100);

		Button saveButton = new Button("Save");
		saveButton.setFont(new Font("System Bold", 20));
		saveButton.setAlignment(Pos.CENTER);
		saveButton.setPadding(new Insets(20, 0, 20, 0));
		saveButton.prefWidthProperty().set(100);

		bottomBar.setAlignment(Pos.CENTER);
		bottomBar.setSpacing(30);
		bottomBar.setPadding(new Insets(20, 0, 20, 0));
		bottomBar.getChildren().setAll(saveButton, launchButton);
		bottomBar.setBackground(new Background(new BackgroundFill(Color.BEIGE, null, null)));

		// 3. Build GUI
		setTop(menuBar);
		setLeft(problems);
		setBottom(bottomBar);

		saveButton.setOnAction(event -> {
			Main.getDataFile().save();
		});

		launchButton.setOnAction(event -> {
			Main.getInstance().lauuuuUUUUUUNNNNCHHHHH(false);
		});
	}

	public void addProblem(MathProblem problem) {
		addProblem(problem, false);
	}

	public void addProblem(MathProblem problem, boolean select) {
		Main.getDataFile().getProblems().add(problem);
		problems.getItems().add(problem);

		if (select) {
			problems.getSelectionModel().select(problem);
		}
	}

	public void removeProblem(MathProblem problem) {
		problems.getItems().remove(problem);
		problems.getSelectionModel().clearSelection();
		
		MathWorksDataFile dataFile = Main.getDataFile();
		if (dataFile != null){
			dataFile.getProblems().remove(problem);
		}
	}

	private void initMenu() {

		MenuItem newItem = new MenuItem("_New...");
		MenuItem openItem = new MenuItem("_Open...");
		MenuItem saveItem = new MenuItem("_Save");
		MenuItem launchItem = new MenuItem("_Launch");
		MenuItem aboutItem = new MenuItem("_About MathWorks");

		// todo: set handlers
		Menu fileMenu = new Menu("_File");
		Menu launchMenu = new Menu("_Launch");
		Menu helpMenu = new Menu("_Help");

		fileMenu.getItems().setAll(newItem, openItem, saveItem);
		launchMenu.getItems().setAll(launchItem);
		helpMenu.getItems().setAll(aboutItem);

		this.menuBar.getMenus().setAll(fileMenu, launchMenu, helpMenu);

		newItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		launchItem.setAccelerator(new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN));

		// etc
		launchItem.setOnAction(event -> {
			Main.getInstance().lauuuuUUUUUUNNNNCHHHHH(false);
		});

		openItem.setOnAction(event -> {
			FileChooser fc = new FileChooser();
			fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("MathWorks File", "mathworks"));
			File f = fc.showOpenDialog(Main.getInstance().getStage());
			if (f != null) {
				MathWorksDataFile d = MathWorksDataFile.loadFromFile(f);
				Main.setDataFile(d);
				problems.getItems().clear();
				problems.getItems().add(DummyProblem.getInstance());
				problems.getItems().addAll(d.getProblems());
				setCenter(null);
			}
		});

		saveItem.setOnAction(event -> {
			Main.getDataFile().save();
		});

		newItem.setOnAction(event -> {
			Main.getDataFile().trySave();
			MathWorksDataFile df = new MathWorksDataFile(null);
			Main.setDataFile(df);
			problems.getItems().clear();
			problems.getItems().add(DummyProblem.getInstance());
			setCenter(null);
		});

	}

	public void refreshList() {
		problems.refresh();
	}

	private static class MathListCell extends ListCell<MathProblem> {

		private Node defaultGraphic() {
			ImageView iv = new ImageView(MathWorksIcons.PLUS_SIGN_IMAGE);
			iv.setFitWidth(40);
			iv.setFitHeight(40);
			return iv;
		}

		@Override
		protected void updateItem(MathProblem item, boolean empty) {
			super.updateItem(item, empty);
			setHeight(30);
			if (empty) {
				setGraphic(null);
				setText(null);
				setPadding(Insets.EMPTY);
				return;
			}

			setGraphic(defaultGraphic());
			setPadding(new Insets(5, 5, 5, 5));

			if (item == DummyProblem.getInstance()) {
				updateEmpty();
			} else {
				updateCell(item);
			}
		}

		private void updateEmpty() {
			setText("Add new...");
			// TODO: set graphic (+)
		}

		private void updateCell(MathProblem item) {
			String rawEquation = item.getRawEquation();
			boolean blank = rawEquation == null || rawEquation.isEmpty();
			setText(blank ? "Empty Problem" : rawEquation);

			ImageView iv = new ImageView(item.getType().getImage());
			iv.setFitWidth(40);
			iv.setFitHeight(40);
			setGraphic(iv);
		}

	}

}
