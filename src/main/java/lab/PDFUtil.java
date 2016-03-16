package lab;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;

/**
 * Created by gd2398 on 4/3/15.
 */
public abstract class PDFUtil {

    public static Document newDocument() {
        return new Document(PageSize.A4);
    }

    public static PdfWriter newWriter(Document document, OutputStream os) throws DocumentException {
        return PdfWriter.getInstance(document, os);
    }

    public static int getViewerPreferences() {
        return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
    }
}
