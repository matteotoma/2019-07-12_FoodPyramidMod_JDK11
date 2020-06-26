package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.food.db.FoodDAO;

public class Model {
	
	private FoodDAO dao;
	private Graph<Food, DefaultWeightedEdge> grafo;
	private Map<Integer, Food> idMap;
	private Simulator sim;
	
	public Model() {
		this.dao = new FoodDAO();
		this.idMap = new HashMap<>();
	}
	
	public void creaGrafo(Integer numMin) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici
		dao.listAllFoods(idMap);
		Graphs.addAllVertices(grafo, dao.getVertici(numMin, idMap));
		
		// aggiungo gli archi
		Map<Food, Double> result = dao.getGrassiMedi(numMin, idMap);
		for(Food f1: grafo.vertexSet()) {
			for(Food f2: grafo.vertexSet()) {
				if(f1 != f2 && !grafo.containsEdge(grafo.getEdge(f1, f2)) && !grafo.containsEdge(grafo.getEdge(f2, f1))) {
					Double grassi1 = result.get(f1);
					Double grassi2 = result.get(f2);
					if(grassi1 > grassi2)
						Graphs.addEdge(grafo, f1, f2, grassi1-grassi2);
					if(grassi1 < grassi2)
						Graphs.addEdge(grafo, f2, f1, grassi2-grassi1);
				}
			}
		}
	}
	
	public List<Adiacenza> getAdiacenze(Food source){
		List<Adiacenza> list = new ArrayList<>();
		for(Food vicino: Graphs.successorListOf(grafo, source))
			list.add(new Adiacenza(source, vicino, grafo.getEdgeWeight(grafo.getEdge(source, vicino))));
		Collections.sort(list);
		return list;
	}
	
	public void simula(Food partenza, Integer K) {
		this.sim = new Simulator();
		sim.init(partenza, K, grafo);
		sim.run();
	}
	
	public Double getTempoTotale() {
		return sim.getTempoTotale();
	}

	public Integer getNumeroCibi() {
		return sim.getNumeroCibi();
	}
	
	public List<Food> getFood(){
		List<Food> result = new ArrayList<>(grafo.vertexSet());
		return result;
	}
	
	public int nVertici() {
		return grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return grafo.edgeSet().size();
	}

}
