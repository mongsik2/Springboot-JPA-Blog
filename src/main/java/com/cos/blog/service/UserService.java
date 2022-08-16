package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해줌.
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Transactional(readOnly = true)
    public User 회원찾기(String username){

        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return user;
    }


    @Transactional
    public void 회원수정(User user){
        // 수정 시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
        // select를 해서 User오브젝트를 DB로부터 가져오는 이유는 영속화하기 위해서!!
        // 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌.
        User persistence = userRepository.findById(user.getId()).orElseThrow(()->{
            return new IllegalArgumentException("해당 아이디를 찾을 수 없습니다.");
        });

        // Vaildation Check
        if(persistence.getOauth() == null || persistence.getOauth().equals("")){
            String rawPassword = user.getPassword();
            String encPassword = encoder.encode(rawPassword);
            persistence.setPassword(encPassword);
            persistence.setEmail(user.getEmail());
        }


        // 회원 수정 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동.
        // 영속화된 persistence 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려줌.
    }

    @Transactional
    public void 회원가입(User user){
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
        /*try {
            userRepository.save(user);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("UserService : 회원가입() :" +e.getMessage());
        }
        return -1;*/
    }

    //전통 로그인 방식
    /*@Transactional(readOnly = true) // Select할 때 트랜잭션 시작, 서비스 종료시 트랜잭션 종료 (정합성)
    public User 로그인(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }*/


}
