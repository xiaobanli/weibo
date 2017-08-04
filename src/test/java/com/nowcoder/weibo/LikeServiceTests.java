package com.nowcoder.weibo;

import com.nowcoder.weibo.service.LikeService;
import org.junit.*;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lenovo on 2017/8/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LikeServiceTests {
    @Autowired
    LikeService likeService;
    @Test
    public void testLike(){
        likeService.like(12,1,3);
        Assert.assertEquals(likeService.getLikeStatus(12,1,3),1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException() {
        throw new IllegalArgumentException("异常");
    }
    @Before
    public void setUp(){

    }
    @After
    public void tearDown(){

    }

@BeforeClass
    public static void beforeClass(){

}
    @AfterClass
    public static void afterClass(){}

}
