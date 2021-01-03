public class CmdJoinTeam extends RecordedCommand {
    private Team t;
    private Employee e;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length != 3)
                throw  new ExInvalidArguments();

            Company company = Company.getInstance();
            String employeeName = cmdParts[1], teamName = cmdParts[2];

            t = company.findTeam(teamName); // throws ExTeamNotFound
            e = company.findEmployee(employeeName); // throws ExEmployeeNotFound
            company.isAssigned(e); // throws ExEmployeeAssigned

            t.addMember(e);
            e.updateEmployeeHistory(t);

            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");

        } catch (ExInvalidArguments | ExTeamNotFound | ExEmployeeNotFound | ExEmployeeAssigned e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        t.removeMember(e);
        e.removeUpdate();
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        t.addMember(e);
        e.updateEmployeeHistory(t);
        addUndoCommand(this);

    }
}
