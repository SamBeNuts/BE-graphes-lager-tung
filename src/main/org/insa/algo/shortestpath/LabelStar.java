package org.insa.algo.shortestpath;

import org.insa.graph.Node;

public class LabelStar extends Label {
	private double cout_estime; 
	
	public LabelStar(Node sommet, double cout_estime) {
		super(sommet);
		this.cout_estime = cout_estime;
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
