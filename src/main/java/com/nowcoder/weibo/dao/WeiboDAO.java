package com.nowcoder.weibo.dao;



import com.nowcoder.weibo.model.Weibo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/2.
 */
@Mapper
public interface WeiboDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " content, image, created_date, user_id,comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{content},#{image},#{createdDate},#{userId})"})
    int addWeibo(Weibo weibo);

    @Select({"select ", SELECT_FIELDS , " from ", TABLE_NAME, " where id=#{id}"})
    Weibo getById(int id);
    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);

}
