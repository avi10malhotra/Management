public class ExEmployeeAssigned extends Exception {
    
    private static final long serialVersionUID = 1L;

    public ExEmployeeAssigned() { super("Employee has joined a team already."); }

    public ExEmployeeAssigned(String message) { super(message); }
}
