public class ExAttemptedProjectReassignment extends Exception {
    
    private static final long serialVersionUID = 1L;

    public ExAttemptedProjectReassignment() { super("Project has already been assigned to a team."); }

    public ExAttemptedProjectReassignment(String message) { super(message); }
}
