package us.pinaple.mathworks.problem;

import us.pinaple.mathworks.CachedEquation;
import us.pinaple.mathworks.ProblemType;

public class SimpleMathProblem extends MathProblem {

	private final CachedEquation equation;

	public SimpleMathProblem(ProblemType type) {
		super(type);
		this.equation = new CachedEquation(null);
	}

	@Override
	public String getLateXEquation() {
		return equation.parseEquation();
	}

	@Override
	public String getRawEquation() {
		return equation.getRawEquation();
	}

	public void setRawEquation(String equation) {
		this.equation.setRawEquation(equation);
	}

}
