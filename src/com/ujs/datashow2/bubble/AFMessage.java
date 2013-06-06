package com.ujs.datashow2.bubble;
/**
 * 消息
 * @author zouning
 *
 */
public class AFMessage {
    
    public AFMessage(){
        
    }
    
    public AFMessage(String text,int type){
        this.text = text;
        this.type = type;
    }

    /**
     * 文本类型,外来的信息
     */
    public static final int TYPE_INCOMING_TEXT = 10;
    
    /**
     * 文本类型,自己的信息
     */
    public static final int TYPE_OUTGOING_TEXT = 11;
    
    /**
     * 音频类型,外来的信息
     */
    public static final int TYPE_INCOMING_AUDIO = 20;
    
    /**
     * 音频类型,外来的信息
     */
    public static final int TYPE_OUTGOING_AUDIO = 21;
    
    /**
     * 内容
     */
    public String text;
    
    /**
     * 消息类型
     * @see TYPE_TEXT,TYPE_AUDIO
     */
    public int type;
    
    
}
