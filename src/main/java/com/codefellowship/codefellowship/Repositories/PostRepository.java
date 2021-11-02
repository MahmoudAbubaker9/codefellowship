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
    List<PostModel> findAllByUserId(long user_id);
}
