import com.microsoft.z3.Context;

import java.util.ArrayList;

public class Muz3 {

    public static void main(String[] args) {
        if (args.length <2 ) {
            System.err.println("Not enough arguments...");
            System.exit(1);
        }
        System.out.println("Setting up Z3...");
        MusescoreData dataIn;
        String inFile = args[0];
        String outFile = args[1];
        FileIO fileIO = new FileIO();
        dataIn = fileIO.loadJson(inFile);
        System.out.println("Setting up Z3...");
        final Context context = new Context();
        MusescoreReader m = new MusescoreReader(context,dataIn);
        ArrayList<Note> notes = m.getNotes();
        Note n = notes.get(5);
        Note n2 = m.getPrevNote(n);
        Note n3 = m.getPrevNote(n2);
        Note n4 = m.getPrevNote(n3);
        Note n5 = m.getNextNote(n4);
        //System.out.println(EnhCalc.degreeInChromaticScale(2,-3));
        //System.out.println(EnhCalc.keyAsDegree(-3));
        System.out.println(EnhCalc.deltaPitchFromDiatonicAddition(14,-2,-3));
        //fileIO.writeJson(dataOut, outFile);
        //TimeUnit.SECONDS.sleep(2);

    }
}
