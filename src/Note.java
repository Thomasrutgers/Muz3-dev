import com.microsoft.z3.Context;
import com.microsoft.z3.IntExpr;

//an object that holds musescore note Data, and creates a z3 intExpr from it

public class Note {

    public int getStaff() {
        return staff;
    }

    public IntExpr getIntExpr() {
        return intExpr;
    }

    public MSNote getMsNote() {
        return msNote;
    }

    private final int staff;
    private final IntExpr intExpr;
    private final MSNote msNote;

    public Note(MSNote msNote, Context z3context) {
        this.msNote = msNote;
        staff = msNote.getTrack() /4;
        if (msNote.isFixed()) {
            //fixed note, a number for z3
            intExpr = z3context.mkInt(msNote.getPitch());
        }
        else {
            //flex note, a variable for z3.
            //selectionIndex is a unique number to use as a label
            int s = msNote.getSelectionIndex();
            intExpr = z3context.mkIntConst(Integer.toString(s));
        }
    }

}
