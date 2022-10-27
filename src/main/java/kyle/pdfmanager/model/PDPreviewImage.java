package kyle.pdfmanager.model;

import javafx.scene.image.Image;
import lombok.Getter;

import java.io.InputStream;

@Getter
public class PDPreviewImage extends Image {

    private final int pageNumber;

    public PDPreviewImage(final InputStream inputStream, final int pageNumber) {
        super(inputStream);
        this.pageNumber = pageNumber;
    }
}
