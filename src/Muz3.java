public class Muz3 {

    public static void main(String[] args) {
        System.out.println("Setting up Z3...");
        MusescoreData data;
        String inFile = args[0];
        String outFile = args[1];
        FileIO fileIO = new FileIO();
        data = fileIO.loadJson(inFile);
        for (int i =0; i< data.getNotes().length; i++) {
            data.getNotes()[i].setPitch((data.getNotes()[i].getPitch() + 1));
            data.getNotes()[i].setTpc((data.getNotes()[i].getTpc() + 7));
        }
        System.out.println("Setting up Z3...");
        fileIO.writeJson(data, outFile);


    }
}
