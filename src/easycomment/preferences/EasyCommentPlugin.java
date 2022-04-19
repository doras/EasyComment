package easycomment.preferences;

import org.eclipse.ui.plugin.AbstractUIPlugin;

public class EasyCommentPlugin extends AbstractUIPlugin {

	private static EasyCommentPlugin plugin;

	public EasyCommentPlugin() {
		super();
		if(plugin != null) {
			throw new IllegalStateException("EasyCommentPlugin is a singleton!");
		}
		plugin = this;
	}

	public static EasyCommentPlugin getDefault() {
        return plugin;
    }

}
