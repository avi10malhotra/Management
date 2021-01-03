import java.util.ArrayList;

// By definition, this command cannot be undone, so it need not implement the Recorded Command abstract class
public class CmdSuggestTeam {
	private Project urgentProject;
	private ArrayList<Team> suggestedTeams;
	
	CmdSuggestTeam(String[] cmdParts) {
		try {
			if (cmdParts.length != 2)
				throw new ExInvalidArguments();
			
			Company company = Company.getInstance();
			urgentProject = company.findProject(cmdParts[1]);
			suggestedTeams = company.findUrgentProjectTeam(urgentProject);
			for (Team t : suggestedTeams)
				System.out.printf("%s (Work period: %s to %s)\n", t, t.getUrgentProjectStartingDay(), t.getUrgentProjectLastDay());
		} catch (ExInvalidArguments | ExProjectNotFound e) {
			System.out.println(e.getMessage());
		}
	}
}
