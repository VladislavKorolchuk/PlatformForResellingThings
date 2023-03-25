

#  Graduation project
## Group thesis. Stream - Sprint 3.0.project

Links to the provided documentation files:
- [Openapi](https://drive.google.com/file/d/1NInRupH5y59DMAFvUDcd2C0kIWaMk93Q/view).
- [Technical specification](https://skyengpublic.notion.site/02df5c2390684e3da20c7a696f5d463d)

**-GitHub Team project** https://github.com/VladislavKorolchuk/GraduateWork

**-Team Bulletin Board** (https://www.notion.so/1349500fbd564acc806ff1d4fb5d1652)

## The development team

- Korolchuk Vladislav
- Demenkov Pavel
- Anastasia Shutova
- Ruslan Gilyazev
- Novitsky Alexey
- Sobolev Alexey
- Golubev Sergey

The development of the diploma project code was carried out using the development environment IntelliJ IDEA

We also used the service for joint development and hosting of GitHub projects.


**Goal of the work:**

1. Write a fully working project according to the TOR and the stages of implementation described below. 
The link to the terms of reference is provided above.

2. Coherence and ability to work in a team, to overcome difficulties in conflict situations.

3. Writing documentation and code comments

4. Writing tests to check the project code


Stages of the graduation project

**Stage I. Setting up a Spring project.**
-Prepare the Spring project for work.
-Description of the application skeleton based on the openapi.yaml file. Add the necessary controllers and DTO classes that will return default values.
Check that the code is working and returns the default values using Postman.

**Stage II. Setting up authorization and authentication.**
-Connect the PostgreSQL database.
-Create the necessary entity classes: user, ad and review.
-Make services that map (match) from entity classes to DTO classes and back.

**Stage III. Description of ad models and reviews.**
-Writing query logic so that the front part of the project works.
-Stage IV. Adding authorization and defining permissions to controllers.
-The mandatory requirements for completing the task are as follows:
1. An anonymous user can:
   • receive a list of ads.
2. An authorized user can:
   • receive a list of ads,
   • receive one ad,
   • create an ad,
   • edit and delete your ad,
   • get a list of comments,
   • create comments,
   • edit/delete your comments.

**Stage V. Saving and receiving images.**
- At this stage, it is necessary to implement the transfer of images to the front and save images to the backend-part of the project.

**Conclusion:**

1. As a result of the thesis, a completely serviceable, tested code was written.
2. Cleaned up the code by removing unused classes, methods and comments.
3. The code was refactored using the IntelliJ IDEA development environment.
4. Written code for testing project code. 
5. At each stage, each team member had his own task that he performed, and at general meetings a decision was made 
to merge the code into the Dev branch
6. Upon completion of the task, according to the terms of reference, the Wum branch was uploaded to the master branch

