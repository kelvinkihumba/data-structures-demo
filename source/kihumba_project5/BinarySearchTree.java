/**
 *  A linked data structure implementation of a binary search tree
 *
 *  @author COSC 311, Fall '22
 *  @version (10-18-22)
 */

package kihumba_project5;

/**
 * A class that represents a binary search tree
 *
 */

public class BinarySearchTree<E extends Comparable<E>> {
	
	// Class Node is defined as an inner class
	private static class Node <E extends Comparable <E>> {
		
		// data stored in the node
		private E data;
		
		// reference to the root of the left and right subtrees
		private Node<E> left;
		private Node<E> right;
		
		/**
         * Construct a node with the given data value
         * @param item - The data value 
         */
		public Node(E item) {
			data = item;
			left = right = null;
		}
		
		/** 
		 * Return a string representing the node
		 * @param  None  
		 * @return a string representing the data stored in the node  	
		 */
		public String toString () {
			return data.toString();
		}

		public int compareTo(Node<E> item) {
			if (data.compareTo(item.data) < 0)
				return -1;
			else if (data.compareTo(item.data) > 0)
				return 1;
			return 0;
		}
	}
	
	//data member
	private Node<E> root;
	
	
	/**
     * Construct an empty binary search tree 
     * @param none
     */
	public BinarySearchTree () {
		root = null;
	}
	
	/**
	 *This method gets the root of the binary tree 
	 * 
	 * @return the data in the root node
	 */
	public E getRoot() {
		if (root == null)
			return null;
		return root.data;
	}
	
	/**
	 * This method finds and returns any node 
	 * 
	 * @param item is the pair (key-address)
	 * @return the node 
	 */
	public Node<E> search (Node<E> item){
		return search(root, item);
	}
	
	public Node<E> search(Node<E> current , Node<E> item) {
		if (current == null)
			return null;
		int result = current.compareTo(item);
		if (result == 0)
			return current;
		else if (result < 0)
			return search (current.right, item);
		else
			return search (current.left, item);
	}
	
	/**
	 * This method finds and returns any pair 
	 * 
	 * @param item is the pair 
	 * @return the pair if found
	 */
	public E searchPair (E item){
		return searchPair(root, item);
	}
	
	public E searchPair (Node<E> current , E item) {
		if (current == null)
			return null;
		int result = current.data.compareTo(item);
		if (result == 0)
			return current.data;
		else if (result < 0)
			return searchPair (current.right, item);
		else
			return searchPair (current.left, item);
	}
	
	/**
	 * This method gets the left node of any node 
	 * in the tree
	 * @param curr 
	 * @return the pair if any
	 */
	public Node<E> getLeft(Node<E> curr) {
		Node<E> myNode = search(curr);
		if (myNode.left == null)
			return null;
		return myNode.left;
	}
	
	/**
	 * This method gets the right node of any node 
	 * in the tree
	 * @param pair 
	 * @return the pair if any
	 */
	public Node<E> getRight(Node<E> pair) {
		Node<E> myNode = search(pair);
		if (myNode.right == null)
			return null;
		return myNode.right;
	}
	

	/** 
	 * Search for an item in the tree
	 * @param  item  the target value
	 * @return item if it is in the tree, and null otherwise  
	 */
	public E find (E item) {
		Node<E> node = find (root, item);
		return node== null? null:node.data;
	}
	
	/** 
	 * Search for an item in the tree rooted at current
	 * @param  current  the current root
	 * @param  item  the target value
	 * @return Reference to the node that holds the item, or null  
	 */
	private Node<E> find (Node<E> current , E item) {
		if (current == null)
			return null;
		int result = current.data.compareTo(item);
		if (result == 0)
			return current;
		else if (result < 0)
			return find (current.right, item);
		else
			return find (current.left, item);
	}
	
	/** 
	 * Insert an item to the tree
	 * @param  item  the value to be inserted 
	 * @return none 
	 */
	public void add(E item) {
		
			root =  add (root, item);
	}
	
	/** 
	 * Insert an item to the tree rooted at current
	 * @param  current  the current root
	 * @param  item  the value to be inserted 
	 * @return reference to the node that was inserted 
	 */
	private Node<E> add (Node <E>current , E item) {
		if (current == null) 
			current = new Node<>(item);
		else {
            int result = current.data.compareTo(item);
            if (result < 0)
                current.right =  add (current.right, item);
            else if (result > 0)
                current.left =  add (current.left, item);
		}
		return current;
	}
	 
	/** 
	 * Return the smallest value in the tree 
	 * @param  none
	 * @return the smallest value 
	 */
	public E min () {
		return min(root);
	}

	/** 
	 * Return the smallest value in the tree  
	 * @param  current the current root
	 * @return the smallest value
	 */
	private E min (Node<E> current) {
		if (current.left == null)
			return current.data;
		else 
			return min(current.left);
	}
	
	/** 
	 * Delete a given item from the tree 
	 * @param  item the item to be deleted
	 * @return none
	 */
	public void delete (E item) {
		root = delete (root,item);
	}
	
	/** 
	 * Delete a given item from the tree 
	 * @param  current the current root
	 * @param  item the item to be deleted
	 * @return a reference to a node 
	 */
	private Node<E> delete(Node<E> current , E item) {
		if (current != null) {
			int result = current.data.compareTo(item);
			if (result < 0)
				current.right =  delete (current.right, item);
			else if (result > 0)
				current.left =  delete (current.left, item);	
			else {    // find it
				if (current.left == null)    // current has 1 child
					current = current.right;
				else if (current.right == null)  
					current = current.left;
				else {   // current has two children
					E replace = min(current.right);
					current.data = replace;
					current.right = delete(current.right, replace);	
				}
			}
		}
		return current;
	}
	
	/** 
	 * replace the occurrence oldItem with newItem
	 * @param  oldItem  the object being replaced
	 * @param  newItem  the replacement object
	 * @return None 
	 */
	public void replace (E oldItem, E newItem) {
		Node <E> current = find(root,oldItem);
		if (current != null)
			current.data = newItem;
	}
	
	/** 
	 * Traverse the tree using preorder traversal 
	 * @param  none
	 * @return none 
	 */
	public void preorder() {
		System.out.println();
		preorder (root, 1);
	}
	
	/** 
	 * Traverse the tree using preorder traversal 
	 * @param  current the current root
	 * @param  level the level of the current node
	 * @return none 
	 */
	private void preorder (Node<E> current, int level) {
		if (current != null) {
			for (int i = 1; i  < level; i++ )
				System.out.print("\t");
			System.out.println(current);
			preorder(current.left, level+1);
			preorder(current.right, level+1);
		}
	}
	
	/**
	 * this method print the nodes of a tree level by level
	 * by using a queue
	 */
	public void breadthFirst() {
		QueueSLL<Node<E>> temp = new QueueSLL<>();
		if (root != null) {
			temp.offer(root);
			temp.offer(null);
			while (!temp.empty()) {
				Node<E> curr = temp.remove();
				if (curr == null) {
					if (!temp.empty()) {
						temp.offer(null);
						System.out.println();
					}
				}
				else {
					Node<E> leftNode = getLeft(curr);
					Node<E> rightNode = getRight(curr);
					if (leftNode != null)
						temp.offer(leftNode);
					if (rightNode != null)
						temp.offer(rightNode);
					System.out.print(curr);
				}
			}
		}
	}
	
	/**
	 * this method print the nodes of a tree on one line
	 * by using a queue
	 * 
	 */
	public void sameLine() {
		QueueSLL<Node<E>> temp = new QueueSLL<>();
		if (root != null) {
			temp.offer(root);
			temp.offer(null);
			while (!temp.empty()) {
				Node<E> curr = temp.remove();
				if (curr == null) {
					if (!temp.empty()) {
						temp.offer(null);
						System.out.print(" ");
					}
				}
				else {
					Node<E> leftNode = getLeft(curr);
					Node<E> rightNode = getRight(curr);
					if (leftNode != null)
						temp.offer(leftNode);
					if (rightNode != null)
						temp.offer(rightNode);
					System.out.print(curr);
				}
			}
		}
	}
}

