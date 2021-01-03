public class ExInvalidStartingDate extends Exception {

    private static final long serialVersionUID = 1L;

    public ExInvalidStartingDate() { super("The earliest start day is tomorrow."); }

    public ExInvalidStartingDate(String message) { super(message); }
}
