package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class List_MotDocIDFreq implements Comparable<List_MotDocIDFreq> {
	protected String mot;
	public LinkedList<NoeudMotDocIDFreq> listMotDocIdFreq;
	public ArrayList<Fichier> listFichieRechercheparMot;

	public List_MotDocIDFreq(String mot) {
		this.mot = mot;
		this.listMotDocIdFreq = new LinkedList<NoeudMotDocIDFreq>();

		this.listFichieRechercheparMot = new ArrayList<Fichier>();

	}

	public ArrayList<Integer[]> get_DocIDFreq_List() {
		ArrayList<Integer[]> IDFreqlist = new ArrayList<Integer[]>();
		if (!this.listMotDocIdFreq.isEmpty()) {
			NoeudMotDocIDFreq noeudActuel = this.listMotDocIdFreq.getFirst();
			if (noeudActuel == null) {
			} else {
				while (noeudActuel != null) {
					Integer[] temp = new Integer[2];
					temp[0] = noeudActuel.getDocID();
					temp[1] = noeudActuel.getMotFreq();
					IDFreqlist.add(temp);
					noeudActuel = noeudActuel.getProchainNoeud();
				}
			}
		}
		return IDFreqlist;
	}

	public void printNoeuds() {
		String ans = "";
		if (!this.listMotDocIdFreq.isEmpty()) {
			NoeudMotDocIDFreq noeudActuel = this.listMotDocIdFreq.getFirst();
			while (noeudActuel != null) {
				ans = "DocID :" + noeudActuel.docID + " : " + this.mot + " ; freq : " + noeudActuel.freq;
				noeudActuel = noeudActuel.getProchainNoeud();
				System.out.println(ans);
			}
		} else
			System.out.println("La liste est vide");
	}

	public String getMot() {
		return mot;
	}

	public void setMot(String mot) {
		this.mot = mot;
	}

	@Override
	public int compareTo(List_MotDocIDFreq o) {
		return this.mot.compareTo(o.mot);
	}

}
