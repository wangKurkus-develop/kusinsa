package com.kurkus.kusinsa.entity;
import com.kurkus.kusinsa.enums.UserType;
import com.kurkus.kusinsa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void userBuilderTest() throws Exception {
        User user = User.builder()
                .password("123")
                .phoneNumber("123123123")
                .email("hello")
                .address("주소")
                .name("최왕규")
                .type(UserType.USER)
                .build();
        userRepository.save(user);
//
//        System.out.println(user.getId());
//        User user1 = userRepository.findById(user.getId()).get();
//        Assertions.assertThat("최왕규").isEqualTo(user1.getName());
    }
}