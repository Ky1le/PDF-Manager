package kyle.pdfmanager.models;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class PDDocumentWrapper {

    public static final String PREFIX = ".pdf";

    private final UUID uuid;
    private final String fileName;
    private final String filePath;
    private final PDDocument pdDocument;
    private final List<PDPreviewImage> previewImages;

    public PDDocumentWrapper(final String fileName,
                             final String filePath,
                             @NonNull final PDDocument pdDocument) {
        this.uuid = UUID.randomUUID();
        this.fileName = fileName;
        this.filePath = filePath;
        this.pdDocument = pdDocument;
        this.previewImages = new ArrayList<>(pdDocument.getNumberOfPages());
    }

    /**
     * Checks if the Pdf wrapper has any images loaded.
     *
     * @return indicator if the preview Images are loaded.
     */
    public boolean hasPreviewImages() {
        return !previewImages.isEmpty();
    }

    /**
     *
     */
    //@NonNull
    public List<Integer> missingPictures() {
        return null;
    }

    /**
     *
     */
    public int missingPictureCount() {
        return missingPictures().size();
    }
}
