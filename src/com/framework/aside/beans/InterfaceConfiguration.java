package com.framework.aside.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class InterfaceConfiguration implements PropertyChangeListener{

	
	private String fieldId;
	private String fieldName;
	private String fieldDescription ;
	private String sourceColumn;
	private String sourceTable;
	private String targetColumn;
	private String targetTable;
	private String truncateFlag;
	private String fieldType;
	private String dataFormatString;
	private String javaFormatString;
	private String inputFieldLength;
	private String outputFieldLength;
	private String inputPosition;
	private String outputPosition;
	private String recordLevel;
	private String alignment;
	
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

	
	
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		propertyChangeSupport.firePropertyChange("fieldName", this.fieldName,
				this.fieldName = fieldName);
	}
	public String getFieldDescription() {
		return fieldDescription;
	}
	public void setFieldDescription(String fieldDescription) {
		propertyChangeSupport.firePropertyChange("fieldDescription",this.fieldDescription,
				this.fieldDescription = fieldDescription);
	}
	public String getSourceColumn() {
		return sourceColumn;
	}
	public void setSourceColumn(String sourceColumn) {
		propertyChangeSupport.firePropertyChange("sourceColumn", this.sourceColumn, 
		this.sourceColumn = sourceColumn);
	}
	public String getSourceTable() {
		return sourceTable;
	}
	public void setSourceTable(String sourceTable) {
		propertyChangeSupport.firePropertyChange("sourceTable", this.sourceTable, 
		this.sourceTable = sourceTable);
	}
	public String getTargetColumn() {
		return targetColumn;
	}
	public void setTargetColumn(String targetColumn) {
		propertyChangeSupport.firePropertyChange("targetColumn", this.targetColumn,
		this.targetColumn = targetColumn);
	}
	public String getTargetTable() {
		return targetTable;
	}
	public void setTargetTable(String targetTable) {
		propertyChangeSupport.firePropertyChange("targetTable", this.targetTable,
		this.targetTable = targetTable);
	}
	public String getTruncateFlag() {
		return truncateFlag;
	}
	public void setTruncateFlag(String truncateFlag) {
		propertyChangeSupport.firePropertyChange("truncateFlag", this.truncateFlag,
		this.truncateFlag = truncateFlag);
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		propertyChangeSupport.firePropertyChange("fieldType", this.fieldType,
		this.fieldType = fieldType);
	}
	public String getDataFormatString() {
		return dataFormatString;
	}
	public void setDataFormatString(String dataFormatString) {
		propertyChangeSupport.firePropertyChange("dataFormatString", this.dataFormatString,
		this.dataFormatString = dataFormatString);
	}
	public String getJavaFormatString() {
		return javaFormatString;
	}
	public void setJavaFormatString(String javaFormatString) {
		propertyChangeSupport.firePropertyChange("javaFormatString", this.javaFormatString,
		this.javaFormatString = javaFormatString);
	}
	public String getInputFieldLength() {
		return inputFieldLength;
	}
	public void setInputFieldLength(String inputFieldLength) {
		propertyChangeSupport.firePropertyChange("inputFieldLength", this.inputFieldLength,
		this.inputFieldLength = inputFieldLength);
	}
	public String getOutputFieldLength() {
		return outputFieldLength;
	}
	public void setOutputFieldLength(String outputFieldLength) {
		propertyChangeSupport.firePropertyChange("outputFieldLength", this.outputFieldLength,
		this.outputFieldLength = outputFieldLength);
	}
	public String getInputPosition() {
		return inputPosition;
	}
	public void setInputPosition(String inputPosition) {
		propertyChangeSupport.firePropertyChange("inputPosition", this.inputPosition,
		this.inputPosition = inputPosition);
	}
	public String getOutputPosition() {
		return outputPosition;
	}
	public void setOutputPosition(String outputPosition) {
		propertyChangeSupport.firePropertyChange("outputPosition", this.outputPosition,
		this.outputPosition = outputPosition);
	}
	public String getRecordLevel() {
		return recordLevel;
	}
	public void setRecordLevel(String recordLevel) {
		propertyChangeSupport.firePropertyChange("recordLevel", this.recordLevel,
		this.recordLevel = recordLevel);
	}
	public String getAlignment() {
		return alignment;
	}
	public void setAlignment(String alignment) {
		propertyChangeSupport.firePropertyChange("alignment", this.alignment,
		this.alignment = alignment);
	}
	

}
