public class CmdHire extends RecordedCommand {
    
    private Employee e;
    
    @Override
    public void execute(String[] cmdParts) {
        try {
            if (cmdParts.length != 2)
                throw new ExInvalidArguments();
            
            Company company = Company.getInstance();
            String employeeName = cmdParts[1];
            e = company.createEmployee(employeeName); // throws ExDuplicateEmployee

            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExInvalidArguments | ExDuplicateEmployee e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void undoMe() {
        Company.getInstance().removeEmployee(e);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company.getInstance().addEmployee(e);
        addUndoCommand(this);
    }
    
}
