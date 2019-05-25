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
import org.insa.graph.Point;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
	
	public void addLabel(Node node, HashMap<Node, Label> map, ShortestPathData data) {
		if(!map.containsKey(node)) map.put(node, new LabelStar(node, data));
	}
}
