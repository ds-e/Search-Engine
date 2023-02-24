package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import library.Commun;

public class Repertoir {

	public IndexDoc indexDoc;
	public IndexMots indexMots;

	public Repertoir(File[] listOfFile) throws IOException {

		this.indexDoc = new IndexDoc();

		fillRepertoir(listOfFile);

		this.indexMots = new IndexMots(this.indexDoc);

	}

	public void fillRepertoir(File[] listOfFiles) {
		String fullText;
		String name;
		for (int i = listOfFiles.length - 1; i >= 0; i--) {
			try {
				fullText = lireFichier(listOfFiles[i].getPath());
				name = listOfFiles[i].getName();
				Fichier newDoc = new Fichier();
				newDoc.setName(name);
				newDoc.setPath(listOfFiles[i].getPath());
				newDoc.setFullText(fullText);
				for (String s : Commun.creerTokens(fullText)) {
					newDoc.token.addMot(s);
				}
				newDoc.token.sort();
				indexDoc.addNewDoc(newDoc);

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public String lireFichier(String pathFichier) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(pathFichier))) {
			String ligne = "";
			String texteLu = "";
			while ((ligne = reader.readLine()) != null) {
				texteLu += ligne + " ";
			}
			return texteLu;
		}
	}

	public IndexDoc getIndexedDocs() {
		return indexDoc;
	}

	public void setIndexDoc(IndexDoc indexDoc) {
		this.indexDoc = indexDoc;
	}

}