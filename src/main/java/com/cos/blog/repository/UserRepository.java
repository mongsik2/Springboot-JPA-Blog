package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// DAO
// 자동으로 bean등록이 된다.
// @Repository // 생략이 가능하다
public interface UserRepository extends JpaRepository<User, Integer> {
    // SELECT * FROM user WHERE username =1?;
    Optional<User> findByUsername(String username);
}

// JPA 네이밍 쿼리
// SELECT * FROM user WHERE username =?1 AND password =?2;
// 전통 로그인 쓰는법
//User findByUsernameAndPassword(String username, String password);

    /*// 같은거
    @Query(value="SELECT * FROM user WHERE username =?1 AND password =?2", nativeQuery = true)
    User login(String username, String password);*/