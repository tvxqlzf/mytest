package com.msr.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.msr.mybatisplus.entity.User;
import com.msr.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QueryWrapperTests {

    @Autowired
    private UserMapper userMapper;

    //  ge、gt、le、lt、isNull、isNotNull
    //  SQL：UPDATE user SET deleted=1 WHERE deleted=0 AND name IS NULL AND age >= ? AND email IS NOT NULL
    @Test
    public void testDelete(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .isNull("name")
                .ge("age",12)
                .isNotNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println("delete return count = " + result);

    }

    //  eq、ne
    //  SELECT id,name,age,email,create_time,update_time,deleted,version FROM user WHERE deleted=0 AND name = ?
    @Test
    public void testSelectOne(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name","Tom");
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    //  between、notBetween
    //  SELECT COUNT(1) FROM user WHERE deleted=0 AND age BETWEEN ? AND ?
    @Test
    public void testSelectCount(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("age",20,30);
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    //  allEq
    //  SELECT id,name,age,email,create_time,update_time,deleted,version FROM user WHERE deleted=0 AND name = ? AND id = ? AND age = ?
    @Test
    public void testSelectList(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        Map<String,Object> map = new HashMap<>();
        map.put("id",2);
        map.put("name","jack");
        map.put("age",20);

        queryWrapper.allEq(map);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }

    //  like、notLike、likeLeft、likeRight
    //  SELECT id,name,age,email,create_time,update_time,deleted,version FROM user WHERE deleted=0 AND name NOT LIKE ? AND email LIKE ?
    @Test
    public void testSelectMaps(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .notLike("name","e")
                .likeRight("email","t");
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);//返回值是Map列表
        maps.forEach(System.out::println);
    }

    //  in、notIn、inSql、notinSql、exists、 notExists
    //  SELECT id,name,age,email,create_time,update_time,deleted,version FROM user WHERE deleted=0 AND id IN (select id from user where id < 3)
    @Test
    public void testSelectObjs(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id","select id from user where id < 3");
        List<Object> objects = userMapper.selectObjs(queryWrapper);
        objects.forEach(System.out::println);
    }

    //  or、and 使用的是 UpdateWrapper 默认为使用 and
    //  UPDATE user SET name=?, age=?, update_time=? WHERE deleted=0 AND name LIKE ? OR age BETWEEN ? AND ?
    @Test
    public void testUpdate1(){
        //修改值
        User user = new User();
        user.setAge(99);
        user.setName("Andy");

        //修改条件
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name","s")
                .or()
                .between("age",20,30);
        int result = userMapper.update(user, userUpdateWrapper);
        System.out.println(result);
    }

    //  嵌套or、嵌套and
    //UPDATE user SET name=?, age=?, update_time=? WHERE deleted=0 AND name LIKE ? OR ( name = ? AND age <> ? )
    @Test
    public void testUpdate2(){
        User user = new User();
        user.setAge(98);
        user.setName("lily");

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name","h")
                .or(i -> i.eq("name","礼拜").ne("age",20));
        int result = userMapper.update(user, userUpdateWrapper);
        System.out.println(result);

    }

    //orderBy、orderByDesc、orderByAsc
    //SELECT id,name,age,email,create_time,update_time,deleted,version FROM user WHERE deleted=0 ORDER BY id DESC
    @Test
    public void testSelectListOrderBy(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    //last
    //SELECT id,name,age,email,create_time,update_time,deleted,version FROM user WHERE deleted=0 limit 1
    @Test
    public void testSelectListLast(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.last("limit 1");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);

    }

    //指定要查询的列
    //SELECT id,name,age FROM user WHERE deleted=0
    @Test
    public void testSelectListColumn(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","name","age");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    //set、setSql
    //UPDATE user SET age=?, update_time=?, name=?, email = '123@qq.com' WHERE deleted=0 AND name LIKE ?
    @Test
    public void testUpdateSet(){
        User user = new User();
        user.setAge(99);

        UpdateWrapper<User>  userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper
                .like("name","h")
                .set("name","老李")
                .setSql("email='123@qq.com'");//可以有子查询

        int result = userMapper.update(user, userUpdateWrapper);
    }


}
