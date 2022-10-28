package kyle.pdfmanager.model;

import javafx.scene.image.Image;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

@Getter
public class PDPreviewImage extends Image {

    private final UUID pdDocumentWrapperUUID;
    private final int pageNumber;

    public PDPreviewImage(final UUID pdDocumentWrapperUUID, final InputStream inputStream, final int pageNumber) {
        super(inputStream);
        this.pdDocumentWrapperUUID = pdDocumentWrapperUUID;
        this.pageNumber = pageNumber;
    }
}
