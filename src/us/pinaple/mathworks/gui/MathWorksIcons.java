package us.pinaple.mathworks.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MathWorksIcons {

	public static final Image ARITHMETIC_IMAGE;
	public static final Image DERIVATIVE_IMAGE;
	public static final Image INTEGRAL_IMAGE;
	public static final Image PLUS_SIGN_IMAGE;
	public static final Image COMP_OVERLAY_IMAGE;
	public static final Image BUTTON_CREATE_IMAGE;
	public static final Image BUTTON_LAUNCH_IMAGE;
	public static final Image TITLE_IMAGE;

	static {
		try {
			ARITHMETIC_IMAGE = new Image(new FileInputStream(new File("./resources/C_Arithmetic.png")));
			DERIVATIVE_IMAGE = new Image(new FileInputStream(new File("./resources/C_Derivative.png")));
			INTEGRAL_IMAGE = new Image(new FileInputStream(new File("./resources/C_Integral.png")));
			PLUS_SIGN_IMAGE = new Image(new FileInputStream(new File("./resources/PlusSign.png")));
			COMP_OVERLAY_IMAGE = new Image(new FileInputStream(new File("./resources/P_Overlay.png")));
			BUTTON_CREATE_IMAGE = new Image(new FileInputStream(new File("./resources/MM_Create.png")));
			BUTTON_LAUNCH_IMAGE = new Image(new FileInputStream(new File("./resources/MM_Load.png")));
			TITLE_IMAGE = new Image(new FileInputStream(new File("./resources/Title.png")));
			
		} catch (FileNotFoundException ex) {
			throw new AssertionError(ex);
		}
	}

	public static void ayye() {

	}
}
