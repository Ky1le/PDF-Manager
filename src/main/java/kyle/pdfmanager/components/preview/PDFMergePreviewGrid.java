package kyle.pdfmanager.components.preview;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import kyle.pdfmanager.components.buttons.MergeButton;
import kyle.pdfmanager.components.itemlist.Item;
import kyle.pdfmanager.components.itemlist.ItemList;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.models.PDDocumentWrapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public final class PDFMergePreviewGrid extends StackPane {

    private final PreviewGrid previewGrid;
    private final MergeButton mergeButton;
    private final ItemList itemList;
    private final Label label;

    public PDFMergePreviewGrid(@NonNull final PreviewGrid previewGrid,
                               @NonNull final MergeButton mergeButton,
                               @NonNull final ItemList itemList) {
        super();
        this.previewGrid = previewGrid;
        this.mergeButton = mergeButton;
        this.mergeButton.setPreviewGrid(this);
        this.itemList = itemList;
        this.label = new Label("No PDFs!");
        this.label.getStylesheets().add(StyleConstants.PREVIEW_MERGE_STYLE_RESOURCE);
        getChildren().addAll(previewGrid, label, mergeButton);
        setAlignment(mergeButton, Pos.BOTTOM_RIGHT);
        applyListeners();
    }

    public ObservableList<PreviewImage> getItems() {
        return this.previewGrid.getItems();
    }

    public void createPreviewImages() {
        final List<PDDocumentWrapper> wrappers =
                itemList.getItems().stream().map(Item::getPDDocumentWrapper).collect(Collectors.toList());
        previewGrid.createPreviewImages(wrappers);
    }

    private void applyListeners() {
        this.itemList.getItems().forEach(item -> {
            item.getPDDocumentWrapperProperty().addListener(listener -> createPreviewImages());
            item.getHasPreviewChangesProperty().addListener((observable, oldValue, newValue) -> {
                createPreviewImages();
                item.getHasPreviewChangesProperty().setValue(false);
            });
        });

        this.itemList.getItems().stream()
                .map(Item::getPDDocumentWrapperProperty)
                .forEach(wrapperProperty -> wrapperProperty.addListener(listener -> createPreviewImages()));
    }
}
