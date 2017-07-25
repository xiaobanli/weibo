package com.nowcoder.weibo.service;

import com.nowcoder.weibo.dao.CommentDAO;
import com.nowcoder.weibo.model.Comment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/7.
 */
@Service
public class CommentService {


    @Autowired
    CommentDAO commentDAO;

    public List<Comment> getCommentsByEntity(int entityId, int entityType) {
        return commentDAO.selectByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }
    public void deleteComment(int entityId, int entityType) {
        commentDAO.updateStatus(entityId, entityType, 1);
    }

}
