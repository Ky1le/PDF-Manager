package kyle.pdfmanager.components.preview;

import javafx.scene.Node;
import org.controlsfx.control.GridCell;

public class PreviewImageGridCell extends GridCell<PreviewImage> {

    public PreviewImageGridCell() {
        this.getStyleClass().add("image-grid-cell");
    }

    protected void updateItem(PreviewImage item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) this.setGraphic(null);
        else {
            setMinSize(item.getWidth(), item.getHeight());
            this.setGraphic(item);
        }
    }
}
