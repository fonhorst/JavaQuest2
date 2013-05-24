package Android;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class JavaQuest extends SingleFrameApplication {

	private JavaQuestView view;

	/**
	 * At startup create and show the main frame of the application.
	 */
	@Override
	protected void startup() {
		show(this.view = new JavaQuestView(this));
	}

	/**
	 * This method is to initialize the specified window by injecting resources.
	 * Windows shown in our application come fully initialized from the GUI
	 * builder, so this additional configuration is not needed.
	 */
	@Override
	protected void configureWindow(java.awt.Window root) {
	}

	/**
	 * A convenient static getter for the application instance.
	 * 
	 * @return the instance of DesktopApplication1
	 */
	public static JavaQuest getApplication() {
		return Application.getInstance(JavaQuest.class);
	}

	private static void test() throws Exception {
		throw new Exception("inner");
	}

	/**
	 * Main method launching the application.
	 */
	public static void main(String[] args) throws FileNotFoundException,
			IOException, Exception {
		launch(JavaQuest.class, args);

	}

}
