package com.framework.aside.tokenizer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;

import com.framework.aside.bean.InterfaceConfigurationBean;

public class DynamicFixedLengthTokenizer extends FixedLengthTokenizer implements InitializingBean
{
	private List<InterfaceConfigurationBean> sortedBySourcePositionInterfaceConfigurationBeanList;

	private String key;

	@Required
	public void setKey(String key_)
	{
		this.key = key_;
		setColumns(getColumns());
		setNames(getNames());
	}



	



	/**
	 * @return the sortedBySourcePositionInterfaceConfigurationBeanList
	 */
	public List<InterfaceConfigurationBean> getSortedBySourcePositionInterfaceConfigurationBeanList()
	{
		return sortedBySourcePositionInterfaceConfigurationBeanList;
	}







	/**
	 * @param sortedBySourcePositionInterfaceConfigurationBeanList the sortedBySourcePositionInterfaceConfigurationBeanList to set
	 */
	public void setSortedBySourcePositionInterfaceConfigurationBeanList(
			List<InterfaceConfigurationBean> sortedBySourcePositionInterfaceConfigurationBeanList)
	{
		this.sortedBySourcePositionInterfaceConfigurationBeanList = sortedBySourcePositionInterfaceConfigurationBeanList;
	}







	public void afterPropertiesSet() throws Exception
	{
		
	}

	private Range[] getColumns()
	{
		List<Range> result = new ArrayList<Range>();
		int startPosition = 0;
		int endPosition = 0;
		Range range = null;
		
		for (InterfaceConfigurationBean interfaceConfigurationBean : sortedBySourcePositionInterfaceConfigurationBeanList)
		{
			if (key.equals(interfaceConfigurationBean.getSource()))
			{
				startPosition = endPosition + 1;
				int fieldLength = Integer.parseInt(interfaceConfigurationBean.getInputFieldLength());
				endPosition = startPosition + fieldLength - 1;
				range = new Range(startPosition, endPosition);
				result.add(range);
			}
		}
		Range[] rangeResult = new Range[result.size()-1];
		return  result.toArray(rangeResult);

	}
	
	private String[] getNames(){
		
		List<String> result = new ArrayList<String>();
		
		
		for (InterfaceConfigurationBean interfaceConfigurationBean : sortedBySourcePositionInterfaceConfigurationBeanList)
		{
			if (key.equals(interfaceConfigurationBean.getSource()))
			{	// set key as field id instead of Source column
				//result.add(interfaceConfigurationBean.getSourceColumn());
				result.add(interfaceConfigurationBean.getFieldId());
			}
		}
		String[] nameResult = new String[result.size()-1];
		return   result.toArray(nameResult);
		
	}
 
}
