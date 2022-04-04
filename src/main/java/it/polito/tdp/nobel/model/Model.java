package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {

	private List<Esame> esami;
	private Set<Esame> migliore;
	private double mediaMigliore;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		esami=dao.getTuttiEsami();
	}


	public Set<Esame> calcolaSottoinsiemeEsami(int m) {
		// Ripristino soluzione migliore
		migliore=new HashSet<Esame>();
		mediaMigliore=0;
		Set<Esame> parziale=new HashSet<Esame>();
		// cerca(parziale,0,m);
		cerca2(parziale,0,m);
		return migliore;	
	}

	
	private void cerca2(Set<Esame> parziale, int l, int m) {
		// Controllare i casi terminali
		int sommaCrediti=sommaCrediti(parziale);
		if(sommaCrediti>m) // Soluzione non valida
			return;
		if(sommaCrediti==m) { // Soluzione valida, valutiamo se è la migliore soluzione fino a qui
			double mediaVoti=calcolaMedia(parziale);
			if(mediaVoti>mediaMigliore) {
				migliore=new HashSet<Esame>(parziale);
				mediaMigliore=mediaVoti;
			}
			return;
		}
		// Sicuramente crediti<m
		if(l==esami.size()) 
			return;	
				
		// Provo ad aggiungere esami[L]
		parziale.add(esami.get(l));
		cerca2(parziale,l+1,m);
		
		// Provo a non aggiungere esami[L]
		parziale.remove(esami.get(l));
		cerca2(parziale,l+1,m);
	}

/*
	private void cerca(Set<Esame> parziale, int l, int m) {
		// Controllare i casi terminali
		int sommaCrediti=sommaCrediti(parziale);
		if(sommaCrediti>m) // Soluzione non valida
			return;
		if(sommaCrediti==m) { // Soluzione valida, valutiamo se è la migliore soluzione fino a qui
			double mediaVoti=calcolaMedia(parziale);
			if(mediaVoti>mediaMigliore) {
				migliore=new HashSet<Esame>(parziale);
				mediaMigliore=mediaVoti;
			}
			return;
		}
		// Sicuramente crediti<m
		if(l==esami.size()) 
			return;	
		
		// Possiamo generare sotto-problemi
		for(Esame e : esami) {
			if(!parziale.contains(e))
				parziale.add(e);
				cerca(parziale,l+1,m);
				parziale.remove(e); // con List backttracking parzile.remove(list.size()-1);
		}
	}
*/

	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
