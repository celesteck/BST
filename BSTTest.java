import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.*;

public class BSTTest {
	
	//general remove
	@Test
	public void removeMethodTest() {
		BST<String,Integer> bst = new BST<>();
		String[] testvals = { "a", "b", "c", "d", "e", "f"};
	    bst.addtoArray(bst, testvals);
	    bst.remove("f");
	
	    List<String> expected = new ArrayList<>();
	    expected.add("a");
	    expected.add("b");
	    expected.add("c");
	    expected.add("d");
	    expected.add("e");

	    //System.out.println(bst.keys());
	
	    assertEquals(expected, bst.keys());
	}
	
	//remove non-existent element
	@Test
	public void removeMethodTest2() {
		BST<String,Integer> bst = new BST<>();
		String[] testvals = { "a", "b", "c", "d", "e", "f"};
	    bst.addtoArray(bst, testvals);
	    bst.remove("x");
	
	    List<String> expected = new ArrayList<>();
	    expected.add("a");
	    expected.add("b");
	    expected.add("c");
	    expected.add("d");
	    expected.add("e");
	    expected.add("f");

	    //System.out.println(bst.keys());
	
	    assertEquals(expected, bst.keys());
	}
	
	//general set
	@Test
	public void setMethodTest() {
		BST<String,Integer> bst = new BST<>();
		String[] testvals = { "a", "b", "c", "d", "e", "f"};
	    bst.addtoArray(bst, testvals);
	    bst.set("g", 2000);
	    bst.set("a", 1);
	   
	    List<String> expected = new ArrayList<>();
	    expected.add("a");
	    expected.add("b");
	    expected.add("c");
	    expected.add("d");
	    expected.add("e");
	    expected.add("g");
	    
	}
	
	
	//reset val twice
	@Test
	public void setMethodTest2() {
		BST<String,Integer> bst = new BST<>();
		String[] testvals = { "a", "b", "c", "d", "e", "f"};
	    bst.addtoArray(bst, testvals);
	    bst.set("g", 2000);
	    bst.set("a", 1);
	    bst.set("a", 17);
	   
	    List<String> expected = new ArrayList<>();
	    expected.add("a");
	    expected.add("b");
	    expected.add("c");
	    expected.add("d");
	    expected.add("e");
	    expected.add("g");
	}
	
	//general get
	@Test
	public void getMethodTest() {
		BST<String,Integer> bst = new BST<>();
		
	    bst.set("r", 7);   
	
	    Integer expectedrVal = 7;
	
	    assertEquals(expectedrVal, bst.get("r"));
	}
	
	
	//change val twice
	@Test
	public void getMethodTest1() {
		BST<String,Integer> bst = new BST<>();
		String[] testvals = { "a", "b", "c", "d", "e", "f"};
	    bst.addtoArray(bst, testvals);
	    bst.set("g", 2000);
	    bst.set("a", 1);
	    bst.set("a", 17);
	    
	    assertEquals(null, bst.get("x"));
	}
	
	//general replace
	@Test
	public void replaceMethodTest() {
		BST<String,Integer> bst = new BST<>();
		String[] testVals = { "r", "s", "t", "u"};
		
	    bst.addtoArray(bst, testVals);
	    bst.replace("r", 7);
	    Integer expectedrVal = 7;
	    
	    assertEquals(expectedrVal, bst.get("r"));
	}
	
	//replace a value not in tree
	@Test
	public void replaceMethodTest2() {
		BST<String,Integer> bst = new BST<>();
		String[] testVals = { "r", "s", "t", "u"};
		
	    bst.addtoArray(bst, testVals);
	    bst.replace("a", 7);
	    
	    assertEquals(null, bst.get("a"));
	}
	
	//true contains
	@Test
	public void containsKeyMethodTest() {
		BST<String,Integer> bst = new BST<>();
		String[] testvals = { "t", "u", "v"};
		
	    bst.addtoArray(bst, testvals);
	    
	    assertEquals(true, bst.containsKey("v"));
	}
	
	//false contains
	@Test
	public void containsKeyMethod1() {
		BST<String,Integer> bst = new BST<>();
		String[] testvals = { "t", "u", "v"};
		
	    bst.addtoArray(bst, testvals);
	    
	    assertEquals(false, bst.containsKey("m"));
	}
	
	//general size
	@Test
	public void sizeMethodTest() {
		BST<String,Integer> bst = new BST<>();

		String[] testvals = { "a", "b", "c", "d", "e", "f", "g"};
	    bst.addtoArray(bst, testvals);
	
	    assertEquals(7, bst.size());
	}
	
	//empty size check
	@Test
	public void sizeMethodTest2() {
		BST<String,Integer> bst = new BST<>();
		
		assertEquals(0, bst.size());
	}
	
	//general put
	@Test
	public void putMethodTest() {
		BST<String,Integer> bst = new BST<>();
		String[] testvals = { "a", "b", "c", "d", "e", "f"};
	    bst.addtoArray(bst, testvals);
	    bst.put("g", 2000);
	    
	    List<String> expected = new ArrayList<>();
	    expected.add("a");
	    expected.add("b");
	    expected.add("c");
	    expected.add("d");
	    expected.add("e");
	    expected.add("f");
	    expected.add("g");
	    
	    assertEquals(expected, bst.keys());
	
	}
	
	//key alr exists
	@Test
	public void putMethodTest2() {
		BST<String,Integer> bst = new BST<>();
		String[] testvals = { "a", "b", "c", "d", "e", "f"};
	    bst.addtoArray(bst, testvals);
	    bst.put("d", 2000);
	    
	    List<String> expected = new ArrayList<>();
	    expected.add("a");
	    expected.add("b");
	    expected.add("c");
	    expected.add("d");
	    expected.add("e");
	    expected.add("f");
	    assertEquals(expected, bst.keys());
	}
	
	//general keys
	@Test
	public void keysMethodTest() {
		BST<String,Integer> bst = new BST<>();
		
	    bst.set("f", 4); 
	    bst.set("a", 5); 
	    bst.set("r", 6); 
	    
	
	    List<String> expected = new ArrayList<>();
	    expected.add("a");
	    expected.add("f");
	    expected.add("r");
	
	    assertEquals(expected, bst.keys());
	}
	
	//duplicate keys
	@Test
	public void keysMethodTest2() {
BST<String,Integer> bst = new BST<>();
		
	    bst.set("f", 4); 
	    bst.set("a", 5); 
	    bst.set("r", 6); 
	    bst.set("f", 3);
	    
	
	    List<String> expected = new ArrayList<>();
	    expected.add("a");
	    expected.add("f");
	    expected.add("r");
	
	    assertEquals(expected, bst.keys());
	}
	
}
