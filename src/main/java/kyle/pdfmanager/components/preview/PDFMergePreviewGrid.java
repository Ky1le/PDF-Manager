package kyle.pdfmanager.components.preview;

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

    private final PDFMergeService pdfMergeService;

    private final PreviewGrid previewGrid;
    private final MergeButton mergeButton;
    private final ItemList itemList;

    public PDFMergePreviewGrid(@NonNull final PreviewGrid previewGrid,
                               @NonNull final MergeButton mergeButton,
                               @NonNull final ItemList itemList,
                               @NonNull final PDFMergeService pdfMergeService) {
        super();
        this.previewGrid = previewGrid;
        this.mergeButton = mergeButton;
        this.itemList = itemList;
        this.pdfMergeService = pdfMergeService;
        getChildren().addAll(previewGrid, mergeButton);
        setAlignment(mergeButton, Pos.BOTTOM_RIGHT);
        applyListeners();
    }

    private void applyListeners() {
        mergeButton.pressedProperty().addListener(event -> {
            final MergeAnimation mergeAnimation = new MergeAnimation(this.mergeButton)
                    .addNodes(this.previewGrid.getItems());
            mergeAnimation.setOnFinished(e -> itemList.clear());

            try {
                final boolean isMerged = pdfMergeService.merge(this.itemList.getItems().stream().map(Item::getPDDocumentWrapper).filter(Objects::nonNull).collect(Collectors.toList()));
                if(isMerged) {
                    mergeAnimation.play();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
