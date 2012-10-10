package com.framework.aside.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.framework.aside.wizards.AsideTransformationWizard;

public class AsideTransformCommand extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public AsideTransformCommand() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		Shell shell = HandlerUtil.getActiveShell(event);
	    ISelection selection = HandlerUtil.getActiveMenuSelection(event);
		AsideTransformationWizard wizard = new AsideTransformationWizard();
		wizard.setSelection(selection);
		WizardDialog dialog= new WizardDialog(shell, wizard);
		dialog.create();
		dialog.open();
		

		return null;
	}
}
