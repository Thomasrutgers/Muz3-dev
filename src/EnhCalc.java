public class EnhCalc {

    public static int getNormalTpc(int pitch, int key) {
        //see https://musescore.org/en/handbook/developers-handbook/plugin-development/tonal-pitch-class-enum
        int modPitch = pitch % 12;
        return tpcList(key)[modPitch];
    }

    private static int[] tpcList(int key) {
        int[] tpc = {14, 21, 16, 11, 18, 13, 20, 15, 10, 17, 12, 19};
        //todo: suppress warning
        if (key > 0) {
            tpc[8] = 10; //ambivalent tpc, calculate from low
            int sharps = key;
            int index = 8;
            for (int s = 0; s < sharps; s++) {
                tpc[index % 12] = tpc[index % 12] + 12;
                index = index + 7;
            }
        }
        if (key < 0) {
            tpc[8] = 22; //ambivalent tpc, calculate from high
            int flats = key * -1;
            int index = 8;
            for (int s = 0; s < flats; s++) {
                tpc[index % 12] = tpc[index % 12] - 12;
                index = index + 5;
            }
        }
        return tpc;
    }

    public static int pitchDeltaFromDiatonicDelta(int tpc, int degreeDelta, int key) {
        //how many pitches, when we jump to the next note belonging to scale?
        //for instance, in the key of G, adding 1 to a Ebb gives F#, a deltaPitch of 4
        //in the key of Bb, adding 1 to a## gives -2

        //the algorithm:
        //get pitch of tpc, normalized (%12), but: extra 12 for wrapping Cb / B# / B##
        //get tpc degree of old note (0=C, 1=D, 2=E .... 6=B)
        //add degreeDelta to degree. Then, get degree%7, remember added/subtracted octaves to get this
        //get degree position in chromatic scale of key, but subtract key degree itself ("normalize to C")
        //so, to clarify: say we want the pitch of degree 2 in the key of Eb, we want the pitch of Eb, not of G
        //add octaves

        //get pitch of tpc
        int base = ((tpc-2)%12);
        int tpcPitch = (base * 7)%12;
        if (tpc==7) tpcPitch = -1; //Cb
        if (tpc==26) tpcPitch = 12; //B#
        if (tpc==33) tpcPitch = 13; //B##
        //get tpcDegree
        int tpcMod = tpc%7;
        if (tpc == -1) tpcMod = 6; // Fbb is negative, modulus doesnt work
        //tpcMod: 0=C(0), 1=G(4), 2=D(1), etc.
        int tpcDegree = (tpcMod*4)%7;
        int newDegree = tpcDegree + degreeDelta;
        int octaves = newDegree / 7;
        if (newDegree < 0) {
            octaves = octaves - 1;
        }
        int newPitch = (keyDegreeInChromaticScale((newDegree-keyAsDegree(key)+7)%7, key))+ (octaves*12); //+7 to be sure of no negative modulus

        return newPitch - tpcPitch;
    }

    public static int keyAsDegree (int key) {
        //this could also be done with a formula...
        int degree = 0;
        if (key > 0) {
            int sharps = key;
            degree = degree +(sharps * 4);
        }
        if (key < 0) {
            int flats = key * -1;
            degree= degree + (flats*3);
        }
        int out = degree%7;
        return out;
    }


    public static int keyDegreeInChromaticScale(int degree, int key) {
    //for instance, in key Eb, degree 2 is 7 (G)
        //major scale
        int index=0;
        if (degree == 0 ) index = 0;
        if (degree == 1 ) index = 2;
        if (degree == 2 ) index = 4;
        if (degree == 3 ) index = 5;
        if (degree == 4 ) index = 7;
        if (degree == 5 ) index = 9;
        if (degree == 6 ) index = 11;

        if (key > 0) {
            int sharps = key;
            index = index +(sharps * 7);
        }
        if (key < 0) {
            int flats = key * -1;
             index = index + (flats*5);
        }
        int out = index%12;
        return out;
    }
}

