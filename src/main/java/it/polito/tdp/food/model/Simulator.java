package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.food.model.Event.EventType;

public class Simulator {
	
	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;
	
	// STATO DEL SISTEMA
	private Graph<Food, DefaultWeightedEdge> grafo;
	private List<Food> inPreparazione;
	private List<Food> preparati;
	
	// OUTPUT DA CALCOLARE
	private Double tempoTotale;
	private Integer numeroCibi;
	
	// PARAMETRI DELLA SIMULAZIONE
	private List<Cucina> cucine;
	private Integer K;
	private Food partenza;
	
	public void init(Food f, Integer k, Graph<Food, DefaultWeightedEdge> g) {
		this.queue = new PriorityQueue<>();
		this.grafo = g;
		this.tempoTotale = 0.0;
		this.numeroCibi = 0;
		this.cucine = new ArrayList<>();
		this.inPreparazione = new ArrayList<>();
		this.preparati = new ArrayList<>();
		this.K = k;
		this.partenza = f;
		
		setCucine();
		
		List<Adiacenza> list = new ArrayList<>(this.getAdiacenze(partenza));
		for(int i=0; i<k; i++) {
			Cucina c = cucine.get(i);
			Food daPreparare = list.get(i).getF2();
			if(!inPreparazione.contains(daPreparare) && !preparati.contains(daPreparare) && c.isLibera()) {
				c.setLibera(false);
				Double tempo = list.get(i).getPeso();
				Event e = new Event(tempo, EventType.FINE, cucine.get(i), daPreparare);
				inPreparazione.add(daPreparare);
				queue.add(e);
			}
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch(e.getType()) {
		
		case PREPARAZIONE:
			for(Food vicino: Graphs.successorListOf(grafo, e.getF())) {
				if(!inPreparazione.contains(vicino) && !preparati.contains(vicino) &&e.getC().isLibera()) {
					e.getC().setLibera(false);
					Double tempo = grafo.getEdgeWeight(grafo.getEdge(e.getF(), vicino));
					Event nuovo = new Event(e.getTime()+tempo, EventType.FINE, e.getC(), vicino);
					queue.add(nuovo);
					inPreparazione.add(vicino);
				}
			}
			break;
			
		case FINE:
			this.numeroCibi++;
			this.tempoTotale = e.getTime();
			this.preparati.add(e.getF());
			e.getC().setLibera(true);
			queue.add(new Event(e.getTime(), EventType.PREPARAZIONE, e.getC(), e.getF()));
			break;
		}
	}

	public Double getTempoTotale() {
		return tempoTotale;
	}

	public Integer getNumeroCibi() {
		return numeroCibi;
	}

	private void setCucine() {
		for(int i=0; i<K; i++)
			cucine.add(new Cucina(i, true));
	}
	
	private List<Adiacenza> getAdiacenze(Food source){
		List<Adiacenza> list = new ArrayList<>();
		for(Food vicino: Graphs.successorListOf(grafo, source))
			list.add(new Adiacenza(source, vicino, grafo.getEdgeWeight(grafo.getEdge(source, vicino))));
		Collections.sort(list);
		return list;
	}

}
