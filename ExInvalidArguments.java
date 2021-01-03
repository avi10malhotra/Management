public class ExInvalidArguments extends Exception {
    
    private static final long serialVersionUID = 1L;

    public ExInvalidArguments() { super("Insufficient command arguments."); }
    
    public ExInvalidArguments(String message) { super(message); }
    
}
