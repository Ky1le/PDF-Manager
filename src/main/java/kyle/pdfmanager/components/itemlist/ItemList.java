package kyle.pdfmanager.components.itemlist;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import kyle.pdfmanager.constants.StyleConstants;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemList extends VBox {

    private final ApplicationContext applicationContext;

    private final int maxItems;

    public ItemList(@NonNull final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        this.maxItems = 5;
        style();
        createItems();
    }

    /**
     * Returns all Items in the ItemList.
     *
     * @return a List with all Items in the list.
     */
    @SuppressWarnings("unchecked")
    public List<Item> getItems() {
        return (List<Item>)(List<?>) getChildren()
                .stream().filter(child -> child instanceof Item).collect(Collectors.toList());
    }

    public void clear() {
        getChildren().stream().map(Item.class::cast).forEach(item -> item.setPdDocumentWrapper(null));
    }

    private void createItems() {
        for(int i=0;i<maxItems;i++) {
            final Item item = applicationContext.getBean(Item.class);
            getChildren().add(item);
        }
    }

    private void style() {
        getStyleClass().add("item-list");
        setAlignment(Pos.CENTER);
        setSpacing(20);
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.ITEM_LIST_STYLE_RESOURCE;
    }
}
