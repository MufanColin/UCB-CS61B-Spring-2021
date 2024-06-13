package gh2;

import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static final int NUM_KEYS = 37;
    public static final double CONCERT_A = 440.0;

    public static void main(String[] args) {
        GuitarString[] strings = new GuitarString[NUM_KEYS];
        for (int i = 0; i < NUM_KEYS; i++) {
            strings[i] = new GuitarString(CONCERT_A * Math.pow(2.0, (i - 24) * 1.0 / 12));
        }
        while (true) {
            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (KEYBOARD.contains(String.valueOf(key))) {
                    int keyIndex = KEYBOARD.indexOf(key);
                    strings[keyIndex].pluck();

                }
            }

            /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < NUM_KEYS; i++) {
                sample += strings[i].sample();
            }
            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < NUM_KEYS; i++) {
                strings[i].tic();
            }
        }
    }
}

