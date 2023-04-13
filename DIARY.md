# Restaurant Review API Project

From: https://www.codecademy.com/paths/create-rest-apis-with-spring-and-java/tracks/spring-apis-portfolio-project/modules/spring-dining-review-api/kanban_projects/dining-review-api

For this project, you will build an application using everything you’ve learned so far. You’ll start from scratch and finish with a working API of your own. This is your chance to showcase your skills to potential employers and teammates!

We’ve provided the high-level tasks to complete this project, which includes a few new techniques you may have to research on your own. If you’d like to challenge yourself even further, keep building! This project can be extended in a variety of ways as you learn more Spring concepts.

Project Objectives:
- Construct a RESTful web API with data persistence using Spring and Spring Data JPA
- Use Spring Initializr to generate the initial Java project
- Configure application properties for certain dependencies, including the H2 embedded database
- Define the entities that comprise this application scenario
- Define the repositories that enable creating, updating, and querying these different entities
- Define the API contracts that will enable this application scenario
- Leverage the convenience of Lombok
- Use cURL to test your API scenarios

Additional Challenge(set personally)
- Use Contract First approach
- Design yaml using Swagger Editor
- Explore Spring Security (step a: Use hardcoded user details. step b: use user database. step 3: use JWT?)

Diary:
- Tue 14th March: Restarted the project so I could take a contract first approach (different to the Codecademy project). Created the yaml file for the intended api, but am starting by implementing a small section of it at a time so I can understand how each part works without becoming confused. Used the OpenApi plugin to create the interfaces within the maven project - this took a while to get working as there were some dependancy issues. To implement this I have used a multi module setup (again, new to me today). I'm now at the stage where I can start building out some of the Api endpoints and will learn how to test with Postman as I go - that's the plan anyway!
- Wed 15th March: A day of headaches attempting to get my head around using the multi module setup and mapping Entity to DTO (api side and database side). A bit of a two steps forward, three back kind of day. On the positive side came across lots of new concepts and have probably learnt something! Day rounded out with a git issue, thankfully sorted eventually.
- Tue 21st March: Yesterday Rich helped me finally get the base project setup - looking back I had come close to the solution on a number of attempts but was mssing one key piece of information regarding the config options of the OpenApi Maven Plugin. Today I was able to push ahead and create the restaurant end points - get all, add, get by id, update and delete are now working and ready to be improved. Before adding the user and review endpoints I'm going to improve what I have, create tests and learn postman.
- Thur 23rd March: Updated the code to use MapStruct and moved logic from controller to service.
- Fri 24th March: Basic InMemory Authentication implemented using Spring Security 6. Next steps will be to create the user and review endpoints, and set up the authentication to work with the users stored in the database. Database tables need to be updated to store the creator user id so they (as well as admins) can update and edit their entries. Users will need to have a role assigned to them.
- Mon 27th March: Added the user endpoints to yaml, alongside the basic endpoint logic. Updated mapper re: rich's comments. Began adding integration tests (before upgrading security to use the h2 database.)
- Wed 29th March: Spent yesterday and today getting my head around integration tests. Have a strange failing test in restaurants - it should expect a 401 (which it gets) but for some reason it's saying it expects a 201? Shall come back to this in the future. Plan is to finish user tests tomorrow and implement the review endpoints before update security to use users within the database.
- Thur 30th March: Productive day - managed to get security using JPA setup so now users in the database can have ownership over restaurant creation, editing and deletion, as can admins. Finished off a few more integration test this morning but may need to update to take into account to new security settings. Tomorrow the plan is to get all the review endpoints created, and any logic to pull restaurant ratings through from the input reviews.
- Fri 31st March: Review endpoints in place. Review ratings now update the restaurant rating automatically.  Tweaks to security and review logic. Need to update the tests to take into account the updated setup.
- Mon 3rd April: Back on integration tests. 
- Tuesday 11th April: Updated documentation. Changed database tables to link to other tables. Removed the lombok @EqualsAndHashCode from entity classes. Updated yaml for api version control.
- Thursday 13th April: Failed on the JWT front yesterday so went back to where I was on tuesday. Seem to have got the authentication working with the JWT token alongside the database of users which is good. Not sure I understand how exactly, but it's a good start. The entity classes are still using the incorrect lombok so that needs re-fixing, and unit and integration tests need updating.

To Do:
- Integration Tests end in IT and own folder
- Create unit tests
- JWT
- When restaurant updated, rating returns to null