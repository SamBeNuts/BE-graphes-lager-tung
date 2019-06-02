package org.insa.algo.shortestpath;

import org.insa.graph.Arc;
import org.insa.graph.Node;

public class Label implements Comparable<Label>{

	private Node sommet;
	private boolean marque;
	private double cout;
	private Arc predeccesseur;
	
	public Label(Node sommet) {
		this.marque = false;
		this.cout = Double.POSITIVE_INFINITY;
		this.predeccesseur = null;
		this.sommet = sommet;
	}
	
	public Node getSommet() {
		return this.sommet;
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
	
	@Override
    public int compareTo(Label other) {
		return Double.compare(getCost(), other.getCost());
    }

    public boolean equals(Label other) {
		return (this.getSommet().getId() == other.getSommet().getId());
    }

	@Override
	public String toString() {
		return sommet + " - " + cout;
	}
}
