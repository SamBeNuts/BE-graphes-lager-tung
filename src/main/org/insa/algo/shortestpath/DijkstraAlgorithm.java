package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BSTDuplicateBalance;
import org.insa.algo.utils.BinaryHeap;
import org.insa.algo.utils.BinarySearchTreeDuplicate;
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
		HashMap<Node, Label> map = new HashMap<>();
		//BinaryHeap<Label> heap = new BinaryHeap<>();
		BinarySearchTreeDuplicate<Label> heap = new BinarySearchTreeDuplicate<>();
		//BSTDuplicateBalance<Label> heap = new BSTDuplicateBalance<>(); //Ne fonctionne pas, erreur sur l'auto-balancement
	    // Data for the solution
	    int nb_explores = 1;
	    int nb_marques = 0;
	    int max_tas = 1;
		
		// origin = destination (emptyPath)
		if(data.getDestination().getId() == data.getOrigin().getId()) {
			// The destination has been found, notify the observers.
			notifyDestinationReached(data.getDestination());
			// Create the final solution.
			return new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, data.getDestination()));
		}

		Label s = addLabel(data.getOrigin(), map, data);
		s.setCost(0);
		heap.insert(s);
		notifyOriginProcessed(data.getOrigin());
		boolean stop = false;
		// ITERATION
		while (!heap.isEmpty() && !stop) { //tant que tous les sommets sont non marqués ou si un chemin est trouvé entre origin et dest (stop)
			Label l_s = heap.deleteMin();
			l_s.setMark(true);
			nb_marques++;
			for (Arc arc : l_s.getSommet().getSuccessors()) {
				if (data.isAllowed(arc)) {
					Label l_destination = addLabel(arc.getDestination(), map, data);
					if (!l_destination.getMark()) {
						if (l_destination.getCost() > l_s.getCost() + data.getCost(arc)) {
							notifyNodeReached(arc.getDestination());
							if(heap.contains(l_destination)) {
								heap.remove(l_destination);
							}else {
								nb_explores++;
								if(heap.size() > max_tas) max_tas = heap.size();
							}
							l_destination.setCost(l_s.getCost() + data.getCost(arc));
							l_destination.setFather(arc);
							heap.insert(l_destination);
							//Ancienne solution avec la BinaryHeap
							/*try {
								int index_destination = heap.indexOf(l_destination);
								heap.update(index_destination);
							} catch (ElementNotFoundException e) {
								heap.insert(l_destination);
								nb_explores++;
								if(heap.size() > max_tas) max_tas = heap.size();
							}*/
						}
					}
				}
			}
			if(l_s.getSommet().getId() == data.getDestination().getId()) stop = true;
			// Affichage performance
			//System.out.println("coût label marqué : " + l_s.getCost());
			//System.out.println("nb successeurs testés : " + l_s.getSommet().getSuccessors().size());
			//System.out.println("taille tas : " + heap.size());
		}
		// SOLUTION
		ShortestPathSolution solution = null;
		// Destination jamais explor�e, ou destination != origine et cout=+inf destination jamais explor�e, le chemin n'est pas trouvé
		if (!map.containsKey(data.getDestination()) || map.get(data.getDestination()).getCost() == Double.POSITIVE_INFINITY) {
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
			// Create the final solution.
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs), arcs.size(), nb_explores, nb_marques, max_tas);
		}
		return solution;
	}
	
	public Label addLabel(Node node, HashMap<Node, Label> map, ShortestPathData data) {
		if(!map.containsKey(node)) {
			Label l = new Label(node);
			map.put(node, l);
			return l;
		} else return map.get(node);
	}
}
