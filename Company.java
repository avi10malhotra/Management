import java.util.ArrayList;
import java.util.Collections;

public class Company {

    private final ArrayList<Employee> allEmployees;
    private final ArrayList<Team> allTeams;
    private final ArrayList<Project> allProjects;

    private static final Company instance = new Company();

    private Company() {
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
        allProjects = new ArrayList<>();
    }

    public static Company getInstance() { return instance; }

    // Employee subdivision
    public Employee createEmployee(String name) throws ExDuplicateEmployee {
        if (this.employeeExists(name))
            throw new ExDuplicateEmployee();

        Employee e = new Employee(name);
        allEmployees.add(e);
        Collections.sort(allEmployees);
        return e;
    }
    
    public void addEmployee(Employee e) {
        allEmployees.add(e);
        Collections.sort(allEmployees);
    }
    
    public void removeEmployee(Employee e) {
        allEmployees.remove(e);
    }

    public Employee findEmployee(String name) throws ExEmployeeNotFound {
        return Employee.searchEmployee(allEmployees, name);
    }

    public boolean employeeExists(String name) {
        try {
            this.findEmployee(name);
            return true;
        } catch (ExEmployeeNotFound e) {
            return false;
        } 
    }
    
    public void listEmployees() { Employee.list(allEmployees); }
    // End of Employee subdivision
    
    // Team subdivision
    public Team createTeam(String teamName, Employee leader) throws ExDuplicateTeam {
        if (this.teamExists(teamName))
            throw new ExDuplicateTeam();

        Team t = new Team(teamName, leader);
        allTeams.add(t);
        Collections.sort(allTeams);
        return t;
    }
    
    public void addTeam(Team t) {
        allTeams.add(t);
        Collections.sort(allTeams);
    }
    
    public void removeTeam(Team t) { allTeams.remove(t); }

    public Team findTeam(String name) throws ExTeamNotFound {
        return Team.searchTeam(allTeams, name);
    }

    public boolean teamExists(String name) {
        try {
            this.findTeam(name);
            return true;
        } catch (ExTeamNotFound e) {
            return false;
        }
    }

    public void isLeader(Employee leader) throws ExEmployeeAssigned {
        if (Team.isLeader(allTeams, leader))
            throw new ExEmployeeAssigned();
    }

    public void isAssigned(Employee member) throws ExEmployeeAssigned {
        if (Team.isAssigned(allTeams, member))
            throw new ExEmployeeAssigned();
    }
    
    public void listTeams() { Team.list(allTeams); }
    // End of Team subdivision
    
    // Projects subdivision
    public Project createProject(String projectName, int estManpower) throws ExDuplicateProject {
        if (this.projectExists(projectName))
            throw new ExDuplicateProject();
            
        Project p = new Project(projectName, estManpower);
        allProjects.add(p);
        Collections.sort(allProjects);
        return p;
    }
    
    public void addProject(Project p) {
        allProjects.add(p);
        Collections.sort(allProjects);
    }

    public void removeProject(Project p) { allProjects.remove(p); }

    public Project findProject(String name) throws ExProjectNotFound {
        return Project.searchProject(allProjects, name);
    }

    public boolean projectExists(String name) {
        try {
            this.findProject(name);
            return true;
        } catch (ExProjectNotFound e) {
            return false;
        }
    }

    public void isTeamAvailable(Team t, Day day1, Day day2) throws ExTeamUnavailable {
        if (Project.isTeamBusy(allProjects, t, day1, day2))
            throw new ExTeamUnavailable();
    }

    public void listProjects() { Project.list(allProjects); }

    public void isProjectAvailable(Project p) throws ExAttemptedProjectReassignment {
        if (p.isAssigned())
            throw new ExAttemptedProjectReassignment();
    }
	
	public ArrayList<Team> findUrgentProjectTeam(Project urgentProject) {
        for (Team t : allTeams)
            t.deleteUrgentProject();
        return Project.calcBestSuitedTeam(allTeams, urgentProject);
	}
    
    public void showProjectDetails(Project project) { project.showDetails(allEmployees); }
    // End of Project subdivision
}
/* End of class */