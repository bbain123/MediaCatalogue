//CS2210a 
//Brendan Bain
//251086487

public class OrderedDictionary implements OrderedDictionaryADT {
	private BinarySearchTree bsTree;												//the binary tree used for the ordered dictionary
	private boolean hasElement = false;												//if the tree is empty, the first DataItem added becomes the root
	
	//get: returns the DataItem of node with key k
	public DataItem get(Key k) {
		return bsTree.get(bsTree.getRoot(), k).getData();
	}
	
	//put: adds an item with DataItem d.
	public void put(DataItem d) throws DictionaryException{
		if (hasElement) {																//if the tree is not empty, add the DataItem
			boolean success = bsTree.put(bsTree.getRoot(), d);
			if (success == false)
				throw new DictionaryException("Cannot insert: Data item with same key is in the tree");
		}
		else {
			bsTree = new BinarySearchTree(new Node(d), d, new Node(null), new Node(null)); //if the tree is empty, initialize the tree with DataItem
			hasElement = true;
		}
	}
	
	//remove: removes the node with key k if it is in the tree
	public void remove(Key k) throws DictionaryException{
		boolean success = bsTree.remove(bsTree.getRoot(), k);
		if (success == false)
			throw new DictionaryException("Cannot remove: couldn't find key in dictionary");
	}
	
	//successor: gets the DataItem immediately larger than node with key k
	public DataItem successor(Key k) {
		return bsTree.successor(bsTree.getRoot(), k).getData();
	}
	
	//predecessor: gets the DataItem immediately smaller than node with key k
	public DataItem predecessor (Key k) {
		return bsTree.predecessor(bsTree.getRoot(), k).getData();
	}
	
	//smallest: returns the DataItem of the smallest item in ordered dictionary
	public DataItem smallest() {
		return bsTree.smallest(bsTree.getRoot()).getData();
	}
	
	//largest: returns the DataItem of the largest item in ordered dictionary
	public DataItem largest() {
		return bsTree.largest(bsTree.getRoot()).getData();
	}
}
