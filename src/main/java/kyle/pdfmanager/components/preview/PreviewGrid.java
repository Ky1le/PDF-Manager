package kyle.pdfmanager.components.preview;

import kyle.pdfmanager.components.itemlist.Item;
import kyle.pdfmanager.components.itemlist.ItemList;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.models.PDDocumentWrapper;
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
        applyProperties();
        applyListeners();
    }

    /**
     * Creates and displays the preview images for all pdfs.
     * Attention: Does not include any page restrictions right now!
     */
    private void createPreviewImages() {
        getItems().clear();
        final List<PDDocumentWrapper> pdDocumentWrapperList =
                this.itemList.getItems().stream().map(Item::getPDDocumentWrapper).filter(Objects::nonNull).collect(Collectors.toList());
        pdDocumentWrapperList.forEach(wrapper -> wrapper.getPreviewImages().forEach(image -> {
            final PreviewImage previewImage = new PreviewImage(image);
            setCellWidth(image.getWidth());
            setCellHeight(image.getHeight());
            getItems().add(previewImage);
        }));
    }

    /**
     * Highlights all images for the specific PDF.
     *
     * @param uuid of the PDF which images should be highlighted.
     */
    public void highlightPreviewItems(final UUID uuid) {
        System.out.println(uuid);
        getItems().forEach(item -> item.setHighlight(item.getPdDocumentWrapperUUID() == uuid));
    }

    private void applyStyle() {
        getStyleClass().add(STYLE_CLASS);
    }

    private void applyProperties() {
        //setCellHeight(100);
        //setCellWidth(100);
    }

    private void applyListeners() {
        this.itemList.getItems().stream()
                .map(Item::getPDDocumentWrapperProperty)
                .forEach(wrapperProperty -> wrapperProperty.addListener(listener -> createPreviewImages()));
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.PREVIEW_STYLE_RESOURCE;
    }
}
