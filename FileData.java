/**
 * FileData.java is a class that has the information for th name, directory, and  
 * last modified date of a file. 
 * @author Celeste Keyes
 *
 */

/**
 * This class allows us to access the name, directory, and date data within the other
 * files because it stores it all in one as an object
 *
 */
public class FileData {

    public String name;
    public String dir;
    public String lastModifiedDate;

    /**
     * This is a constructor that initializes instances variables
     * @param name
     * @param directory
     * @param modifiedDate
     */
    public FileData(String name, String directory, String modifiedDate) {
    	this.name = name;
    	this.dir = directory;
    	this.lastModifiedDate = modifiedDate;
    }

    /**
     * @return a string of the date, directory, and last modified date of the object
     */
    public String toString() {
		return "{Name: " + this.name+ ", Directory: " + this.dir + ", Modified Date: " + this.lastModifiedDate + "}";

    }
}