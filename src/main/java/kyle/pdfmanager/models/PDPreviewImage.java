package kyle.pdfmanager.models;

import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;
import java.util.UUID;

@Getter
@Setter
public class PDPreviewImage extends Image {

    private final UUID pdDocumentWrapperUUID;
    private final int pageNumber;
    private boolean isShown;

    public PDPreviewImage(final UUID pdDocumentWrapperUUID, final InputStream inputStream, final int pageNumber) {
        super(inputStream);
        this.pdDocumentWrapperUUID = pdDocumentWrapperUUID;
        this.pageNumber = pageNumber;
        this.isShown = true;
    }
}
