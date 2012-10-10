package com.framework.aside.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.framework.aside.wizards.AsideTransformationWizard;

public class AddToTransformActionDelegate implements IWorkbenchWindowActionDelegate {

	
	public AddToTransformActionDelegate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
//		MessageDialog.openInformation(targetPart.getSite().getShell(),
//				"Add to FAvorites", "Triggered the " + getClass().getName() + " action");

		AsideTransformationWizard wizard = new AsideTransformationWizard();
		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		ISelection selection = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getSelection();
		wizard.setSelection(selection);
		WizardDialog dialog= new WizardDialog(shell, wizard);
		dialog.create();
		dialog.open();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		
	}

}
