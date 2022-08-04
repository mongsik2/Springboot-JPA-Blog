package com.cos.blog.test;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import net.bytebuddy.TypeCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable int id) {

        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }

        return "삭제되었습니다." + id;
    }

    // save함수는 id를 전달하지 않으면 insert를 해주고
    // save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    // svae함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert
    //password, email
    @Transactional // 함수 종료시 자동 commit
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){ //json 데이터를 요청 => Java Object
        System.out.println("id : "+id);
        System.out.println("password : "+requestUser.getPassword());
        System.out.println("email : "+requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        System.out.println(user.getUsername());
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        //userRepository.save(user);
        
        // 더티 체킹
        return user;
    }

    // http://localhost:9090/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    // 한 페이지 당 2건에 데이터를 리턴받아 올 예정
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2,sort = "id",direction = Sort.Direction.DESC)Pageable pageable){
        Page<User> pagingUsers = userRepository.findAll(pageable);

        List<User> users = pagingUsers.getContent();
        return users;
    }


    // {id} 주소로 파라미터를 전달 받을 수 있음
    // http://localhost:9090/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){

        // user/4를 찾으면 내가 데이터베이스에서 못찾아오게 되면 user가 null이 될 것 아냐? 현재 3개
        // 그럼 return null 이 리턴이 되게됨. 프로그램 문제 생김
        // Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return 해!
        // User user = userRepository.findById(id).get() 으로 에러의 원인을 사용자에게 전달을 못함
        
        // 람다식
        /*
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalStateException("해당 사용자는 없습니다.");
        });
        */

        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
            }
        });
        // 요청 : 웹 브라우저 -> 자바스크립트나 html 정도 이해함
        // user 객체는 = 자바 오브젝트
        // 변환 (웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
        // 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
        // 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
        // user 오브젝트를 json으로 변환해서 브라우저에게 던져줌.
        return user;
    }

    //http://localhost:dummy/join (요청)
    // http의 body에 username, passward, email 데이터를 가지고 (요청)
    @PostMapping("/dummy/join")
    //public String join(String username, String passward, String email){ // Key=value (약속된 규칙)
    public String join(User user){ // Key=value (약속된 규칙)
        System.out.println("username : " + user.getUsername());
        System.out.println("passward : " + user.getPassword());
        System.out.println("email : " + user.getEmail());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
