package com.framework.aside.tokenizer;

import java.util.List;

import org.springframework.batch.item.file.transform.LineTokenizer;

import com.framework.aside.bean.InterfaceConfigurationBean;

public interface DynamicLineTokenizer extends LineTokenizer {

	public void setKey(String key_);
	public void setSortedBySourcePositionInterfaceConfigurationBeanList(List<InterfaceConfigurationBean> sortedBySourcePositionInterfaceConfigurationBeanList);
}
