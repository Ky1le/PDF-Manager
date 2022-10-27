package kyle.pdfmanager;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdfManagerApplication {

	public static void main(String[] args) {
		Application.launch(PdfManagerFxApplication.class, args);
	}
}
