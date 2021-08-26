package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    /**
     * Check if user already exist by username
     * @param username the unique Id user
     * @return true if user already exist
     */
    boolean existsByUsername(String username);

    /**
     * Find a list of users sorted by id desc
     *
     * @return a list of all users sorted by id desc
     */
    List<User> findAllByOrderByIdDesc();

    User findUserByUsername(String username);
}
