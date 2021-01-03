public class CmdListProjects implements Command {
  
    @Override
    public void execute(String[] cmdParts) {
        Company.getInstance().listProjects();
    }
}
