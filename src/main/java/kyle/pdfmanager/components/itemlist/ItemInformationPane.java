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

    private final Label fileName;
    private final Label filePath;
    private final RangeSlider rangeSlider;
    private final TextField lowPageNumber;
    private final TextField highPageNumber;
    private final ItemDeleteButton deleteButton;
    private final ItemHighlightButton highlightButton;
    private final ItemChangeButton changeButton;

    private PDDocumentWrapper pdDocumentWrapper;

    public ItemInformationPane(@NonNull final ItemDeleteButton itemDeleteButton,
                               @NonNull final ItemHighlightButton itemHighlightButton,
                               @NonNull final ItemChangeButton itemChangeButton) {
        super();
        this.fileName = new Label();
        this.filePath = new Label();
        this.rangeSlider = new RangeSlider();
        this.lowPageNumber = new TextField();
        this.highPageNumber = new TextField();
        this.deleteButton = itemDeleteButton;
        this.highlightButton = itemHighlightButton;
        this.highlightButton.setInformationPane(this);
        this.changeButton = itemChangeButton;

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
        this.pdDocumentWrapper = pdDocumentWrapper;
        fileName.setText(pdDocumentWrapper.getFileName());
        filePath.setText(pdDocumentWrapper.getFilePath());
        rangeSlider.setMin(1);
        rangeSlider.setMax(pdDocumentWrapper.getPdDocument().getNumberOfPages());
        //rangeSlider.setLowValue(1.0);
        //rangeSlider.setHighValue(pdDocumentWrapper.getPdDocument().getNumberOfPages());
    }

    private void applyStyle() {
        new ItemInformationSkin(this);
        fileName.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        fileName.getStyleClass().add(STYLE_CLASS_NAME);
        filePath.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        filePath.getStyleClass().add(STYLE_CLASS_PATH);
        lowPageNumber.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        highPageNumber.getStylesheets().add(StyleConstants.ITEM_INFORMATION_STYLE_RESOURCE);
        System.out.println(lowPageNumber.getStyleClass());
    }

    private void createContents() {
        rangeSlider.setShowTickLabels(true);
        rangeSlider.setShowTickMarks(true);
        //rangeSlider.setSnapToTicks(true);
        //rangeSlider.setBlockIncrement(1.0);
        final VBox vBox = new VBox();
        final HBox hBox = new HBox();
        hBox.getChildren().addAll(lowPageNumber, highPageNumber);
        final HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(changeButton, highlightButton, deleteButton);
        vBox.getChildren().addAll(fileName, filePath, hBox, rangeSlider, buttonBox);
        setContentNode(vBox);
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

        lowPageNumber.textProperty().addListener((observable, oldValue, newValue) -> {
           final double sliderValue = Double.parseDouble(newValue);
           rangeSlider.setLowValue(sliderValue);
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
