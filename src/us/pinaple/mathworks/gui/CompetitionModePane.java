package us.pinaple.mathworks.gui;

import com.proudapes.jlatexmathfx.Control.LateXMathControl;
import com.proudapes.jlatexmathfx.Control.LateXMathSkin;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import us.pinaple.mathworks.FormulaParser;
import us.pinaple.mathworks.Main;
import us.pinaple.mathworks.MathWorksDataFile;
import us.pinaple.mathworks.problem.MathProblem;

public class CompetitionModePane extends StackPane {

	private final BorderPane body;
	private Label timerLabel;
	private LateXMathControl equation;

	private int minutes, seconds;
	private Timeline timeline;
	private MathProblem currentProblem;

	private ListIterator<MathProblem> problems;

	private boolean instructionsShown = true;
	private StackPane instructionsPane;

	private Label middleText;

	private boolean phase;

	public CompetitionModePane() {
		this.body = new BorderPane();
		this.timerLabel = new Label("[ xx:xx ]");
		this.equation = new LateXMathControl();
		this.timeline = new Timeline();
		this.middleText = new Label("Press Enter to Start");

		timerLabel.setFont(new Font("System Bold", 70));
		timerLabel.setAlignment(Pos.CENTER);
		timerLabel.prefWidthProperty().bind(body.widthProperty());
		timerLabel.setPadding(new Insets(30, 0, 0, 0));
		body.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		equation.setSize(85);
		middleText.setFont(new Font("System", 60));
		middleText.setAlignment(Pos.CENTER);

		body.setTop(timerLabel);
		body.setCenter(equation);
		body.setPadding(new Insets(30));

		getChildren().add(body);

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
			if (seconds >= 0) {
				--seconds;
				if (seconds == -1) {
					if (minutes > 0) {
						--minutes;
						seconds = 59;
					} else {
						timeline.stop();

						minutes = 0;
						seconds = 0;
					}
				}
			}

			timerLabel.setText("[ " + formatTime(minutes) + ":" + formatTime(seconds) + " ]");
		}));

		this.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case ESCAPE:
					Main.getInstance().getStage().setFullScreen(false);
					break;
				case SPACE:
					switch (timeline.getStatus()) {
						case RUNNING:
							timeline.pause();
							timerLabel.setTextFill(Color.RED);
							break;
						case PAUSED:
							timeline.play();
							timerLabel.setTextFill(Color.BLACK);
							break;
					}
					break;
				case ENTER:
					if (instructionsShown) {
						hideInstructions();
						getChildren().add(middleText);
					} else if (phase || currentProblem == null) {
						phase = false;
						// go to next problem
						getChildren().remove(middleText);
						timeline.stop();
						nextProblem();

						if (timeline.getStatus() == Status.PAUSED) {
							timeline.play();
							timerLabel.setTextFill(Color.BLACK);
						}

					} else {
						phase = true;
						// show solution
						equation.setSize(60);
						updateFormula("\\text{Solution}:   "+FormulaParser.parse(currentProblem.getSolution()));
					}

					break;
			}
		});

		this.setOnMousePressed(event -> {
			if (instructionsShown) {
				hideInstructions();
				getChildren().add(middleText);
			}
		});
	}

	public void updateProblems() {
		MathWorksDataFile df = Main.getDataFile();
		if (df == null) {
			return;
		}

		List<MathProblem> list = df.getProblems();
		Collections.shuffle(list);
		problems = list.listIterator();
	}

	public void showInstructions(double width, double height) {
		instructionsShown = true;
		ImageView instructionsImage = new ImageView(MathWorksIcons.COMP_OVERLAY_IMAGE);
		instructionsImage.setFitWidth(width);
		instructionsImage.setFitHeight(height);

		instructionsPane = new StackPane(instructionsImage);
		instructionsPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(20), new BorderWidths(4))));
		instructionsPane.setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 30, 0.7), new CornerRadii(20), null)));
		instructionsPane.setOpacity(0.7);
		instructionsPane.setPrefSize(width, height);
		instructionsPane.setMaxSize(width, height);
		instructionsPane.setAlignment(Pos.CENTER);

		getChildren().add(instructionsPane);
	}

	public void hideInstructions() {
		getChildren().remove(instructionsPane);
		instructionsShown = false;
	}

	public void nextProblem() {
		if (!problems.hasNext()) {
			Main.getInstance().getStage().setFullScreen(false);
			return;
		}

		currentProblem = problems.next();
		equation.setSize(85);
		updateFormula(currentProblem.getLateXEquation());
		int t = currentProblem.getCountdownTime();
		minutes = t / 60;
		seconds = t % 60;

		timeline.playFromStart();

		if (instructionsShown) {
			instructionsShown = false;
			getChildren().remove(instructionsPane);
		}
	}

	private String formatTime(int t) {
		if (t < 10) {
			return "0" + t;
		}

		return String.valueOf(t);
	}

	private void updateFormula(String str) {
		equation.setFormula(str);

		LateXMathSkin skin = (LateXMathSkin) equation.getSkin();
		if (skin != null) {
			skin.updateCanvas();
		}
	}
}
