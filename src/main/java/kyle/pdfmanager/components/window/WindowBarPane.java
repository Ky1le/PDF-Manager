package kyle.pdfmanager.components.window;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import kyle.pdfmanager.components.buttons.ExitButton;
import kyle.pdfmanager.components.buttons.MinimizeButton;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.holder.StageHolder;
import kyle.pdfmanager.properties.WindowProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Top Bar of the window.
 *
 */
@Component
public class WindowBarPane extends Pane {

    private static final String STYLE_CLASS = "window-bar";
    private static final int DEFAULT_HEIGHT = 50;

    private final StageHolder stageHolder;
    private final WindowProperties windowProperties;
    private final ApplicationContext applicationContext;

    private double offsetX;
    private double offsetY;

    public WindowBarPane(@NonNull final StageHolder stageHolder,
                         @NonNull final WindowProperties windowProperties,
                         @NonNull final ApplicationContext applicationContext) {
        super();
        this.stageHolder = stageHolder;
        this.windowProperties = windowProperties;
        this.applicationContext = applicationContext;

        style();
        createItems();
        createMovable();
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.WINDOW_STYLE_RESOURCE;
    }

    private void createItems() {
        final HBox buttonContainer = new HBox();
        buttonContainer.setPrefSize(windowProperties.getWidth(), DEFAULT_HEIGHT);
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        final Button minimizeButton = applicationContext.getBean(MinimizeButton.class);
        final Button exitButton = applicationContext.getBean(ExitButton.class);
        buttonContainer.getChildren().addAll(minimizeButton, exitButton);
        getChildren().add(buttonContainer);
    }

    private void createMovable() {
        setOnMousePressed(event -> {
            offsetX = event.getSceneX();
            offsetY = event.getSceneY();
        });

        setOnMouseDragged(event -> {
            stageHolder.getStage().setX(event.getScreenX() - offsetX);
            stageHolder.getStage().setY(event.getScreenY() - offsetY);
        });
    }

    private void style() {
        getStyleClass().add(STYLE_CLASS);
        setPrefSize(windowProperties.getWidth(), DEFAULT_HEIGHT);
    }
}
