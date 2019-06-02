package org.insa.algo.utils;

public class BinarySearchTreeDuplicate<E extends Comparable<E>> implements PriorityQueue<E> {
	class node{
		E key;
		node less, more, equal;
		
		public node(E key) {
			this.key = key;
			less = more = equal = null;
		}
	}
	
	private node root;
	private int size;
	
	public BinarySearchTreeDuplicate() {
		root = null;
		size = 0;
	}
	
	@Override
    public boolean isEmpty() {
        return (root == null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void insert(E x) { 
    	size++;
        root = insert(root, x); 
     } 

    public node insert(node root, E x) { 
        if (root == null) { 
            root = new node(x);      
            return root;
        } 
        if (root.key.compareTo(x) == 0) {
			node new_root = new node(x);
			new_root.equal = root;
			new_root.less = root.less;
			new_root.more = root.more;
			root.less = root.more = null;   
			return new_root;
        } 
         
        if (root.key.compareTo(x) > 0) 
            root.less = insert(root.less, x); 
        else if (root.key.compareTo(x) < 0) 
            root.more = insert(root.more, x); 
        return root; 
    }

    @Override
    public void remove(E x) throws ElementNotFoundException {
    	if(remove(null, root, x, true)) size--;
    	else throw new ElementNotFoundException(x);
    }

    public boolean remove(node previous, node root, E x, boolean less) {
    	if (root == null)  return false;     	
        if (root.key.compareTo(x) > 0) 
            return remove(root, root.less, x, true); 
        else if (root.key.compareTo(x) < 0) 
            return remove(root, root.more, x, false); 
        else
        { 
        	if(root.key.equals(x)) {
        		if(previous == null) {
        			if(root.equal != null) {
            			this.root = root.equal;
            			root.equal.less = root.less;
            			root.equal.more = root.more;
            		}else if (root.less == null) {
            			this.root = root.more;
            		}else if (root.more == null) {
            			this.root = root.less;
                    }else {
                    	node p = root;
                    	node n = root.more;
                    	while(n.less != null) {
                    		p = n;
                    		n = n.less;
                    	}
                    	this.root = n;
                    	if(p != root) {
                    		p.less = n.more;
                        	n.more = root.more;
                    	}
                    	n.less = root.less;
                    }
        		}else if(root.equal != null) {
        			if (less) previous.less = root.equal;
        			else previous.more = root.equal;
        			root.equal.less = root.less;
        			root.equal.more = root.more;
        		}else if (root.less == null) {
        			if (less) previous.less = root.more;
        			else previous.more = root.more;
        		}else if (root.more == null) {
        			if (less) previous.less = root.less;
        			else previous.more = root.less;
                }else {
                	node p = root;
                	node n = root.more;
                	while(n.less != null) {
                		p = n;
                		n = n.less;
                	}
                	if (less) previous.less = n;
        			else previous.more = n;
                	if(p != root) {
                		p.less = n.more;
                    	n.more = root.more;
                	}
                	n.less = root.less;
                }      
        		return true;
        	}else {
        		node n_p = root;
            	node n = root.equal;
            	while(n != null) {
            		if(n.key.equals(x)) {
            			n_p.equal = n.equal;
            			return true;
            		}
            		n_p = n;
            		n = n.equal;
            	}   
            	return false;
        	} 
        }   
    }

    @Override
    public E findMin() throws EmptyPriorityQueueException {
        if (isEmpty()) {
            throw new EmptyPriorityQueueException();
        }
    	node n = this.root;
    	while(n.less != null) {
    		n = n.less;
    	}
        return n.key;
    }

    public boolean contains(E x) {
        boolean c = contains(root, x);
        return c;
    }

    public boolean contains(node root, E x) {
        if(root == null) return false;
        if(root == root.less || root == root.more || root == root.equal) {
        	System.out.println("OUPS");
        	System.exit(1);
        }
        if(root.key.compareTo(x) > 0) return contains(root.less, x);
        else if(root.key.compareTo(x) < 0) return contains(root.more, x);
        else {
        	node n = root;
        	while(n != null) {
        		if(n.key.equals(x)) return true;
        		n = n.equal;
        	}
        	return false;
        }
    }

    @Override
    public E deleteMin() throws EmptyPriorityQueueException {
        E min = findMin();
        remove(min);
        return min;
    }
    
    public void print() {
    	print(this.root);
    	System.out.println();
    }
    
    public void print(node root) {
    	if(root != null) {
    		if(root.less != null) System.out.print(root.less.key);
    		else System.out.print("|");
        	System.out.print("<" + root.key + "<");
    		if(root.more != null) System.out.print(root.more.key + "\t");
    		else System.out.print("| \t");
            if(root == root.less || root == root.more || root == root.equal) {
            	System.out.println("OUPS");
            	System.exit(1);
            }
        	print(root.less);
        	print(root.more);
    	}
    }
}
