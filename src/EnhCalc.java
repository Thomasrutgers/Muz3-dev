public class EnhCalc {
    public static int getNormalTpc (int pitch,int key) {
        //see https://musescore.org/en/handbook/developers-handbook/plugin-development/tonal-pitch-class-enum
        int[] tpc = {14,21,16,11,18,13,20,15,10,17,12,19};
        if (key > 0) {
            //8th has no tpc, when gong sharp, calculate from low (=10)
            //key = number of sharps
            int index = 8;
            for (int s =0;s<key;s++) {
                tpc[index%12]=tpc[index%12]+12;
                index = index + 7;
            }
        }
        if (key < 0) {
            tpc[8]=22; //8th has no tpc, when going flat, calculate from high
            int flats = key*-1;
            int index = 8;
            for (int s =0;s<flats;s++) {
                tpc[index%12]=tpc[index%12]-12;
                index = index + 5;
            }
        }
        int modPitch = pitch % 12;
        return tpc[modPitch];
    }
}
