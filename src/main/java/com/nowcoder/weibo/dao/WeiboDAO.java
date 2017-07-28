package com.nowcoder.weibo.dao;



import com.nowcoder.weibo.model.Weibo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by lenonvo on 2017/7/2.
 */
@Mapper
public interface WeiboDAO {
    String TABLE_NAME = " weibo ";
    String INSERT_FIELDS = " content, image, created_date, user_id, comment_count ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{content},#{image},#{createdDate},#{userId},#{commentCount})"})
    int addWeibo(Weibo weibo);

    @Select({"select ", SELECT_FIELDS , " from ", TABLE_NAME, " where id=#{id}"})
    Weibo getById(int id);
    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);
    @Select({"select",})
    List<Weibo> selectByUserIdAndOffset(@Param("userId") int userId, @Param("offset") int offset,
                                       @Param("limit") int limit);
    @Update({"update ", TABLE_NAME, " set like_count = #{likeCount} where id=#{id}"})
    int updateLikeCount(@Param("id") int id, @Param("likeCount") int likeCount);
}
