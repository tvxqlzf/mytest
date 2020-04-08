package com.msr.mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.msr.mybatisplus.entity.User;
import com.msr.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CRUDTest {

    @Autowired
    private UserMapper userMapper;

   @Test
    public void contextLoads() {
       User user = userMapper.selectById(1);
       System.out.println(user);
    }

    //  更新
    @Test
    public void testInsert(){
       User user = new User();
       user.setName("小李");
       user.setAge(20);
       user.setEmail("tvv@gmail.com");
       user.setId(10L);

       userMapper.insert(user);
    }
    //  修改
    @Test
    public void testUpdate(){
       User user = userMapper.selectById(1);
       user.setName("aa");

       userMapper.updateById(user);
    }
    //  测试 乐观锁插件
    @Test
    public void testOptimisticLocker(){
       User user = userMapper.selectById(1L);
       user.setName("helen");
       user.setEmail("helen@qq.com");
       userMapper.updateById(user);
    }

    //  分页测试
    @Test
    public void testPage(){
        Page<User> page = new Page<>(1,5);
        IPage<User> userPage = userMapper.selectPage(page, null); //有数据
        System.out.println(userPage.getPages());
        System.out.println(userPage.getCurrent());
        System.out.println(userPage.getRecords());
        System.out.println(userPage.getSize());
        System.out.println(userPage.getTotal());
    }

    //  通过多个id批量查询
    @Test
    public void testSelectBatchIds(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //  通过map封装查询条件
    @Test
    public void testSelectByMap(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","李");
        map.put("age",18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //  根据id删除记录
    @Test
    public void testDeleteById(){
       int result = userMapper.deleteById(3L);
        System.out.println(result);
    }

    //  批量删除
    @Test
    public void testDeleteBatchIds(){
        int result = userMapper.deleteBatchIds(Arrays.asList(1, 2, 4));

        System.out.println(result);
    }

    //  简单的条件查询删除
    @Test
    public void testDeleteByMap(){
       HashMap<String,Object> map = new HashMap<>();
       map.put("name","li");
       map.put("age",18);

        int result = userMapper.deleteByMap(map);
        System.out.println(result);
    }

    //  测试 逻辑删除
    @Test
    public void testLogicDelete(){
        int result = userMapper.deleteById(1L);
        System.out.println(result);
    }

    /**
     *  测试 逻辑删除后的查询：
     *  不包括被逻辑删除的记录
     */
    @Test
    public void testLogicDeleteSelect(){
        User user = new User();
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    /**
     * 测试 性能分析插件
     */


}
