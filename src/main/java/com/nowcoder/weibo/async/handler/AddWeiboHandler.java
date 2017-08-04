package com.nowcoder.weibo.async.handler;


import com.nowcoder.weibo.async.EventHandler;
import com.nowcoder.weibo.async.EventModel;
import com.nowcoder.weibo.async.EventType;
import com.nowcoder.weibo.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by nowcoder on 2016/8/28.
 */
@Component
public class AddWeiboHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(AddWeiboHandler.class);
    @Autowired
    SearchService searchService;

    @Override
    public void doHandle(EventModel model) {
        try {
            searchService.indexWeibo(model.getEntityId(),
                    model.getExt("content"));
        } catch (Exception e) {
            logger.error("增加微博索引失败");
        }
    }

    @Override
    public List<EventType> getSupportEventTypes() {

        return Arrays.asList(EventType.ADD_WEIBO);
    }
}
