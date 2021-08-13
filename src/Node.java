//CS2210a 
//Brendan Bain
//251086487

public class Node {
	private Node rightChild, leftChild, parent;
	private DataItem data;

	
	public Node(DataItem val) {
		data = val;
		parent = null;
		leftChild = null;
		rightChild = null;
	}
	
	public Node(DataItem val, Node left, Node right) {
		data = val;
		leftChild = left;
		left.parent = this;
		rightChild = right;
		right.parent = this;
	}
	
	//setData: set nodes data to DataItem d
	public void setData(DataItem d) {
		data = d;
	}
	
	//setLeft: set nodes left child as node left
	public void setLeft (Node left) {
		leftChild = left;
		left.parent = this;
	}
	
	//setRight: set nodes right child as node right
	public void setRight (Node right) {
		rightChild = right;
		right.parent = this;
	}
	
	//setParent: set nodes parent as node par
	public void setParent(Node par) {
		parent = par;
	}
	
	//getData: returns the data item in node
	public DataItem getData() {
		return data;
	}
	
	//getLeft: returns the left child of node
	public Node getLeft() {
		return leftChild;
	}
	
	//getRight: returns the right child of node
	public Node getRight() {
		return rightChild;
	}
	
	//getParent: returns the parent of node 
	public Node getParent() {
		return parent;
	}
	
	//isLeaf: returns true if it is a leaf (no children), returns false otherwise
	public boolean isLeaf() {
		return (leftChild == null) && (rightChild == null);
	}
}
