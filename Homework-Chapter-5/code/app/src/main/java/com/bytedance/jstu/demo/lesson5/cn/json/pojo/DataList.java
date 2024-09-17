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
public class DataList {

    private String pinyin;
    private List<Sense> sense;
    private String word;
    public void setPinyin(String pinyin) {
         this.pinyin = pinyin;
     }
     public String getPinyin() {
         return pinyin;
     }

    public void setSense(List<Sense> sense) {
         this.sense = sense;
     }
     public List<Sense> getSense() {
         return sense;
     }

    public void setWord(String word) {
         this.word = word;
     }
     public String getWord() {
         return word;
     }

}