public class CmdTakeProject extends RecordedCommand {
    private Team t;
    private Project p;
    private Day startingDate, deadlineDate;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length != 4)
                throw new ExInvalidArguments();
            else if (!Day.correctFormat(cmdParts[3]))
                throw new ExInvalidDateFormat();
            else
                startingDate = new Day(cmdParts[3]);

            Company company = Company.getInstance();
            String teamName = cmdParts[1], projectName = cmdParts[2];
            
            if (SystemDate.getInstance().toString().equals(startingDate.toString()))
                throw new ExInvalidStartingDate();

            t = company.findTeam(teamName); // throws ExTeamNotFound
            p = company.findProject(projectName); // throws ExProjectNotFound
            deadlineDate = p.calcProjectDeadline(startingDate, t);
            
            company.isTeamAvailable(t, startingDate, deadlineDate); // throws ExTeamUnavailable
            company.isProjectAvailable(p); // throws ExAttemptedProjectReassignment
            p.assignTeam(t, startingDate, deadlineDate);
            t.addProject(p);
            
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");

        } catch (ExInvalidArguments | ExTeamNotFound | ExProjectNotFound | ExInvalidStartingDate |
                ExAttemptedProjectReassignment | ExInvalidDateFormat e) {
            System.out.println(e.getMessage());
        } catch (ExTeamUnavailable e) {
            System.out.printf("The team is not available during the period (%s to %s).\n", startingDate, deadlineDate);
        }
    }

    @Override
    public void undoMe() {
        p.resetTeamAssignment();
        t.removeProject(p);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        p.assignTeam(t, startingDate, deadlineDate);
        t.removeProject(p);
        addUndoCommand(this);
    }

}
