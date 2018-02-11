package us.pinaple.mathworks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.stage.FileChooser;
import us.pinaple.mathworks.problem.MathProblem;

public class MathWorksDataFile implements Serializable {

	public static final long serialVersionUID = 1;

	public static MathWorksDataFile loadFromFile(File f) {
		try {
			ObjectInputStream str = new ObjectInputStream(new BufferedInputStream(new FileInputStream(f)));
			return (MathWorksDataFile) str.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private File file;
	private final List<MathProblem> problems;

	public MathWorksDataFile(File file) {
		this.file = file;
		this.problems = new ArrayList<>();
	}

	public MathWorksDataFile(File file, List<MathProblem> problems) {
		this.file = file;
		this.problems = problems;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List<MathProblem> getProblems() {
		return problems;
	}

	public void trySave() {
		if (file != null) {
			save0();
		}
	}

	public void save() {
		if (file == null) {
			FileChooser fc = new FileChooser();
			fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("MathWorks File", "mathworks"));
			file = fc.showSaveDialog(Main.getInstance().getStage());
			if (file == null) {
				return;
			}
		}

		save0();
	}

	private void save0() {
		try {
			ObjectOutputStream str = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			str.writeObject(this);
			str.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			return;
		}

	}
}
