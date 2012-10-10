package com.framework.aside.writer;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.database.JdbcBatchItemWriter;

import com.framework.aside.bean.AttributesBean;
import com.framework.aside.bean.InterfaceConfigurationBean;
import com.framework.aside.bean.common.Constants;

public class MapJdbcItemWriter extends JdbcBatchItemWriter<Map<String, String>>
{
	private List<InterfaceConfigurationBean> interfaceConfigurationBeanList;

	@Override
	public void setSql(String sql)
	{
		super.setSql(generateInsertStatement());
	}

	private String generateInsertStatement()
	{
		StringBuilder query = new StringBuilder();
		StringBuilder parameters = new StringBuilder();
		boolean tableNameAdded = false;
		for (InterfaceConfigurationBean interfaceConfigurationBean : interfaceConfigurationBeanList)
		{
			if (!tableNameAdded)
			{
				query.append("INSERT INTO " + interfaceConfigurationBean.getTarget() + " ( ");
				parameters.append(" values (");

			}
			if (!Constants.FILLER.equals(interfaceConfigurationBean.getTargetColumn()) 
					&& Constants.FIELD_TYPE_DETAIL_RECORD.equals(interfaceConfigurationBean.getFieldType()))
			{
				String targetColumn = interfaceConfigurationBean.getTargetColumn();
				if (targetColumn != null && !"".equals(targetColumn.trim()))
				{
					if (tableNameAdded)
					{
						query.append(",");
						parameters.append(",");
					}
					query.append(targetColumn);
					parameters.append("?");
				}
			}
			
			tableNameAdded = true;
		}
		query.append(")");
		parameters.append(")");
		query.append(parameters);
		return query.toString();

	}

	public List<InterfaceConfigurationBean> getInterfaceConfigurationBeanList()
	{
		return interfaceConfigurationBeanList;
	}

	public void setInterfaceConfigurationBeanList(List<InterfaceConfigurationBean> interfaceConfigurationBeanList)
	{
		this.interfaceConfigurationBeanList = interfaceConfigurationBeanList;
	}
	@Override
	public void write(List<? extends Map<String, String>> items) throws Exception
	{
		for(Object obj : items)
		{
			System.out.println("===============================");
			super.write(((AttributesBean)obj).getListHashMap());
			System.out.println("===============================");
		}
		
	}
}
