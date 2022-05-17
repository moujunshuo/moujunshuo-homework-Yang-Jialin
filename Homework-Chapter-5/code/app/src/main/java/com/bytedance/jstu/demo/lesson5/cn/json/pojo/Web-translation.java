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
class Web_translation {

    private String same;
    private String key;
    private List<Trans> trans;
    public void setsame(String same) {
         this.same = same;
     }
     public String getsame() {
         return same;
     }

    public void setKey(String key) {
         this.key = key;
     }
     public String getKey() {
         return key;
     }

    public void setTrans(List<Trans> trans) {
         this.trans = trans;
     }
     public List<Trans> getTrans() {
         return trans;
     }

}