package kyle.pdfmanager.components.buttons;

import javafx.scene.control.Button;
import kyle.pdfmanager.animations.MergeAnimation;
import kyle.pdfmanager.components.itemlist.Item;
import kyle.pdfmanager.components.itemlist.ItemList;
import kyle.pdfmanager.components.preview.PDFMergePreviewGrid;
import kyle.pdfmanager.components.preview.PreviewGrid;
import kyle.pdfmanager.constants.GlyphFontFamilyConstants;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.services.PDFMergeService;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.glyphfont.Glyph;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MergeButton extends Button {

    private static final String STYLE_CLASS = "merge-button";

    private final PDFMergeService pdfMergeService;
    private final ItemList itemList;

    private PDFMergePreviewGrid previewGrid;

    public MergeButton(@NonNull final PDFMergeService pdfMergeService,
                       @NonNull final ItemList itemList) {
//        super(StringUtils.EMPTY, new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf24d'));
        super(StringUtils.EMPTY, new Glyph(GlyphFontFamilyConstants.FONT_AWESOME, '\uf0c5'));
        this.pdfMergeService = pdfMergeService;
        this.itemList = itemList;
        style();
        clickable();
    }

    public void setPreviewGrid(final PDFMergePreviewGrid previewGrid) {
        this.previewGrid = previewGrid;
    }

    private void style() {
        getStyleClass().add(STYLE_CLASS);
        setFocusTraversable(false);
    }

    private void clickable() {
        setOnAction(event -> {
            final MergeAnimation mergeAnimation = new MergeAnimation(this)
                    .addNodes(this.previewGrid.getItems());
            mergeAnimation.setOnFinished(e -> itemList.clear());

            try {
                final boolean isMerged =
                        pdfMergeService.merge(
                            this.itemList.getItems().stream()
                                .map(Item::getPDDocumentWrapper)
                                .filter(Objects::nonNull)
                                .collect(Collectors.toList())
                        );
                if(isMerged) mergeAnimation.play();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getUserAgentStylesheet() {
        return StyleConstants.PREVIEW_MERGE_STYLE_RESOURCE;
    }
}
