package com.framework.aside.wizards;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.PojoObservables;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.wizard.WizardPageSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.framework.aside.beans.InterfaceMaster;
import com.framework.aside.constants.AsideConstants;

/**
 * 
 * @author Amar Gehani
 * @version 1.0
 * 
 */
public class AsideSelectFileTransformationPage extends WizardPage {

	private InterfaceMaster interfaceMaster = new InterfaceMaster();

	private ISelection selection;

	private String projectName;

	public InterfaceMaster getInterfaceMaster() {
		return interfaceMaster;
	}

	public void setInterfaceMaster(InterfaceMaster interfaceMaster) {
		this.interfaceMaster = interfaceMaster;
	}

	private Text interfaceName;
	private Text interfaceDescription;
	private Text inputFileName;
	private Text outputFileName;
	private Text interfaceNumber;
	private Text currentVersionNumber;
	private Text maxVerionNumber;
	private Text minVerionNumber;
	private Combo headerValidFlag;
	private Combo trailerValidFlag;
	private Button fileToFile;
	private Button fileToDatabase;
	private Button databaseToFile;

	/**
	 * 
	 * @param selection
	 */
	protected AsideSelectFileTransformationPage(ISelection selection) {
		super("File Transformation");
		setTitle("Select Type of Transformation");
		setDescription("Choose the type of transformation to be performed");
		this.selection = selection;
	}

	public IWizardPage getNextPage() {
		return super.getNextPage();
	}

	@Override
	public void createControl(Composite parent) {

		final Composite composite = new Composite(parent, SWT.NONE);

		final GridLayout layout = new GridLayout();
		layout.numColumns = 1;

		composite.setLayout(layout);

		Group selectionGroup = new Group(composite, SWT.SHADOW_IN);

		selectionGroup.setText("Select the type of transformation");
		selectionGroup.setLayout(new RowLayout(SWT.HORIZONTAL));
		fileToFile = new Button(selectionGroup, SWT.RADIO);
		fileToFile.setText("File to File");

		fileToFile.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fileToFile.getSelection()) {
					interfaceMaster
							.setInterfaceType(AsideConstants.FILE_TO_FILE);
					inputFileName.setEditable(true);
					inputFileName.setEnabled(true);
					outputFileName.setEditable(true);
					outputFileName.setEnabled(true);
				}

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		fileToDatabase = new Button(selectionGroup, SWT.RADIO);
		fileToDatabase.setText("File to Database");
		fileToDatabase.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (fileToDatabase.getSelection()) {
					interfaceMaster.setInterfaceType(AsideConstants.FILE_TO_DB);
					inputFileName.setEditable(true);
					inputFileName.setEnabled(true);
					outputFileName.setEditable(false);
					outputFileName.setEnabled(false);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});
		databaseToFile = new Button(selectionGroup, SWT.RADIO);
		databaseToFile.setText("Database to File");
		databaseToFile.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (databaseToFile.getSelection()) {
					interfaceMaster.setInterfaceType(AsideConstants.DB_TO_FILE);
					inputFileName.setEditable(false);
					inputFileName.setEnabled(false);
					outputFileName.setEditable(true);
					outputFileName.setEnabled(true);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}
		});

		initialize();
		setControl(composite);
		// dialogChanged();
		createDetailLayout(composite);

		bindValues(interfaceMaster);

		// setPageComplete(false);

	}

	/**
	 * 
	 * @param composite
	 */
	private void createDetailLayout(Composite composite) {
		Group detailGroup = new Group(composite, SWT.SHADOW_IN);
		final GridLayout detailLayout = new GridLayout(4, true);
		detailGroup.setText("Enter the details :");
		detailGroup.setLayout(detailLayout);
		final Label interfaceNameLabel = new Label(detailGroup, SWT.NONE);
		interfaceNameLabel.setText("&Interface Name:");
		interfaceName = new Text(detailGroup, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		interfaceName.setLayoutData(gd);
		interfaceName.addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
				 String interfaceName = getInterfaceName();
				 validateInputField(interfaceName,"Interface Name cannot be blank. ");
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		final Label interfaceDescLabel = new Label(detailGroup, SWT.NONE);
		interfaceDescLabel.setText("&Interface Description:");
		interfaceDescription = new Text(detailGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		interfaceDescription.setLayoutData(gd);

		final Label inputFileNameLabel = new Label(detailGroup, SWT.NONE);
		inputFileNameLabel.setText("Input File Name :");
		inputFileName = new Text(detailGroup, SWT.BORDER | SWT.SINGLE);

		gd = new GridData(GridData.FILL_HORIZONTAL);
		inputFileName.setLayoutData(gd);
		inputFileName.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				String inputFileName = getInputFileName();
				validateInputField(inputFileName, "Input File Name is required.");
				
			}
			@Override
			public void keyPressed(KeyEvent e) {

			}
		});

		final Label outputFileNameLabel = new Label(detailGroup, SWT.NONE);
		outputFileNameLabel.setText("Output File Name :");
		outputFileName = new Text(detailGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		outputFileName.setLayoutData(gd);
		outputFileName.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				String outputFileName = getOutputFileName();
				validateInputField(outputFileName, "Output File Name is required.");
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		
		final Label interfaceNumberLabel = new Label(detailGroup, SWT.NONE);
		interfaceNumberLabel.setText("Interface Number :");
		interfaceNumber = new Text(detailGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		interfaceNumber.setText("1");
		interfaceNumber.setLayoutData(gd);
		interfaceNumber.addKeyListener(new KeyListener() {

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
		
		final Label currentVersionLabel = new Label(detailGroup, SWT.NONE);
		currentVersionLabel.setText("Version Number :");
		currentVersionNumber = new Text(detailGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		currentVersionNumber.setText("1");
		currentVersionNumber.setLayoutData(gd);
		currentVersionNumber.addKeyListener(new KeyListener() {

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
		
		final Label minVerionNumberLabel = new Label(detailGroup, SWT.NONE);
		minVerionNumberLabel.setText("Minimum Version Number");
		minVerionNumber = new Text(detailGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		minVerionNumber.setLayoutData(gd);
		minVerionNumber.addKeyListener(new KeyListener() {

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
		final Label maxVerionNumberLabel = new Label(detailGroup, SWT.NONE);
		maxVerionNumberLabel.setText("Maximum Version Number");
		maxVerionNumber = new Text(detailGroup, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		maxVerionNumber.setLayoutData(gd);
		maxVerionNumber.addKeyListener(new KeyListener() {

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
			}
		});
		
		final Label headerValidationLabel = new Label(detailGroup, SWT.NONE);
		headerValidationLabel.setText("Header Validation");

		headerValidFlag = new Combo(detailGroup, SWT.DROP_DOWN | SWT.VERTICAL
				| SWT.BORDER | SWT.READ_ONLY);
		headerValidFlag.add("YES");
		headerValidFlag.add("NO");

		headerValidFlag.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (headerValidFlag.getText().equalsIgnoreCase("Yes")) {
					interfaceMaster.setHeaderValidFlag(AsideConstants.YES_FLAG);
				} else {
					interfaceMaster.setHeaderValidFlag(AsideConstants.NO_FLAG);
				}

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		final Label trailerValidationLabel = new Label(detailGroup, SWT.NONE);
		trailerValidationLabel.setText("Trailer Validation");
		trailerValidFlag = new Combo(detailGroup, SWT.DROP_DOWN | SWT.VERTICAL
				| SWT.BORDER | SWT.READ_ONLY);
		trailerValidFlag.add("YES");
		trailerValidFlag.add("NO");

		trailerValidFlag.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (trailerValidFlag.getText().equalsIgnoreCase("Yes")) {

					interfaceMaster
							.setTrailerValidFlag(AsideConstants.YES_FLAG);
				} else {
					interfaceMaster.setTrailerValidFlag(AsideConstants.NO_FLAG);
				}

			}
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

	}

	private void bindValues(InterfaceMaster interfaceMaster) {
		// The DataBindingContext object will manage the databindings
		// Lets bind it
		DataBindingContext context = new DataBindingContext();

		WizardPageSupport.create(this, context);
		context.bindValue(
				SWTObservables.observeText(interfaceName, SWT.Modify),
				PojoObservables.observeValue(interfaceMaster, "interfaceName"));

		context.bindValue(SWTObservables.observeText(interfaceDescription,
				SWT.Modify), PojoObservables.observeValue(interfaceMaster,
				"interfaceDescription"));

		context.bindValue(
				SWTObservables.observeText(inputFileName, SWT.Modify),
				PojoObservables.observeValue(interfaceMaster, "inputFileName"));

		context.bindValue(
				SWTObservables.observeText(outputFileName, SWT.Modify),
				PojoObservables.observeValue(interfaceMaster, "outputFileName"));

//		context.bindValue(SWTObservables.observeText(minVerionNumber,
//				SWT.Modify), ((AsideTransformationWizard) getWizard())
//				.getValidator().intValue, new UpdateValueStrategy()
//				.setAfterConvertValidator(new SingleDigitValidator()), null);
		context.bindValue(SWTObservables.observeText(interfaceNumber,
				SWT.Modify), PojoObservables.observeValue(interfaceMaster,
				"interfaceNumber"));
		
		context.bindValue(SWTObservables.observeText(currentVersionNumber,
				SWT.Modify), PojoObservables.observeValue(interfaceMaster,
				"currentVersionNumber"));
		
		context.bindValue(SWTObservables.observeText(minVerionNumber,
				SWT.Modify), PojoObservables.observeValue(interfaceMaster,
				"minVerionNumber"));
		
		context.bindValue(SWTObservables.observeText(maxVerionNumber,
				SWT.Modify), PojoObservables.observeValue(interfaceMaster,
				"maxVerionNumber"));

		context.bindValue(SWTObservables.observeSelection(headerValidFlag),
				PojoObservables
						.observeValue(interfaceMaster, "headerValidFlag"));

		context.bindValue(SWTObservables.observeSelection(trailerValidFlag),
				PojoObservables.observeValue(interfaceMaster,
						"trailerValidFlag"));

	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				projectName = container.getFullPath().toString();
			}else if (obj instanceof IJavaElement)
			{
				IJavaProject javaElement;
				javaElement = ((IJavaElement)obj).getJavaProject();
				projectName = javaElement.getProject().getName();
			}
		}

	}

	public String getProjectName() {
		return projectName;
	}

	public String getInterfaceMasterName() {
		return "interfaceMaster.sql";
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
	private void dialogChanged() {
		// String interfaceName = getInterfaceName();
		// String inputfileName = getInputFileName();
		// String outputFileName = getOutputFileName();
		// String minVersionNumber = getMinVerionNumber();
		// String maxVersionNumber = getMaxVerionNumber();
		// if (interfaceName.length() == 0) {
		// updateStatus("File container must be specified");
		// return;
		// }
		// else if(inputfileName.length()==0)
		// {
		// updateStatus("Input file name must be specified");
		// return;
		// }
		// else if(outputFileName.length()==0)
		// {
		// updateStatus("OutPut File Name must be specified");
		// return;
		// }
		updateStatus(null);
	}

	public ISelection getSelection() {
		return selection;
	}

	public String getInterfaceName() {
		return interfaceName.getText();
	}

	public String getInterfaceDescription() {
		return interfaceDescription.getText();
	}

	public String getInputFileName() {
		return inputFileName.getText();
	}

	public String getOutputFileName() {
		return outputFileName.getText();
	}

	public String getMaxVerionNumber() {
		return maxVerionNumber.getText();
	}

	public String getMinVerionNumber() {
		return minVerionNumber.getText();
	}

	public String getHeaderValidFlag() {
		return headerValidFlag.getText();
	}

	public String getTrailerValidFlag() {
		return trailerValidFlag.getText();
	}

	public String getInterfaceNumber() {
		return interfaceNumber.getText();
	}

	public String getCurrentVersionNumber() {
		return currentVersionNumber.getText();
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

}

//final class SingleDigitValidator implements IValidator {
//	public IStatus validate(Object value) {
//		Integer i = (Integer) value;
//		if (i == null) {
//			return ValidationStatus.info("Please enter a value.");
//		}
//		if (i.intValue() < 0 || i.intValue() > 9) {
//			ValidationStatus.error("Value must be between 0 and 9.");
//		}
//		return ValidationStatus.ok();
//	}
//}
