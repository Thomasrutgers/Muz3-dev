import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;

import java.util.ArrayList;

public class Constraint {
    //an object that holds a MSConstraint, links it to a note, and creates a z3 BoolExpr from it
    private MSConstraint msConstraint;

    public MSConstraint getMsConstraint() {
        return msConstraint;
    }

    private Context z3Context;
    private ArrayList<Note> notes;

    public BoolExpr getBoolExpr() {
        return boolExpr;
    }

    private BoolExpr boolExpr = null;

    private  MusescoreReader musescoreReader;

    public Constraint(MSConstraint msConstraint, Context z3Context, ArrayList<Note> notes, MusescoreReader musescoreReader) {
        this.msConstraint = msConstraint;
        this.z3Context = z3Context;
        this.notes = notes;
        this.musescoreReader = musescoreReader;

        //use lexer later. for now just get the interval
        int interval = Integer.parseInt(msConstraint.getConstraintText());
        if (notes.size() > 0) {
            Note note2 = notes.get(0);
            Note note1 = musescoreReader.getPrevNote(note2);
            boolExpr = pitchInterval(note1,note2,interval);
            if (!note1.getMsNote().isFixed()) musescoreReader.addUsedNote(note1);
            if (!note2.getMsNote().isFixed()) musescoreReader.addUsedNote(note2);
        }
    }

    BoolExpr pitchInterval(Note note1, Note note2, int interval) {
        int diatonicDelta = 0;
        if (interval>0) diatonicDelta = interval - 1;
        if (interval<0) diatonicDelta = interval + 1;
        int pitchDelta = EnhCalc.pitchDeltaFromDiatonicDelta(note1.getMsNote().getTpc(),diatonicDelta,note2.getMsNote().getKeySignature());
        final BoolExpr expr = z3Context.mkEq(note2.getIntExpr(), z3Context.mkAdd(note1.getIntExpr(),z3Context.mkInt(pitchDelta)));
        return expr;
    }

}
