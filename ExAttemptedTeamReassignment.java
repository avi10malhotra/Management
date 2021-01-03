public class ExAttemptedTeamReassignment extends Exception {

    private static final long serialVersionUID = 1L;

    public ExAttemptedTeamReassignment() { super("The old and new teams should not be the same."); }

    public ExAttemptedTeamReassignment(String message) { super(message); }
}
