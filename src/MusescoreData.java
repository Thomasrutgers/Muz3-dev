public class MusescoreData {

    //format for JSON data from and to musescore

    private MSNote[] notes;
    private MSConstraint[] constraints;
    int result; //0=succes 1=unsat (with core constraints) 2=syntax error (with constraint)

    public void setNotes(MSNote[] notes) {
        this.notes = notes;
    }

    public void setConstraints(MSConstraint[] constraints) {
        this.constraints = constraints;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public MSNote[] getNotes() {
        return notes;
    }
    public MSConstraint[] getConstraints() {
        return constraints;
    }
}

class MSNote {
    private int tick; //all these are initialized form JSON data
    private int nextTick;
    private boolean fixed;
    private int pitch;
    private int tpc;
    private int track;
    private int selectionIndex;
    private int keySignature;

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public int getNextTick() {
        return nextTick;
    }

    public void setNextTick(int nextTick) {
        this.nextTick = nextTick;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public int getPitch() {
        return pitch;
    }

    public void setPitch(int pitch) {
        this.pitch = pitch;
    }

    public int getTpc() {
        return tpc;
    }

    public void setTpc(int tpc) {
        this.tpc = tpc;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public int getSelectionIndex() {
        return selectionIndex;
    }

    public void setSelectionIndex(int selectionIndex) {
        this.selectionIndex = selectionIndex;
    }

    public int getKeySignature() {
        return keySignature;
    }

    public void setKeySignature(int keySignature) {
        this.keySignature = keySignature;
    }
}
class MSConstraint {
    private String constraintText; //from JSON data
    private int tick; //all these are initialized form JSON data
    private int nextTick;
    private int selectionIndex;

    public int getSelectionIndex() {
        return selectionIndex;
    }

    public void setSelectionIndex(int selectionIndex) {
        this.selectionIndex = selectionIndex;
    }

    public String getConstraintText() {
        return constraintText;
    }

    public void setConstraintText(String constraintText) {
        this.constraintText = constraintText;
    }

    public int getTick() {
        return tick;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public int getNextTick() {
        return nextTick;
    }

    public void setNextTick(int nextTick) {
        this.nextTick = nextTick;
    }
}
