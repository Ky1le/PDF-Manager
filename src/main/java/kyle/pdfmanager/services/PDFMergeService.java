package kyle.pdfmanager.services;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import kyle.pdfmanager.models.PDDocumentWrapper;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class PDFMergeService {

    private final Logger LOGGER = LoggerFactory.getLogger(PDFMergeService.class);

    private final FileChooser fileChooser;

    public PDFMergeService(@NonNull final FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public boolean merge(@NonNull final List<PDDocumentWrapper> wrappers) throws IOException {
        final PDDocument finalPDF = new PDDocument();
        wrappers.forEach(wrapper -> {
            for(int i=0; i<wrapper.getPdDocument().getNumberOfPages(); i++) {
                final PDPage page = wrapper.getPdDocument().getPage(i);
                if(wrapper.getPreviewImages().get(i).isShown()) finalPDF.addPage(page);
            }
        });

        final File file = fileChooser.showSaveDialog(new Stage());
        if(file != null) {
            finalPDF.save(file);
            return true;
        } else LOGGER.info("Merge was canceled because no file was created");
        return false;
    }
}
