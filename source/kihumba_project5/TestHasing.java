/**
 *  Topics covered: A generic class with bounded type parameter
 *  
 *  @author COSC 311, Fall '22
 *  @version (10-27-22)
*/

package kihumba_project5;

public class TestHasing {
	public static void main (String [] args) {
		Hashing <Integer, Integer> hash = new Hashing<> ();
		hash.put(new Pair<Integer,Integer>(2,5));
		hash.put(new Pair<>(39,10));
		hash.put(new Pair<>(39,15));
		hash.put(new Pair<>(10,2));
		hash.print();
		Integer value = hash.put(new Pair<>(10,5));
		System.out.println ("the old value is: " + value);
		hash.print();

	}

}
