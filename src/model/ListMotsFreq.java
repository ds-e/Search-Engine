package model;

import java.util.ArrayList;
import java.util.Collections;

public class ListMotsFreq {

	ArrayList<MotFreq> motFreqList;

	public ListMotsFreq() {
		motFreqList = new ArrayList<MotFreq>();
	}

	public ArrayList<MotFreq> getMotFreqList() {
		return motFreqList;
	}

	public void setMotFreqList(ArrayList<MotFreq> motFreqList) {
		this.motFreqList = motFreqList;
	}

	public void addMot(String newmot) {
		if (existe(newmot)) {
			this.motFreqList.get(findeIndexOfWord(newmot)).augmenterFreq();
		} else {
			this.motFreqList.add(new MotFreq(newmot));
		}
	}

	public int getFerq(String mot) {
		int freq = 0;
		for (MotFreq mf : this.motFreqList)
			if (mf.getMot() == mot) {
				freq = mf.getFrequence();
			}
		return freq;
	}

	public int getListSize() {
		return this.motFreqList.size();
	}

	public ArrayList<String> getSet() {
		ArrayList<String> set = new ArrayList<String>();
		for (MotFreq mf : this.motFreqList) {
			if (mf.getMot() != null)
				set.add(mf.getMot());
		}
		return set;
	}

	public int findeIndexOfWord(String mot) { 
		int ans = -1, i = 0;
		for (MotFreq mf : this.motFreqList) {
			if (mf.getMot().equals(mot)) {
				return i;
			}
			i++;
		}
		return ans;
	}

	public boolean existe(String mot) {
		int size = this.motFreqList.size();
		for (int i = 0; i < size; i++)
			if ((this.motFreqList.get(i)).mot.equals(mot)) {
				return true;
			}
		return false;
	}

	public void sort() {
		Collections.sort(this.motFreqList);
	}

	public String toString() {
		String list = "";
		for (MotFreq mf : this.motFreqList) {
			list += mf.getMot() + " : " + mf.getFrequence() + "\n";
			System.out.println(mf.getMot() + " : " + mf.getFrequence());
		}
		return list;
	}

}
