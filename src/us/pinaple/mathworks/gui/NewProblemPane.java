package us.pinaple.mathworks.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import us.pinaple.mathworks.ProblemType;
import us.pinaple.mathworks.problem.MathProblem;

public class NewProblemPane extends GridPane {

	private final Label subtitle;
	private final EditModePane editModePane;
	
	private boolean consumed = false;

	public NewProblemPane(EditModePane editModePane) {
		this.editModePane = editModePane;

		// 1. init comp
		this.subtitle = new Label("Create a Problem");

		// 2. style up
		subtitle.setFont(new Font("System Bold", 40));
		subtitle.setAlignment(Pos.CENTER);
		subtitle.prefWidthProperty().bind(widthProperty());
		subtitle.setPadding(new Insets(0, 0, 10, 0));

		// 3. build
		add(subtitle, 0, 0, 2, 1);
		add(buildIcon("Simple\nEquation", ProblemType.EQUATION), 0, 1);
		add(buildIcon("Derivative", ProblemType.DERIVATIVE), 1, 1);
		add(buildIcon("Integral", ProblemType.DEFINITE_INTEGRAL), 0, 2);
		add(buildIcon("Antiderivative", ProblemType.ANTIDERIVATIVE), 1, 2);

		setAlignment(Pos.CENTER);
		setHgap(30);
		setVgap(30);
		setPadding(new Insets(15, 30, 30, 30));

		ColumnConstraints constr = new ColumnConstraints();
		constr.setHgrow(Priority.ALWAYS);
		getColumnConstraints().setAll(constr, constr);

		RowConstraints constr2 = new RowConstraints();
		constr2.setVgrow(Priority.NEVER);
		getRowConstraints().setAll(constr2, constr2, constr2);
	}

	private VBox buildIcon(String name, ProblemType type) {
		VBox vbox = new VBox();

		Label label = new Label(name);
		label.setFont(new Font("System", 18));

		label.setAlignment(Pos.CENTER);
		label.setTextAlignment(TextAlignment.CENTER);
		label.prefWidthProperty().bind(vbox.widthProperty());

		ImageView iv = new ImageView(type.getImage());
		iv.setFitWidth(100);
		iv.setFitHeight(100);
		HBox b = new HBox(iv);
		b.setAlignment(Pos.CENTER);
		
		vbox.setPrefSize(200, 200);
		vbox.getChildren().setAll(label, b);
		vbox.setPadding(new Insets(30));

		vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		vbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), null)));

		vbox.setOnMouseEntered(event -> {
			vbox.setBackground(new Background(new BackgroundFill(Color.WHITESMOKE, null, null)));
		});

		vbox.setOnMouseExited(event -> {
			vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		});

		vbox.setOnMousePressed(event -> {
			if (consumed)
				return;
			
			vbox.setBackground(new Background(new BackgroundFill(Color.AZURE, null, null)));
			
			MathProblem problem = type.createProblem();
			editModePane.addProblem(problem, true);
			editModePane.setCenter(new EditProblemPane(editModePane, problem));
			
			consumed = true;
		});

		return vbox;
	}

}
