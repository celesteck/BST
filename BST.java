import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

/**
 * BSt.java implements the DefaultMap<K,V> interface. The methods in this
 * file are used to develop a BST that is reflective of an in-order traversal.
 * This returns the keys in a sorted manner that increases. 
 * @author Celeste Keyes
 */


/**
 * This initializes the BST constructor by referring to the nodes. Specifically,
 *  root refers to the head or top of the node. It initializes the size of the BST, and 
 *  initializes the comparable which allows the keys to be compared.
 *  
 * of the tree. 
 * @param <K> The type of the keys of this BST. They need to be comparable by nature of the BST
 * "K extends Comparable" means that BST will only compile with classes that implement Comparable
 * interface. This is because our BST sorts entries by key. Therefore keys must be comparable.
 * @param <V> The type of the values of this BST. 
 */
public class BST<K extends Comparable<? super K>, V> implements DefaultMap<K, V> {
	
	
	private static final int Node = 0;
	Node<K, V> root;
	BST() {this.root = null;}
	BST(Node <K,V> root) {this.root = root;}
	int size = 0;
	Comparable<K> comparator;
	
	
	/**
	 * This is a helper method that is recursive. It checks if the key is
	 * already in the BST by using the compareTo() method. If it is not already
	 * in the BST it creates a new node.
	 * 
	 * @param node 
	 * @param key
	 * @param value
	 * @return new Node with key value pair, for a new key
	 * @return existing node if key is there
	 * @throws IllegalArgumentException if the key is null
	 */
	
	private Node<K,V> recursivePut(Node<K,V> node, K key, V value) {
		if (key == null) { throw new IllegalArgumentException("Null Key");}
		if(node == null) {
			this.size++;
			return new Node <K,V> (key, value, null, null);
		}
		int compared = node.key.compareTo(key);
		if (compared < 0) {
			node.right = recursivePut(node.right, key, value);
			
			}
		else if (compared > 0) {
			node.left = recursivePut(node.left, key, value);
		}
		return node;
	}
	
	/**
	 * Adds the specified key, value pair to this DefaultMap
	 * Note: duplicate keys are not allowed
	 * 
	 * @return true if the key value pair was added to this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	
	@Override
	public boolean put(K key, V value) throws IllegalArgumentException {
		if ( key == null) { throw new IllegalArgumentException("Null Key");}
		
		int initialSize = this.keys().size();
		this.root = recursivePut(this.root, key, value);
		
		if (initialSize == this.keys().size()) {
			return false;
		}
		return true;
	}

	/**
	 * This is a helped method that finds the key value pair and 
	 * changes the nodes content to the the key value parameters
	 * 
	 * @param node Node to recursively check
	 * @return node updated node 
	 * @throws NoSuchElementException if key is not in BST
	 */	
	private Node<K,V> replace(Node<K,V> node, K key, V value) throws NoSuchElementException{
		if (key == null) {
			throw new IllegalArgumentException("Null Key");
		}
		if(node == null) {
			throw new NoSuchElementException("Key Not Found");
		}
		
		int comparable = node.key.compareTo(key);
		if (comparable < 0) {
			node.right = replace(node.right, key, value);
			return node;
		}
		else if(comparable > 0) {
			node.left = replace(node.left, key, value);
			return node;
		}
		else {
			node.value = value;
			return node;
		}
	}
	
	/**
	 * Replaces the value that maps to the key if it is present
	 * @param key The key whose mapped value is being replaced
	 * @param newValue The value to replace the existing value with
	 * @return true if the key was in this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public boolean replace(K key, V newValue) throws IllegalArgumentException {
		
		if(key == null) {throw new IllegalArgumentException() ; }
		if ( keys().contains(key)) {
			this.root = replace(this.root, key, newValue);
			return true;
		}
		return false;
	}
	
	/**
	 * This is a helper method that looks at the 
	 * furthest left node to find the smallest node
	 * 
	 * @param root starting node
	 * @return smallest key
	 */
	Node <K,V> nodewithMin(Node<K,V> root){
		Node<K,V> min = root;
		while (root.left != null) {
			min = root.left;
			root = root.left;
		}
		return min;
	}

	/**
	 * This is a helper method that checks for the largest key after the
	 * specified one is removed
	 * 
	 * @param node Node to see if there are children or if it is empty
	 * @return the node that is getting removed
	 */
	public Node<K, V> remove(Node<K,V> node, K keyToRemove) {
	
		//empty tree
		if(node == null) {
			return null;
		}
		
		int comparedValues = node.key.compareTo(keyToRemove);
		if ( comparedValues > 0) {
			node.left = remove(node.left, keyToRemove);
		}
		else if (comparedValues < 0) {
			node.right = remove(node.right, keyToRemove);
		}
		else {
			this.size--;
			//one or no kids
			if (node.left == null) {
				return node.right;
			}
			else if (node.right == null) {
				return node.left;
			}
			
			//two kids
			Node<K,V> nextLargest = nodewithMin(node.right);
			node.key = nextLargest.key;
			node.value = nextLargest.value;
			this.size++;
			
			//remove the next largest node
			node.right = remove(node.right, node.key);
		}
		
		return node;
	}
	
	/**
	 * Remove the entry corresponding to the given key
	 * 
	 * @return true if an entry for the given key was removed
	 * @throws IllegalArgumentException if the key is null
	 */
	
	@Override
    public boolean remove(K key) throws IllegalArgumentException {
        if (key == null) { throw new IllegalArgumentException("Null Key"); }

        if (keys().contains(key)) {
			this.root = remove(this.root, key);
			return true;
		}
		return false;
    }
	
	

	/**
	 * This is a helper method that looks for the key to see if it already exists, 
	 * if it does, it replaces its content. If it doesnt, it adds it to the BST
	 * @param node with key value pair information

	 * @return new Node with the specified key value pair content
	 * @throws IllegalArgumentException if key is null
	 */
	Node <K,V> set(Node<K,V> node, K key, V value){
		if (key == null) {throw new IllegalArgumentException("Null Key");}
		if (node == null) {
			this.size++;
			return new Node<K,V>(key, value, null,null);
		}
		if (node.key.compareTo(key) < 0) {
			node.right = this.set(node.right,  key,  value);
			return node;
		}
		else if (node.key.compareTo(key) > 0) {
			node.left = this.set(node.left,  key,  value);
			return node;
		}
		else {
			node.value = value;
			return node;
		}
	}
	
	/**
	 * Adds the key, value pair to this DefaultMap if it is not present,
	 * otherwise, replaces the value with the given value
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public void set(K key, V value) throws IllegalArgumentException {
		if (key == null) { throw new IllegalArgumentException("Null Key");}
		this.root = this.set(this.root, key, value);
		
	}
	
	/**
	 * Helper method that finds the value of the key that we are looking for
	 * 
	 * @param node to check if there is a key that matches
	 * @return value of key in the BST
	 * @return null if the key is not found 
	 * @throws NoSuchElementException
	 */
	private V get(Node<K,V> node, K k) throws NoSuchElementException{
		if (node == null) {
			return null;
		}
		if (node.key.equals(k)) {
			return node.value;
		}
		
		int comparable = node.key.compareTo(k);
		if (comparable > 0) {
			return get(node.left, k);
		}
		else {
			return get(node.right, k);
		}
	}

	/**
	 * @return the value corresponding to the specified key
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public V get(K key) throws IllegalArgumentException {
		if (key == null) {
			throw new IllegalArgumentException("Null Key");
		}
		
		return this.get(this.root, key);
	}

	/**
	 * 
	 * @return The number of (key, value) pairs in this DefaultMap
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * 
	 * @return true iff this.size() == 0 is true
	 */
	@Override
	public boolean isEmpty() {
		if (this.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method that uses compareTo in orderr to see if the 
	 * key is in the BST or not
	 * 
	 * @param node at which specified key is being compared
	 * @return true if key was found in BST, false otherwise
	 */
	boolean containsKey(Node <K,V> node, K k) {
		if(node == null) {
			return false;
		}
		if (node.key.compareTo(k) < 0) {
			return containsKey(node.right, k);
		}
		else if ( node.key.compareTo(k) > 0) {
			return containsKey(node.left, k);
		}
		else {
			return true;
		}
	}

	/**
	 * @return true if the specified key is in this DefaultMap
	 * @throws IllegalArgumentException if the key is null
	 */
	@Override
	public boolean containsKey(K key) throws IllegalArgumentException {
		
		if (key == null) {
			throw new IllegalArgumentException("Null Key");
		}
		return this.containsKey(this.root, key);
	}

	// Keys must be in ascending sorted order
	// You CANNOT use Collections.sort() or any other sorting implementations
	// You must do inorder traversal of the tree
	@Override
	public List<K> keys() {
		
		List<K> bstList = new ArrayList<>();
		
		toList(bstList, this.root);
		return bstList;
	}
		
	/**
	 * 
	 * @return an array containing the keys of this DefaultMap. If this DefaultMap is 
	 * empty, returns array of length zero. 
	 */
	public List<K> toList(List<K> holdList, Node<K,V> n){
		if (n != null) {
			toList(holdList, n.left);
			holdList.add(n.getKey());
			toList(holdList, n.right);
		}
		return holdList;
	}

	/**
	 * This class is a Node class that has the contents of the key value
	 * pair stored. It also takes note of the children of the specified node.
	 * @author Celeste Keyes
	 *
	 * @param <K> Keys in the BST that need to be comparable
	 * @param <V> Values in the BST
	 */
	private static class Node<K extends Comparable<? super K>, V> 
								implements DefaultMap.Entry<K, V> {
		
		K key;
		V value;
		Node <K, V> left, right;
	
		public Node( K key, V value, Node <K, V> left, Node<K, V> right) {
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}
		

		@Override
		public K getKey() {
			return this.key;
		}

		@Override
		public V getValue() {
			return this.value;
		}
		@Override
		public void setValue(V value) {
			this.value = value;
		}
		
	}
	/**
	   * Implements the set method to add the kv pairs to an array
	   * @param BStex the BST map that the key and values are to be added to
	   * @param keys variable with the entries that are going to be added
	   */
	  public void addtoArray(BST<String, Integer> BSTex, String[] keys) {
		  for(int i = 0; i < keys.length; i++) {
			  BSTex.set(keys[i], i+1);
		  }
	  }

	 
}