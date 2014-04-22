jira-burndown-charts
====================

Create charts from your Jira/Greenhopper instance!

Currently, this tool creates charts from greenhopper if you are using sprints and story points.
It will create a chart with 2 series, the number of points completed every sprint, and the 4 week moving average for each sprint, ending that sprint.

Configuration 
=============
You will need to configure the API endpoints in the application.conf file

Using
=====
The project uses spray and Akka to server the web content. Simply export to a jar to get the app running on localhost, and hit the /track/[project name] url to see a graph

Preview
=======
![alt tag](chart.png)
