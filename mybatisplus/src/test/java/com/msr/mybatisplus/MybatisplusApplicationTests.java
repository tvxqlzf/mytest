package com.msr.mybatisplus;

import com.msr.mybatisplus.entity.User;
import com.msr.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MybatisplusApplicationTests {

    //1、引入UserMapper对象
    @Autowired
    private UserMapper userMapper;

    @Test
    public void contextLoads() {
        List<User> usersList = userMapper.selectList(null);
        for (User user : usersList) {
            System.out.println(user.getId() + "\t" + user.getName());
        }
    }

}
