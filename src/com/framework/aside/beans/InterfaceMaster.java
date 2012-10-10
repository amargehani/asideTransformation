package com.framework.aside.beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class InterfaceMaster implements PropertyChangeListener {

	private String interfaceNumber;
	private String interfaceName;
	private String interfaceDescription;
	private String inputFileName;
	private String outputFileName;
	private String currentVersionNumber;
	private Integer maxVerionNumber;
	private Integer minVerionNumber;
	private String headerValidFlag;
	private String trailerValidFlag;
	private String interfaceType;

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

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

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		propertyChangeSupport.firePropertyChange("interfaceName", this.interfaceName,
				this.interfaceName=interfaceName);
	}

	public String getInterfaceDescription() {
		return interfaceDescription;
	}

	public void setInterfaceDescription(String interfaceDescription) {
		propertyChangeSupport.firePropertyChange("interfaceDescription", this.interfaceDescription,
		this.interfaceDescription = interfaceDescription);
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		propertyChangeSupport.firePropertyChange("inputFileName", this.inputFileName,
		this.inputFileName = inputFileName);
	}

	public String getOutputFileName() {
		return outputFileName;
	}

	public void setOutputFileName(String outputFileName) {
		propertyChangeSupport.firePropertyChange("outputFileName",this.outputFileName , 
				this.outputFileName = outputFileName);
	}

	public Integer getMaxVerionNumber() {
		return maxVerionNumber;
	}

	public void setMaxVerionNumber(Integer maxVerionNumber) {
		propertyChangeSupport.firePropertyChange("maxVerionNumber", this.maxVerionNumber,
				this.maxVerionNumber = maxVerionNumber);
	}

	public Integer getMinVerionNumber() {
		return minVerionNumber;
	}

	public void setMinVerionNumber(Integer minVerionNumber) {
		propertyChangeSupport.firePropertyChange("minVerionNumber", this.minVerionNumber, 
		this.minVerionNumber = minVerionNumber);
	}

	public String getHeaderValidFlag() {
		return headerValidFlag;
	}

	public void setHeaderValidFlag(String headerValidFlag) {
		propertyChangeSupport.firePropertyChange("headerValidFlag", this.headerValidFlag,
		this.headerValidFlag = headerValidFlag);
	}

	public String getTrailerValidFlag() {
		return trailerValidFlag;
	}

	public void setTrailerValidFlag(String trailerValidFlag) {
		propertyChangeSupport.firePropertyChange("trailerValidFlag", this.trailerValidFlag,
		this.trailerValidFlag = trailerValidFlag);
	}

	public String getInterfaceNumber() {
		return interfaceNumber;
	}

	public void setInterfaceNumber(String interfaceNumber) {
		propertyChangeSupport.firePropertyChange("interfaceNumber", this.interfaceName,
		this.interfaceNumber = interfaceNumber);
	}

	public String getCurrentVersionNumber() {
		return currentVersionNumber;
	}

	public void setCurrentVersionNumber(String currentVersionNumber) {
		propertyChangeSupport.firePropertyChange("currentVersionNumber",this.currentVersionNumber,
		this.currentVersionNumber = currentVersionNumber);
	}

	public PropertyChangeSupport getPropertyChangeSupport() {
		return propertyChangeSupport;
	}

	public void setPropertyChangeSupport(PropertyChangeSupport propertyChangeSupport) {
		this.propertyChangeSupport = propertyChangeSupport;
	}
	
	public String toString()
	{
		
		return interfaceType+"  "+ interfaceName+ "  "+ interfaceDescription+"  "+inputFileName+"  "+outputFileName+"  "+
		minVerionNumber+"  "+maxVerionNumber+"  "+headerValidFlag+"  "+trailerValidFlag;
		
	}

}
