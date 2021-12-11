import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public MusescoreData loadJson(String inFile) {
        MusescoreData data = null;
        try {
            data = gson.fromJson(new FileReader(inFile), MusescoreData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void writeJson(MusescoreData data, String outFile) {
        try (FileWriter writer = new FileWriter(outFile)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
