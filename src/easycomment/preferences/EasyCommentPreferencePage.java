package easycomment.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
//import pluginClass;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They
 * are stored in the preference store that belongs to
 * the main plug-in class. That way, preferences can
 * be accessed directly via the preference store.
 */
public class EasyCommentPreferencePage
	extends PreferencePage
	implements IWorkbenchPreferencePage {

	protected TabFolder tabFolder;

	protected Text sqlAuthorText;

	public EasyCommentPreferencePage() {
		super();
		setPreferenceStore(EasyCommentPlugin.getDefault().getPreferenceStore());
	}

	 /*
     * @see IWorkbenchPreferencePage#init(IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {
        // do-nothing
    }

    /*
     * @see PreferencePage#createContents(Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        tabFolder = new TabFolder(parent, SWT.TOP);
        createTabSql();
        return tabFolder;
    }

    private void createTabSql() {
        TabItem tabOpen = new TabItem(tabFolder, SWT.NONE);
        tabOpen.setText("SQL comment");

        Composite composite = createContainer(tabFolder);
        tabOpen.setControl(composite);

        IPreferenceStore store = getPreferenceStore();

        sqlAuthorText = createLabeledText("Author for comment",
                "In comment, you can use this value.",
                store.getString(PreferenceConstants.AUTHOR_IN_COMMENT),
                composite, SWT.NONE);
    }

    protected static Composite createContainer(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        return composite;
    }

    protected static Text createLabeledText(String title, String tooltip, String value, Composite defPanel,
            int style) {
        Composite commonPanel = new Composite(defPanel, SWT.NONE);
        GridData gridData = new GridData (SWT.FILL, SWT.FILL, true, true);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.marginWidth = 0;
        commonPanel.setLayout(layout);
        commonPanel.setLayoutData(gridData);

        Label label = new Label(commonPanel, SWT.LEFT);
        label.setText(title);
        label.setToolTipText(tooltip);

        Text text = new Text(commonPanel, SWT.SHADOW_IN | SWT.BORDER | style);
        gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        text.setLayoutData(gridData);
        text.setText(value);
        text.setToolTipText(tooltip);
        return text;
    }

    @Override
    public boolean performOk() {
        IPreferenceStore store = getPreferenceStore();
        store.setValue(PreferenceConstants.AUTHOR_IN_COMMENT, sqlAuthorText.getText());
        return true;
    }
}