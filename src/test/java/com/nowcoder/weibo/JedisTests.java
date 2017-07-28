package com.nowcoder.weibo;


import com.nowcoder.weibo.model.User;
import com.nowcoder.weibo.util.JedisAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;



public class JedisTests {
    @Autowired
    JedisAdapter jedisAdapter;

    @Test
    public void testObject() {
        User user = new User();
        user.setHeadUrl("http://image.nowcoder.com/head/100t.png");
        user.setName("user1");
        user.setPassword("pwd");;
        user.setSalt("salt");

        jedisAdapter.setObject("user1xx", user);

        User u = jedisAdapter.getObject("user1xx", User.class);

        System.out.println(ToStringBuilder.reflectionToString(u));

    }

}
