package dk.tolbøl;

public class Roll extends AnyRoll {
    public Roll(int pins) throws BowlingException {
        super(pins);
        if (pins > 9)
            throw new BowlingException("A Roll has less than 10 pins");
    }
}
