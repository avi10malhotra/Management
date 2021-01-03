import java.util.ArrayList;

public class Employee implements Comparable<Employee> {
    
    private final String name;
    private final Membership membership;
    private String assignedToTeam;

    public Employee(String n) {
        this.name = n;
        this.membership = new Membership(name);
    }
    
    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) throws ExEmployeeNotFound {
        for (Employee e : list)
            if (e.name.equals(nameToSearch))
                return e;
        throw new ExEmployeeNotFound();
    }

    public static void list(ArrayList<Employee> list) {
        for (Employee e : list) {
            if (e.assignedToTeam == null)
                System.out.println(e.name);
            else
                System.out.printf("%s (%s)\n", e.name, e.assignedToTeam);
        }
    }
    
    public static void showMemberHistory(ArrayList<Employee> list, Team teamAssigned, Day projectFirstDay, Day deadlineDay) {
        for (Employee e : list)
            if (!teamAssigned.getTeamHead().equals(e))
                e.membership.checkTeamHistory(teamAssigned, projectFirstDay, deadlineDay);
            
    }
    
    /* Note: The assignTeam method defined in Phases I and II
    has been been renamed and modified as follows to account for
    the updates in the membership class */
    public void updateEmployeeHistory(Team team) {
        this.membership.joinTeam(team);
        this.assignedToTeam = team.toString();
    }
    
    public void removeUpdate() {
        this.membership.removeTeamUpdate();
        this.assignedToTeam = null;
    }
    
    public void removeUpdate(String oldTeamName) {
        this.membership.removeTeamUpdate();
        this.assignedToTeam = oldTeamName;
    }
    
    public String getAssignedTeamName() { return this.assignedToTeam; }
    
    public void showDetails() {
        this.membership.showHistory(this);
    }
    
    @Override
    public int compareTo(Employee another) { return this.name.compareTo(another.name); }
    
    @Override
    public String toString() { return this.name; }
    
}
