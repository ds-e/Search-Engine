package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import frames.Admin;
import frames.User;

public class Main {
  
	public static IndexDoc listDoc;
	public static Repertoir repertoir;
	public static ArrayList<String> listeAllWords;
	
	public static void main(String[] args) throws IOException{

		repertoir = reload();

		Admin admin = new Admin();
		admin.setVisible(true);
		
		User user = new User();
		user.setVisible(true);
		
	}

	public static Repertoir reload() throws IOException {
		File folder = new File("src/ressources");  // default path.
		File[] listOfFiles = folder.listFiles();
		repertoir = new Repertoir(listOfFiles);
		
		listDoc = new IndexDoc();
		Main.listDoc = repertoir.getIndexedDocs();
		Main.listeAllWords = listDoc.ensembleDesMots;
			
		return repertoir;
	}

	
}

