import com.microsoft.z3.Context;

import java.util.ArrayList;

public class MusescoreReader
{
    //reads msdata into Note and Constraint objects to set up z3

    Context z3Context;
    MusescoreData data;
    ArrayList<Note> notes = new ArrayList<>();
    ArrayList<Constraint> constraints = new ArrayList<>();
    ArrayList<Note> usedNotes = new ArrayList<>(); //not sure if this is the right place!

    public ArrayList<Note> getUsedNotes() {
        return usedNotes;
    }

    public MusescoreReader(Context z3Context, MusescoreData data) {
        this.z3Context = z3Context;
        this.data = data;
        for (MSNote msN : data.getNotes()) {
            notes.add(new Note(msN,z3Context));
        }
        for (MSConstraint msC : data.getConstraints()) {
            constraints.add(new Constraint(msC,z3Context, getConstraintNotes(msC),this));
        }
    }

    private ArrayList<Note> getConstraintNotes(MSConstraint msC) { //get the notes that this constrain applies to
        ArrayList<Note> constraintNotes = new ArrayList<>();
        for (Note n : notes) {
            //for now, works with text on segments, add fingerings / lines
            if ((n.getMsNote().getTick() <= msC.getTick()) &&
                    (n.getMsNote().getNextTick() > msC.getTick())) {
                constraintNotes.add(n);
            }
        }
        return constraintNotes;
    }

    public Note getPrevNote (Note referenceNote) {
        int closestTick = -1;
        Note closestNote = null;
        for (Note candidateNote : notes) { //candidate
            if (
                    (candidateNote.getStaff() == referenceNote.getStaff()) &&
                    (candidateNote.getMsNote().getNextTick() <= referenceNote.getMsNote().getTick()) &&
                    (candidateNote.getMsNote().getTick() > closestTick)) {
                closestNote = candidateNote;
                closestTick = candidateNote.getMsNote().getTick();
            }
        }
        return closestNote;
    }

    public Note getNextNote (Note referenceNote) {
        int closestTick = -1;
        Note closestNote = null;
        for (Note candidateNote : notes) { //candidate
            if (
                    (candidateNote.getStaff() == referenceNote.getStaff()) &&
                            (candidateNote.getMsNote().getTick() >= referenceNote.getMsNote().getNextTick()) &&
                            ((candidateNote.getMsNote().getTick() < closestTick) || closestTick < 0 )) {
                closestNote = candidateNote;
                closestTick = candidateNote.getMsNote().getTick();
            }
        }
        return closestNote;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }

    public void addUsedNote(Note n) { //again, this might not be the place!
        if (!usedNotes.contains(n)) usedNotes.add(n);
    }

}
