package kyle.pdfmanager.components.itemlist;

import kyle.pdfmanager.model.PDDocumentWrapper;
import org.controlsfx.control.PopOver;


public class ItemInformationPane extends PopOver {

    public ItemInformationPane(final PDDocumentWrapper wrapper) {
        super();
    }

    public ItemInformationPane() {
        super();
        setPrefSize(100, 100);
    }
}
