package com.codefellowship.codefellowship.Controllers;


import com.codefellowship.codefellowship.Models.ApplicationUser;
import com.codefellowship.codefellowship.Models.PostModel;
import com.codefellowship.codefellowship.Repositories.ApplicationUserRepository;
import com.codefellowship.codefellowship.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    /// Home Page
    @GetMapping("/")
    public String getRoot(Principal p,Model m) {
            if(p != null)
                m.addAttribute("username",p.getName());
        return "Home";
    }

    /// Signup Page
    @GetMapping("/signup")
    public String getSignUp(){
        return "Signup";
    }


    @PostMapping("/signup")
    public RedirectView signup(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String dateOfBirth,
                               @RequestParam String bio) {

        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName, lastName, dateOfBirth, bio);
        newUser = applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("/");
    }


    /// Login Page
    @GetMapping("/login")
    public String getLogin(){
        return "/login";
    }

    @GetMapping("/users/{id}")
    public String getUser(Principal p,Model m, @PathVariable long id){
        try {
            String username = p.getName();
            ApplicationUser currentUser = applicationUserRepository.findUserByUsername(username);
            ApplicationUser user = applicationUserRepository.findById(id).get();
            System.out.println(currentUser.getUsers());
            boolean isFollowed = currentUser.isFollowedUser(user);
            boolean isSameUser = false;
            if(username.equals(user.getUsername()))
                isSameUser = true;
            m.addAttribute("user", user);
            m.addAttribute("isSameUser", isSameUser);
            m.addAttribute("username", username);
            m.addAttribute("isFollowed", isFollowed);
            return "Profiles";
        }
        catch(Exception e){
            return "Profiles";
        }

    }

    /// Profile Page
    @GetMapping("/myprofile")
    public RedirectView displayProfile(Principal principal){
        String name = principal.getName();
        ApplicationUser user = applicationUserRepository.findUserByUsername(name);
        long id = user.getId();
        return new RedirectView("/users/"+id);
    }


    @GetMapping("/profile")
    public String getPosts(Model model, Principal principal){
        String username = principal.getName();
        ApplicationUser user = applicationUserRepository.findUserByUsername(username);
        long id = user.getId();
        List<PostModel> posts = postRepository.findAllByUserId(id);

        model.addAttribute("posts",posts);
        model.addAttribute("username",principal.getName());
        return "MyProfile";
    }


    /// Post Page
    @PostMapping("/addPost")
    public RedirectView createPost(Principal principal, String body,Model model){
        String username = principal.getName();
        ApplicationUser user = applicationUserRepository.findUserByUsername(username);
        PostModel newPost = new PostModel(body,user);
        postRepository.save(newPost);
        return new RedirectView("/profile");
    }

    @GetMapping("/addPost")
    public String getAddPost(){
        return "Post";
    }

    /// Follow + Unfollow
    @PostMapping("/followUser/{username}")
    public RedirectView followUser(Principal principal,@PathVariable String username){
        ApplicationUser currentUser = applicationUserRepository.findUserByUsername(principal.getName());
        ApplicationUser userWantedToFollow = applicationUserRepository.findUserByUsername(username);
        currentUser.addFollowToUser(userWantedToFollow);
        applicationUserRepository.save(currentUser);
        return new RedirectView("/users/"+userWantedToFollow.getId());
    }
    @PostMapping("/unfollowUser/{username}")
    public RedirectView unFollow(Principal principal,@PathVariable String username){
        ApplicationUser currentUser = applicationUserRepository.findUserByUsername(principal.getName());
        ApplicationUser userWantedToUnFollow = applicationUserRepository.findUserByUsername(username);
        currentUser.unFollowUser(userWantedToUnFollow);
        applicationUserRepository.save(currentUser);
        return new RedirectView("/users/"+userWantedToUnFollow.getId());
    }

    /// Page will show following post
    @GetMapping("/feed")
    public String showFeed(Model model, Principal principal){
        String username = principal.getName();
        ApplicationUser user = applicationUserRepository.findUserByUsername(username);
        List<Long> ids = user.getUsers().stream().map(u -> u.getId()).collect(Collectors.toList());
        List < List<PostModel> > postsOfUsers = ids.stream().map(i -> postRepository.findAllByUserId(i)).collect(Collectors.toList());
        model.addAttribute("postsOfUsers",postsOfUsers);
        model.addAttribute("username",principal.getName());
        return "Feeds";
    }

}