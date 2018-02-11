package us.pinaple.mathworks.problem;

/**
 *
 * @author Totom3
 */
public class DummyProblem extends MathProblem {

	private static final DummyProblem instance;

	static {
		instance = new DummyProblem();
	}

	public static DummyProblem getInstance() {
		return instance;
	}

	private DummyProblem() {
		super(null);
	}

	@Override
	public String getRawEquation() {
		return "";
	}

	@Override
	public String getLateXEquation() {
		return "";
	}

}
