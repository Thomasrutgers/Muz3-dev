import com.microsoft.z3.*;

import java.util.ArrayList;

public class Muz3 {

    public static void main(String[] args) throws InterruptedException {
        if (args.length <2 ) {
            System.err.println("Not enough arguments...");
            System.exit(1);
        }
        System.out.println("Loading Data...");
        MusescoreData dataIn;
        String inFile = args[0];
        String outFile = args[1];
        FileIO fileIO = new FileIO();
        dataIn = fileIO.loadJson(inFile);
        System.out.println("Setting up Z3...");
        final Context context = new Context();
        MusescoreReader m = new MusescoreReader(context,dataIn);
        ArrayList<Constraint> constraints = m.getConstraints();
        ArrayList<Note> usedNotes = new ArrayList<>();
        final Solver solver = context.mkSimpleSolver();
        for (Constraint c : constraints) {
            BoolExpr e = c.getBoolExpr();
            if (e != null) {
                String s = Integer.toString(c.getMsConstraint().getSelectionIndex());
                solver.assertAndTrack(e, context.mkBoolConst(s));

            }
        }
        Status status = solver.check();
        if (status==Status.SATISFIABLE) {
            final Model model = solver.getModel();
            System.out.println("Results:");
            MSNote[] msNotes = new MSNote[m.getUsedNotes().size()];
            int i =0;
            for (Note n : m.getUsedNotes()) {
                int solvedPitch = Integer.parseInt(model.getConstInterp(n.getIntExpr()).toString());
                System.out.println(solvedPitch);
                n.getMsNote().setPitch(solvedPitch);
                n.getMsNote().setTpc(EnhCalc.getNormalTpc(solvedPitch,n.getMsNote().getKeySignature()));
                msNotes[i] =n.getMsNote();
                i++;
            }
            MusescoreData dataOut = new MusescoreData();
            dataOut.setNotes(msNotes);
            fileIO.writeJson(dataOut,outFile);
        }
        if (status==Status.UNKNOWN) {
            System.out.println("Unknown solver status");
        }
        if (status==Status.UNSATISFIABLE) {
            System.out.println("Unsolvable");
            final BoolExpr[] unSatCore = solver.getUnsatCore();
            for (BoolExpr bx : unSatCore) {
                System.out.println(bx.toString());
            }
        }
        context.close();
    }
}
