package org.insa.algo.shortestpath;

import org.insa.graph.Arc;
import org.insa.graph.Node;

public class Label {

	//private Node sommet; //à voir
	private boolean marque;
	private double cout;
	private Arc predeccesseur;
	
	public Label() {
		this.marque = false;
		this.cout = Double.POSITIVE_INFINITY;
		this.predeccesseur = null;
	}
	
	public Label(boolean marque, double cout) {
		this.marque = marque;
		this.cout = cout;
		this.predeccesseur = null;
	}
	
	public double getCost() {
		return this.cout;
	}
	
	public boolean getMark() {
		return this.marque;
	}
	
	public Arc getFather() {
		return this.predeccesseur;
	}
	
	public void setCost(double cout) {
		this.cout = cout;
	}
	
	public void setMark(boolean marque) {
		this.marque = marque;
	}
	
	public void setFather(Arc predeccesseur) {
		this.predeccesseur = predeccesseur;
	}
}
