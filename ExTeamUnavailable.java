public class ExTeamUnavailable extends Exception {

    private static final long serialVersionUID = 1L;

     public ExTeamUnavailable() { super("The team is not available during the period "); }

    public ExTeamUnavailable(String message) { super(message); }
}
