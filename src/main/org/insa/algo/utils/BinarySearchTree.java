package org.insa.algo.utils;

import java.util.SortedSet;
import java.util.TreeSet;

import org.insa.algo.shortestpath.Label;

public class BinarySearchTree<E extends Comparable<E>> implements PriorityQueue<E> {

    // Underlying implementation
    private final SortedSet<E> sortedSet;

    /**
     * Create a new empty binary search tree.
     */
    public BinarySearchTree() {
        this.sortedSet = new TreeSet<>();
    }

    /**
     * Create a copy of the given binary search tree.
     * 
     * @param bst Binary search tree to copy.
     */
    public BinarySearchTree(BinarySearchTree<E> bst) {
        this.sortedSet = new TreeSet<>(bst.sortedSet);
    }

    @Override
    public boolean isEmpty() {
        return sortedSet.isEmpty();
    }

    @Override
    public int size() {
        return sortedSet.size();
    }

    @Override
    public void insert(E x) {
        sortedSet.add(x);
    }

    @Override
    public void remove(E x) throws ElementNotFoundException {
        print();
        if (!sortedSet.remove(x)) {
            print();
            System.out.println();
        	throw new ElementNotFoundException(x);
        }
        print();
    }

    @Override
    public E findMin() throws EmptyPriorityQueueException {
        if (isEmpty()) {
            throw new EmptyPriorityQueueException();
        }
        return sortedSet.first();
    }

    public boolean contains(E x) {
    	System.out.println("BST : " + x + " - " + sortedSet.contains(x));
        return sortedSet.contains(x);
    }

    @Override
    public E deleteMin() throws EmptyPriorityQueueException {
        E min = findMin();
        remove(min);
        return min;
    }
    
    public void print() {
        System.out.println(sortedSet.toString());
    }
}
