package engine.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileLoader {
	public static String loadFile(String file){
		return loadFile(new File(file));
	}

	public static String loadFile(File file) {
		try {
			String data = "";
			Scanner in = new Scanner(file);
			while(in.hasNextLine()){
				data+=in.nextLine()+"\n";
			}
			return data;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
