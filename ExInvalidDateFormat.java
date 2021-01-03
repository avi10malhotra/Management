public class ExInvalidDateFormat extends Exception {

    private static final long serialVersionUID = 1L;

    public ExInvalidDateFormat() { super("Invalid date."); }

    public ExInvalidDateFormat(String message) { super(message); }
}
