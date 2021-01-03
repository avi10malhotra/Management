
# Project Management System
The aim of this project was to use 'good programming styles' and Object-Oriented Programming (OOP) in Java to develop a Project Management System. All program files were originally coded for a course assignment, which was designed by professor Helena Wong of the City University of Hong Kong. For more information about the course, click [here](https://www.cs.cityu.edu.hk/~helena/cs2312202021A/). For more information about the assignment itself, click [here](https://www.cs.cityu.edu.hk/~helena/cs2312202021A/Assignment/Asg_2312202021A.pdf).
<br><br>
The project files altogether that encompass the management system can perform the following tasks:

 - Hire and list _employees_.
 - Setup and list _teams_.
 - Change dates.
 - Create and list _projects_.
 - Allow employees to join or change teams.
 - Allow teams to take projects.
 - Suggest the team(s) that can finish a particular project at the earliest.
 - Show an employee's history.
 - Show a project's details.
 
Note that all of the aforementioned actions, with the obvious exceptions of the last two, are encapsulated with _undo_ and _redo_  commands (written as a _abstract_ class called  **RecordedCommand** that implements an _interface_ aliased **Command**).  The **testcases/** folder on the other hand, as the name suggests, provides the framework on which the project is standardized on. It also implicitly serves as a checklist for proper usage of the Project Management System.
<br><br>
Finally, it is also worth noting that all _command_ classes are prefixed with "Cmd" whereas all _exception_ classes are prefixed with "Ex". In addition to the use of _interfaces, abstract classes_, and _exception handlers_, this project also employs use of _comparators_ and _cloneables_.  
