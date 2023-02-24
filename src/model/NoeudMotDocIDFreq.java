package model;

public class NoeudMotDocIDFreq {

	public int docID;
	public int freq;

	public NoeudMotDocIDFreq prochainNoeud;

	public NoeudMotDocIDFreq(int id, int freq, NoeudMotDocIDFreq prochain) {
		this.docID = id;
		this.freq = freq;
		this.prochainNoeud = prochain;
	}

	public int getDocID() {
		return this.docID;
	}

	public void setDocID(int ID) {
		this.docID = ID;
	}

	public int getMotFreq() {
		return freq;
	}

	public void setMotFreq(int motFreq) {
		this.freq = motFreq;
	}

	public NoeudMotDocIDFreq getProchainNoeud() {
		return this.prochainNoeud;
	}

	public void setProchainNoeud(NoeudMotDocIDFreq prochainNoeud) {
		this.prochainNoeud = prochainNoeud;
	}

}
