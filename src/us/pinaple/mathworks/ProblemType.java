package us.pinaple.mathworks;

import javafx.scene.image.Image;
import us.pinaple.mathworks.gui.MathWorksIcons;
import us.pinaple.mathworks.problem.AntiderivativeMathProblem;
import us.pinaple.mathworks.problem.DerivativeMathProblem;
import us.pinaple.mathworks.problem.IntegralMathProblem;
import us.pinaple.mathworks.problem.MathProblem;
import us.pinaple.mathworks.problem.SimpleMathProblem;

public enum ProblemType {
	EQUATION("Simple Equation"),
	DERIVATIVE("Derivative"),
	ANTIDERIVATIVE("Antiderivative"),
	DEFINITE_INTEGRAL("Definite Integral");

	private final String displayName;

	private ProblemType(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public MathProblem createProblem() {
		switch (this) {
			case EQUATION:
				return new SimpleMathProblem(this);
			case DERIVATIVE:
				return new DerivativeMathProblem(this);
			case ANTIDERIVATIVE:
				return new AntiderivativeMathProblem(this);
			case DEFINITE_INTEGRAL:
				return new IntegralMathProblem(this);
			default:
				throw new AssertionError(this);
		}
	}

	public Image getImage() {
		switch (this) {
			case EQUATION:
				return MathWorksIcons.ARITHMETIC_IMAGE;
			case DERIVATIVE:
				return MathWorksIcons.DERIVATIVE_IMAGE;
			case ANTIDERIVATIVE:
			case DEFINITE_INTEGRAL:
				return MathWorksIcons.INTEGRAL_IMAGE;
			default:
				throw new AssertionError(this);
		}
	}
}
