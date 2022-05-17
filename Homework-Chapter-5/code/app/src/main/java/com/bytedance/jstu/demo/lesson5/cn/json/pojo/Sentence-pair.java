/**
  * Copyright 2022 json.cn 
  */
package com.bytedance.jstu.demo.lesson5.cn.json.pojo;

/**
 * Auto-generated: 2022-03-27 20:11:44
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
class Sentence_pair {

    private String sentence;
    private String sentence_translation_speech;
    private String sentence_eng;
    private String sentence_translation;
    private String speech_size;
    private Aligned_words aligned_words;
    private String source;
    private String url;
    public void setSentence(String sentence) {
         this.sentence = sentence;
     }
     public String getSentence() {
         return sentence;
     }

    public void setSentence_translation_speech(String sentence_translation_speech) {
         this.sentence_translation_speech = sentence_translation_speech;
     }
     public String getSentence_translation_speech() {
         return sentence_translation_speech;
     }

    public void setSentence_eng(String sentence_eng) {
         this.sentence_eng = sentence_eng;
     }
     public String getSentence_eng() {
         return sentence_eng;
     }

    public void setSentence_translation(String sentence_translation) {
         this.sentence_translation = sentence_translation;
     }
     public String getSentence_translation() {
         return sentence_translation;
     }

    public void setSpeech_size(String speech_size) {
         this.speech_size = speech_size;
     }
     public String getSpeech_size() {
         return speech_size;
     }

    public void setAligned_words(Aligned_words aligned_words) {
         this.aligned_words = aligned_words;
     }
     public Aligned_words getAligned_words() {
         return aligned_words;
     }

    public void setSource(String source) {
         this.source = source;
     }
     public String getSource() {
         return source;
     }

    public void setUrl(String url) {
         this.url = url;
     }
     public String getUrl() {
         return url;
     }

}