package com.nowcoder.weibo.util;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nowcoder.weibo.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.util.HtmlUtils;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by lenovo on 2017/7/19.
 */
public class CommonUtil {
    public static void print(int index, Object obj) {
        System.out.println(String.format("{%d},%s", index, obj.toString()));

    }

    public static void StringUtil() {

        print(1, StringUtils.isBlank(""));
        print(2, StringUtils.compare("hello", "hallo"));
        String[] str = {"1","2","3","4"};
        print(3, StringUtils.join(str,"_"));
        print(4,StringUtils.remove("hello","l") );
        print(5,StringUtils.contains("hahaha,woshi","wo"));
        print(6,StringUtils.isBlank("null"));

        User u = new User();

        u.setName("zhengtian");


        print(7,ToStringBuilder.reflectionToString(u, ToStringStyle.NO_FIELD_NAMES_STYLE));
    }

    public static void DatetimeUtil() throws Exception{
      Date date = DateUtils.parseDate("2012-04-23","yyyy-MM-dd");
        print(1,date);
        print(2, DateFormatUtils.format(date,"yyyy/MM/dd"));
        print(3,DateUtils.addDays(date,365));
        print(4,new Date().after(date));
    }
public static void htmlUtil(){
   String html="<b>helloworld</b>";
   String eschtml= HtmlUtils.htmlEscape(html);
   print(1,eschtml);
   String hhtnl=HtmlUtils.htmlUnescape(html);
    print(2,hhtnl);

}
public static void JsonUtil(){
    String str="{ \"abd\":1}";
    JSONObject jso= JSON.parseObject(str);
    jso.put("1","a");

    print(1,jso);
}
public static void Xmlutil() throws Exception {
    File xmlFile = new File("G:\\weibo\\weibo\\pom.xml");
    SAXReader sax = new SAXReader();
    Document doc = sax.read(xmlFile);
    Element ele = doc.getRootElement();
    print(1,ele.element("groupId").getStringValue());
    getNodes(ele);


}
    public static void getNodes(Element node){

        System.out.println("节点:"+node.getName());
        List<Element> listElement=node.elements();
        for(Element e:listElement){
            getNodes(e);
        }
    }
    public static void OfficeUtil()throws Exception{
        Workbook wrb=new HSSFWorkbook();
        Sheet sheet=wrb.createSheet("sheet1");
        Row row=sheet.createRow(4);
        Cell cell=row.createCell(7);
        cell.setCellValue(2.2);
        cell.setCellType(CellType.NUMERIC);


        wrb.write(new FileOutputStream("hh.xls"));
    }



    public static void main2(String[] args) {
        try {
            /*
           StringUtil();
           DatetimeUtil();
           htmlUtil();
           JsonUtil();
           */
            Xmlutil();
            OfficeUtil();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}