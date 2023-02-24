package library;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Commun {

	public static List<String> stopwords;
	
	public Commun() throws IOException {
		
		loadStopwords();
	}
	
	static public String[] creerTokens(String texteAll) throws IOException {
		loadStopwords();
		String temp = "";
		String[] mots;
		texteAll = texteAll.replaceAll("[^A-z0-9]", " "); 
		texteAll = texteAll.toLowerCase();
		//mots = texteAll.split(" +"); 
		for(String s:texteAll.split(" +")) {
			if(!stopwords.contains(s)) {
				temp += s +" ";
			}	
		}
		mots = temp.split(" +");
		return mots;
	}

	public static void loadStopwords() throws IOException { 
		try {
			stopwords = Files.readAllLines(Paths.get("src/library/stopwords.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isStopword(String s) {
		if (stopwords.contains(s)) {return true;}
		else {return false;}
		
	}

}