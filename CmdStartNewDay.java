public class CmdStartNewDay extends RecordedCommand {
    private String oldDate;
    private String newDate;

	@Override
    public void execute(String[] cmdParts) {
        SystemDate instance = SystemDate.getInstance();
        oldDate = instance.toString();
        newDate = cmdParts[1];
        instance.set(newDate);

        addUndoCommand(this);
        clearRedoList();
        System.out.println("Done.");
	}

    @Override
    public void undoMe() {
        SystemDate.getInstance().set(oldDate);
        addRedoCommand(this);;
    }

    @Override
    public void redoMe() {
        SystemDate.getInstance().set(newDate);
        addUndoCommand(this);
    }
}