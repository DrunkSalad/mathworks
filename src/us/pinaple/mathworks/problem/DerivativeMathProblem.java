package us.pinaple.mathworks.problem;

import us.pinaple.mathworks.ProblemType;

public class DerivativeMathProblem extends SimpleMathProblem {

	public DerivativeMathProblem(ProblemType type) {
		super(type);
	}

	@Override
	public String getLateXEquation() {
		return "\\frac{d}{dx} \\left[" + super.getLateXEquation() + " \\right]";
	}

}
