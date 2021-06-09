package com.ngandang.intern.reporitory;

import com.ngandang.intern.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameAndPassword(String username, String password );
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM users join user_roles ON users.id = user_roles.user_id " +
                    "where user_roles.role_id = ?1 and users.id= ?1"
            , nativeQuery=true)
    Optional<User> findUserByRole (Integer roleId,Integer userId);

    Optional<User> findUserById (Integer id);

}
