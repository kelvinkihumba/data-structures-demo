/**
 * A partial implementation of a hash table using chaining (binary search trees)
 *
 *  @author COSC 311, Fall '22
 *  @version (10-27-22)
 *  
 *  Note: You must add methods get, remove, and find to this class. Also, you must 
 *        replace the call to the preOrder method with a call to the levelOrder method
 *        which you will add to the class BST.
 */

package kihumba_project5;

@SuppressWarnings ("unchecked")
public class Hashing <K extends Comparable <K>, V>{
	private final int SIZE= 37;
	private BinarySearchTree<Pair<K,V>> [] table;
	
	// constructor 
	public Hashing () {
		//table = (BST<Pair<E>> [])new Object [SIZE];  //*** this doesn't work!
		table = new BinarySearchTree[SIZE];
	}

	// hash the key using division  (replace this hash function whit the one given)
	public int hash(K key) {
		return (((Integer)key*(Integer)key) >>> 10) % 37;
	}
	
	// add a (key,value) pair into the hash table, make sure this method works!
	public V put (Pair<K,V> item) {
		K key = item.getKey();
		int index = hash(key);
		if (index < 0) index += table.length;
		if (table[index] == null) table[index] = new BinarySearchTree<Pair<K,V>> ();
		Pair<K,V> value = table[index].searchPair(item);
		if (value == null) {
			table[index].add(item);
			return null;
		}
		table[index].replace(value,item);
		return value.getValue();
	}	
	
	// print the hash table using preorder traversal of BSTs
	public void print() {
		for (int i=0;i<SIZE;i++)
			if (table[i] != null) {
				System.out.print(i + ": ");
				table[i].preorder();
			}
	}
	
	/**
	 * 
	 * this method prints a binary search tree level by level
	 * @param start is the starting index
	 * @param end is the ending index
	 */
	public void printLevels(int start, int end) {
		for (int i=start;i<end+1;i++)
			if (table[i] != null) {
				if (table[i].getRoot() != null) {
					System.out.print(i + ": ");
					table[i].breadthFirst();
					System.out.println();
				}
			}
	}
	
	/**
	 * 
	 * this method prints a binary search tree on one line
	 * @param start
	 * @param end
	 */
	public void printLevels2(int start, int end) {
		for (int i=start;i<end+1;i++)
			if (table[i] != null) {
				if (table[i].getRoot() != null) {
					System.out.print(i + ": ");
					table[i].sameLine();
					System.out.println();
				}
			}
	}
	
	/**
	 * 
	 * this method finds a pair in a binary search tree
	 * @param pair is the pair to find
	 * @return the pair if found
	 */
	public Pair<K,V> find(Pair<K,V> pair) {
		Pair<K,V> myPair = new Pair<>();
		for (int i = 0; i < 37; i++) {
			if (table[i] != null) {
				myPair = table[i].searchPair(pair);
				if (myPair != null) return myPair;
			}
		}
		return myPair; 
	}
	
	/**
	 * deletes a pair from a binary search tree
	 * @param pair is the pair to be deleted
	 */
	public void delete(Pair<K,V> pair) {
		Pair<K,V> myPair = new Pair<>();
		for (int i = 0; i < 37; i++) {
			if (table[i] != null) {
				myPair = table[i].searchPair(pair);
				if (myPair != null) table[i].delete(myPair);
			}
		}
	}

}
