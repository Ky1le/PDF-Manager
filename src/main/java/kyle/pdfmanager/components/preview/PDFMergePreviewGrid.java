package kyle.pdfmanager.components.preview;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import kyle.pdfmanager.animations.MergeAnimation;
import kyle.pdfmanager.components.buttons.MergeButton;
import kyle.pdfmanager.components.itemlist.Item;
import kyle.pdfmanager.components.itemlist.ItemList;
import kyle.pdfmanager.services.PDFMergeService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public final class PDFMergePreviewGrid extends StackPane {

    private final PreviewGrid previewGrid;
    private final MergeButton mergeButton;

    public PDFMergePreviewGrid(@NonNull final PreviewGrid previewGrid,
                               @NonNull final MergeButton mergeButton) {
        super();
        this.previewGrid = previewGrid;
        this.mergeButton = mergeButton;
        this.mergeButton.setPreviewGrid(this);
        getChildren().addAll(previewGrid, mergeButton);
        setAlignment(mergeButton, Pos.BOTTOM_RIGHT);
    }

    public ObservableList<PreviewImage> getItems() {
        return this.previewGrid.getItems();
    }
}
