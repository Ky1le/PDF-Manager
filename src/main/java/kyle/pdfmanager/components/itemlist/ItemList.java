package kyle.pdfmanager.components.itemlist;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ItemList extends VBox {

    private final ApplicationContext applicationContext;

    private final int maxItems;

    public ItemList(@NonNull final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        this.maxItems = 5;
        applyProperties();
        createItems();
    }

    private void createItems() {
        for(int i=0;i<maxItems;i++) {
            final Item item = applicationContext.getBean(Item.class);
            getChildren().add(item);
        }
    }

    private void applyProperties() {
        setAlignment(Pos.CENTER_LEFT);
        setSpacing(20);
    }
}
