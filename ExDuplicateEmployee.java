public class ExDuplicateEmployee extends Exception {

    private static final long serialVersionUID = 1L;

	public ExDuplicateEmployee() { super("Employee name already exists."); }

    public ExDuplicateEmployee(String message) { super(message); }
}
