/**
  * Copyright 2022 json.cn 
  */
package com.bytedance.jstu.demo.lesson5.cn.json.pojo;
import java.util.List;

/**
 * Auto-generated: 2022-03-27 20:11:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Media_sents_part {

    private String query;
    private List<Sent> sent;
    public void setQuery(String query) {
         this.query = query;
     }
     public String getQuery() {
         return query;
     }

    public void setSent(List<Sent> sent) {
         this.sent = sent;
     }
     public List<Sent> getSent() {
         return sent;
     }

}