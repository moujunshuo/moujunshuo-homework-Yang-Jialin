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
public class Baike {

    private List<Summarys> summarys;
    private Source source;
    public void setSummarys(List<Summarys> summarys) {
         this.summarys = summarys;
     }
     public List<Summarys> getSummarys() {
         return summarys;
     }

    public void setSource(Source source) {
         this.source = source;
     }
     public Source getSource() {
         return source;
     }

}