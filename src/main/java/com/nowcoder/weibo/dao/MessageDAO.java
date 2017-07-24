package com.nowcoder.weibo.dao;


import com.nowcoder.weibo.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = "Message";
    String INSERT_FIELDS = "content ，image, created_date, user_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{image},#{content},#{createdDate},#{userId})"})
    int addMessage(Message message);

    @Select({"select ", SELECT_FIELDS , " from ", TABLE_NAME, " where id=#{id}"})
    Message getById(int id);





    List<Message> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                       @Param("limit") int limit);
}
