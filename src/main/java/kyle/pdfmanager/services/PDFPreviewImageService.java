package kyle.pdfmanager.services;

import kyle.pdfmanager.models.PDDocumentWrapper;
import kyle.pdfmanager.models.PDPreviewImage;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class PDFPreviewImageService {

    /**
     * Converts the PDDocument Pages to images with the predefined scale of 0.2.
     *
     * @param wrapper the wrapper which contains the PDDocument.
     * @return List with all PDDocument images.
     * @throws IOException when an image could not be rendered.
     */
    public List<PDPreviewImage> convertToPreviewImage(final PDDocumentWrapper wrapper) throws IOException {
        return convertToPreviewImage(wrapper, 0.2f);
    }

    /**
     * Converts the PDDocument Pages to images.
     *
     * @param wrapper the wrapper which contains the PDDocument.
     * @param scale the scale for the images.
     * @return List with all PDDocument images.
     * @throws IOException when an image could not be rendered.
     */
    public List<PDPreviewImage> convertToPreviewImage(final PDDocumentWrapper wrapper, final float scale) throws IOException {
        final PDFRenderer renderer = new PDFRenderer(wrapper.getPdDocument());
        final int pageCount = wrapper.getPdDocument().getNumberOfPages();
        final List<PDPreviewImage> images = new ArrayList<>(pageCount);

        for(int i=0;i<pageCount; i++) {
            final BufferedImage bufferedImage = renderer.renderImage(i, scale);
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
            final InputStream is = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            final PDPreviewImage previewImage = new PDPreviewImage(wrapper.getUuid(), is, i + 1);
            images.add(previewImage);
        }

        return images;
    }
}
