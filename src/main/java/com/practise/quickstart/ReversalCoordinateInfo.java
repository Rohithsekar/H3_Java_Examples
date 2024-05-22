package quickstart;

public class ReversalCoordinateInfo {

    private Coordinates from;
    private Coordinates to;
    private double bearingValue;

    public ReversalCoordinateInfo() {
    }

    public ReversalCoordinateInfo(Coordinates from, Coordinates to, double bearingValue) {
        this.from = from;
        this.to = to;
        this.bearingValue = bearingValue;
    }

    public Coordinates getFrom() {
        return from;
    }

    public void setFrom(Coordinates from) {
        this.from = from;
    }

    public Coordinates getTo() {
        return to;
    }

    public void setTo(Coordinates to) {
        this.to = to;
    }

    public double getBearingValue() {
        return bearingValue;
    }

    public void setBearingValue(double bearingValue) {
        this.bearingValue = bearingValue;
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "from: " + this.from + " ,to: " + this.to + " , " + " bearing: " + this.bearingValue;
    }
}
