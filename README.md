# codefellowship lab 16

### About CodeFellowship
App that allows people to learn programming to connect with each other and support each other on their coding journeys


Feature : 

1. an app that allows users to create their profile on CodeFellowship.

2. The site have a splash page at the root route (/) that contains basic information about the site, as well as a link to the “sign up” page.

3. An ApplicationUser have a username, password (hashed using BCrypt), firstName, lastName, dateOfBirth, bio.

4. The site allow users to create an ApplicationUser on the “sign up” page.

5. Controller have an @Autowired private PasswordEncoder passwordEncoder.

6. The site have a page which allows viewing the data about a single ApplicationUser, at a route like /profile.

7. This include a default profile picture, which is the same for every user, and their basic information.

8. When a user is logged in, the app display the user’s username on every page.

9. homepage, login, and registration routes are accessible to non-logged in users. All other routes are limited to logged-in users.

10. The site use reusable templates for its information.

11. The site have a non-whitelabel error handling page that lets the user know, at minimum, the error code and a brief message about what went wrong.

12. Post entity has a body and a createdAt timestamp.

13. A logged-in user able to create a Post, and a post should belong to the user that created it.

14. A user’s posts visible on their profile page.

# Spring Authorization lab 17

### About Lab

in this lab i add login to the CodeFellowship app, and allow users to create posts and show their post in profile page.

Feature : 

1. Create My Profile page to display user infromation
2. a user's can add post 
3. A user’s posts should be visible on their profile page.
4. create signout button.

# Spring Security against User Input lab 18

### About Lab

in this lab will allow users to follow other users. Following a user means that their posts show up in the logged-in user’s feed, where they can see what all of their followed users have posted recently.


Feature :

1. On a user profile page display a “Follow” button. When a user clicks that follow button.
2. A user can visit a url (like /feed) to view all of the posts from the users that they follow
3. Each post should have a link to the user profile of the user who wrote the post.


