import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team> {
    
    private final String teamName;
    private final Employee head;
    private final ArrayList<Employee> members;
    private final Day dateSetup;
    private final ArrayList<Project> projectsAssigned;
    private Day urgentProjectStarts;
    private Day urgentProjectEnds;

    public Team(String name, Employee head) {
        this.teamName = name;
        this.head = head;
        this.members = new ArrayList<>();
        this.dateSetup = SystemDate.getInstance().clone();
        this.projectsAssigned = new ArrayList<>();
    }

    public static Team searchTeam(ArrayList<Team> list, String name) throws ExTeamNotFound {
        for (Team t : list)
            if (t.teamName.equals(name))
                return t;
        throw new ExTeamNotFound();
    }

    public static boolean isLeader(ArrayList<Team> list, Employee e) {
        for (Team t : list)
            if (t.head.equals(e))
                return true;
        return false;
    }
    
    public Employee getTeamHead() { return this.head; }

    public static boolean isAssigned(ArrayList<Team> list, Employee member) {
        for (Team t : list)
            for (Employee e : t.members)
                if (e.equals(member))
                    return true;
        return false;
    }

    public void addMember(Employee e) {
        this.members.add(e);
        Collections.sort(members);
    }

    public void removeMember(Employee e) { this.members.remove(e); }

    public int getMemberCount() { return this.members.size(); }
    
    public void addProject(Project p) {
        this.projectsAssigned.add(p);
        // sorts projects assigned to a team by their undertaking date!
        this.projectsAssigned.sort(new Project.sortProjectsByDate());
    }
    
    public void removeProject(Project p) { this.projectsAssigned.remove(p); }
    
    public ArrayList<Project> getProjectsAssigned() { return this.projectsAssigned; }
    
    public void setUrgentProjectTimeline(Day start, Day end) {
        this.urgentProjectStarts = start;
        this.urgentProjectEnds = end;
    }
    
    public boolean isAssignedUrgentProject() { return this.urgentProjectStarts != null;}
    
    public Day getUrgentProjectStartingDay() { return this.urgentProjectStarts; }
    
    public Day getUrgentProjectLastDay() { return this.urgentProjectEnds; }
    
    public void deleteUrgentProject() { this.urgentProjectStarts = null; this.urgentProjectEnds = null; }
    
    public static void list(ArrayList<Team> list) {
        System.out.printf("%-15s%-10s%-14s%-20s\n", "Team Name", "Leader", "Setup Date", "Members");
        for (Team t : list)
            System.out.printf("%-15s%-10s%-14s%-20s\n", t.teamName, t.head, t.dateSetup.toString(), t.allMembers());
    }
    
    private String allMembers() {
        if (members.isEmpty())
            return "(no member)";

        StringBuilder memberNames = new StringBuilder();
        for (Employee e : members)
            memberNames.append(e).append(" ");
        return memberNames.toString().trim();
    }
    
    @Override
    public int compareTo(Team another) { return this.teamName.compareTo(another.teamName); }
    
    @Override
    public String toString() { return this.teamName; }
}
