import java.util.ArrayList;
import java.util.Comparator;

public class Project implements Comparable<Project> {
    private final String projectID;
    private final int manpower;
    private Day startingDay, deadlineDay;
    private Team teamAssigned;

    Project(String pID, int manpower) {
        this.projectID = pID;
        this.manpower = manpower;
    }

    public static Project searchProject(ArrayList<Project> list, String nameToSearch) throws ExProjectNotFound {
        for (Project p : list)
            if (p.projectID.equals(nameToSearch))
                return p;
        throw new ExProjectNotFound();
    }
    
    public static void list(ArrayList<Project> list) {
        System.out.printf("%-9s%-14s%-13s%-13s%-13s\n", "Project", "Est manpower", "Team", "Start Day", "End Day");
        for (Project p : list)
            System.out.printf("%-9s%-14s%-13s\n", p.projectID, p.manpower + " man-days", p.printProjectInfo());
    }
    
    private String printProjectInfo() {
        if (this.teamAssigned == null)
            return "(Not Assigned)";
        else
            return String.format("%-13s%-13s%-13s", this.teamAssigned, this.startingDay, this.deadlineDay);
    }
    
    public static ArrayList<Team> calcBestSuitedTeam(ArrayList<Team> allTeams, Project urgentProject) {
        Day bestStartingDay;
        Day bestDeadlineDay = null;// needs explicit initialization for compilation purposes
        ArrayList<Team> bestTeams = new ArrayList<>();
        
        // Calculates the earliest day on which the ith team can finish the urgent project
        for (Team team : allTeams) {
            bestStartingDay = SystemDate.getInstance().next();
            Day teamProjectDeadline;
            int counter = 0;
            
            // Checks if the urgent project can be done by team before its ith assigned project
            for (Project teamProj : team.getProjectsAssigned()) {
                teamProjectDeadline = urgentProject.calcProjectDeadline(bestStartingDay, team);
                
                /* Note: if team is busy, next iteration checks if the urgent project can be done
                after the ith 'teamProj' is finished */
                if (Project.isTeamBusy(team.getProjectsAssigned(), team, bestStartingDay, teamProjectDeadline))
                    bestStartingDay = teamProj.deadlineDay.next();
                else
                    team.setUrgentProjectTimeline(bestStartingDay, teamProjectDeadline);
                
                counter++;
                // Worst case: team can take the urgent project only after finishing all its existing projects
                if (counter == team.getProjectsAssigned().size() && !team.isAssignedUrgentProject())
                    team.setUrgentProjectTimeline(bestStartingDay, urgentProject.calcProjectDeadline(bestStartingDay, team));
            }
            
            // updates the teams that can complete the urgent project at the earliest time
            if (bestDeadlineDay == null || bestDeadlineDay.format() > team.getUrgentProjectLastDay().format()) {
                bestDeadlineDay = team.getUrgentProjectLastDay();
                bestTeams.clear();
                bestTeams.add(team);
            } else if (bestDeadlineDay.format() == team.getUrgentProjectLastDay().format())
                bestTeams.add(team);
        }
        
        return bestTeams;
    }
    
	public void showDetails(ArrayList<Employee> employeesList) {
        System.out.printf("%12s : %d man-days\n", "Est manpower", this.manpower);
        System.out.printf("%-12s : %s (Leader is %s)\n", "Team", this.teamAssigned, this.teamAssigned.getTeamHead());
        System.out.printf("%-12s : %s to %s\n", "Work Period", this.startingDay, this.deadlineDay);
        
        System.out.println("Members:");
        Employee.showMemberHistory(employeesList, this.teamAssigned, this.startingDay, this.deadlineDay);
	}
    
    // I learnt how to write my own comparator!
    static class sortProjectsByDate implements Comparator<Project> {
        public int compare(Project a, Project b) {
            // boolean to int conversion needed
            return (a.startingDay.format() < b.startingDay.format()) ? -1 : 0;
        }
    }

    public static boolean isTeamBusy(ArrayList<Project> list, Team t, Day firstDay, Day lastDay) {
        for (Project p : list)
            if (p.teamAssigned != null && p.teamAssigned.equals(t))
                if ((p.startingDay.format() <= firstDay.format() && firstDay.format() <= p.deadlineDay.format())
                    || (p.startingDay.format() <= lastDay.format() && lastDay.format() <= p.deadlineDay.format())
                    || (firstDay.format() <= p.startingDay.format() && p.deadlineDay.format() <= lastDay.format()))
                        return true;
        return false;
    }

    public void assignTeam(Team team,Day firstDay, Day lastDay) {
        this.teamAssigned = team;
        this.startingDay = firstDay;
        this.deadlineDay = lastDay;
    }
    
    public boolean isAssigned() { return this.teamAssigned != null; }

    public void resetTeamAssignment() {
        this.teamAssigned = null;
        this.startingDay = null;
        this.deadlineDay = null;
    }

    public Day calcProjectDeadline(Day firstDay, Team t) {
        int projectTime = (int) Math.ceil((double) this.manpower / (t.getMemberCount() + 1)) - 1;
        return firstDay.forwardDays(projectTime);
    }

    @Override
    public int compareTo(Project another) { return this.projectID.compareTo(another.projectID); }
    
}
