package lab;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Component
public class Watermark {

    private static final Logger log = LoggerFactory.getLogger(Watermark.class);
    private static final int ROTATION = 45;

    private final Phrase watermark =
            new Phrase("For reference only. Not for use.",
                    new Font(FontFamily.HELVETICA, 45, Font.NORMAL,
                            BaseColor.RED.darker()));

    public ByteArrayOutputStream stampPdfByteArray(byte[] source) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(source);
        ByteArrayOutputStream out = stampPdfByteArray(reader);
        reader.close();
        return out;
    }

    public ByteArrayOutputStream stampPdfByteArray(ByteArrayOutputStream os) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(os.toByteArray());
        ByteArrayOutputStream out = stampPdfByteArray(reader);
        reader.close();
        return out;
    }

    private ByteArrayOutputStream stampPdfByteArray(PdfReader reader) throws IOException, DocumentException {
        // if read only, don't bother further, simply return null
        if (reader.isEncrypted() || reader.isMetadataEncrypted()) return null;
        ByteArrayOutputStream dest = new ByteArrayOutputStream();
        int number_of_page = reader.getNumberOfPages() + 1;
        PdfStamper stamper = new PdfStamper(reader, dest);
        for (int i = 1; i < number_of_page; i++) {
            PdfContentByte under = stamper.getUnderContent(i);
            PdfGState gState = new PdfGState();
            gState.setFillOpacity(0.2f);
            under.setGState(gState);
            ColumnText.showTextAligned(under, Element.ALIGN_CENTER, watermark, 280, 390, ROTATION);
        }

        PdfContentByte over = stamper.getOverContent(1);
        over.saveState();
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.5f);
        over.setGState(gs1);
        over.restoreState();
        stamper.close();
        return dest;
    }

}