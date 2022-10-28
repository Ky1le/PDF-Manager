package kyle.pdfmanager.components.itemlist;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import kyle.pdfmanager.model.PDDocumentWrapper;
import org.controlsfx.control.PopOver;


public class ItemInformationPane extends PopOver {

    private final Label fileName;
    private final Label filePath;

    public ItemInformationPane(final PDDocumentWrapper wrapper) {
        super();
        this.fileName = new Label(wrapper.getFileName());
        this.filePath = new Label(wrapper.getFilePath());
        createContents();
    }

    private void createContents() {
        final VBox vBox = new VBox();
        vBox.getChildren().addAll(fileName, filePath);
        setContentNode(vBox);
    }
}
