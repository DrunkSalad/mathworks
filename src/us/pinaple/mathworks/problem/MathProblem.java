package us.pinaple.mathworks.problem;

import java.io.Serializable;
import us.pinaple.mathworks.ProblemType;

public abstract class MathProblem implements Serializable {

	public static final long serialVersionUID = 10;

	private final ProblemType type;

	private String prompt;
	private String solution; // brute formula

	private String hint1, hint2;
	private int countdownTime;

	public MathProblem(ProblemType type) {
		this.type = type;
		this.countdownTime = -1;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public ProblemType getType() {
		return type;
	}

	public int getCountdownTime() {
		return countdownTime;
	}

	public void setCountdownTime(int countdownTime) {
		this.countdownTime = countdownTime;
	}

	public String getHint1() {
		return hint1;
	}

	public String getHint2() {
		return hint2;
	}

	public void setHint1(String hint1) {
		this.hint1 = hint1;
	}

	public void setHint2(String hint2) {
		this.hint2 = hint2;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public abstract String getRawEquation();

	public abstract String getLateXEquation();
}
