package kyle.pdfmanager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kyle.pdfmanager.components.itemlist.ItemList;
import kyle.pdfmanager.components.window.WindowPane;
import kyle.pdfmanager.holder.StageHolder;
import kyle.pdfmanager.properties.WindowProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

public class PdfManagerFxApplication extends Application{

    private ConfigurableApplicationContext configurableApplicationContext;

    @Override
    public void init() {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
                applicationContext -> {
                    applicationContext.registerBean(Application.class, () -> PdfManagerFxApplication.this);
                    applicationContext.registerBean(Application.Parameters.class, this::getParameters);
                };

        this.configurableApplicationContext = new SpringApplicationBuilder()
                .sources(PdfManagerApplication.class)
                .initializers(initializer)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void stop() {
        this.configurableApplicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) {
        this.configurableApplicationContext.publishEvent(new StageReadyEvent(stage));
    }
}

class StageReadyEvent extends ApplicationEvent {

    public StageReadyEvent(Stage source) {
        super(source);
    }

    public Stage getStage() {
        return (Stage) getSource();
    }
}

@Component
class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private final WindowProperties windowProperties;
    private final StageHolder stageHolder;
    private final ApplicationContext applicationContext;

    public StageInitializer(@NonNull final WindowProperties windowProperties,
                            @NonNull final StageHolder stageHolder,
                            @NonNull final ApplicationContext applicationContext) {
        this.windowProperties = windowProperties;
        this.stageHolder = stageHolder;
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        final Stage stage = event.getStage();
        stageHolder.setStage(stage);
        final WindowPane windowPane = applicationContext.getBean(WindowPane.class);
        final ItemList itemList = applicationContext.getBean(ItemList.class);
        windowPane.setLeft(itemList);
        Scene scene = new Scene(windowPane, windowProperties.getWidth(), windowProperties.getHeight());
        stage.setScene(scene);
        stage.setTitle(windowProperties.getTitle());
        stage.show();
    }
}

