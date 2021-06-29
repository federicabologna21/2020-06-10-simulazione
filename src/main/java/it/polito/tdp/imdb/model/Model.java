package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	private SimpleWeightedGraph <Actor, DefaultWeightedEdge> grafo;
	private ImdbDAO dao;
	private Map<Integer, Actor> idMap;
	
	public Model() {
		dao = new ImdbDAO();
		idMap = new HashMap<Integer, Actor>();
		dao.listAllActors(idMap);
		
	}
	
	public List<String> getGenere(){
		return dao.getGenere();
	}
	
	public void creaGrafo(String genere) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		// aggiungo i vertici 
		Graphs.addAllVertices(grafo , dao.getVertici(genere, idMap));
		
		// aggiungo gli archi
		for (Adiacenza a : dao.getAdiacenze(genere, idMap)) {
			if(this.grafo.containsVertex(a.getA1()) && this.grafo.containsVertex(a.getA2())) {
				DefaultWeightedEdge e = this.grafo.getEdge(a.getA1(), a.getA2());
				
				if (e == null) {
					Graphs.addEdgeWithVertices(grafo, a.getA1(), a.getA2(), a.getPeso());
				}
			}
		}
		
	}

	public int getNumVertici() {
		if(this.grafo!=null) {
			return this.grafo.vertexSet().size();
		}
		return 0;
	}
	
	public int getNumArchi() {
		if(this.grafo!=null) {
			return this.grafo.edgeSet().size();
		}
		return 0;
	}
	
	public Set <Actor> getVerticiTendina(){
		return this.grafo.vertexSet();
	}
	
	public List<Actor> getAttorriConnessi (Actor a){
		ConnectivityInspector <Actor, DefaultWeightedEdge> ci = new ConnectivityInspector <Actor, DefaultWeightedEdge> (grafo);
		
		List<Actor> actors = new ArrayList<>(ci.connectedSetOf(a));
		actors.remove(a);
		Collections.sort(actors, new Comparator<Actor>() {

			@Override
			public int compare(Actor o1, Actor o2) {
				return o1.lastName.compareTo(o2.lastName);
			}
			
		});
		
		return actors ;
	}
	
}