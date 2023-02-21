package com.naratmal.user.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByuserNickname(String nickname);
    User findByUserEmail(String email);
}
