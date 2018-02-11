package us.pinaple.mathworks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Totom3
 */
public class FormulaParser {

	private FormulaParser() {

	}

	private static final File MATH_JS_FILE = new File("./resources/math.js");
	private static final File CONVERT_JS_FILE = new File("./resources/convert.js");

	private static final ScriptEngine engine;

	static {
		try {
			engine = new ScriptEngineManager().getEngineByName("Nashorn");
			engine.eval(new FileReader(MATH_JS_FILE));
			engine.eval(new FileReader(CONVERT_JS_FILE));
		} catch (FileNotFoundException | ScriptException ex) {
			throw new AssertionError(ex);
		}
	}

	public static String parse(String formula) {
		if (formula == null || formula.isEmpty()) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		for (String str : formula.split("=")) {
			builder.append(parse0(str));
			builder.append('=');
		}

		return builder.substring(0, builder.length() - 1);
	}

	private static String parse0(String str) {
		if (str.isEmpty()) {
			return "";
		}

		try {
			Invocable invocable = (Invocable) engine;
			return (String) invocable.invokeFunction("convert", str);
		} catch (ScriptException | NoSuchMethodException ex) {
			return "";
		}
	}
}
