package kyle.pdfmanager.components.itemlist;

import impl.org.controlsfx.skin.PopOverSkin;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import kyle.pdfmanager.components.buttons.ItemChangeButton;
import kyle.pdfmanager.components.buttons.ItemDeleteButton;
import kyle.pdfmanager.components.buttons.ItemHighlightButton;
import kyle.pdfmanager.constants.StyleConstants;
import kyle.pdfmanager.models.PDDocumentWrapper;
import lombok.Getter;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.RangeSlider;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * A information panel which provides information for an item as also additional functionalities.
 */
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class ItemInformationPane extends PopOver {

    private static final String STYLE_CLASS_NAME = "item-information-title";
    private static final String STYLE_CLASS_PATH = "item-information-path";
    private static final String STYLE_CLASS_LOW_PAGE_NUMBER = "item-information-low-page-number";
    private static final String STYLE_CLASS_HIGH_PAGE_NUMBER = "item-information-high-page-number";
    private static final String STYLE_CLASS_BUTTON_BOX = "item-information-button-box";
    private static final String STYLE_CLASS_CONTENT_BOX = "item-information-content-box";

    private final Label fileName;
    private final Label filePath;
    private final RangeSlider rangeSlider;
    private final TextField lowPageNumber;
    private final TextField highPageNumber;
    private final VBox contentBox;
    private final HBox buttonBox;
    private final ItemDeleteButton deleteButton;
    private final ItemHighlightButton highlightButton;
    private final ItemChangeButton changeButton;

    private Item item;

    public ItemInformationPane(@NonNull final ItemDeleteButton itemDeleteButton,
                               @NonNull final ItemHighlightButton itemHighlightButton,
                               @NonNull final ItemChangeButton itemChangeButton) {
        super();
        this.fileName = new Label();
        this.filePath = new Label();
        this.rangeSlider = new RangeSlider();
        this.lowPageNumber = new TextField();
        this.highPageNumber = new TextField();
        this.contentBox = new VBox();
        this.buttonBox = new HBox();
        this.deleteButton = itemDeleteButton;
        this.deleteButton.setInformationPane(this);
        this.highlightButton = itemHighlightButton;
        this.highlightButton.setInformationPane(this);
        this.changeButton = itemChangeButton;
        this.changeButton.setItemInformationPane(this);

        applyStyle();
        createContents();
        createListeners();
    }

    /**
     * Used to provide the information for the Pane.
     * Stores a reference to the PDF.
     *
     * @param pdDocumentWrapper the wrapper which provides the necessary information.
     */
    public void setInformationContent(final PDDocumentWrapper pdDocumentWrapper) {
        if(item.getPDDocumentWrapper() != pdDocumentWrapper) item.setPdDocumentWrapper(pdDocumentWrapper);
        if(item.getPDDocumentWrapper() == null) return;

        fileName.setText(item.getPDDocumentWrapper().getFileName());
        filePath.setText(item.getPDDocumentWrapper().getFilePath());
        rangeSlider.setMin(1);
        rangeSlider.setMax(item.getPDDocumentWrapper().getPdDocument().getNumberOfPages());
        rangeSlider.setLowValue(1.0);
        rangeSlider.setHighValue(item.getPDDocumentWrapper().getPdDocument().getNumberOfPages());
    }

    public void setItem(final Item item) {
        this.item = item;
    }

    @NonNull
    public PDDocumentWrapper getPDDocumentWrapper() {
        final PDDocumentWrapper wrapper = item.getPDDocumentWrapper();
        assert wrapper != null;
        return wrapper;
    }

    private void applyStyle() {
        new ItemInformationSkin(this);
        fileName.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        fileName.getStyleClass().add(STYLE_CLASS_NAME);
        filePath.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        filePath.getStyleClass().add(STYLE_CLASS_PATH);
        lowPageNumber.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        lowPageNumber.getStyleClass().add(STYLE_CLASS_LOW_PAGE_NUMBER);
        highPageNumber.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        highPageNumber.getStyleClass().add(STYLE_CLASS_HIGH_PAGE_NUMBER);
        buttonBox.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        buttonBox.getStyleClass().add(STYLE_CLASS_BUTTON_BOX);
        contentBox.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        contentBox.getStyleClass().add(STYLE_CLASS_CONTENT_BOX);
    }

    private void createContents() {
        rangeSlider.setShowTickLabels(true);
//        rangeSlider.setShowTickMarks(true);
        rangeSlider.setSnapToTicks(true);
        rangeSlider.setMinorTickCount(1);
        rangeSlider.setMajorTickUnit(2.0);
//        rangeSlider.setBlockIncrement(1.0);
        final HBox labelBox = new HBox();
        labelBox.getChildren().addAll(lowPageNumber, highPageNumber);
        buttonBox.getChildren().addAll(changeButton, highlightButton, deleteButton);
        contentBox.getChildren().addAll(fileName, filePath, labelBox, rangeSlider, buttonBox);
        setContentNode(contentBox);
    }

    private void createListeners() {
        rangeSlider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            final String sliderValue = String.valueOf(newValue);
            lowPageNumber.setText(sliderValue);
        });

        rangeSlider.highValueProperty().addListener((observable, oldValue, newValue) -> {
            final String sliderValue = String.valueOf(newValue);
            highPageNumber.setText(sliderValue);
        });

        setOnAutoHide(event -> {
            final int min = (int)rangeSlider.getLowValue();
            final int max = (int)rangeSlider.getHighValue();
            getPDDocumentWrapper().setShownPages(min, max);
        });
    }

}

class ItemInformationSkin extends PopOverSkin {

    //private static final String STYLE_CLASS = "item-information";

    private final StackPane skinnableStackPane;

    public ItemInformationSkin(final ItemInformationPane informationPane) {
        super(informationPane);
        this.skinnableStackPane = getSkinnable().getRoot();
        this.skinnableStackPane.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
    }
}
