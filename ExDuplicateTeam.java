public class ExDuplicateTeam extends Exception {

    private static final long serialVersionUID = 1L;

	public ExDuplicateTeam() { super("Team name already exists."); }

    public ExDuplicateTeam(String message) { super(message); }
}
