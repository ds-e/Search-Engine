package model;

import java.util.ArrayList;
import java.util.Collections;

import model.IndexDoc;
import model.List_MotDocIDFreq;

public class IndexMots {
	public IndexDoc indexDoc;
	public ArrayList<List_MotDocIDFreq> indexMots;
	public ArrayList<String> ensembleDesMotsparIndex;

	public IndexMots(IndexDoc indexDoc) {
		indexMots = new ArrayList<List_MotDocIDFreq>();
		this.indexDoc = indexDoc;
		this.ensembleDesMotsparIndex = this.indexDoc.ensembleDesMots;
		fillIndexMots();
	}

	public void fillIndexMots() { // index tout les mots (ArrayListe<linkedList (mot-DocId-Freq)>) existant dans l'index de document
		for (String S : this.ensembleDesMotsparIndex) {
			List_MotDocIDFreq lst_mdif = new List_MotDocIDFreq(S);
			for (Fichier f : this.indexDoc.getIndex()) {
				for (MotFreq mf : f.token.motFreqList) {
					if ((mf.mot).equals(S)) {
						if (lst_mdif.listMotDocIdFreq.isEmpty()) {
							NoeudMotDocIDFreq nextNoeud = new NoeudMotDocIDFreq(f.id, mf.frequence, null);
							lst_mdif.listMotDocIdFreq.addFirst(nextNoeud);
						} else {
							NoeudMotDocIDFreq nextNoeud = new NoeudMotDocIDFreq(f.id, mf.frequence, null);
							NoeudMotDocIDFreq noeudActuel = lst_mdif.listMotDocIdFreq.getFirst();
							while (noeudActuel.getProchainNoeud() != null) {
								noeudActuel = noeudActuel.getProchainNoeud();
							}
							noeudActuel.setProchainNoeud(nextNoeud);
						}
					}
				}
			}
			indexMots.add(lst_mdif);
		}
		Collections.sort(indexMots);
	}

	public ArrayList<List_MotDocIDFreq> getIndexMots() {
		return indexMots;
	}

	public void setIndexMots(ArrayList<List_MotDocIDFreq> indexMots) {
		this.indexMots = indexMots;
	}

	/// methodes de recherche
	public ArrayList<Integer[]> rechercheParPhrase(String[] S) {
		ArrayList<Integer[]> list_vide = new ArrayList<Integer[]>();
		ArrayList<Integer[]> list_premir_mot = new ArrayList<Integer[]>();
		ArrayList<Integer[]> list_autre_mot = new ArrayList<Integer[]>();
		if (S.length == 0) {
			System.out.println("Aucun doc contint tout les mots");
			return list_vide;
		} else if (S.length == 1) {
			list_premir_mot = rechercheParMot(S[0]);
			list_premir_mot = sortByFreq(0, list_premir_mot);
			return list_premir_mot;
		} else if (S.length > 1) {
			list_premir_mot = rechercheParMot(S[0]);
			for (int i = 1; i < S.length; i++) {
				list_autre_mot = rechercheParMot(S[i]);
				if (list_premir_mot.size() == 0 || list_autre_mot.size() == 0) { // liste vide, on arrete la recherche!
					System.out.println("Aucun doc contint tout les mots");
					return list_vide;
				} else {
					list_premir_mot = mergeIndexFichier(list_premir_mot, list_autre_mot);
				}
			}
		}

		list_premir_mot = sortByFreq(0, list_premir_mot);
		return list_premir_mot;
	}

	private ArrayList<Integer[]> sortByFreq(int index, ArrayList<Integer[]> list_final_FreqCumule) {
		int depart = index;
		if (depart < list_final_FreqCumule.size()) {
			int indexMaxVal = qetMaxFreq_index(index, list_final_FreqCumule);
			swap(depart, indexMaxVal, list_final_FreqCumule);
			depart++;
			sortByFreq(depart, list_final_FreqCumule);
		} else
			return list_final_FreqCumule;

		return list_final_FreqCumule;
	}

	public void swap(int i1, int i2, ArrayList<Integer[]> list) {
		Integer[] temp = list.get(i1);
		list.set(i1, list.get(i2));
		list.set(i2, temp);
	}

	public int qetMaxFreq_index(int index, ArrayList<Integer[]> list) {
		int tempFreq = 0;
		for (int i = index; i < list.size(); i++) {
			if (list.get(i)[1] > tempFreq) {
				tempFreq = list.get(i)[1];
				index = i;
			}
		}
		return index;
	}

	public ArrayList<Fichier> trouverFichierParId(ArrayList<Integer[]> ID) { 
		ArrayList<Fichier> listResultat = new ArrayList<Fichier>();
		if (ID.size() == 0)
			return listResultat;
		for (int i = 0; i < ID.size(); i++) {
			for (Fichier idFishier : this.indexDoc.getIndex()) {
				if (idFishier.id == ID.get(i)[0]) {
					listResultat.add(idFishier);
				}
			}
		}
		return listResultat;
	}

	public ArrayList<Integer[]> mergeIndexFichier(ArrayList<Integer[]> resulPremierMot, ArrayList<Integer[]> resulAutreMot) {
		ArrayList<Integer[]> ans = new ArrayList<Integer[]>();
		if (resulPremierMot.size() == 0 && resulAutreMot.size() != 0)
			return resulAutreMot;
		if (resulPremierMot.size() != 0 && resulAutreMot.size() == 0)
			return resulPremierMot;
		if (resulPremierMot.size() == 0 && resulAutreMot.size() == 0) {
			System.out.println("La phrase n'est pas dans l'ensemble de documents");
			return ans; 
		}
		for (Integer[] list : resulPremierMot)
			ans.add(list);

		for (Integer[] list : resulPremierMot) {
			if (exist(list[0], resulAutreMot)) {
				for (Integer[] list2 : resulAutreMot) {
					if (list[0].equals(list2[0])) {
						ans.get(ans.indexOf(list))[1] += list2[1];
					}
				}
			} else {
				ans.remove(list);
				if (ans.size() == 0)
					return ans;
			}
		}
		return ans;
	}

	public boolean exist(int id, ArrayList<Integer[]> resulAutreMot) { 
		for (Integer[] list : resulAutreMot) {
			if (id == list[0])
				return true;
		}
		return false;
	}

	public ArrayList<Integer[]> rechercheParMot(String S) { 
		ArrayList<Integer[]> listResultat = new ArrayList<Integer[]>();
		if (this.ensembleDesMotsparIndex.contains(S)) {
			listResultat = listContenantMot(S).get_DocIDFreq_List();
		}
		return listResultat;
	}

	public List_MotDocIDFreq listContenantMot(String S) {
		int i = 0;
		for (List_MotDocIDFreq lmoidf : this.indexMots) {
			/// introduir QuickSearch ICI !!!!!!!!!!! eviter de parcouriri toute la liste a chaque fois.
			if ((lmoidf.mot).equals(S)) {
				this.indexMots.get(i).printNoeuds();
				return this.indexMots.get(i);
			}
			i++;
		}
		return null;
	}

	public void printList() {
		for (List_MotDocIDFreq lmoidf : this.indexMots) {
			lmoidf.printNoeuds();
		}
	}

}
