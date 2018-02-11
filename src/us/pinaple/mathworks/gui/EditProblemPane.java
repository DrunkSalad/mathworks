package us.pinaple.mathworks.gui;

import com.proudapes.jlatexmathfx.Control.LateXMathControl;
import com.proudapes.jlatexmathfx.Control.LateXMathSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import us.pinaple.mathworks.FormulaParser;
import us.pinaple.mathworks.problem.AntiderivativeMathProblem;
import us.pinaple.mathworks.problem.DerivativeMathProblem;
import us.pinaple.mathworks.problem.IntegralMathProblem;
import us.pinaple.mathworks.problem.MathProblem;
import us.pinaple.mathworks.problem.SimpleMathProblem;

public class EditProblemPane extends GridPane {

	private final MathProblem problem;
	private LateXMathControl previewFormula;
	private EditModePane editMode;

	public EditProblemPane(EditModePane editMode, MathProblem problem) {
		this.editMode = editMode;
		this.problem = problem;

		// 1. init
		Button deleteButton = new Button("Delete");
		Label subtitle = new Label(problem.getType().getDisplayName());
		Label previewLabel = new Label("Preview");
		previewFormula = new LateXMathControl();
		previewFormula.setSize(60);

		switch (problem.getType()) {
			case EQUATION:
				initSimpleEquation();
				break;
			case DERIVATIVE:
				initDerivativeEquation();
				break;
			case ANTIDERIVATIVE:
				initAntiderivativeEquation();
				break;
			case DEFINITE_INTEGRAL:
				initIntegralEquation();
				break;
		}

		// 2. style
		subtitle.setFont(new Font("System Bold", 40));
		subtitle.setAlignment(Pos.CENTER);
		subtitle.prefWidthProperty().bind(widthProperty());

		previewLabel.setFont(new Font("System Bold", 30));
		previewLabel.setPadding(new Insets(30, 0, 15, 0));

		deleteButton.setFont(new Font("System Bold", 18));
		deleteButton.setStyle("-fx-text-fill: red;");
		deleteButton.setAlignment(Pos.CENTER);
		deleteButton.setPadding(new Insets(20, 20, 20, 20));

		setPadding(new Insets(20, 20, 20, 20));

		// 3. build GUI
		add(subtitle, 0, 0);
		add(previewLabel, 0, 2);
		add(previewFormula, 0, 3);
		add(deleteButton, 0, 4);

		// etc
		deleteButton.setOnAction(event -> {
			editMode.removeProblem(problem);
			editMode.setCenter(null);
		});
	}

	public void updatePreview(String eq) {
		previewFormula.setFormula(eq);

		LateXMathSkin skin = (LateXMathSkin) previewFormula.getSkin();
		if (skin != null) {
			skin.updateCanvas();
		}
	}

	private void initSimpleEquation() {
		GridPane pane = new GridPane();
		Label equationLabel = new Label("Enter equation: ");
		TextField equationField = new TextField();
		equationField.setPromptText("example: 2x + 5 = 3");

		Label solutionLabel = new Label("Enter solution: ");
		TextField solutionField = new TextField();
		solutionField.setPromptText("(optional)");

		Label promptLabel = new Label("Enter question: ");
		TextField promptField = new TextField();
		promptField.setPromptText("(optional)");

		//CheckBox timerBox = new CheckBox();
		Label timerLabel = new Label("Time: ");
		Label l1 = new Label("m");
		Label l2 = new Label("s");
		Spinner<Integer> minutesChoice = new Spinner<>(0, 59, 0);
		Spinner<Integer> secondsChoice = new Spinner<>(0, 59, 0);

		// style
		Font textFont = new Font("System", 18);
		equationLabel.setFont(textFont);
		solutionLabel.setFont(textFont);
		promptLabel.setFont(textFont);
		timerLabel.setFont(textFont);
		l1.setFont(textFont);
		l2.setFont(textFont);
		minutesChoice.setStyle("-fx-font: 14px \"System\";");
		secondsChoice.setStyle("-fx-font: 14px \"System\";");

		equationField.setPrefColumnCount(30);
		solutionField.setPrefColumnCount(30);

		equationLabel.setPadding(new Insets(0, 10, 10, 0));
		solutionLabel.setPadding(new Insets(0, 10, 10, 0));

		minutesChoice.setPrefWidth(80);
		secondsChoice.setPrefWidth(80);

		HBox hbox = new HBox(minutesChoice, l1, secondsChoice, l2);
		hbox.setSpacing(20);

		// build GUI
		pane.add(equationLabel, 0, 0);
		pane.add(equationField, 1, 0);
		pane.add(solutionLabel, 0, 1);
		pane.add(solutionField, 1, 1);
		pane.add(timerLabel, 0, 2);
		pane.add(hbox, 1, 2, 2, 1);

		add(pane, 0, 1);

		// etc
		equationField.textProperty().addListener((observable, oldValue, newValue) -> {
			((SimpleMathProblem) problem).setRawEquation(newValue);
			updatePreview(problem.getLateXEquation());
			editMode.refreshList();
		});

		solutionField.textProperty().addListener((observable, oldValue, newValue) -> {
			problem.setSolution(newValue);
		});

		minutesChoice.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
			int seconds = problem.getCountdownTime() % 60;
			problem.setCountdownTime((60 * newValue) + seconds);
		});

		secondsChoice.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
			int minutes = problem.getCountdownTime() / 60;
			problem.setCountdownTime(newValue + (60 * minutes));
		});

		equationField.setText(problem.getRawEquation());
		solutionField.setText(problem.getSolution());

		int countdownTime = problem.getCountdownTime();
		int minutes = countdownTime / 60;
		int seconds = countdownTime % 60;

		minutesChoice.getValueFactory().setValue(minutes);
		secondsChoice.getValueFactory().setValue(seconds);
	}

	private void initDerivativeEquation() {
		GridPane pane = new GridPane();
		Label equationLabel = new Label("Enter function: ");
		TextField equationField = new TextField();
		equationField.setPromptText("example: 2x + 5 = 3");

		Label solutionLabel = new Label("Enter solution: ");
		TextField solutionField = new TextField();
		solutionField.setPromptText("(optional)");

		Label promptLabel = new Label("Enter question: ");
		TextField promptField = new TextField();
		promptField.setPromptText("(optional)");

		//CheckBox timerBox = new CheckBox();
		Label timerLabel = new Label("Time: ");
		Label l1 = new Label("m");
		Label l2 = new Label("s");
		Spinner<Integer> minutesChoice = new Spinner<>(0, 59, 0);
		Spinner<Integer> secondsChoice = new Spinner<>(0, 59, 0);

		// style
		Font textFont = new Font("System", 18);
		equationLabel.setFont(textFont);
		solutionLabel.setFont(textFont);
		promptLabel.setFont(textFont);
		timerLabel.setFont(textFont);
		l1.setFont(textFont);
		l2.setFont(textFont);
		minutesChoice.setStyle("-fx-font: 14px \"System\";");
		secondsChoice.setStyle("-fx-font: 14px \"System\";");

		equationField.setPrefColumnCount(30);
		solutionField.setPrefColumnCount(30);

		equationLabel.setPadding(new Insets(0, 10, 10, 0));
		solutionLabel.setPadding(new Insets(0, 10, 10, 0));

		minutesChoice.setPrefWidth(80);
		secondsChoice.setPrefWidth(80);

		HBox hbox = new HBox(minutesChoice, l1, secondsChoice, l2);
		hbox.setSpacing(20);

		// build GUI
		pane.add(equationLabel, 0, 0);
		pane.add(equationField, 1, 0);
		pane.add(solutionLabel, 0, 1);
		pane.add(solutionField, 1, 1);
		pane.add(timerLabel, 0, 2);
		pane.add(hbox, 1, 2, 2, 1);

		add(pane, 0, 1);

		// etc
		equationField.textProperty().addListener((observable, oldValue, newValue) -> {
			((DerivativeMathProblem) problem).setRawEquation(newValue);
			updatePreview(problem.getLateXEquation());
			editMode.refreshList();
		});

		solutionField.textProperty().addListener((observable, oldValue, newValue) -> {
			problem.setSolution(newValue);
		});

		minutesChoice.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
			int seconds = problem.getCountdownTime() % 60;
			problem.setCountdownTime((60 * newValue) + seconds);
		});

		secondsChoice.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
			int minutes = problem.getCountdownTime() / 60;
			problem.setCountdownTime(newValue + (60 * minutes));
		});
		
		equationField.setText(problem.getRawEquation());
		solutionField.setText(problem.getSolution());

		int countdownTime = problem.getCountdownTime();
		int minutes = countdownTime / 60;
		int seconds = countdownTime % 60;

		minutesChoice.getValueFactory().setValue(minutes);
		secondsChoice.getValueFactory().setValue(seconds);
	}

	private void initAntiderivativeEquation() {
		GridPane pane = new GridPane();
		Label equationLabel = new Label("Enter function: ");
		TextField equationField = new TextField();
		equationField.setPromptText("example: 2x + 5 = 3");

		Label solutionLabel = new Label("Enter solution: ");
		TextField solutionField = new TextField();
		solutionField.setPromptText("(optional)");

		Label promptLabel = new Label("Enter question: ");
		TextField promptField = new TextField();
		promptField.setPromptText("(optional)");

		//CheckBox timerBox = new CheckBox();
		Label timerLabel = new Label("Time: ");
		Label l1 = new Label("m");
		Label l2 = new Label("s");
		Spinner<Integer> minutesChoice = new Spinner<>(0, 59, 0);
		Spinner<Integer> secondsChoice = new Spinner<>(0, 59, 0);

		// style
		Font textFont = new Font("System", 18);
		equationLabel.setFont(textFont);
		solutionLabel.setFont(textFont);
		promptLabel.setFont(textFont);
		timerLabel.setFont(textFont);
		l1.setFont(textFont);
		l2.setFont(textFont);
		minutesChoice.setStyle("-fx-font: 14px \"System\";");
		secondsChoice.setStyle("-fx-font: 14px \"System\";");

		equationField.setPrefColumnCount(30);
		solutionField.setPrefColumnCount(30);

		equationLabel.setPadding(new Insets(0, 10, 10, 0));
		solutionLabel.setPadding(new Insets(0, 10, 10, 0));

		minutesChoice.setPrefWidth(80);
		secondsChoice.setPrefWidth(80);

		HBox hbox = new HBox(minutesChoice, l1, secondsChoice, l2);
		hbox.setSpacing(20);

		// build GUI
		pane.add(equationLabel, 0, 0);
		pane.add(equationField, 1, 0);
		pane.add(solutionLabel, 0, 1);
		pane.add(solutionField, 1, 1);
		pane.add(timerLabel, 0, 2);
		pane.add(hbox, 1, 2, 2, 1);

		add(pane, 0, 1);

		// etc
		equationField.textProperty().addListener((observable, oldValue, newValue) -> {
			((AntiderivativeMathProblem) problem).setRawEquation(newValue);
			updatePreview(problem.getLateXEquation());
			editMode.refreshList();
		});

		solutionField.textProperty().addListener((observable, oldValue, newValue) -> {
			problem.setSolution(newValue);
		});

		minutesChoice.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
			int seconds = problem.getCountdownTime() % 60;
			problem.setCountdownTime((60 * newValue) + seconds);
		});

		secondsChoice.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
			int minutes = problem.getCountdownTime() / 60;
			problem.setCountdownTime(newValue + (60 * minutes));
		});

		equationField.setText(problem.getRawEquation());
		solutionField.setText(problem.getSolution());

		int countdownTime = problem.getCountdownTime();
		int minutes = countdownTime / 60;
		int seconds = countdownTime % 60;

		minutesChoice.getValueFactory().setValue(minutes);
		secondsChoice.getValueFactory().setValue(seconds);
	}

	private void initIntegralEquation() {
		GridPane pane = new GridPane();
		Label equationLabel = new Label("Enter function: ");
		TextField equationField = new TextField();
		equationField.setPromptText("example: sin(x)*e^x");

		Label lowerBoundLabel = new Label("Enter lower bound: ");
		TextField lowerBoundField = new TextField();
		lowerBoundField.setPromptText("example: 0");

		Label upperBoundLabel = new Label("Enter upper bound: ");
		TextField upperBoundField = new TextField();
		upperBoundField.setPromptText("example: e");

		Label solutionLabel = new Label("Enter solution: ");
		TextField solutionField = new TextField();
		solutionField.setPromptText("(optional)");

		Label promptLabel = new Label("Enter question: ");
		TextField promptField = new TextField();
		promptField.setPromptText("(optional)");

		//CheckBox timerBox = new CheckBox();
		Label timerLabel = new Label("Time: ");
		Label l1 = new Label("m");
		Label l2 = new Label("s");
		Spinner<Integer> minutesChoice = new Spinner<>(0, 59, 0);
		Spinner<Integer> secondsChoice = new Spinner<>(0, 59, 0);

		// style
		Font textFont = new Font("System", 18);
		equationLabel.setFont(textFont);
		lowerBoundLabel.setFont(textFont);
		upperBoundLabel.setFont(textFont);
		solutionLabel.setFont(textFont);
		promptLabel.setFont(textFont);
		timerLabel.setFont(textFont);
		l1.setFont(textFont);
		l2.setFont(textFont);
		minutesChoice.setStyle("-fx-font: 14px \"System\";");
		secondsChoice.setStyle("-fx-font: 14px \"System\";");

		equationField.setPrefColumnCount(30);
		solutionField.setPrefColumnCount(30);

		equationLabel.setPadding(new Insets(0, 10, 10, 0));
		solutionLabel.setPadding(new Insets(0, 10, 10, 0));

		minutesChoice.setPrefWidth(80);
		secondsChoice.setPrefWidth(80);

		HBox hbox = new HBox(minutesChoice, l1, secondsChoice, l2);
		hbox.setSpacing(20);

		// build GUI
		pane.add(equationLabel, 0, 0);
		pane.add(equationField, 1, 0);
		pane.add(lowerBoundLabel, 0, 1);
		pane.add(lowerBoundField, 1, 1);
		pane.add(upperBoundLabel, 0, 2);
		pane.add(upperBoundField, 1, 2);
		pane.add(solutionLabel, 0, 3);
		pane.add(solutionField, 1, 3);
		pane.add(timerLabel, 0, 4);
		pane.add(hbox, 1, 4, 2, 1);

		add(pane, 0, 1);

		// etc
		equationField.textProperty().addListener((observable, oldValue, newValue) -> {
			((IntegralMathProblem) problem).setRawEquation(newValue);
			updatePreview(problem.getLateXEquation());
			editMode.refreshList();
		});

		lowerBoundField.textProperty().addListener((observable, oldValue, newValue) -> {
			((IntegralMathProblem) problem).getLowerBound().setRawEquation(newValue);
			updatePreview(problem.getLateXEquation());
		});

		upperBoundField.textProperty().addListener((observable, oldValue, newValue) -> {
			((IntegralMathProblem) problem).getUpperBound().setRawEquation(newValue);
			updatePreview(problem.getLateXEquation());
		});

		solutionField.textProperty().addListener((observable, oldValue, newValue) -> {
			problem.setSolution(newValue);
		});

		minutesChoice.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
			int seconds = problem.getCountdownTime() % 60;
			problem.setCountdownTime((60 * newValue) + seconds);
		});

		secondsChoice.getValueFactory().valueProperty().addListener((observable, oldValue, newValue) -> {
			int minutes = problem.getCountdownTime() / 60;
			problem.setCountdownTime(newValue + (60 * minutes));
		});

		IntegralMathProblem p = (IntegralMathProblem) problem;

		equationField.setText(problem.getRawEquation());
		lowerBoundField.setText(p.getLowerBound().getRawEquation());
		upperBoundField.setText(p.getUpperBound().getRawEquation());
		solutionField.setText(problem.getSolution());

		int countdownTime = problem.getCountdownTime();
		int minutes = countdownTime / 60;
		int seconds = countdownTime % 60;

		minutesChoice.getValueFactory().setValue(minutes);
		secondsChoice.getValueFactory().setValue(seconds);
	}

}
