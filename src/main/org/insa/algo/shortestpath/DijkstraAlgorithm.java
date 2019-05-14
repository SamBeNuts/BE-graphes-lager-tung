package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.ElementNotFoundException;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	public DijkstraAlgorithm(ShortestPathData data) {
		super(data);
	}

	@Override
	protected ShortestPathSolution doRun() throws ElementNotFoundException {
		ShortestPathData data = getInputData();
		Graph graph = data.getGraph();
		HashMap<Node, Label> map = new HashMap<Node, Label>();
		BinaryHeap<Label> heap = new BinaryHeap<Label>();
		
		// origin = destination (emptyPath)
		if(data.getDestination().getId() == data.getOrigin().getId()) {
			// The destination has been found, notify the observers.
			notifyDestinationReached(data.getDestination());
			// Create the final solution.
			return new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, data.getDestination()));
		}

		// INITIALISATION
		for (Node node : graph.getNodes()) {
			Label l = new Label(node);
			map.put(node, l);
		}
		Node s = data.getOrigin();
		map.get(s).setCost(0);
		heap.insert(map.get(s));
		notifyOriginProcessed(data.getOrigin());
		int cmp = 0;
		// ITERATION
		// Affichage performance
		System.out.println("taille tas : " + heap.size());
		while (!heap.isEmpty()) {
			Label l_s = heap.deleteMin();
			l_s.setMark(true);
			for (Arc arc : l_s.getSommet().getSuccessors()) {
				if (data.isAllowed(arc)) {
					Node destination = arc.getDestination();
					Label l_destination = map.get(destination);
					if (!l_destination.getMark()) {
						if (l_destination.getCost() > l_s.getCost() + data.getCost(arc)) {
							notifyNodeReached(arc.getDestination());
							l_destination.setCost(l_s.getCost() + data.getCost(arc));
							l_destination.setFather(arc);
							try {
								int index_destination = heap.indexOf(l_destination);
								heap.update(index_destination);
							} catch (ElementNotFoundException e) {
								heap.insert(l_destination);
							}
						}
					}
				}
			}
			cmp++;
			// Affichage performance
			System.out.println("coût label marqué : " + l_s.getCost());
			System.out.println("nb successeurs testés : " + l_s.getSommet().getSuccessors().size());
			System.out.println("taille tas : " + heap.size());
		}
		// SOLUTION
		ShortestPathSolution solution = null;
		// Destination != origine et cout=0, le chemin n'est pas trouvé
		if (map.get(data.getDestination()).getCost() == Double.POSITIVE_INFINITY) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {
			// The destination has been found, notify the observers.
			notifyDestinationReached(data.getDestination());
			// Create the path from the array of predecessors...
			ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = map.get(data.getDestination()).getFather();
			while (arc != null) {
				arcs.add(arc);
				arc = map.get(arc.getOrigin()).getFather();
			}
			// Reverse the path...
			Collections.reverse(arcs);
			// Affichage performance
			System.out.println("nb arcs plus courts chemins : " + arcs.size());
			System.out.println("nb iteration : " + cmp);
			// Create the final solution.
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
		}
		return solution;
	}

}
