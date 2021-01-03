public class ExNonPositiveInteger extends Exception {
    
    private static final long serialVersionUID = 1L;

    public ExNonPositiveInteger(int value) {
        super("Estimated manpower should not be zero or negative.\n" +
            String.format("Consider changing %d to a positive non-zero amount.", value));
    }

    public ExNonPositiveInteger(String message) { super(message); }
}
