package com.nowcoder.weibo.service;



import com.nowcoder.weibo.dao.WeiboDAO;


import com.nowcoder.weibo.model.Weibo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by nowcoder on 2016/7/2.
 */
@Service
public class WeiboService {
    @Autowired
    private WeiboDAO weiboDAO;


    public int addWeibo(Weibo weibo) {
        weiboDAO.addWeibo(weibo);
        return weibo.getId();
    }

    public Weibo getById(int weiboId) {
        return weiboDAO.getById(weiboId);
    }

    public int updateCommentCount(int id, int count) {
        return weiboDAO.updateCommentCount(id, count);
    }
}



