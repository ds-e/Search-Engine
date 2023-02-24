package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections; 
import library.Commun; 

public class IndexDoc {

	public ArrayList<Fichier> index;
	public ArrayList<String> ensembleDesMots;
	
	public IndexDoc() throws IOException {
		 this.ensembleDesMots = new ArrayList<String>();	
		 this.index = new ArrayList<Fichier>(); 
		 Commun.loadStopwords(); 
	 }
	
	public ArrayList<String> creerSetMot(){
		for(Fichier f: this.index) {
			for(String s :f.token.getSet()) {
				if(filtrerSetMots(s)) {}
				else {
				 this.ensembleDesMots.add(s);
				}
			}
		}
		Collections.sort(ensembleDesMots);
		return ensembleDesMots;
	}
	
	public boolean filtrerSetMots(String mot) {
		if(this.ensembleDesMots.size() != 0) {
			for(String s:this.ensembleDesMots) {
				if(s.equals(mot))
					return true;
			}
		}
	return false;
	}
	 
	public ArrayList<String> getEnsembleDesMots() {
		return ensembleDesMots;
	}
	
	public void setEnsembleDesMots(ArrayList<String> ensembleDesMots) {
		this.ensembleDesMots = ensembleDesMots;
	}

	public void printEnsembleDesMots() {
		for(String S:this.ensembleDesMots)
		System.out.println(S);
	}
	
	// Index
	 public ArrayList<Fichier> getIndex() {
		return this.index;
	}
	 
	public void addNewDoc(Fichier e) {
		if(comfirmID(e.getId())){
			e.setNewId();
		}
		this.index.add(e);
		creerSetMot();  
	} 
	
	public Boolean comfirmID(int id) {
		for(Fichier f:this.index) {
			if(f.getId()==id) return true;
		}
	return false;
	}
	
	public int getIndexSize() {
		return this.index.size(); 
	}
	
	
	public int getIndexedDocID(int ind) {
		return  this.index.get(ind).getId();
	}

	public int getDocIndex_FromDocID(int id) {
		int docIndex = -1;
		for(Fichier f:this.index) {
			if(f.getId()==id) 
			{
				docIndex = this.index.indexOf(f);	
			}	
		}
	 return  docIndex;
	}
	
	
	public void indexNewDoc(Fichier Doc) throws IOException {
		Fichier newDoc = Doc;
		String fullText = new String();
		try {
			fullText = lireFichier(newDoc.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		String name = Doc.getName();  
	    newDoc.setName(name);
	    newDoc.setFullText(fullText);
	    
	        for( String s: Commun.creerTokens(fullText)){
		    	newDoc.token.addMot(s); 
		    }
		    addNewDoc(newDoc);
		    
	}	
	
	
	public String lireFichier(String pathFichier) throws IOException {	 
		try (BufferedReader reader = new BufferedReader(new FileReader(pathFichier))) {
			String ligne = ""; 
			String texteLu = ""; 
			while((ligne = reader.readLine()) != null) {
				   texteLu += ligne + " ";
			}
			return texteLu;
		}
	}

	public Fichier getByindex(int row) {
		return this.index.get(row);
	}
	
/// Pour David: Optionel pour faciliter la revition 
//	
//	public void printIndex() {
//		int i = 1;
//		for(Fichier f:this.index) {
//			System.out.println("liste de mots dans le doc " + i + " :");
//			i+=1;
//			f.token.toString();  
//		}
//		
//	}
	
}
