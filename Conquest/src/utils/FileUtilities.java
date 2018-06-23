package utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FileUtilities{

    public static String loadResource(String filePath) throws Exception {
    	System.out.println("[FileUtilities.loadResource]: Loading file " + filePath + "...");
    	double start = System.nanoTime();
    	
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String lineSeparator = System.getProperty("line.separator");
        
        try (
            InputStream in = Class.forName(FileUtilities.class.getName()).getResourceAsStream(filePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            )
        {

        	while((line = bufferedReader.readLine()) != null) {
        		stringBuilder.append(line);
        		
        		while((line = bufferedReader.readLine()) != null) {
        			stringBuilder.append(lineSeparator);
        			stringBuilder.append(line);
        		}
        	}
        	bufferedReader.close();
        } 
        double end = System.nanoTime();
        System.out.println("[FileUtilities.loadResource]: It took " + (end-start) + " milliseconds (" + ((end-start)/1_000_000 +"  seconds)") + " to read file " + filePath);
        System.out.println("----------------------" + filePath + "----------------------" + "\n"
        		          + stringBuilder.toString() +
        		          "-------------------------------------------------------------");
        return stringBuilder.toString();
    }
    
    /**
     * This method reads through all of the lines in a file and returns a String array, where
     * each String element represents one line of text
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static String[] readAllLines(String fileName) throws FileNotFoundException {
    	String[] result = null;
    	try {
    		BufferedReader br = new BufferedReader(new FileReader(fileName));
        	List<String> lines = new ArrayList<>();
        	
        	String line;
            while((line = br.readLine()) != null) {
				lines.add(line);
			}
            
            result = new String[lines.size()];
            
            int i = 0;
            for(String currentLine : lines) {
            	result[i] = currentLine;
            	i++;
            }            
    	} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	if(result == null) {
    		System.err.println("[FileUtilities.readAllLines]: Couldn't read lines of file: " + fileName);
    	}
    	
    	return result;	
    }

}