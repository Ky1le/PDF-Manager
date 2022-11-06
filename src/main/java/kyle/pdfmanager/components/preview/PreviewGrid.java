package kyle.pdfmanager.components.preview;

import kyle.pdfmanager.animations.PopUpAnimation;
import kyle.pdfmanager.animations.SequentialPopUpAnimation;
import kyle.pdfmanager.components.itemlist.Item;
import kyle.pdfmanager.components.itemlist.ItemList;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.models.PDDocumentWrapper;
import kyle.pdfmanager.models.PDPreviewImage;
import org.controlsfx.control.GridView;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PreviewGrid extends GridView<PreviewImage> {

    private static final String STYLE_CLASS = "preview-grid";

    private final ItemList itemList;

    public PreviewGrid(@NonNull final ItemList itemList) {
        this.itemList = itemList;
        setCellFactory(gridView -> new PreviewImageGridCell());
        applyStyle();
        applyListeners();
    }

    /**
     * Creates and displays the preview images for all pdfs.
     * Attention: Does not include any page restrictions right now!
     */
    public void createPreviewImages() {
        getItems().clear();
        final List<PDDocumentWrapper> pdDocumentWrapperList =
                this.itemList.getItems().stream().map(Item::getPDDocumentWrapper).filter(Objects::nonNull).collect(Collectors.toList());

        final SequentialPopUpAnimation sequentialPopUpAnimation = new SequentialPopUpAnimation();
        pdDocumentWrapperList.stream()
                .map(PDDocumentWrapper::getPreviewImages)
                .forEach(pdPreviewImages -> pdPreviewImages.stream().filter(PDPreviewImage::isShown).forEach(pdPreviewImage -> {
                            final PreviewImage previewImage = new PreviewImage(pdPreviewImage);
                            setCellWidth(pdPreviewImage.getWidth());
                            setCellHeight(pdPreviewImage.getHeight());
                            getItems().add(previewImage);
                            sequentialPopUpAnimation.add(new PopUpAnimation(previewImage));
                        }
        ));
        sequentialPopUpAnimation.play();
    }

    /**
     * Highlights all images for the specific PDF.
     *
     * @param uuid of the PDF which images should be highlighted.
     */
    public void highlightPreviewItems(final UUID uuid) {
        getItems().forEach(item -> item.setHighlight(item.getPdDocumentWrapperUUID() == uuid));
    }

    private void applyStyle() {
        getStyleClass().add(STYLE_CLASS);
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

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.PREVIEW_STYLE_RESOURCE;
    }
}
