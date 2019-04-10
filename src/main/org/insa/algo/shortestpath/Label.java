package org.insa.algo.shortestpath;

import org.insa.graph.Arc;
import org.insa.graph.Node;

public class Label {

	private Node sommet;
	private boolean marque;
	private double cout;
	private Arc predeccesseur;
	
	public Label(Node sommet, boolean marque, double cout, Arc predeccesseur) {
		this.sommet = sommet;
		this.marque = marque;
		this.cout = cout;
		this.predeccesseur = predeccesseur;
	}
	
	public double getCost() {
		return this.cout;
	}
}
