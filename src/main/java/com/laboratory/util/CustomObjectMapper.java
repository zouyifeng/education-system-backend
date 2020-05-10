package com.laboratory.util;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("jsonObjectMapper")
public class CustomObjectMapper extends ObjectMapper{

	private static final long serialVersionUID = 1L;

	public CustomObjectMapper() {
		super();
//		this.setDateFormat(new CustomDateFormat("yyyy-MM-dd HH:mm:ss"));
		this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));

		// 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		this.setConfig(this.getDeserializationConfig().without(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES));
		
		//设置不输出值为 null 的属性
		this.setSerializationInclusion(Include.NON_NULL);
		
		//将驼峰转为下划线
		//this.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

		/*// null值处理为空字符串
		this.getSerializerProvider().setNullValueSerializer(
				new JsonSerializer<Object>() {
		
					@Override
					public void serialize(Object value, JsonGenerator jg,
							SerializerProvider sp) throws IOException,
							JsonProcessingException {
						jg.writeString("");
					}
				});*/
	}
}
