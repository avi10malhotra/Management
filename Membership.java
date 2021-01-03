import java.util.ArrayList;

public class Membership {
	private final String employeeName;
	private final ArrayList<Team> teamsJoined;
	private final ArrayList<Day> firstTeamDay;
	private final ArrayList<Day> lastTeamDay;
	
	public Membership(String name) {
		employeeName = name;
		teamsJoined = new ArrayList<>();
		firstTeamDay = new ArrayList<>();
		lastTeamDay = new ArrayList<>();
		
	}
	
	public void joinTeam(Team team) {
		if (!teamsJoined.isEmpty())
			lastTeamDay.add(SystemDate.getInstance().prev().clone());
		firstTeamDay.add(SystemDate.getInstance().clone());
		teamsJoined.add(team);
	}
	
	public void removeTeamUpdate() {
		if (!lastTeamDay.isEmpty())
			lastTeamDay.remove(lastTeamDay.size() - 1);
		firstTeamDay.remove(firstTeamDay.size() - 1);
		teamsJoined.remove(teamsJoined.size() - 1);
	}
	
	public void showHistory(Employee e) {
		System.out.printf("The teams that %s has joined:\n", employeeName);
		if (teamsJoined.get(0).getTeamHead().equals(e)) // Remember: a team leader cannot switch teams
			System.out.printf("%s (%s to --), as a leader\n", teamsJoined.get(0), firstTeamDay.get(0));
		else {
			int i = 0;
			for (; i < teamsJoined.size() - 1; i++)
				System.out.printf("%s (%s to %s)\n", teamsJoined.get(i), firstTeamDay.get(i), lastTeamDay.get(i));
			System.out.printf("%s (%s to --)\n", teamsJoined.get(i), firstTeamDay.get(i));
		}
	}
	
	public void checkTeamHistory(Team targetTeam, Day projectFirstDay, Day projectDeadline) {
		// Employee never changed teams
		if (teamsJoined.size() == 1 && targetTeam.equals(teamsJoined.get(0))) {
			if (firstTeamDay.get(0).format() < projectFirstDay.format())
				System.out.printf("\t%s (%s to %s)\n", employeeName, projectFirstDay, projectDeadline);
			else
				System.out.printf("\t%s (%s to %s)\n", employeeName, teamsJoined.get(0), projectDeadline);
			return;
		}
		
		for (int i = 0; i < teamsJoined.size(); i++)
			if (targetTeam.equals(teamsJoined.get(i)))
				if (lastTeamDay.size() == i)
					// Employee was a member throughout the project
					if (firstTeamDay.get(i).format() <= projectFirstDay.format())
						System.out.printf("\t%s (%s to %s)\n", employeeName, projectFirstDay, projectDeadline);
					else // Employee joined mid-project but did not change teams
						System.out.printf("\t%s (%s to %s)\n", employeeName, firstTeamDay.get(i), projectDeadline);
				// Employee left mid-project
				else if (projectDeadline.format() > lastTeamDay.get(i).format()
						&& projectFirstDay.format() <= lastTeamDay.get(i).format())
					System.out.printf("\t%s (%s to %s)\n", employeeName, projectFirstDay, lastTeamDay.get(i));
				// Employee joined mid-project
				else if (projectFirstDay.format() < firstTeamDay.get(i).format()
						&& firstTeamDay.get(i).format() <= projectDeadline.format())
					System.out.printf("\t%s (%s to %s)\n", employeeName, firstTeamDay.get(i), projectDeadline);
				// Employee joined and left mid-project
				else if (projectFirstDay.format() < firstTeamDay.get(i).format()
			            && lastTeamDay.get(i).format() < projectDeadline.format())
					System.out.printf("\t%s (%s to %s)\n", employeeName, firstTeamDay.get(i), lastTeamDay.get(i));
	}
}
