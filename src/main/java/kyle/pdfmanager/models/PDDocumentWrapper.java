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
     * Set the preview images for the PDF.
     * Pages who are not shown as preview images will be not included in the merged pdf.
     *
     * @param min the min page which should be included.
     * @param max the max page which should be inlcuded.
     */
    public void setShownPages(final int min, final int max) {
        for(int i=0; i < previewImages.size(); i++) {
            final PDPreviewImage previewImage = previewImages.get(i);
            previewImage.setShown((i+1) >= min && (i+1) <= max);
        }
    }
}
