package us.pinaple.mathworks.problem;

import us.pinaple.mathworks.CachedEquation;
import us.pinaple.mathworks.ProblemType;

public class IntegralMathProblem extends SimpleMathProblem {

	private final CachedEquation lowerBound;
	private final CachedEquation upperBound;

	public IntegralMathProblem(ProblemType type) {
		super(type);

		this.lowerBound = new CachedEquation(null);
		this.upperBound = new CachedEquation(null);
	}

	public CachedEquation getLowerBound() {
		return lowerBound;
	}

	public CachedEquation getUpperBound() {
		return upperBound;
	}

	@Override
	public String getLateXEquation() {
		return "\\int_{" + lowerBound.parseEquation() + "}^{" + upperBound.parseEquation() + "}{" + super.getLateXEquation() + "}dx";
	}

}
