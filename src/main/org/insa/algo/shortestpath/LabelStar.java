package org.insa.algo.shortestpath;

import org.insa.algo.AbstractInputData;
import org.insa.graph.Node;
import org.insa.graph.Point;

public class LabelStar extends Label {
	private double cout_estime; 
	
	public LabelStar(Node sommet, ShortestPathData data) {
		super(sommet);
		if(data.getMode() == AbstractInputData.Mode.LENGTH) {
			this.cout_estime = Point.distance(sommet.getPoint(), data.getDestination().getPoint());
		} else {
			int vitesse = Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed());
			this.cout_estime = Point.distance(sommet.getPoint(), data.getDestination().getPoint())/vitesse*3.6;
		}
	}
	
	public double getCostEstime() {
		return cout_estime;
	}
	
	@Override
    public int compareTo(Label other) {
		LabelStar ls = (LabelStar) other;
    	int cmp = Double.compare(getCost() + getCostEstime(), ls.getCost() + ls.getCostEstime());
        if (cmp == 0) {
        	return super.compareTo(other);
        }
        else return cmp;
    }
}
