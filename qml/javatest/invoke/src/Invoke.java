
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Invoke {

	public static void main(String[] args) {
		if (args.length<1) {
			System.err.println("not enough arguments");
		}
		else {
			JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(args[0]))
        {

            JSONObject data = (JSONObject) jsonParser.parse(reader);;
            JSONArray rules = (JSONArray) data.get("rules");
            System.out.println(rules.get(1).toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
		}
	}

}
