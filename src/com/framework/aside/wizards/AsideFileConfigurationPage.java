package com.framework.aside.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.framework.aside.beans.InterfaceConfiguration;
import com.framework.aside.constants.AsideConstants;

public class AsideFileConfigurationPage extends WizardPage {

	private ISelection selection;

	private InterfaceConfiguration interfaceConfiguration;

	private List<InterfaceConfiguration> interfaceConList = new ArrayList<InterfaceConfiguration>();

	private List<String> configurationList = new ArrayList<String>();
	
	private int fieldId = 1;
	
	private String interfaceNumber;

	private Text fieldName;
	private Text fieldDescription;
	private Text sourceColumn;
	private Text sourceTable;
	private Text targetColumn;
	private Text targetTable;
	private Combo truncateFlag;
	private Combo fieldType;
	private Text dataFormatString;
	private Text javaFormatString;
	private Text inputFieldLength;
	private Text outputFieldLength;
	private Text inputPosition;
	private Text outputPosition;
	private Text recordLevel;
	private Combo alignment;
	private Button addMore;
	private Button yes;
	private Button no;

	protected AsideFileConfigurationPage(ISelection selection) {
		super("File Transformation");
		setTitle("File Layout Details");
		setDescription("Configure File Layout");
	}

	// public IWizardPage getNextPage(IWizardPage page) {
	// if(getContainer().getCurrentPage() == fileConfigurationPage)
	// {
	//
	// // getNextPage();// getContainer().showPage(fileConfigurationPage);
	// }
	// return super.getNextPage();
	// }

	// public IWizardPage getNextPage() {
	// if (getContainer().getCurrentPage().getName()
	// .equalsIgnoreCase("File Configuration")) {
	//
	// if(configurationList!=null)
	// {
	// captureInputDetails();
	//
	// }
	// }
	//
	// return null;
	// }

	@Override
	public void createControl(Composite parent) {

		interfaceConfiguration = new InterfaceConfiguration();
		final Composite composite = new Composite(parent, SWT.NONE);
		final GridLayout layout = new GridLayout();
		layout.numColumns = 1;

		composite.setLayout(layout);

		createFileLayout(composite);
		setControl(composite);
		bindValues(interfaceConfiguration);
		setPageComplete(false);
	}

	/***
	 * This method create the layout to capture the details of the file layout.
	 * 
	 * @param parent
	 */
	private void createFileLayout(final Composite parent) {

		Group layoutGroup = new Group(parent, SWT.SHADOW_IN);

		final GridLayout detailLayout = new GridLayout(4, true);
		layoutGroup.setText("Enter the details :");
		layoutGroup.setLayout(detailLayout);

		final Label fieldNameLabel = new Label(layoutGroup, SWT.NONE);
		fieldNameLabel.setText("Field Name");
		fieldName = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		fieldName.setLayoutData(gd);
		fieldName.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				setFileLayoutFields();
				final String fieldName = getFieldName();
				validateInputField(fieldName, "Field Name is required.");
				
			}
			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		final Label fieldDescLabel = new Label(layoutGroup, SWT.NONE);
		fieldDescLabel.setText("Field Description");
		fieldDescription = new Text(layoutGroup, SWT.BORDER | SWT.MULTI);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fieldDescription.setLayoutData(gd);
		

		final Label sourceColumnLabel = new Label(layoutGroup, SWT.NONE);
		sourceColumnLabel.setText("Source Column : ");
		sourceColumn = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		sourceColumn.setLayoutData(gd);
		sourceColumn.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				final String sourceColumn = getSourceColumn();
				validateInputField(sourceColumn, "Source column is required.");
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		final Label sourceTableLabel = new Label(layoutGroup, SWT.NONE);
		sourceTableLabel.setText("Source Table : ");
		sourceTable = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		sourceTable.setLayoutData(gd);
		sourceTable.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				final String sourceTable = getSourceTable();
				validateInputField(sourceTable, "Source Table is required.");
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final Label targetColumnLabel = new Label(layoutGroup, SWT.NONE);
		targetColumnLabel.setText("Target Column : ");
		targetColumn = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		targetColumn.setLayoutData(gd);
		targetColumn.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				final String targetcolumn = getTargetColumn();
				validateInputField(targetcolumn, "Target column is required.");
			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		final Label targetTableLabel = new Label(layoutGroup, SWT.NONE);
		targetTableLabel.setText("Target Table : ");
		targetTable = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		targetTable.setLayoutData(gd);
		targetTable.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				final String targetTable = getTargetTable();
				validateInputField(targetTable, "Target Table is required.");
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final Label truncateFlagLabel = new Label(layoutGroup, SWT.NONE);
		truncateFlagLabel.setText("Header Validation");

		truncateFlag = new Combo(layoutGroup, SWT.DROP_DOWN | SWT.VERTICAL
				| SWT.BORDER | SWT.READ_ONLY);
		truncateFlag.add("YES");
		truncateFlag.add("NO");

		truncateFlag.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (truncateFlag.getText().equalsIgnoreCase("Yes")) {
					interfaceConfiguration
							.setTruncateFlag(AsideConstants.YES_FLAG);
				} else {
					interfaceConfiguration
							.setTruncateFlag(AsideConstants.NO_FLAG);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		final Label fieldTypeLabel = new Label(layoutGroup, SWT.NONE);
		fieldTypeLabel.setText("Field Type : ");
		fieldType = new Combo(layoutGroup, SWT.DROP_DOWN | SWT.VERTICAL
				| SWT.BORDER | SWT.READ_ONLY);
		fieldType.add("Header");
		fieldType.add("Detail");
		fieldType.add("Trailer");
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fieldType.setLayoutData(gd);
		fieldType.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fieldType.getText().equalsIgnoreCase("Header")) {
					
					recordLevel.setText("0");
					recordLevel.setEditable(false);
					recordLevel.setEnabled(false);
					
					interfaceConfiguration
							.setFieldType(AsideConstants.HEADER_RECORD);
				} else if (fieldType.getText().equalsIgnoreCase("Detail")) {
					
					recordLevel.setEditable(true);
					recordLevel.setEnabled(true);
					recordLevel.setText("");
					interfaceConfiguration
							.setFieldType(AsideConstants.DETAIL_RECORD);
				} else {
					interfaceConfiguration
							.setFieldType(AsideConstants.TRAILER_RECORD);
					recordLevel.setText("-1");
					recordLevel.setEditable(false);
					recordLevel.setEnabled(false);

				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		final Label dataformatLabel = new Label(layoutGroup, SWT.NONE);
		dataformatLabel.setText("Data Formatting String : ");
		dataFormatString = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		dataFormatString.setLayoutData(gd);
		

		final Label javaformatLabel = new Label(layoutGroup, SWT.NONE);
		javaformatLabel.setText("Java Formatting String : ");
		javaFormatString = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		javaFormatString.setLayoutData(gd);
		

		final Label inputLengthLabel = new Label(layoutGroup, SWT.NONE);
		inputLengthLabel.setText("Input Field Length : ");
		inputFieldLength = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		inputFieldLength.setLayoutData(gd);
		inputFieldLength.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				validateNumber(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final Label outputLengthLabel = new Label(layoutGroup, SWT.NONE);
		outputLengthLabel.setText("Output Field Length : ");
		outputFieldLength = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		outputFieldLength.setLayoutData(gd);
		outputFieldLength.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				validateNumber(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final Label inputPositionLabel = new Label(layoutGroup, SWT.NONE);
		inputPositionLabel.setText("Input Position : ");
		inputPosition = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		inputPosition.setLayoutData(gd);
		inputPosition.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				validateNumber(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final Label outputPositionLabel = new Label(layoutGroup, SWT.NONE);
		outputPositionLabel.setText("Output Position : ");
		outputPosition = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		outputPosition.setLayoutData(gd);
		outputPosition.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.keyCode >=97 && e.keyCode <=122)
				{
					updateStatus("Please enter valid number.");
					return;
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final Label recordLevelLabel = new Label(layoutGroup, SWT.NONE);
		recordLevelLabel.setText("Record Level : ");
		recordLevel = new Text(layoutGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		recordLevel.setLayoutData(gd);
		recordLevel.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				validateNumber(e);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});

		final Label aligmentLabel = new Label(layoutGroup, SWT.NONE);
		aligmentLabel.setText("Alignment : ");

		alignment = new Combo(layoutGroup, SWT.DROP_DOWN | SWT.VERTICAL
				| SWT.BORDER | SWT.READ_ONLY);
		alignment.add("LEFT");
		alignment.add("RIGHT");
		alignment.add("CENTER");

		alignment.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (alignment.getText().equalsIgnoreCase("LEFT")) {
					interfaceConfiguration
							.setAlignment(AsideConstants.LEFT_ALIGN);
				} else if (alignment.getText().equalsIgnoreCase("RIGHT")) {
					interfaceConfiguration
							.setAlignment(AsideConstants.RIGHT_ALIGN);
				} else {
					interfaceConfiguration
							.setAlignment(AsideConstants.CENTER_ALIGN);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// bindValues(interfaceConfiguration);
		Group buttonGroup = new Group(parent, SWT.SHADOW_IN|SWT.CENTER);

		buttonGroup.setText("Do you wish to add more?");
		final GridLayout buttonLayout = new GridLayout(1, true);

		buttonGroup.setLayout(buttonLayout);

		yes = new Button(buttonGroup, SWT.RADIO);
		yes.setText("Yes");
		// yes.setSelection(false);
		yes.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (yes.getSelection()) {
					if (!getFieldName().equalsIgnoreCase("")) {
						captureInputDetails();
						clearAllFields();
						setPageComplete(false);
					}
				}
			}
		});

		no = new Button(buttonGroup, SWT.RADIO);
		no.setText("No");
		// no.setSelection(false);
		no.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (no.getSelection()) {
					if (!getFieldName().equalsIgnoreCase("")) {
						captureInputDetails();
						setPageComplete(true);
					}
				}
			}
		});
	}

	private void bindValues(InterfaceConfiguration interfaceConfiguration) {
		// The DataBindingContext object will manage the databindings
		// Lets bind it
		DataBindingContext context = new DataBindingContext();

		context.bindValue(SWTObservables.observeText(fieldName, SWT.Modify),
				PojoObservables.observeValue(interfaceConfiguration,
						"fieldName"));

		context.bindValue(SWTObservables.observeText(fieldDescription,
				SWT.Modify), PojoObservables.observeValue(
				interfaceConfiguration, "fieldDescription"));

		context.bindValue(SWTObservables.observeText(sourceColumn, SWT.Modify),
				PojoObservables.observeValue(interfaceConfiguration,
						"sourceColumn"));

		context.bindValue(SWTObservables.observeText(sourceTable, SWT.Modify),
				PojoObservables.observeValue(interfaceConfiguration,
						"sourceTable"));

		context.bindValue(SWTObservables.observeText(targetColumn, SWT.Modify),
				PojoObservables.observeValue(interfaceConfiguration,
						"targetColumn"));

		context.bindValue(SWTObservables.observeText(targetTable, SWT.Modify),
				PojoObservables.observeValue(interfaceConfiguration,
						"targetTable"));

		context.bindValue(SWTObservables.observeSelection(truncateFlag),
				PojoObservables.observeValue(interfaceConfiguration,
						"truncateFlag"));

		context.bindValue(SWTObservables.observeSelection(fieldType),
				PojoObservables.observeValue(interfaceConfiguration,
						"fieldType"));

		context.bindValue(SWTObservables.observeText(dataFormatString,
				SWT.Modify), PojoObservables.observeValue(
				interfaceConfiguration, "dataFormatString"));

		context.bindValue(SWTObservables.observeText(javaFormatString,
				SWT.Modify), PojoObservables.observeValue(
				interfaceConfiguration, "javaFormatString"));

		context.bindValue(SWTObservables.observeText(inputFieldLength,
				SWT.Modify), PojoObservables.observeValue(
				interfaceConfiguration, "inputFieldLength"));

		context.bindValue(SWTObservables.observeText(outputFieldLength,
				SWT.Modify), PojoObservables.observeValue(
				interfaceConfiguration, "outputFieldLength"));

		context.bindValue(
				SWTObservables.observeText(inputPosition, SWT.Modify),
				PojoObservables.observeValue(interfaceConfiguration,
						"inputPosition"));

		context.bindValue(SWTObservables
				.observeText(outputPosition, SWT.Modify), PojoObservables
				.observeValue(interfaceConfiguration, "outputPosition"));

		context.bindValue(SWTObservables.observeText(recordLevel, SWT.Modify),
				PojoObservables.observeValue(interfaceConfiguration,
						"recordLevel"));

		context.bindValue(SWTObservables.observeSelection(alignment),
				PojoObservables.observeValue(interfaceConfiguration,
						"alignment"));

	}

	/**
	 * 
	 */
	private void captureInputDetails() {

		StringBuilder configureQuery = new StringBuilder(1024);
		String fieldType = null;
		if(getFieldType().equalsIgnoreCase("header"))
		{
			fieldType = AsideConstants.HEADER_RECORD;
		}else if(getFieldType().equalsIgnoreCase("detail"))
		{
			fieldType = AsideConstants.DETAIL_RECORD;
		}else if(getFieldType().equalsIgnoreCase("trailer"))
		{
			fieldType = AsideConstants.TRAILER_RECORD;
		}
		 
		configureQuery
				.append("INSERT INTO INTERFACE_CONFIGURATION (FIELD_ID,INTERFACE_ID,FIELD_NAME,")
				.append("FIELD_DESCRIPTION,SOURCE_COLUMN,SOURCE,TARGET_COLUMN, TARGET,TRUNCATE_FLAG,")
				.append("FIELD_TYPE,DATA_FORMATTING_STRING,JAVA_FORMATTING_STRING,INPUT_FIELD_LENGTH,")
				.append("OUTPUT_FIELD_LENGTH,RECORD_LEVEL,INPUT_POSITION,OUTPUT_POSITION,ALIGNMENT_IND)  VALUES (")
				.append(fieldId++)
				.append(",")
				.append(interfaceNumber)
				.append(",'")
				.append(getFieldName()).append("','")
				.append(getFieldDescription()).append("','")
				.append(getSourceColumn()).append("','")
				.append(getSourceTable()).append("','")
				.append(getTargetColumn()).append("','")
				.append(getTargetTable()).append("','")
				.append(getTruncateFlag()).append("','").append(fieldType)
				.append("','").append(getDataFormatString()).append("','")
				.append(getJavaFormatString()).append("','")
				.append(getInputFieldLength()).append("','")
				.append(getOutputFieldLength()).append("','")
				.append(getRecordLevel()).append("','")
				.append(getInputPosition()).append("','")
				.append(getOutputPosition()).append("','")
				.append(getAlignment()).append("'").append(");");

		System.out.println(configureQuery.toString());

		configurationList.add(configureQuery.toString());

		for (String config : configurationList) {
			System.out.println("Value of the ArrayList--->" + config);
		}

	}

	/**
	 * Ensures that all the fields are set.
	 */

	private void dialogChanged() {

		// AsideSelectFileTransformationPage page =
		// (AsideSelectFileTransformationPage)getWizard().getPreviousPage(this);
		// System.out.println("Value from prevuos page"+page.getInterfaceMaster().getInterfaceName());
		updateStatus(null);
	}

	private void setFileLayoutFields()
	{
		AsideSelectFileTransformationPage page =
				 (AsideSelectFileTransformationPage)getWizard().getPreviousPage(this);
		
		interfaceNumber =page.getInterfaceMaster().getInterfaceNumber(); 
		if(page.getInterfaceMaster().getInterfaceType().equalsIgnoreCase(AsideConstants.FILE_TO_FILE))
		{
			sourceColumn.setEditable(false);
			sourceColumn.setEnabled(false);
			sourceTable.setEditable(false);
			sourceTable.setEnabled(false);
			
			targetColumn.setEditable(false);
			targetColumn.setEnabled(false);
			targetTable.setEditable(false);
			targetTable.setEnabled(false);
			
		}else if(page.getInterfaceMaster().getInterfaceType().equalsIgnoreCase(AsideConstants.FILE_TO_DB))
		{
			sourceColumn.setEditable(false);
			sourceColumn.setEnabled(false);
			sourceTable.setEditable(false);
			sourceTable.setEnabled(false);
		}
		else if(page.getInterfaceMaster().getInterfaceType().equalsIgnoreCase(AsideConstants.DB_TO_FILE))
		{
			targetColumn.setEditable(false);
			targetColumn.setEnabled(false);
			targetTable.setEditable(false);
			targetTable.setEnabled(false);
		}
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		// setPageComplete(message == null);
	}

	public ISelection getSelection() {
		return selection;
	}

	public String getFieldName() {
		return fieldName.getText();
	}

	public String getFieldDescription() {
		return fieldDescription.getText();
	}

	public String getSourceColumn() {
		return sourceColumn.getText();
	}

	public String getSourceTable() {
		return sourceTable.getText();
	}

	public String getTargetColumn() {
		return targetColumn.getText();
	}

	public String getTargetTable() {
		return targetTable.getText();
	}

	public String getTruncateFlag() {
		return truncateFlag.getText();
	}

	public String getFieldType() {
		return fieldType.getText();
	}

	public String getDataFormatString() {
		return dataFormatString.getText();
	}

	public String getJavaFormatString() {
		return javaFormatString.getText();
	}

	public String getInputFieldLength() {
		return inputFieldLength.getText();
	}

	public String getOutputFieldLength() {
		return outputFieldLength.getText();
	}

	public String getInputPosition() {
		return inputPosition.getText();
	}

	public String getOutputPosition() {
		return outputPosition.getText();
	}

	public String getRecordLevel() {
		return recordLevel.getText();
	}

	public String getAlignment() {
		return alignment.getText();
	}

	public String getInterfaceConfigurationName() {
		return "interfaceConfiguration.sql";
	}

	public InterfaceConfiguration getInterfaceConfiguration() {
		return interfaceConfiguration;
	}

	public void setInterfaceConfiguration(
			InterfaceConfiguration interfaceConfiguration) {
		this.interfaceConfiguration = interfaceConfiguration;
	}

	public List<InterfaceConfiguration> getInterfaceConList() {
		return interfaceConList;
	}

	public void setInterfaceConList(
			List<InterfaceConfiguration> interfaceConList) {
		this.interfaceConList = interfaceConList;
	}

	public List<String> getConfigurationList() {
		return configurationList;
	}

	public void setConfigurationList(List<String> configurationList) {
		this.configurationList = configurationList;
	}

	/**
	 * Ensures that all the fields are set.
	 */

	private void validateInputField(final String fieldName , final String message)
	{
		if(fieldName.length()==0)
		{
			updateStatus(message);
			return;
		}
		else
		{
			updateStatus(null);
			setPageComplete(message==null);
		}
	}
	
	private void validateNumber(KeyEvent event)
	{
		if(event.keyCode >=97 && event.keyCode <=122)
		{
			updateStatus("Please enter valid number.");
			return;
		}
	}
	private void clearAllFields() {
		fieldName.setText("");
		fieldDescription.setText("");
		sourceColumn.setText("");
		sourceTable.setText("");
		targetColumn.setText("");
		targetTable.setText("");
		dataFormatString.setText("");
		javaFormatString.setText("");
		inputFieldLength.setText("");
		outputFieldLength.setText("");
		inputPosition.setText("");
		outputPosition.setText("");
		recordLevel.setText("");

	}
}
