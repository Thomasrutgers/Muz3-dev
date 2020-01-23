
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Invoke {

	public static void main(String[] args) {
		JSONObject dataIn = new JSONObject();
		
		//System.out.println("test1234");
		
		//Read file (arg1)
		
		if (args.length<2) {
			System.out.println("Error: not enough arguments");
			System.exit(1);
		}

			JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0]))
        {
            dataIn = (JSONObject) jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
        	System.out.println("Error: File not found");
        	System.exit(1);
        } catch (IOException e) {
        	System.out.println("Error: IOException");
        	System.exit(1);
        } catch (ParseException e) {
        	System.out.println("Error: ParseException");
        	System.exit(1);
        }

        //Manipulate Array
        
        JSONArray rules = (JSONArray) dataIn.get("notes");
        //JSONObject firstnote = (JSON)
		
        JSONObject dataOut = new JSONObject();
        JSONArray myArray = new JSONArray();
        myArray.add(new Integer(4));
        myArray.add(new Integer(5));
        myArray.add(new Integer(7));

        dataOut.put("naam","Thomas");
        dataOut.put("array",myArray);
        
        //Write JSON file
        try (FileWriter file = new FileWriter(args[1])) {
 
            file.write(dataOut.toJSONString());
            file.flush();
 
        } catch (IOException e) {
        	System.out.println("Error: IOException");
        	System.exit(1);
        }
	
	}

}
