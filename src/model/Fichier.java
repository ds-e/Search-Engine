package model;

import java.util.Random;

public class Fichier implements Comparable<Fichier>{

    public String path; 
	public ListMotsFreq token;
	int id;
	public String name;
	public String fullText;
	
	

	public Fichier () {
		this.name = "";
		this.token = new ListMotsFreq();
		Random generateur = new Random();
		this.id = generateur.nextInt(1000); 
		this.fullText = "";
	}	
	
	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ListMotsFreq getToken() {
		return token;
	}

	public void setToken(ListMotsFreq token) {
		this.token = token;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNewId() {
		Random generateur = new Random();
		this.id = generateur.nextInt(1000);
	}

	@Override
	public int compareTo(Fichier F) {
			return this.getName().compareTo(F.getName()); 
	}

	public String toString() {
		String s = this.name + " ;" + this.id ;
		System.out.println(s);		
		return s;
	}
	
	
	
	
}
