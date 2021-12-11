import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;

import java.util.ArrayList;

public class Constraint {
    //an object that holds a MSConstraint, links it to a note, and creates a z3 BoolExpr from it
    private MSConstraint msConstraint;
    private Context z3Context;
    private ArrayList<Note> notes;
    private BoolExpr boolExpr;

    public Constraint(MSConstraint msConstraint, Context z3Context, ArrayList<Note> notes) {
        this.msConstraint = msConstraint;
        this.z3Context = z3Context;
        this.notes = notes;
        //use lexer later. for now just get the interval
        int interval = Integer.parseInt(msConstraint.getConstraintText());

    }

    BoolExpr pitchInterval(Note note1, Note note2, int interval) {
        return null;
    }

}
