package easycomment.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.contexts.ContextManager;
import org.eclipse.core.resources.IFile;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AddCmntCurrElemHandler extends AbstractHandler {

	private static final int FILE_JSP = 0;
	private static final int FILE_SQL = 1;

	/**
	 * The constructor.
	 */
	public AddCmntCurrElemHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
       /* part = HandlerUtil.getActivePart(event);
        run(new Action(){
            @Override
            public String getId() {
                return event.getCommand().getId();
            }
        });*/
		String tmp = "none";
		IEditorPart editor = window.getActivePage().getActiveEditor();//.getSelectionProvider().getSelection();;
		IEditorInput input = editor.getEditorInput();
		if (input instanceof FileEditorInput) {
		    tmp = ((FileEditorInput) input).getFile().getName();
		}


		MessageDialog.openInformation(
				window.getShell(),
				"Current Filename",
				tmp);

		MessageDialog.openInformation(
				window.getShell(),
				"Current activepart",
				((IEclipseContext)editor.getEditorSite().getService(IEclipseContext.class)).get(ContextManager.class).getActiveContextIds().toString());

		return null;
	}
}
