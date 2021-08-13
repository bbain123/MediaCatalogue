//CS2210a 
//Brendan Bain
//251086487

public class BinarySearchTree {
	private Node root;								//the root of the tree
	
	public BinarySearchTree(Node r, DataItem d) {
		root = r;
		root.setData(d);
	}
	
	public BinarySearchTree(Node r, DataItem d, Node left, Node right) {
		root = r;
		root.setData(d);
		root.setLeft(left);
		root.setRight(right);
	}
	
	//getRoot: returns root
	public Node getRoot() {
		return root;
	}
	
	//get: gets the Node with key k 
    public Node get(Node r, Key k) {
    	if(r.isLeaf())
    		return r;
    	else {
    		if(k.compareTo(r.getData().getKey()) == 0)
    			return r;
    		else {
    			if (k.compareTo(r.getData().getKey()) == -1)
    				return get(r.getLeft(), k);
    			if(k.compareTo(r.getData().getKey()) == 1)
    				return get(r.getRight(), k);
    		}
    	}
    	return null;
	}
	
    //put: adds a DataItem d to binary search tree if not already inside. True if successful, false if otherwise
	public boolean put(Node r, DataItem d){
		Node p = get(r, d.getKey());
		if(!p.isLeaf())		//if it is internal
			return false;
		else {
			p.setData(d);
			p.setLeft(new Node(null)); //set left to leaf
			p.setRight(new Node(null));//set right to leaf
			return true;
		}
	}
	
	//remove: removes a Node with key k from binary search tree if inside. True if successful, false otherwise
	public boolean remove(Node r, Key k){
		Node p = get(r, k);
		if(p.isLeaf())
			return false;
		
		else {
			if(p.getLeft().isLeaf() || p.getRight().isLeaf()) { //if has a leaf child
				Node replacement;
				Node parentP = p.getParent();
				
				if(p.getLeft().isLeaf())
					replacement = p.getRight();
				else 
					replacement = p.getLeft();
				
				if (this.getRoot() == p) {
					root = replacement;
					root.setParent(null); 	//get rid of the parent
				}
				else {
					if(parentP.getRight() == p)
						parentP.setRight(replacement);
					else if (parentP.getLeft() == p)
						parentP.setLeft(replacement);
				}
			}
			else {
				Node small = smallest(p.getRight());
				p.setData(small.getData());
				remove(root, k);
			}
			return true;
		}
	}
	
	
	//successor: returns the Node that is the successor to node with key k
	public Node successor(Node r, Key k) {
		if(r.isLeaf())
			return null;
		else {
			Node p = get(r, k);
			if(!p.isLeaf() && !p.getRight().isLeaf())
				return smallest(p.getRight());
			else {
				Node parentP = p.getParent();
				while(p != this.getRoot() && parentP.getRight() == p) {
					p = parentP;
					parentP = p.getParent();
				}
				if (p == this.getRoot())
					return null;
				else
					return parentP;	
			}
		}
	}
	
	//predecessor: returns the node that is the predecessor to the node with key K
	public Node predecessor(Node r, Key k) {
		if (r.isLeaf())
			return null;
		else {
			Node p = get(r, k);
			if (!p.isLeaf() && !p.getLeft().isLeaf())
				return largest(p.getLeft());
			else {
				Node parentP = p.getParent();
				while (p != this.getRoot() && parentP.getLeft() == p) {
					p = parentP;
					parentP = p.getParent();
				}
				if (p == this.getRoot())
					return null;
				else
					return parentP;
			}
		}
	}
	
	//smallest: returns the node that is the smallest in binary search tree
	public Node smallest(Node r) {
		if(r.isLeaf())
			return null;
		else {
			Node p = r;
			while (!p.getLeft().isLeaf()) //go all the way to the left
				p = p.getLeft();
			return p;
		}
	}
	
	//largest: returns the node that is the largest in the binary search tree
	public Node largest(Node r) {
		if(r.isLeaf())
			return null;
		else {
			Node p = r;
			while(!p.getRight().isLeaf()) //go all the way to the right
				p = p.getRight();
			return p;
		}
		
	}
}
