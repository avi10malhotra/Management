// By definition, this command cannot be undone, so it need not implement the Recorded Command abstract class
public class CmdShowProjectWorkerDetails {
	private Project project;
	
	CmdShowProjectWorkerDetails(String[] cmdParts) {
		try {
			if (cmdParts.length != 2)
				throw new ExInvalidArguments();
			
			Company company = Company.getInstance();
			String projectName = cmdParts[1];
			
			project = company.findProject(projectName);
			company.showProjectDetails(project);
		} catch (ExInvalidArguments | ExProjectNotFound e) {
			System.out.println(e.getMessage());
		}
	}
}
