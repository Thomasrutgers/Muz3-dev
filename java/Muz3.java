import java.io.File;

public class Muz3 {

    public static void main(String[] args) {
        MusescoreData data = new MusescoreData();
        String inFile = args[0];
        String outFile = args[1];
        FileIO fileIO = new FileIO();
        data = fileIO.loadJson(inFile);
        for (int i =0; i< data.getNotes().length; i++) {
            data.getNotes()[i].setPitch((data.getNotes()[i].getPitch() + 1));
            data.getNotes()[i].setTpc((data.getNotes()[i].getTpc() + 1));
        }
        System.out.println(data.getNotes()[0].getTick());
        fileIO.writeJson(data, outFile);


    }
}
