package com.framework.aside.wizards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.osgi.framework.Bundle;

import com.framework.aside.activator.AsideActivator;
import com.framework.aside.beans.InterfaceConfiguration;
import com.framework.aside.beans.InterfaceMaster;
import com.framework.aside.validator.AsideValidator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class AsideTransformationWizard extends Wizard implements INewWizard {

	private InterfaceMaster interfaceMaster = new InterfaceMaster();

	private InterfaceConfiguration interfaceConfiguration = new InterfaceConfiguration();

	private AsideValidator validator = new AsideValidator();

	public AsideTransformationWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	public AsideValidator getValidator() {
		return validator;
	}

	private AsideSelectFileTransformationPage selectFileTransformationPage;

	private AsideFileConfigurationPage fileConfigurationPage;

	private ISelection selection;

	private byte[] stringConfigByte;

	public ISelection getSelection() {
		return selection;
	}

	public void setSelection(ISelection selection) {
		this.selection = selection;
	}

	public void addPages() {
		setWindowTitle("File Transformation");
		selectFileTransformationPage = new AsideSelectFileTransformationPage(
				selection);
		addPage(selectFileTransformationPage);
		fileConfigurationPage = new AsideFileConfigurationPage(selection);
		addPage(fileConfigurationPage);

	}

	// @Override
	// public IWizardPage getNextPage(IWizardPage page) {
	// if(getContainer().getCurrentPage() == fileConfigurationPage)
	// {
	//
	// // getNextPage();// getContainer().showPage(fileConfigurationPage);
	// }
	// return super.getNextPage(page);
	// }

	@Override
	public boolean performFinish() {

		// First save all the page data as variables.

		final String containerName = selectFileTransformationPage
				.getProjectName();
		final String interfaceMasterFileName = selectFileTransformationPage
				.getInterfaceMasterName();
		final String interfaceConfigurationFileName = fileConfigurationPage
				.getInterfaceConfigurationName();
		final List<String> interfaceConfigQuery = fileConfigurationPage
				.getConfigurationList();

		final Object[] interfaceConfigArray = interfaceConfigQuery.toArray();
		// Now invoke the finish method.
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				try {
					final String interfaceMasterQuery = createInterfaceMasterScript();

					doFinish(containerName, interfaceMasterFileName,
							interfaceMasterQuery,
							interfaceConfigurationFileName,
							interfaceConfigArray, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error",
					realException.getMessage());
			return false;
		}
		return true;

	}

	private void doFinish(String containerName, String interfaceMasterFileName,
			String interfaceMasterQuery, String interfaceConfigFileName,
			Object[] interfaceConfigQuery, IProgressMonitor monitor)
			throws CoreException, IOException, TemplateException {

		// create a sample file
		monitor.beginTask("Creating " + interfaceMasterFileName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName
					+ "\" does not exist.");
		}
		IContainer container = (IContainer) resource;

		// Create interface Master script

		try {

			OutputStream os = createInsertScriptFile(interfaceMasterFileName,
					container);
			byte stringByte[] = interfaceMasterQuery.getBytes();
			os.write(stringByte);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//create interface configuration file.
		try {
			OutputStream os = createInsertScriptFile(interfaceConfigFileName,
					container);

			for (int i = 0; i < interfaceConfigQuery.length; i++) {
				stringConfigByte = ((String) interfaceConfigQuery[i])
						.getBytes();
				os.write(stringConfigByte);
			}
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create baseconfiguration.xml

		createBaseConfigurationXML(container);

		// Make sure the project is refreshed as the file was created outside
		// the Eclipse API.

		container.refreshLocal(IResource.DEPTH_INFINITE, monitor);

		monitor.worked(1);

		// monitor.setTaskName("Opening file for editing...");
		// getShell().getDisplay().asyncExec(new Runnable() {
		// public void run() {
		// IWorkbenchPage page =
		// PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		// try {
		// IDE.openEditor(page, iFile, true);
		// } catch (PartInitException e) {
		// }
		// }
		// });
		// monitor.worked(1);
	}

	private void throwCoreException(String message) throws CoreException {
		IStatus status = new Status(IStatus.ERROR, "Aside Transformation",
				IStatus.OK, message, null);
		throw new CoreException(status);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub

		this.selection = selection;
	}

	/**
	 * This method creates the interface_master insert query.
	 * 
	 * @return
	 * @throws IOException
	 */
	private String createInterfaceMasterScript() throws IOException {
		StringBuilder query = new StringBuilder(1024);

		query.append("INSERT INTO INTERFACE_MASTER ( ")
				.append("INTERFACE_ID,INTERFACE_NAME,INTERFACE_TYPE,INTERFACE_DESCRIPTION,INPUTFILE_NAME_FORMAT,OUTPUTFILE_NAME_FORMAT, ")
				.append("INPUTFILE_VERSION_NUMBER,OUTPUTFILE_VERSION_NUMBER,MIN_VERSION_NUMBER,MAX_VERSION_NUMBER,HEADER_VALIDATION_FLAG,TRAILER_VALIDATION_FLAG ")
				.append(") values ( ")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getInterfaceNumber())
				.append(",'")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getInterfaceName())
				.append("','")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getInterfaceType())
				.append("','")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getInterfaceDescription())
				.append("','")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getInputFileName())
				.append("','")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getOutputFileName())
				.append("',")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getCurrentVersionNumber())
				.append(",")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getCurrentVersionNumber())
				.append(",")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getMinVerionNumber())
				.append(",")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getMaxVerionNumber())
				.append(",'")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getHeaderValidFlag())
				.append("','")
				.append(selectFileTransformationPage.getInterfaceMaster()
						.getTrailerValidFlag()).append("')").append(";");

		System.out.println(query.toString());
		return query.toString();

	}

	/**
	 * This file created the base configuration file.
	 * 
	 * @param container
	 * @throws IOException
	 * @throws TemplateException
	 */
	private void createBaseConfigurationXML(IContainer container)
			throws IOException, TemplateException {
		Bundle bundle = Platform.getBundle(AsideActivator.PLUGIN_ID);
		String fileName = "resources/BaseConfiguration.ftl";
		Path originPath = new Path(fileName);

		URL bundledFileURL = FileLocator.find(bundle, originPath, null);

		String ftlLocation = null;
		try {
			bundledFileURL = FileLocator.resolve(bundledFileURL);
			System.out.println("VAlue of bundledFileURL.getFile().toString() :"
					+ bundledFileURL.getFile().toString());
			System.out.println("Value of bundledFileURL.getPath() :"
					+ bundledFileURL.getPath());
			ftlLocation = bundledFileURL.getFile().toString();
			ftlLocation = ftlLocation
					.substring(1, ftlLocation.lastIndexOf("/"));
			System.out.println("Value of ftlLocation -->" + ftlLocation);
		} catch (IOException e) {
			throw new RuntimeException("Plugin Error: can't read file: "
					+ fileName, e);
		}

		//

		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(ftlLocation));
		Template template = cfg.getTemplate("BaseConfiguration.ftl");
		final IFolder folder = container.getFolder(new Path("resources"));
		if (folder.exists()) {
			System.out.println(folder.getFullPath());
			System.out.println(folder.getLocation().toString());
			System.out.println(folder.getLocationURI());
			final IFile baseXMLFIle = container.getFile(new Path(folder
					.getFullPath() + "/" + "baseConfiguration.xml"));
			System.out.println(baseXMLFIle.getName());
			System.out.println(baseXMLFIle.getFullPath().toFile());
			System.out.println(baseXMLFIle.getLocation().toFile());
			// final File xmlFile = baseXMLFIle.getFullPath().toFile();

			// Build the data-model
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("message", "Hello World!");

			// File output
			Writer baseFile = new FileWriter(new File(folder.getLocation()
					.toString() + "/" + "baseConfiguration.xml"));
			template.process(data, baseFile);
			baseFile.flush();
			baseFile.close();
		}
	}

	private OutputStream createInsertScriptFile(String scriptName,
			IContainer container) throws CoreException {
		final IFile interfaceScript = container.getFile(new Path(scriptName));
		final File file = interfaceScript.getLocation().toFile();
		OutputStream os = null;
		try {

			os = new FileOutputStream(file, false);

		} catch (IOException e) {
			e.printStackTrace();
			throwCoreException("Error writing to file " + file.toString());
		}

		return os;
	}

}
