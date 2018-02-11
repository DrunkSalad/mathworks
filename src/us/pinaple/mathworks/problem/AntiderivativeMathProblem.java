package us.pinaple.mathworks.problem;

import us.pinaple.mathworks.ProblemType;

public class AntiderivativeMathProblem extends SimpleMathProblem {

	public AntiderivativeMathProblem(ProblemType type) {
		super(type);
	}

	@Override
	public String getLateXEquation() {
		return "\\int{" + super.getLateXEquation() + "}dx";
	}

}
