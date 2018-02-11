package us.pinaple.mathworks;

import java.io.Serializable;

public class CachedEquation implements Serializable {

	public static final long serialVersionUID = 100;

	private String rawEquation;
	private String parsed = null;
	private boolean dirty = true;

	public CachedEquation(String rawEquation) {
		this.rawEquation = rawEquation;
	}

	public String getRawEquation() {
		return rawEquation;
	}

	public void setRawEquation(String rawEquation) {
		this.rawEquation = rawEquation;
		this.dirty = true;
	}

	public String parseEquation() {
		if (dirty) {
			parsed = FormulaParser.parse(rawEquation);
			dirty = false;
		}

		return parsed;
	}
}
