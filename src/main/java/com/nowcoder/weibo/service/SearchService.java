package com.nowcoder.weibo.service;


import com.nowcoder.weibo.model.Weibo;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by nowcoder on 2016/8/28.
 */
@Service
public class SearchService {
    private static final String SOLR_URL = "http://127.0.0.1:8983/solr/weibo";
    private HttpSolrClient client = new HttpSolrClient.Builder(SOLR_URL).build();
    private static final String FIELD = "weibo";


    public List<Weibo> searchWeibo(String keyword, int offset, int count,
                                      String hlPre, String hlPos) throws Exception {
        List<Weibo> weiboList = new ArrayList<>();
        SolrQuery query = new SolrQuery(keyword);
        query.setRows(count);
        query.setStart(offset);
        query.setHighlight(true);
        query.setHighlightSimplePre(hlPre);
        query.setHighlightSimplePost(hlPos);
        query.set("hl.fl", FIELD );
        QueryResponse response = client.query(query);
        for (Map.Entry<String, Map<String, List<String>>> entry : response.getHighlighting().entrySet()) {
            Weibo wei = new Weibo();
            wei.setId(Integer.parseInt(entry.getKey()));
            if (entry.getValue().containsKey(FIELD)) {
                List<String> contentList = entry.getValue().get(FIELD);
                if (contentList.size() > 0) {
                    wei.setContent(contentList.get(0));
                }
            }
            weiboList.add(wei);
        }
        return weiboList;
    }

    public boolean indexWeibo(int wid,String content) throws Exception {
        SolrInputDocument doc =  new SolrInputDocument();
        doc.setField("id", wid);
        doc.setField(FIELD, content);
        UpdateResponse response = client.add(doc, 1000);
        return response != null && response.getStatus() == 0;
    }

}
