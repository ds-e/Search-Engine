package model;

public class MotFreq implements Comparable<MotFreq> {
	public String mot;
	public int frequence;

	public MotFreq(String m) {
		this.mot = m;
		this.frequence = 1;
	}

	public void augmenterFreq() {
		this.setFrequence(this.getFrequence() + 1);
	}

	public String getMot() {
		return mot;
	}

	public void setMot(String mot) {
		this.mot = mot;
	}

	public int getFrequence() {
		return frequence;
	}

	public void setFrequence(int frequence) {
		this.frequence = frequence;
	}

	@Override
	public int compareTo(MotFreq o) {
		return this.getMot().compareTo(o.getMot());
	}

}
