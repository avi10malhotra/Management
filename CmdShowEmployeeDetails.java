// By definition, this command cannot be undone, so it need not implement the Recorded Command abstract class
public class CmdShowEmployeeDetails {
	private Employee employee;
	
	CmdShowEmployeeDetails(String[] cmdParts) {
		try {
			if (cmdParts.length != 2)
				throw new ExInvalidArguments();
			
			String employeeName = cmdParts[1];
			employee = Company.getInstance().findEmployee(employeeName);
			employee.showDetails();
		} catch (ExInvalidArguments | ExEmployeeNotFound e) {
			System.out.println(e.getMessage());
		}
	}
}
