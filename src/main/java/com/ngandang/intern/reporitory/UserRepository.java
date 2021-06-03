package com.ngandang.intern.reporitory;

import com.ngandang.intern.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsernameAndPassword(String username, String password );
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
