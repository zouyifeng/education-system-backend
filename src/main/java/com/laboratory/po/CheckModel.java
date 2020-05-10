package com.laboratory.po;

public class CheckModel extends ErrorCodeModel{
	String signature;
    Long timestamp;
    Long nonce;
    String echostr;
     
     
    public String getSignature() {
        return signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public Long getNonce() {
        return nonce;
    }
    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }
    public String getEchostr() {
        return echostr;
    }
    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }  
}
