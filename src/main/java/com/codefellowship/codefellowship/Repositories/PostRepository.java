package com.codefellowship.codefellowship.Repositories;

import com.codefellowship.codefellowship.Controllers.ApplicationUserController;
import com.codefellowship.codefellowship.Models.ApplicationUser;
import com.codefellowship.codefellowship.Models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostModel, Long> {
    Optional< List<PostModel> > findAllByUserUsername(String username);
    Optional< List<PostModel> > findAllByUserId(Long id);
}
