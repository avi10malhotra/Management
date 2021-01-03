public class ExProjectNotFound extends Exception {
    private static final long serialVersionUID = 1L;

    public ExProjectNotFound() { super("Project does not exist."); }

    public ExProjectNotFound(String message) { super(message); }
}
