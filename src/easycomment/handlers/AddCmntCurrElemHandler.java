package easycomment.handlers;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import easycomment.util.XMLReadUtil;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class AddCmntCurrElemHandler extends AbstractHandler {

	private static final int FILE_JSP = 0;
	private static final int FILE_SQL = 1;
	private static final int FILE_NOT_SUPPORTED = -1;

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
		String fileName = "";
		String testMessage = "";
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IEditorPart editor = window.getActivePage().getActiveEditor();//.getSelectionProvider().getSelection();;
		IEditorInput input = editor.getEditorInput();
		IFile file = null;
		if (input instanceof FileEditorInput) {
			file = ((FileEditorInput) input).getFile();
			fileName = file.getName();
		}

		int fileKind = FILE_NOT_SUPPORTED;
		if(fileName.endsWith(".jsp")) {
			fileKind = FILE_JSP;
		} else if(fileName.endsWith("_sql.xml")) {
			fileKind = FILE_SQL;
		}

		switch (fileKind) {
			case FILE_JSP: {
				try {
					ITextEditor textEditor = (ITextEditor) editor;
					IDocumentProvider provider = textEditor.getDocumentProvider();
					IDocument document = provider.getDocument(editor.getEditorInput());
					ITextSelection textSelection = (ITextSelection)textEditor.getSelectionProvider().getSelection();
					int offset = textSelection.getOffset();
					int lineNumber = document.getLineOfOffset(offset);
					int startLine = textSelection.getStartLine();

					testMessage = textSelection.getText() + "\n" + lineNumber + ":" + offset + "\n" + startLine;
				} catch(Exception e) {
					testMessage = e.toString();
				}
				break;
			}
			case FILE_SQL: {
				Date today = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.");
				String commentString =
					"\t<!--\n"
					+ "\t설명: 쿼리설명\n"
					+ "\t작성자: 작성자명\n"
					+ "\t최초작성일: "+dateFormat.format(today)+"\n"
					+ "\t수정일\t\t\t수정자\t\t수정내용\n"
					+ "\t=====================================\n"
					+ "\t-->\n";
				try {
					ITextSelection textSelection;
					IDocumentProvider provider;
					if(editor instanceof ITextEditor) {
						ITextEditor textEditor = (ITextEditor) editor;
						provider = textEditor.getDocumentProvider();
						textSelection = (ITextSelection)textEditor.getSelectionProvider().getSelection();
					} else {
						textSelection = (ITextSelection) ((MultiPageEditorPart)editor).getSite().getSelectionProvider().getSelection();
						IEditorPart[] editorParts = ((MultiPageEditorPart)editor).findEditors(editor.getEditorInput());
						ITextEditor textEditor = (ITextEditor) editorParts[0];
						provider = textEditor.getDocumentProvider();
					}
					IDocument document = provider.getDocument(editor.getEditorInput());
					int startLine = textSelection.getStartLine();

					InputStream is = file.getContents();
					int lineNo = XMLReadUtil.getLineNoOfCurrentElement(is, startLine) - 1;
					document.replace(document.getLineOffset(lineNo), 0, commentString);

				} catch(Exception e) {
					StringWriter sw = new StringWriter();
					e.printStackTrace(new PrintWriter(sw));
					testMessage = sw.toString();
				}
				break;
			}

			default:
				break;
		}

//		MessageDialog.openInformation(
//				window.getShell(),
//				"Current Filename",
//				testMessage);

		return null;
	}
}
