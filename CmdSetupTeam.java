public class CmdSetupTeam extends RecordedCommand {
   
    private Team team;
    private Employee leader;

    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length != 3)
                throw new ExInvalidArguments();

            Company company = Company.getInstance();
            String teamName = cmdParts[1], leaderName = cmdParts[2];

            leader = company.findEmployee(leaderName); // throws ExEmployeeNotFound
            company.isLeader(leader); // throws ExEmployeeAssigned
            team = company.createTeam(teamName, leader); // throws ExDuplicateTeam
            leader.updateEmployeeHistory(team);
            
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
            
        } catch (ExInvalidArguments | ExDuplicateTeam | ExEmployeeNotFound | ExEmployeeAssigned e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company.getInstance().removeTeam(team);
        leader.removeUpdate();
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company.getInstance().addTeam(team);
        leader.updateEmployeeHistory(team);
        addUndoCommand(this);
    }
    
}
