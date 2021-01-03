public class ExDuplicateProject extends Exception {

	private static final long serialVersionUID = 1L;

	public ExDuplicateProject() { super("Project code already exists."); }

    public ExDuplicateProject(String message) { super(message); }
}
