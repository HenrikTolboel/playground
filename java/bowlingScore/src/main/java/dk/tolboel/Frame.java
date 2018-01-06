package dk.tolbøl;

import java.util.ArrayList;
import java.util.List;

public class Frame {

    private boolean isLastFrame;
    private List<AnyRoll> Rolls;


    public Frame(boolean lastFrame) throws BowlingException {
        isLastFrame = lastFrame;
        Rolls = new ArrayList<>();
    }

    public void add(Strike roll) throws BowlingException {
        if (!isLastFrame) {
            if (!Rolls.isEmpty())
                throw new BowlingException("Adding a Strike to a frame already containg a score - should be a spare?");
        } else {
            if (Rolls.size() == 1 && !(Rolls.get(0) instanceof Strike))
                throw new BowlingException("In last frame a strike cannot follow a non strike first roll");

            if (Rolls.size() == 2 && !(Rolls.get(0) instanceof Strike) && !(Rolls.get(1) instanceof Strike)) {
                throw new BowlingException("In last frame a third roll can only be a Strike if the first 2 are Strikes");
            }
        }
        Rolls.add(roll);
    }

    public void add(Spare roll) throws BowlingException {
        if (!isLastFrame) {
            if (Rolls.size() != 1)
                throw new BowlingException("A Spare cannot be first in a frame");

            if (!(Rolls.get(0) instanceof Roll))
                throw new BowlingException("A spare follows a Roll");

            if (Rolls.get(0).getPins() + roll.getPins() != 10) {
                throw new BowlingException(String.format("A Spare contains the wrong number of pins. Is: %d, should be: %d",
                        roll.getPins(), 10 - Rolls.get(0).getPins()));
            }
        } else {

            if (Rolls.isEmpty())
                throw new BowlingException("A Spare cannot be first in a frame");
            if (Rolls.size() == 1 && !(Rolls.get(0) instanceof Roll))
                throw new BowlingException("A spare follows a Roll");
            if (Rolls.size() == 2) {
                if (!(Rolls.get(1) instanceof Roll))
                    throw new BowlingException("A spare follows a Roll");

                if (Rolls.get(1).getPins() + roll.getPins() != 10) {
                    throw new BowlingException(String.format("A Spare contains the wrong number of pins. Is: %d, should be: %d",
                            roll.getPins(), 10 - Rolls.get(1).getPins()));
                }
            }

        }
        Rolls.add(roll);
    }

    public void add(Roll roll) throws BowlingException {
        if (!isLastFrame) {
            if (Rolls.size() > 1)
                throw new BowlingException("Max 2 Rolls in a frame");

            if (Rolls.size() == 1) {
                if (!(Rolls.get(0) instanceof Roll))
                    throw new BowlingException("A Roll follows a Roll");

                if (Rolls.get(0).getPins() + roll.getPins() >= 10) {
                    throw new BowlingException(String.format("A Roll contains too many pins. Is: %d, should be less than: %d",
                            roll.getPins(), 9 - Rolls.get(0).getPins()));
                }
            }
        } else {

            if (Rolls.size() > 2)
                throw new BowlingException("Max 3 Rolls in last frame");
            if (Rolls.size() == 1) {
                if (Rolls.get(0) instanceof Roll) {
                    if (Rolls.get(0).getPins() + roll.getPins() >= 10) {
                        throw new BowlingException(String.format("A Roll contains too many pins. Is: %d, should be less than: %d",
                                roll.getPins(), 9 - Rolls.get(0).getPins()));

                    }
                }
                // otherwise it must be a Strike, which is OK
            } else {
                // Any values allowed - a roll can come after AnyRoll when it is last in last frame
            }
        }
        Rolls.add(roll);
    }
}
