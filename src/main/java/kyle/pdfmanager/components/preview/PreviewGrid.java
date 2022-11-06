package kyle.pdfmanager.components.preview;

import kyle.pdfmanager.animations.PopUpAnimation;
import kyle.pdfmanager.animations.SequentialPopUpAnimation;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.models.PDDocumentWrapper;
import kyle.pdfmanager.models.PDPreviewImage;
import org.controlsfx.control.GridView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PreviewGrid extends GridView<PreviewImage> {

    private static final String STYLE_CLASS = "preview-grid";

    public PreviewGrid() {
        setCellFactory(gridView -> new PreviewImageGridCell());
        applyStyle();
    }

    /**
     * Creates and displays the preview images for the given PDFs.
     * @param wrappers List with all PDFs who should be displayed.
     */
    public void createPreviewImages(final List<PDDocumentWrapper> wrappers) {
        getItems().clear();
        final List<PDDocumentWrapper> pdDocumentWrapperList =
                wrappers.stream().filter(Objects::nonNull).collect(Collectors.toList());

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

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.PREVIEW_STYLE_RESOURCE;
    }
}
