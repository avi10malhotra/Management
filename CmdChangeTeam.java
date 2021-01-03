public class CmdChangeTeam extends RecordedCommand {
    private Team oldTeam, newTeam;
    private Employee e;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length != 3)
                throw new ExInvalidArguments();

            Company company = Company.getInstance();
            String employeeName = cmdParts[1], teamName = cmdParts[2];

            e = company.findEmployee(employeeName); // throws ExEmployeeNotFound
            if (e.getAssignedTeamName().equals(teamName))
                throw new ExAttemptedTeamReassignment();
            oldTeam = company.findTeam(e.getAssignedTeamName()); // throws ExTeamNotFound
            newTeam = company.findTeam(teamName);

            oldTeam.removeMember(e);
            newTeam.addMember(e);
            e.updateEmployeeHistory(newTeam);

            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");

        } catch (ExInvalidArguments | ExTeamNotFound | ExEmployeeNotFound | ExAttemptedTeamReassignment e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        e.removeUpdate(oldTeam.toString());
        newTeam.removeMember(e);
        oldTeam.addMember(e);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        e.updateEmployeeHistory(newTeam);
        oldTeam.removeMember(e);
        newTeam.addMember(e);
        addUndoCommand(this);
    }
}
