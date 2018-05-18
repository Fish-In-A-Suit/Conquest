package utils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


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

}