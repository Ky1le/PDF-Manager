package kyle.pdfmanager.services;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kyle.pdfmanager.models.PDDocumentWrapper;
import kyle.pdfmanager.models.PDPreviewImage;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PDFLoaderService {

    private final Logger LOGGER = LoggerFactory.getLogger(PDFLoaderService.class);

    private final FileChooser fileChooser;
    private final PDFPreviewImageService pdfPreviewImageService;

    public PDFLoaderService(@NonNull final FileChooser fileChooser,
                            @NonNull final PDFPreviewImageService pdfPreviewImageService) {
        this.fileChooser = fileChooser;
        this.pdfPreviewImageService = pdfPreviewImageService;
    }

    /**
     * Opens a modal to select a PDF file.
     *
     * @return the PDF file as a PDDocumentWrapper
     * @throws IOException when the PDDocument could not be loaded
     * @see PDFPreviewImageService#convertToPreviewImage(PDDocumentWrapper)
     */
    @Nullable
    public PDDocumentWrapper load() throws IOException {
        final File file = fileChooser.showOpenDialog(new Stage());
        if (file == null) {
            LOGGER.info("File Chooser was closed or selected file does not exist anymore!");
            return null;
        }
        final PDDocument pdDocument = PDDocument.load(file);
        final String fileName = sanitizeFileName(file.getName());
        final PDDocumentWrapper wrapper = new PDDocumentWrapper(fileName, file.getPath(), pdDocument);
        final List<PDPreviewImage> previewImages = pdfPreviewImageService.convertToPreviewImage(wrapper);
        wrapper.getPreviewImages().addAll(previewImages);
        return wrapper;
    }

    /**
     * Removes the extension type from the file name.
     *
     * @param fileName which should be sanitized
     * @return the sanitized file name
     */
    @NonNull
    private String sanitizeFileName(@NonNull final String fileName) {
        if (StringUtils.isBlank(fileName)) {
            LOGGER.info("PDF has no name!");
            throw new IllegalArgumentException();
        }
        return StringUtils.substringBeforeLast(fileName, PDDocumentWrapper.PREFIX);
    }
}
