package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {

	private ImdbDAO dao;
	private Map<Integer, Director> idMap;
	private Graph<Director, DefaultWeightedEdge> grafo;
	private List<Director> camminoMax;
	private int pesoMin;

	public Model() {
		dao = new ImdbDAO();
		idMap = new HashMap<>();
		// this.camminoMax = new ArrayList<>();
	}

	public List<Integer> getAnni() {
		return dao.getAnni();
	}

	public void creaGrafo(int anno) {

		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		List<Director> vertici = new ArrayList<>();
		vertici = dao.getRegisti(anno, idMap);

		Graphs.addAllVertices(this.grafo, vertici);

		for (Arco a : dao.getArchi(idMap, anno)) {
			if (this.grafo.containsVertex(a.getD1()) && this.grafo.containsVertex(a.getD2())) {

				Graphs.addEdgeWithVertices(this.grafo, a.getD1(), a.getD2(), a.getPeso());
			}

		}

	}

	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Director> registi() {
		List<Director> d = new ArrayList<>(this.grafo.vertexSet());
		return d;
	}

	public List<Arco> getAdiacenti(Director d) {

		List<Director> vicini = Graphs.neighborListOf(this.grafo, d);
		List<Arco> result = new ArrayList<>();
		for (Director x : vicini) {
			Arco a = new Arco(d, x, (int) this.grafo.getEdgeWeight(this.grafo.getEdge(d, x)));
			result.add(a);
		}
		Collections.sort(result);
		return result;

	}

	public List<Director> cercaCammino(Director partenza, int c) {
		this.camminoMax = null;// new ArrayList<>();
		this.pesoMin = 0;

		List<Director> parziale = new ArrayList<>();
		parziale.add(partenza);

		search(parziale, 1, c);
		return camminoMax;
	}

	private void search(List<Director> parziale, int livello, int c) {

		if (parziale.size() == c) {
			int peso = pesoCammino(parziale);
			if (peso <= c) {
				this.pesoMin = peso;
				// System.out.println(parziale.size());
				this.camminoMax = new ArrayList<>(parziale);

			}
			return;
		}

		List<Director> vicini = Graphs.neighborListOf(this.grafo, parziale.get(livello - 1));
		for (Director v : vicini) {
			if (!parziale.contains(v)) {
				parziale.add(v);
				search(parziale, livello + 1, c);
				parziale.remove(parziale.size() - 1);
			}
		}
	}

	private int pesoCammino(List<Director> parziale) {
		int peso = 0;
		for (int i = 1; i < parziale.size(); i++) {
			double p = this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i - 1), parziale.get(i)));
			peso += p;
		}

		return peso;
	}

	public int getPesoMin() {
		return pesoMin;
	}

	public List<Director> getCamminoMax() {
		return camminoMax;
	}
}
