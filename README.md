# HealthMonitor project repository

**1. [Description. ] (#desc)**
**2. [Usage Instruciton. ] (#usage)**


<a name="desc"></a>
## 1. Description



<a name="usage"></a>
## 2 Instruction to Open the Project:

1) Open the Project using the pom.xml file from top level of the project file.


2) Make sure Java SDK has been added to the file, otherwise check it from the
    project structure, the file option top left hand side. In the Project 
    structure choose project.
    
3) If Driver class dosen't show the running option then choose Module from the
    project structure and select the 'src' as sources.

Setup of the Machine Learning program:

1) Installation of the of required modules.
   * pip install -r requirements.txt  for the python2
   * pip3 install -r requirements.txt for the python3
   or
   * python -m pip install -r requirements.txt

2) Two data set are available that are fetched from the server, both of the files don't have NULL position.

3) Running the MLDrive from the java project(src/MachineLearning) generate the data sets that are fetched from the server.

4) Result of the accuracy would show at the console.

