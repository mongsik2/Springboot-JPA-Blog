package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert // insert시에 null인 필드를 제외 ex) 원래 6개 파라미터를 넣어야하는데 4개만 넣으면 나머지 2개 null필드 제외해서 넣음
@Entity //User 클래스가 Mysql에 자동으로 생성
public class User {

    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라감.
    private int id; // 시퀀스, auto_increment

    @Column(nullable = false, length = 30, unique = true)
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 123456 => 해쉬
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //@ColumnDefault("'user'")
    //private String role; // Enum을 쓰는게 좋다. //admin, user, manager

    // DB는 RoleType이라는 게 없다
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp // 시간이 자동으로 입력
    private Timestamp createDate;
}
