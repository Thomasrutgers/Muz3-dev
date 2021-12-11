import com.microsoft.z3.BoolExpr;
import com.microsoft.z3.Context;

import java.util.ArrayList;

public class Constraint {
    //an object that holds a MSConstraint, links it to a note, and creates a z3 BoolExpr from it
    MSConstraint msConstraint;
    Context z3Context;
    ArrayList<Note> notes;

    public Constraint(MSConstraint msConstraint, Context z3Context, ArrayList<Note> notes) {
        this.msConstraint = msConstraint;
        this.z3Context = z3Context;
        this.notes = notes;
    }

    BoolExpr boolExpr(Context z3context) {
        return null;
    }

}
