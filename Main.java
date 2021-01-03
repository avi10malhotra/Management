import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		Scanner in = new Scanner(System.in);

		System.out.print("Please input the file pathname: ");
		String filepathname = in.nextLine();
		
		Scanner inFile = new Scanner(new File(filepathname));

		String cmdLine1 = inFile.nextLine();
		String[] cmdLine1Parts = cmdLine1.split("\\|");
		System.out.println("\n> "+cmdLine1);
		SystemDate.createTheInstance(cmdLine1Parts[1]);
		
		while (inFile.hasNext()) {
			String cmdLine = inFile.nextLine().trim();
			
			if (cmdLine.equals(""))
				continue;  

			System.out.println("\n> "+cmdLine);
			
			String[] cmdParts = cmdLine.split("\\|");

			switch (cmdParts[0]) {
				case "hire" : (new CmdHire()).execute(cmdParts); break;
				case "listEmployees" : (new CmdListEmployees()).execute(cmdParts); break;
				case "listTeams" : (new CmdListTeams()).execute(cmdParts); break;
				case "listProjects" : (new CmdListProjects()).execute(cmdParts); break;
				case "startNewDay" : (new CmdStartNewDay()).execute(cmdParts); break;
				case "createProject" : (new CmdCreateProject()).execute(cmdParts); break;
				case "takeProject" : (new CmdTakeProject()).execute(cmdParts); break;
				case "setupTeam" : (new CmdSetupTeam()).execute(cmdParts); break;
				case "joinTeam" : (new CmdJoinTeam()).execute(cmdParts); break;
				case "changeTeam" : (new CmdChangeTeam()).execute(cmdParts); break;
				case "suggestTeam" : new CmdSuggestTeam(cmdParts); break;
				case "showEmployeeDetails" : new CmdShowEmployeeDetails(cmdParts); break;
				case "showProjectWorkerDetails" : new CmdShowProjectWorkerDetails(cmdParts); break;
				case "undo" : RecordedCommand.undoOneCommand(); break;
				case "redo" : RecordedCommand.redoOneCommand(); break;
			}
		}
		
		inFile.close();
		in.close();
	}
}
