
/*
 * COMP 352
 * Assignment 2
 * Summer 2021
 * 
 * James Partsafas 40170301
 * Christina Darstbanian 40097340
 */
public class MyBST {
	
	//Inner class
	public class Node {
		
		//Attributes
		public Node parent;
		public Node left;
		public Node right;
		public int v;
		
		//Constructors
		//default
		public Node() {
			parent = left = right = null;
			v = 0;
		}
		
		//Value only constructor
		public Node(int v) {
			parent = left = right = null;
			this.v = v;
		}
		
		//Full constructor, used as a helper for the outer class
		private Node(Node parent, Node left, Node right, int v) {
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.v = v;
		}
		
		//Copy constructor
		public Node(Node node) {
			this.parent = node.parent;
			this.left = node.left;
			this.right = node.right;
			this.v = node.v;
		}
		
		//Methods
		public String toString() {
			return String.valueOf(v);
		}
		
	}
	
	
	//Attributes
	public Node root;
	
	//Constructors
	public MyBST() {
		root = new Node();
	}
	
	public MyBST(int v) {
		root = new Node(v);
	}
	
	//Methods
	
	//Part 1 A
	public static MyBST insertSetRecursive(int[] arr) {
		MyBST myTree = new MyBST(arr[0]);
		for (int i = 1; i < arr.length; i++)
			myTree.insertRecursiveDriver(arr[i], myTree.root);
		
		System.out.println(myTree);
		return myTree;
	}
	
	//Method user calls, but method calls insertRecursiveDriver to work
	public void insertRecursive(int key) {
		insertRecursiveDriver(key, root);
	}
	
	private void insertRecursiveDriver(int key, Node node) {
		
		//Place new node if there is a spot. Otherwise, traverse the tree recursively.
		//Go to left
		if (key < node.v) {
			if (!hasLeft(node)) {
				makeLeftChild(key, node);
				return;
			}
			else {
				insertRecursiveDriver(key, node.left);
			}
		}
		//Go to right
		else {
			if (!hasRight(node)) {
				makeRightChild(key, node);
				return;
			}
			else {
				insertRecursiveDriver(key, node.right);
			}
		}
		
	}
	
	//Part 1 B
	public static MyBST insertSetIterative(int[] arr) {
		MyBST myTree = new MyBST(arr[0]);
		for (int i = 1; i < arr.length; i++)
			myTree.insertIterative(arr[i]);
		
		System.out.println(myTree);
		return myTree;
	}
	
	public void insertIterative(int key) {
		Node node = root;
		
		//Loop until a node is inserted, then return from the method
		while(true) {
			//Go to left
			if (key < node.v) {
				if (!hasLeft(node)) {
					makeLeftChild(key, node);
					return;
				}
				else {
					node = node.left;
					continue;
				}
			}
			//Go to right
			else {
				if (!hasRight(node)) {
					makeRightChild(key, node);
					return;
				}
				else {
					node = node.right;
					continue;
				}
			}
		}
	}
	
	//Part 2
	public static MyBST countSubtree(int[] arr, int lower, int upper) {
		if (arr.length == 0 || lower > upper) {
			System.out.println("Make sure you pass the correct parameters!");
			return null;
		}
		//Create tree
		MyBST myTree = new MyBST(arr[0]);
		for (int i = 1; i < arr.length; i++)
			myTree.insertRecursiveDriver(arr[i], myTree.root);
		
		Wrapper count = new Wrapper(0);
		
		myTree.subtreeCount(myTree.root, lower, upper, count);
		System.out.println("The total number of subtrees is " + count.x + ".");
		
		return myTree;
	}
	
	/*count is initialized to 0 by the caller of the method. node is initialized to root of tree.
	 * method calls itself recursively to traverse the tree in a post-order manner
	 */
	private boolean subtreeCount(Node node, int lower, int upper, Wrapper count) {
		//Base case
		if (node == null)
			return true;
		
		//Move through tree to get to subtrees
		boolean left = subtreeCount(node.left, lower, upper, count);
		boolean right = subtreeCount(node.right, lower, upper, count);
		
		//If left and right subtrees are valid, we can analyze this one. Leaf elements will always have valid subtrees
		if (left && right && isInRange(node, lower, upper)) {
			count.x++;
			return true;
		}
		
		return false;
	}
	
	//REMOVE QUESTION 3
	public static MyBST remove(int[]arr, int r1,int r2) {
		if (arr.length == 0 || r1 > r2) {
			System.out.println("Make sure you pass the correct parameters!");
			return null;
		}
		MyBST myTree = new MyBST(arr[0]);
		int i;
		for ( i = 1; i < arr.length; i++) {
			myTree.insertIterative(arr[i]);
		}
		System.out.println("Before removing nodes: " + myTree);

		myTree.root = myTree.delete(myTree.root, r1, r2);

		System.out.println("After removing nodes: " + myTree);
		return myTree;
	
	}
	
	//Moves through tree in post-order method. Returns root of tree (or subtree).
	public Node delete(Node node, int r1, int r2) {
		//Base case
		if (node == null)
			return null;
		
		//current node is not null
		//Take care of its left and right subtrees
		node.left = delete(node.left, r1, r2);
		node.right = delete(node.right, r1, r2);
		
		//subtrees have been handled in post order manner. Now handle current node
		//Current node is too small. Thus, entire left subtree must also be too small
		if (node.v < r1) {
			Node right = node.right;
			node = null;
			return right;
		}
		//Current node is too big. Thus, entire right subtree must also be too big
		if (node.v > r2) {
			Node left = node.left;
			root = null;
			return left;
		}
		
		//Current node is in range.
		return node;
		
	}
	    
	
	
	private boolean isInRange(Node node, int lower, int upper) {
		return lower <= node.v && node.v <= upper;
	}
	
	
	public String toString() {
		if (root == null)
			return "This tree is empty!";
		StringBuilder builder = new StringBuilder("The BST contains: ");
		getOutput(root, builder);
		return builder.toString();
	}
	
	//toString helper. Gets the values inserted and appends them to the string to be outputted. Uses inorder traversal.
	private void getOutput(Node node, StringBuilder output) {
		if (hasLeft(node)) 
			getOutput(node.left, output);
		output.append(node.v + " ");
		if(hasRight(node))
			getOutput(node.right, output);
	}
	
	
	
	//Helper methods
	public boolean isLeaf(Node node) {
		if (node.left == null && node.right == null)
			return true;
		return false;
	}
	
	//Creates child node on left of passed node
	private void makeLeftChild(int key, Node node) {
		node.left = new Node(node, null, null, key);
	}
	
	private void makeRightChild(int key, Node node) {
		node.right = new Node(node, null, null, key);
	}
	
	private boolean hasRight(Node node) {
		if (node.right == null)
			return false;
		return true;
	}
	
	private boolean hasLeft(Node node) {
		if (node.left == null)
			return false;
		return true;
	}
}
