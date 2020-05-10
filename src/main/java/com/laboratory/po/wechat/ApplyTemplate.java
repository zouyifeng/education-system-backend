package com.laboratory.po.wechat;

public class ApplyTemplate {
 	private String touser; //用户OpenID
    
    private String template_id; //模板消息ID
    
    private String topcolor; //标题颜色
    
    private Data data; //详细内容

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTopcolor() {
		return topcolor;
	}
	
	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
}
