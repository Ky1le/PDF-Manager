package kyle.pdfmanager.components.window;

import javafx.scene.layout.BorderPane;
import kyle.pdfmanager.constants.StyleConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Basic BorderPane with a WindowBar
 *
 */
@Component
public class WindowPane extends BorderPane {

    private static String STYLE_CLASS = "window";

    private final ApplicationContext applicationContext;

    public WindowPane(@NonNull final ApplicationContext applicationContext) {
        super();
        this.applicationContext = applicationContext;
        getStyleClass().add(STYLE_CLASS);
        createWindowBar();
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.WINDOW_STYLE_RESOURCE;
    }

    private void createWindowBar() {
        final WindowBarPane windowBarPane = applicationContext.getBean(WindowBarPane.class);
        setTop(windowBarPane);
    }
}
