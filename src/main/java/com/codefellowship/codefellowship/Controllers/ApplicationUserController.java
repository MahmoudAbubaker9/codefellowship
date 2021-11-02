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

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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
                               @RequestParam String bio,
                               @RequestParam List posts) {

        ApplicationUser newUser = new ApplicationUser(username, passwordEncoder.encode(password), firstName, lastName, dateOfBirth, bio,posts);
        newUser = applicationUserRepository.save(newUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new RedirectView("Home");
    }

    @GetMapping("/users/{id}")
    public String getProfile(Model model, @PathVariable long id) {
        try {
            ApplicationUser user = applicationUserRepository.findById(id).get();
            model.addAttribute("user", user);
            return "Profile";
        } catch (Exception e) {
            return "Profile";
        }
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername());
        model.addAttribute("userDetails", user);

        return "Profile";
    }

    @GetMapping("/posts")
    public String getPosts(@ModelAttribute PostModel posts, Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PostModel> post = postRepository.findAllByUserUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("posts", post);

        return "post";
    }

    @PostMapping("/posts")
    public RedirectView addPosts(@ModelAttribute PostModel posts) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ApplicationUser user = applicationUserRepository.findApplicationUserByUsername(userDetails.getUsername());
        posts.setUser(user);
        applicationUserRepository.save(user);
        postRepository.save(posts);
        user.setPosts(Collections.singletonList(posts));
        return new RedirectView("/profile") ;
    }

    @GetMapping("/profile/{id}")
    public String getProfilePageById(@PathVariable String id , Model model) {
        long Id = Long.parseLong(id);
        ApplicationUser user = applicationUserRepository.findApplicationUserById(Id);
        List<PostModel> list = postRepository.findAllByUserId(user.getId()).orElseThrow();
        model.addAttribute("user", user);
        model.addAttribute("posts", list);
        return "oneUser";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "/login";
    }

}