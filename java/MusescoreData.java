public class MusescoreData {

    private Note[] notes;
    private Constraint[] constraints;

    public Note[] getNotes() {
        return notes;
    }


    class Note {
        int tick;
        int nextTick;
        int headGroup;
        int pitch;
        int tpc;
        int track;

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

        public int getHeadGroup() {
            return headGroup;
        }

        public void setHeadGroup(int headGroup) {
            this.headGroup = headGroup;
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


    }
    class Constraint {
        String constraintText;
        public String getConstraintText() {
            return constraintText;
        }
        public void setConstraintText(String constrainText) {
            this.constraintText = constrainText;
        }
    }


}
