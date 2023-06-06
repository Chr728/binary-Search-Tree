/*
 * COMP 352
 * Assignment 2
 * Summer 2021
 * 
 * James Partsafas 40170301
 * Christina Darstbanian 40097340
 */

public class Driver {

	public static void main(String[] args) {
		System.out.println("Testing BST\n");
		//Data set. Modify the contents to change what the trees will hold.
		int[] dataPart1 = {15, 25, 20, 22, 30, 18, 10, 8, 9, 12, 6};
		int[] dataPart2 = {18,23,12,28,15,9,33,25,13,11,21};
		int[] dataPart3 = {22, 48, 19, 4, 13, 78, 83, 59, 29, 17, 66};
		
		System.out.println("Testing the recursive method (Part 1 A)");
		MyBST treeRecursive = MyBST.insertSetRecursive(dataPart1);
		
		System.out.println("\nTesting the iterative method (Part 1 B)");
		MyBST treeIterative = MyBST.insertSetIterative(dataPart1);
		
		System.out.println("\nTesting the subtree finding, using the recursive method (Part 2)");
		MyBST treeCounting = MyBST.countSubtree(dataPart2, 8, 23);
		
		System.out.println("\nTesting the node deleting (Part 3)");
		MyBST treeDeleting = MyBST.remove(dataPart3, 5, 40);
		
	}

}
