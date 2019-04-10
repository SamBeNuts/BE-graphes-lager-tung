package org.insa.algo.shortestpath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.insa.algo.AbstractSolution.Status;
import org.insa.algo.utils.BinaryHeap;
import org.insa.graph.Arc;
import org.insa.graph.Graph;
import org.insa.graph.Node;
import org.insa.graph.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        HashMap<Node, Label> map = new HashMap<Node, Label>();
        BinaryHeap<Node> heap = new BinaryHeap<Node>();
        
        // INITIALISATION
        for(Node node : graph.getNodes()) {
        	Label l = new Label();
        	map.put(node, l);
        }
        Node s = data.getOrigin();
        map.get(s).setCost(0);
        heap.insert(s);
        notifyOriginProcessed(data.getOrigin());
        
        //ITERATION
        while(!heap.isEmpty()) {
        	s = heap.findMin();
        	Label l_s = map.get(s);
        	l_s.setMark(true);
        	for(Arc arc : s.getSuccessors()) {
        		Node destination = arc.getDestination();
        		Label l_destination = map.get(destination);
        		if(!l_destination.getMark()) {
        			if(l_destination.getCost() > l_s.getCost() + arc.getLength()) {
                        notifyNodeReached(arc.getDestination());
            			l_destination.setCost(l_s.getCost() + arc.getLength());
            			l_destination.setFather(arc);
            			if(!l_destination.getMark()) {
            		    	int index_destination = heap.indexOf(destination);
            		    	heap.update(index_destination);
            			}
            			else heap.insert(destination);
        			}
        		}
        	}
        }

        //SOLUTION
        ShortestPathSolution solution = null;
        // Destination has no predecessor, the solution is infeasible...
        if (map.get(data.getDestination()) == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
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
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        return solution;
    }

}
