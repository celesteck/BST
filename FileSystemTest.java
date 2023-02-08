import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class FileSystemTest {

	@Test
	public void addTest() {
		
		FileSystem fs = new FileSystem();
		
		fs.add("sample1.txt", "/home", "2022/06/01");
		fs.add("sample2.txt", "/home", "2022/06/02");
		
		assertEquals("2022/06/01", fs.nameTree.get("sample1.txt").lastModifiedDate);
	}
	
	@Test
	public void addTest2() {
		FileSystem fs = new FileSystem();
		
		fs.add("sample1.txt", "/home", "2022/06/04");
		fs.add("sample2.txt", "/home", "2020/06/04");
		fs.add("sample3.txt", "/Documents", "2022/09/01");
		fs.add("sample4.txt", "/User", "2022/12/21");
		
		assertEquals(4, fs.nameTree.size());
		assertEquals(4, fs.dateTree.size());
	}
	
	@Test
	public void findFileNamesByDateMethodtest() {
		FileSystem files = new FileSystem("input.txt");
		assertEquals(2, files.findFileNamesByDate("2021/02/01").size());
	}
	
	@Test
	public void findFileNamesByDateTest2() {
		FileSystem fs = new FileSystem();
		fs.add("sample1.txt", "/home", "2022/06/04");
		fs.add("sample2.txt", "/home", "2022/06/04");
		fs.add("sample3.txt", "/Documents", "2022/09/01");
		fs.add("sample4.txt", "/User", "2022/12/21");
		
		
		ArrayList<String> filesWithDate = new ArrayList<>();
		filesWithDate.add("sample1.txt");
		filesWithDate.add("sample2.txt");
		
		assertEquals(true, fs.findFileNamesByDate("2022/06/04").equals(filesWithDate));
	}
	
	@Test
	public void dateFilterTest1() {
		FileSystem fs = new FileSystem();
		
		fs.add("sample1.txt", "/home", "2020/06/01");
		fs.add("sample2.txt", "/home", "2020/10/05");
		fs.add("sample3.txt", "/Documents", "2022/10/06");
		fs.add("sample4.txt", "/User", "2022/06/01");

		FileSystem expected = new FileSystem();
		
		expected.add("sample1.txt", "/home", "2020/06/01");
		expected.add("sample2.txt", "/home", "2020/10/05");
		
		//System.out.println(fs.filter("2020/06/01", "2022/06/01").outputNameTree());
		
		//checking if filter prints expected nameTree
		assertEquals(true, expected.outputNameTree().equals(fs.filter("2020/06/01", "2022/06/01").outputNameTree()));
	}
	
	@Test
	public void dateFilterTest2() {
		FileSystem fs = new FileSystem();
		
		fs.add("sample1.txt", "/home", "2020/06/01");
		fs.add("sample2.txt", "/home", "2020/10/05");
		fs.add("sample3.txt", "/Documents", "2022/10/06");
		fs.add("sample4.txt", "/User", "2022/06/01");

		assertEquals(1, fs.filter("2020/11/22", "2022/10/05").dateTree.size());
	}
	
	@Test
	public void wildcardMethodTest() {
		FileSystem files = new FileSystem("input.txt");
		FileSystem toFilter = new FileSystem("input.txt");
		toFilter.filter("mySample");
		assertEquals(files.nameTree.keys(), toFilter.nameTree.keys());
		assertEquals(files.dateTree.keys(), toFilter.dateTree.keys());
	}
	
	@Test
	public void wildcardMethodTest2() {
		FileSystem fs = new FileSystem();
		
		fs.add("sample1.txt", "/home", "2020/06/01");
		fs.add("sample2.txt", "/home", "2020/10/05");
		fs.add("sample3.txt", "/Documents", "2022/10/06");
		fs.add("sample4.txt", "/User", "2022/06/01");
	
		assertEquals(4, fs.filter("a").nameTree.size());
	}
	
	@Test
	public void nameOutputTest1() {
		FileSystem files = new FileSystem("input.txt");
		
		ArrayList<String> expected = new ArrayList<>();
		
		expected.add("mySample.txt: {Name: " + "mySample.txt" + ", Directory: " + "/home" + ", Modified Date: " + "2021/02/01" + "}");
		expected.add("mySample1.txt: {Name: " + "mySample1.txt" + ", Directory: " + "/root" + ", Modified Date: " + "2021/02/01" + "}");
		expected.add("mySample2.txt: {Name: " + "mySample2.txt" + ", Directory: " + "/user" + ", Modified Date: " + "2021/02/06" + "}");
		
	
		//System.out.println(files.outputNameTree());
		assertEquals(true, expected.equals(files.outputNameTree()));
	}
	
	@Test
	public void nameOutputTest2() {
		FileSystem fs = new FileSystem();
		
		fs.add("sample1.txt", "/home", "2020/06/01");
		fs.add("sample2.txt", "/home", "2020/10/05");
		fs.add("sample3.txt", "/Documents", "2022/10/06");

		
		ArrayList<String> expected = new ArrayList<>();
		
		expected.add("sample1.txt: {Name: " + "sample1.txt" + ", Directory: " + "/home" + ", Modified Date: " + "2020/06/01" + "}");
		expected.add("sample2.txt: {Name: " + "sample2.txt" + ", Directory: " + "/home" + ", Modified Date: " + "2020/10/05" + "}");
		expected.add("sample3.txt: {Name: " + "sample3.txt" + ", Directory: " + "/Documents" + ", Modified Date: " + "2022/10/06" + "}");


		
		//System.out.println(fs.outputNameTree());
		//System.out.println(expected);
		assertEquals(true, expected.equals(fs.outputNameTree()));
	}
	
	@Test
	public void dateOutputTest1() {
		FileSystem files = new FileSystem("input.txt");
		
		ArrayList<String> expected = new ArrayList<>();
		
		expected.add("2021/02/06: {Name: " + "mySample2.txt" + ", Directory: " + "/user" + ", Modified Date: " + "2021/02/06" + "}");
		expected.add("2021/02/01: {Name: " + "mySample1.txt" + ", Directory: " + "/root" + ", Modified Date: " + "2021/02/01" + "}");
		expected.add("2021/02/01: {Name: " + "mySample.txt" + ", Directory: " + "/home" + ", Modified Date: " + "2021/02/01" + "}");
		
		//System.out.println(files.outputDateTree());
		assertEquals(true, expected.equals(files.outputDateTree()));
	}
	
	@Test
	public void dateOutputTest2() {
		FileSystem files = new FileSystem();
		
		files.add("first.txt", "/home", "2022/02/01");
		files.add("second.txt", "/home", "2020/01/55");
		files.add("third.txt", "/Documents", "2022/09/03");
		files.add("first.txt", "/User", "2022/02/01");
		files.add("fourth.txt", "/Downloads", "2022/02/01");
		files.add("fifth.txt", "/home", "2007/11/22");
		files.add("first.txt", "/root", "2022/09/03");
		
		ArrayList<String> expected = new ArrayList<>();
		
		expected.add("2022/09/03: {Name: " + "first.txt" + ", Directory: " + "/root" + ", Modified Date: " + "2022/09/03" + "}");
		expected.add("2022/09/03: {Name: " + "third.txt" + ", Directory: " + "/Documents" + ", Modified Date: " + "2022/09/03" + "}");
		expected.add("2022/02/01: {Name: " + "fourth.txt" + ", Directory: " + "/Downloads" + ", Modified Date: " + "2022/02/01" + "}");
		expected.add("2020/01/55: {Name: " + "second.txt" + ", Directory: " + "/home" + ", Modified Date: " + "2020/01/55" + "}");
		expected.add("2007/11/22: {Name: " + "fifth.txt" + ", Directory: " + "/home" + ", Modified Date: " + "2007/11/22" + "}");
		assertEquals(true, expected.equals(files.outputDateTree()));
	}
	
}