import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * FileSystem.java crates a FileSystem class that allows two BSTs to be manipulated 
 * via the use of the methods within this file. It parses text and retuns the output
 * of the BST
 * @author Celeste Keyes
 *
 */

/**
 * This class initializes a BST that holds the information about the names from the
 * file and another BST that holds the information about the dates from the file.
 * @author celes
 *
 */
public class FileSystem {

    BST<String, FileData> nameTree;
    BST<String, ArrayList<FileData>> dateTree;
    
   /*
    * This creates the BSTs for the tree that holds the name and date data
    */
    public FileSystem() {
    	this.nameTree = new BST<>();
    	this.dateTree = new BST<>();
    }


   /*
    * This creates the BSTs for the name and date. It also reads in the file
    * and assigns each aspect to its respective category.
    */
    public FileSystem(String inputFile) {
    	
    	this.nameTree = new BST<>();
    	this.dateTree = new BST<>();
    	
        try {
            File file = new File(inputFile);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(", ");
                
                String name = data[0];
                String dir = data[1];
                String date = data[2];
                add(name, dir, date);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);

        }
    }


    /*
     * This uses a comparator to check whether or not the name in the file is the same
     * if it is, it adds the files in the nameTree into the dateTree. Otherwise it adds
     * the name to the nameTree and then the file.
     * 
     * @param name a string with the name parsed from the file
     * @param dir a stringith the directory that was parse from the file
     * @param date a string with the date from the file
     * 
     * @return void
     */
    
    public void add(String name, String dir, String date) {
    	if (name == null || dir == null || date == null) {
    		return;
    	}
    	FileData file = new FileData(name, dir, date);

    	//files added to nameTree will be added to dateTree
       	//if they do have the same name
    	if (this.nameTree.containsKey(name)) {
    		//if newer date
    		if (date.compareTo(this.nameTree.get(name).lastModifiedDate) > 0) {
    			
    			FileData ToBeRemoved = this.nameTree.get(name);
    			this.nameTree.replace(name, file);

    			int removed = this.dateTree.get(ToBeRemoved.lastModifiedDate).indexOf(ToBeRemoved);
    			this.dateTree.get(ToBeRemoved.lastModifiedDate).remove(removed);
    			
    			
    			//removes kv is date has no more files
    			if (this.dateTree.get(ToBeRemoved.lastModifiedDate).size() == 0) {
    				this.dateTree.remove(date);
    			}
    			
    			//if date exists, add
    			if (this.dateTree.containsKey(date)) {
    				this.dateTree.get(date).add(file);
    				
    			}
    			// new date
    			else {
    				ArrayList<FileData> filesWithDate = new ArrayList<>();
    				filesWithDate.add(file);
    				this.dateTree.set(date, filesWithDate);
    			}
    				
    		}
    	}
    	
    	
    	else {	//the names aren't the same
    		
    		//add to name tree
    		this.nameTree.put(name,  file);
    		
    		//check if the date is in the list then add file
    		if ( this.dateTree.containsKey(date)) {
    			this.dateTree.get(date).add(file);
    		}
    		
    		// if not make a file for it and add date and files
    		else {
    			ArrayList<FileData> filesWithDate = new ArrayList<>();
				filesWithDate.add(file);
				this.dateTree.set(date, filesWithDate);
    		}
    	}
    }

    /**
	 * Searches for the name of the file using the date
	 * by iterating through the dateTree to get the name
	 *
	 * @param date  a string with the date from the file
	 * @return the list of names from the date
	 */
    public ArrayList<String> findFileNamesByDate(String date) {
    	if (date == null) {
    		return null;
    	}
    	
    	ArrayList<String> dateList = new ArrayList<>();
    	for (int i = 0; i < this.dateTree.get(date).size(); i++) {
    		
    		//check for the same date then add
    		if (this.dateTree.get(date).get(i).lastModifiedDate.equals(date)) {
    			dateList.add(this.dateTree.get(date).get(i).name);
    		}
    	}
		return dateList;
    }

    /**
	 * Goes through the file to add values with corresponding
	 * dates and names to its respective BST
	 *
	 * @param endDate the last date to be looked at
	 * @param startDate the first date to be looked at
	 * @return a filesystem with the BSTs
	 */
    public FileSystem filter(String startDate, String endDate) {
    	
    	FileSystem fs = new FileSystem();
    	
    	BST<String, FileData> TreeName = new BST<>();
    	BST<String, ArrayList<FileData>> TreeDate = new BST<>();
    	
    	//add values to TreeName 
    	for (int i = 0; i < this.nameTree.keys().size(); i++) {
    		
    		//first date
    		int initial = this.nameTree.get(this.nameTree.keys().get(i)).lastModifiedDate.compareTo(startDate);
    		//last date
    		int fin = this.nameTree.get(this.nameTree.keys().get(i)).lastModifiedDate.compareTo(endDate);
    		
    		// check date range
    		if (initial >= 0 && fin < 0) {
    			TreeName.put(this.nameTree.keys().get(i), this.nameTree.get(this.nameTree.keys().get(i)));
    			
    		}
    	}
    	
    	//add values to TreeDate
    	for (int j = 0; j < this.dateTree.keys().size(); j ++) {
    		int initial = this.dateTree.keys().get(j).compareTo(startDate);
    		//last date
    		int fin = this.dateTree.keys().get(j).compareTo(endDate);
    		
    		//check date range
    		if (initial >= 0 && fin < 0) {
    			TreeDate.put(this.dateTree.keys().get(j), this.dateTree.get(this.dateTree.keys().get(j)));
    		}
    	}
    	
    	fs.nameTree = TreeName;
    	fs.dateTree = TreeDate;
    	
		return fs;
 
    }
    
    /**
	 * This takes in a wildcard and checks if it is in the nameTree already. 
	 * If it is, it sets
	 * the key, value pair and then adds it to the dateTree, otherwise it 
	 * adds it to thelist of dates
	 *
	 * @param wildCard a striing with the random name and date
	 * @return the file system with the BSTs
	 */
    public FileSystem filter(String wildCard) {
    	
    	FileSystem fs = new FileSystem();
    	
    	BST<String, FileData> TreeName = new BST<>();
    	BST<String, ArrayList<FileData>> TreeDate = new BST<>();
    	
    	
    	for ( int i = 0; i < this.nameTree.keys().size(); i ++) {
    		
    		//check if wildCard is in here
    		if(this.nameTree.keys().get(i).contains(wildCard)) {
    			
    			//set the kv pair
    			TreeName.set(this.nameTree.keys().get(i), this.nameTree.get(this.nameTree.keys().get(i)));
    			
    			//add to dateTree
    	    	FileData dateTreeFile = this.nameTree.get(this.nameTree.keys().get(i));
    	    	
    	    	// check to see if its within the dates
    	    	if (TreeDate.containsKey(this.nameTree.get(this.nameTree.keys().get(i)).lastModifiedDate)) {
    	    	
    	    		TreeDate.get(this.nameTree.get(this.nameTree.keys().get(i)).lastModifiedDate).add(dateTreeFile);
    	    	}
    	    	else {
    	    		//otherwise add to list of dates
    	    		ArrayList<FileData> filesWithDate = new ArrayList<>();
    	    		filesWithDate.add(dateTreeFile);
    	    		TreeDate.set(this.nameTree.get(this.nameTree.keys().get(i)).lastModifiedDate, filesWithDate);
    	    	}
    		}	
    	}
    	
    	
    	fs.nameTree = TreeName;
    	fs.dateTree = TreeDate;
		return fs;

    }
    
    /**
	 * This goes through the name tree and adds the names to an
	 * arraylist
	 *
	 * @param 
	 * @return the list of names
	 */
    public List<String> outputNameTree(){
    
    	List<String> output = new ArrayList<>();
    	
    	for (int i = 0; i < this.nameTree.keys().size(); i ++) {
    		output.add(this.nameTree.keys().get(i) + ": " + this.nameTree.get(this.nameTree.keys().get(i)).toString());
    	}
		return output;

    }
    
    /**
	 * This goes through the name tree and adds the dates to an
	 * arraylist
	 *
	 * @param 
	 * @return the list of dates
	 */
    public List<String> outputDateTree(){
    	
    	List<String> output = new ArrayList<>();
    	
    	for (int i = this.dateTree.keys().size() - 1; i >= 0; i--) {
    		
    		for ( int j = this.dateTree.get(this.dateTree.keys().get(i)).size() - 1; j >= 0; j --) {
        		
        		String str = this.dateTree.get(this.dateTree.keys().get(i)).get(j).toString();
        		
        		output.add(this.dateTree.keys().get(i) + ": " + str);
        	}
    	}
    	return output;
    }
    

}

