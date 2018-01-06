package dk.tolboel;

import java.util.ArrayList;
import java.util.List;

public class buildFrames {

    public buildFrames() {
    }

    public static List<Frame> get(String game) throws BowlingException {
        List<Frame> frames = new ArrayList<>();

        // game contains a string describing a bowling game
        // e.g. "23-45-4/-X-23-X45"
        // that is 1-3 single digits describing the outcome of the rolls of a frame separated by a "-" between the frames

        String[] strFrames = game.split("-", -1);

        for (int i = 0; i < strFrames.length; i++) {
            boolean last = ((i + 1) == strFrames.length);
            Frame f = new Frame(last);

            String[] s = strFrames[i].split("(?!^)");

            if (s.length < 1 || s[0].isEmpty())
                throw new BowlingException("Parsing a frame without rolls");

            if ((!last && s.length > 2) || (last && s.length > 3))
                throw new BowlingException("Parsing frame too long");

            if (s[0].compareTo("/") == 0)
                throw new BowlingException("A spare cannot be first in frame");

            if (!last) {
                // 2 chars, possibilities: "nn", "n/"
                // 1 char, possibilities: "X"

                if (s[0].compareToIgnoreCase("X") == 0)
                    f.add(new Strike());
                else {
                    int val = Integer.parseUnsignedInt(s[0]);
                    f.add(new Roll(val));
                    if (s[1].compareTo("/") == 0) {
                        f.add(new Spare(10 - val));
                    } else {
                        val = Integer.parseUnsignedInt(s[1]);
                        f.add(new Roll(val));
                    }
                }
            } else {
                // 3 chars, possibilities: "XXX", "XXn", "Xn/", "n/n", "n/X"
                // 2 chars, possibilities: "nn"
                int val = 0;
                if (s[0].compareToIgnoreCase("X") == 0)
                    f.add(new Strike());
                else {
                    val = Integer.parseUnsignedInt(s[0]);
                    f.add(new Roll(val));
                }
                if (s[1].compareToIgnoreCase("X") == 0)
                    f.add(new Strike());
                else if (s[1].compareTo("/") == 0)
                    f.add(new Spare(10 - val));
                else {
                    val = Integer.parseUnsignedInt(s[1]);
                    f.add(new Roll(val));
                }
                if (s.length == 3) {
                    if (s[2].compareToIgnoreCase("X") == 0)
                        f.add(new Strike());
                    else if (s[2].compareTo("/") == 0)
                        f.add(new Spare(10 - val));
                    else {
                        val = Integer.parseUnsignedInt(s[2]);
                        f.add(new Roll(val));
                    }
                }
            }
            frames.add(f);
        }

        return frames;
    }

    public static int score(List<Frame> frames) throws BowlingException {

        int totalPins = 0;

        int noFrames = frames.size();

        for (int i = 0; i < noFrames; i++) {

            if (frames.get(i).isLastFrame()) {
                totalPins += frames.get(i).sum();
            } else if (frames.get(i).numberOfRolls() == 1) {  // Strike
                totalPins += frames.get(i).sum();
                // look forward for 2 rolls
                if (i + 1 >= noFrames)
                    throw new BowlingException("Hmm not enough frames?");
                if (frames.get(i + 1).numberOfRolls() > 1) {
                    // one frame lookahead is enough, it has 2 or more rolls. This also account for next frame being last frame.
                    totalPins += frames.get(i + 1).firstRoll() + frames.get(i + 1).secondRoll();
                } else {
                    if (i + 2 >= noFrames)
                        throw new BowlingException("Hmm not enough frames?");
                    // next frame is a Strike as there are only 1 roll, lookahead 2 frames.
                    totalPins += frames.get(i + 1).firstRoll() + frames.get(i + 2).firstRoll();
                }
            } else {
                totalPins += frames.get(i).sum();
                if (frames.get(i).sum() == 10) { // Spare
                    if (i + 1 >= noFrames)
                        throw new BowlingException("Hmm not enough frames?");
                    totalPins += frames.get(i + 1).firstRoll();
                }
            }
        }
        return totalPins;
    }
}

