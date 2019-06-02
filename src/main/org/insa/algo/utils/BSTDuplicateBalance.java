package org.insa.algo.utils;

public class BSTDuplicateBalance <E extends Comparable<E>> implements PriorityQueue<E> {
	class node{
		E key;
		node less, more, equal;
		int balance;
		
		public node(E key) {
			this.key = key;
			less = more = equal = null;
			balance = 1;
		}
	}
	
	private node root;
	
	public BSTDuplicateBalance() {
		root = null;
	}
	
	@Override
    public boolean isEmpty() {
        return (root == null);
    }

    @Override
    public int size() {
        return 0; //not implement
    }
    
    public int max(int a, int b) {
    	return(a>b)?a:b;
    }
    
    public int height(node n) {
    	if(n == null) return 0;
    	return n.balance;
    }
    
    public int getBalance(node n) {
    	if(n == null) return 0;
    	return height(n.less) - height(n.more);
    }

    @Override
    public void insert(E x) { 
        System.out.println(x + " INSERT :");
        root = insert(root, x); 
        print();
     } 

    public node insert(node root, E x) { 
        if (root == null) {     
            return new node(x);      
        } 
        if (root.key.compareTo(x) == 0) {
			node new_root = new node(x);
			new_root.equal = root;
			new_root.less = root.less;
			new_root.more = root.more;
			new_root.balance = root.balance;
			root.less = root.more = null;   
			root.balance = 1;
			return new_root;
        } 
         
        if (root.key.compareTo(x) > 0) 
            root.less = insert(root.less, x); 
        else if (root.key.compareTo(x) < 0) 
            root.more = insert(root.more, x);         
        root.balance = 1 + max(height(root.less), height(root.more));
        
        int balance = getBalance(root);
        if (balance > 1 && x.compareTo(root.less.key) < 0) 
            return rightRotate(root);
        if (balance < -1 && x.compareTo(root.more.key) > 0) 
            return leftRotate(root); 
        if (balance > 1 && x.compareTo(root.less.key) > 0) { 
        	root.less = leftRotate(root.less); 
            return rightRotate(root); 
        } 
        if (balance < -1 && x.compareTo(root.more.key) < 0) { 
        	root.more = rightRotate(root.more); 
            return leftRotate(root); 
        } 
        
        return root; 
    }

    public void remove(E x) throws ElementNotFoundException {
        System.out.println(x + " REMOVE :");
    	root = remove(root, x); 
    	print();
    }

    public node remove(node root, E x) {
    	if (root == null)  return root;     	
        if (root.key.compareTo(x) > 0) 
            root.less = remove(root.less, x); 
        else if (root.key.compareTo(x) < 0) 
            root.more = remove(root.more, x); 
        
        else
        {     
        	if(root.equal != null) {
        		node temp = root, previous = null;
        		while(!temp.key.equals(x)) {
        			previous = temp;
        			temp = root.equal;
            		if (temp == null) return root;  
        		}
        		if (previous == null) {
        			root.equal.less = root.less;
        			root.equal.more = root.more;
        			root.equal.balance = root.balance;
        			return root.equal;
        		}
        		previous.equal = temp.equal;
        		return root;
        	}
        	else if ((root.less == null) || (root.more == null))  
            {  
                if (root.less == null)
                	root = root.more;  
                else 
                	root = root.less;  
                /*node temp = null;  
                if (root.less == null) 
                	temp = root.more;  
                else 
                	temp = root.less;  
                if (temp == null)  
                {  
                    temp = root;  
                    root = null;  
                }  
                else root = temp; */
            }  
            else
            {  
                node temp = findMinNode(root.more);  
                root.key = temp.key;
                root.equal = temp.equal;
                root.more = remove(root.more, temp.key);  
            }  
        }  
        if (root == null) return root;  

        root.balance = max(height(root.less), height(root.more)) + 1;  

        int balance = getBalance(root);  
  
        if (balance > 1 && getBalance(root.less) >= 0)  
            return rightRotate(root);  
        if (balance > 1 && getBalance(root.less) < 0)  
        {  
            root.less = leftRotate(root.less);  
            return rightRotate(root);  
        }  
        if (balance < -1 && getBalance(root.more) <= 0)  
            return leftRotate(root); 
        if (balance < -1 && getBalance(root.more) > 0)  
        {  
            root.more = rightRotate(root.more);  
            return leftRotate(root);  
        }    
        return root;  
    }

    /*public boolean remove(node previous, node root, E x, boolean less) {
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
    }*/

    public node findMinNode(node root) throws EmptyPriorityQueueException {
        if (isEmpty()) {
            throw new EmptyPriorityQueueException();
        }
    	node n = root;
    	while(n.less != null) {
    		n = n.less;
    	}
        return n;
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
    
    public node rightRotate(node y) {
        System.out.print(y.key);
    	node x = y.less;
    	node T2 = x.more;
    	x.more = y;
    	y.less = T2;
    	y.balance = max(height(y.less), height(y.more)) + 1; 
    	x.balance = max(height(x.less), height(x.more)) + 1; 
        System.out.println(" ROTATE :");
    	return x;
    }
    
    public node leftRotate(node x) {
        System.out.print(x.key);
    	node y = x.more;
    	node T2 = y.less;
    	y.less = x;
    	x.more = T2;
    	y.balance = max(height(x.less), height(x.more)) + 1; 
    	y.balance = max(height(y.less), height(y.more)) + 1; 
        System.out.println(" ROTATE :");
    	return y;
    }
    
    public void print() {
    	print(root);
    	System.out.println();
    }
    
    public void print(node root) {
    	if(root != null) {
    		if(root.less != null) System.out.print(root.less.key);
    		else System.out.print("|");
        	System.out.print("<" + root.key + "(" + root.balance + ")<");
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
