public class CmdCreateProject extends RecordedCommand {
    private Project project;
    
    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length != 3)
                throw new ExInvalidArguments();
            
            Company company = Company.getInstance();
            String projName = cmdParts[1];
            int manpower = Integer.parseInt(cmdParts[2]);
    
            if (manpower < 1)
                throw new ExNonPositiveInteger(manpower);
            
            project = company.createProject(projName, manpower); // throws ExDuplicateProject
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
            
        } catch (NumberFormatException e) {
            System.out.println("Wrong number format -- Please enter an integer.");
        } catch (ExInvalidArguments | ExDuplicateProject | ExNonPositiveInteger e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company.getInstance().removeProject(project);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company.getInstance().addProject(project);
        addUndoCommand(this);
    }

}
