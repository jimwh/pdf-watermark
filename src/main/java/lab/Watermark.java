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

    static final Logger log = LoggerFactory.getLogger(Watermark.class);
    public Phrase watermark =
        new Phrase("For reference only. Not for use.",
                new Font(FontFamily.HELVETICA, 45, Font.ITALIC,
                BaseColor.RED.darker()));


    public ByteArrayOutputStream stampPdfByteArray(byte[] os) throws IOException, DocumentException {
        ByteArrayOutputStream dest = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(os);
        log.info("isEncrypted={}", reader.isEncrypted());
        log.info("isOpenedWithFullPermissions={}", reader.isOpenedWithFullPermissions());
        log.info("hasUsageRights={}", reader.hasUsageRights());
        log.info("isMetadataEncrypted={}", reader.isMetadataEncrypted());

        int number_of_page = reader.getNumberOfPages() + 1;
        log.info("numberOfPages={}", number_of_page);
        PdfStamper stamper = new PdfStamper(reader, dest);
        for (int i = 1; i < number_of_page; i++) {
            PdfContentByte under = stamper.getUnderContent(i);
            PdfGState gState = new PdfGState();
            gState.setFillOpacity(0.5f);
            under.setGState(gState);
            ColumnText.showTextAligned(under, Element.ALIGN_CENTER, watermark, 280, 390, 45);
        }

        PdfContentByte over = stamper.getOverContent(1);
        over.saveState();
        /*
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.8f);
        over.setGState(gs1);
        over.restoreState();
        */
        stamper.close();
        reader.close();
        return dest;
    }

    public ByteArrayOutputStream stampPdfByteArray(ByteArrayOutputStream os) throws IOException, DocumentException {
        ByteArrayOutputStream dest = new ByteArrayOutputStream();
        PdfReader reader = new PdfReader(os.toByteArray());
        int number_of_page = reader.getNumberOfPages() + 1;
        PdfStamper stamper = new PdfStamper(reader, dest);
        for (int i = 1; i < number_of_page; i++) {
            PdfContentByte under = stamper.getUnderContent(i);
            PdfGState gState = new PdfGState();
            gState.setFillOpacity(0.2f);
            under.setGState(gState);
            ColumnText.showTextAligned(under, Element.ALIGN_CENTER, watermark, 280, 390, 45);
        }

        PdfContentByte over = stamper.getOverContent(1);
        over.saveState();
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.5f);
        over.setGState(gs1);
        over.restoreState();
        stamper.close();
        reader.close();
        return dest;
    }
}