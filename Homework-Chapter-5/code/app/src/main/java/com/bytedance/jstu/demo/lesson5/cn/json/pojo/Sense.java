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
public class Sense {

    private List<String> examples;
    private List<String> def;
    private String cat;
    public void setExamples(List<String> examples) {
         this.examples = examples;
     }
     public List<String> getExamples() {
         return examples;
     }

    public void setDef(List<String> def) {
         this.def = def;
     }
     public List<String> getDef() {
         return def;
     }

    public void setCat(String cat) {
         this.cat = cat;
     }
     public String getCat() {
         return cat;
     }

}