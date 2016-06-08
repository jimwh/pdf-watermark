package lab;

import com.inet.pdfc.generator.message.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import static lab.PdfConstructionHelper.FONT_HELVETICA_8;

/**
 * Inner class to add a header and a footer.
 */
class ConsentFormFooter extends PdfPageEventHelper {
    /**
     * The header text.
     */
    private final String footerText;
    /**
     * The template with the total number of pages.
     */
    private PdfTemplate total;

    public ConsentFormFooter(final String footerText, final PdfTemplate total) {
        this.footerText = "Consent Form: CF-AAAA1700 copied from CF-AAAA1500";
        this.total = total;
    }

    /**
     * Creates the PdfTemplate that will hold the total number of pages.
     *
     * @see PdfPageEventHelper#onOpenDocument(PdfWriter, Document)
     */
    @Override
    public void onOpenDocument(final PdfWriter writer, final Document document) {
        // total = writer.getDirectContent().createTemplate(30, 16);
        total = writer.getDirectContent().createTemplate(30, 16);
    }

    @Override
    public void onEndPage(final PdfWriter writer, final Document document) {
        final String additionalFooter =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras et massa non turpis sagittis molestie. Proin ac nisi ligula. Donec suscipit scelerisque lacus, id varius purus egestas nec. Sed sit metus.\n\n";

        final PdfPTable table = new PdfPTable(1);
        //table.setWidths(new int[] { 32, 12, 8, 28 });
        try {
            table.setWidths(new int[]{533});
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        table.setTotalWidth(533);
        table.setLockedWidth(true);
        //
        PdfPCell cellA = new PdfPCell(new Phrase(additionalFooter));
        cellA.setBorder(Rectangle.BOX);
        cellA.setBorderWidth(0.5f);

        // cellA.setBorder(Rectangle.LEFT|Rectangle.RIGHT|Rectangle.RECTANGLE);
        // cellA.setBorder(Rectangle.ALIGN_LEFT);
        table.addCell(cellA);
        //
        table.writeSelectedRows(0, -1, 34, 100, writer.getDirectContent());

    }

    /*
    @Override
    public void onEndPage(final PdfWriter writer, final Document document) {
        final String additionalFooter =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras et massa non turpis sagittis molestie. Proin ac nisi ligula. Donec suscipit scelerisque lacus, id varius purus egestas nec. Sed sit metus.\n\n";

        try {
            final PdfPTable table = new PdfPTable(4);
            table.setWidths(new int[] { 32, 12, 8, 28 });
            table.setTotalWidth(533);
            table.setLockedWidth(true);
            //
            PdfPCell cellA = new PdfPCell(new Phrase(additionalFooter));
            cellA.setColspan(4);
            table.addCell(cellA);
            //
            PdfPCell cellF = new PdfPCell(new Phrase(footerText));
            cellF.setColspan(1);
            table.addCell(cellF);
            //
            PdfPCell cellP = new PdfPCell(new Phrase(String.format("Page %d of", writer.getPageNumber())));
            cellP.setColspan(1);
            cellP.setHorizontalAlignment( Element.ALIGN_RIGHT);
            table.addCell(cellP);
            //
            PdfPCell cell = new PdfPCell(Image.getInstance(total));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setBorder(Rectangle.ALIGN_LEFT);
            cell.setBorder(Rectangle.BOX);
            table.addCell(cell);
            //
            PdfPCell cellE = new PdfPCell(new Phrase("Printed on: 03/16/2016 10:50 am"));
            cellE.setColspan(1);
            cellE.setHorizontalAlignment( Element.ALIGN_RIGHT);
            table.addCell(cellE);

            table.writeSelectedRows(0, -1, 34, 80, writer.getDirectContent());

        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }

    }
    */

    /**
     * Fills out the total number of pages before the document is closed.
     *
     * @see PdfPageEventHelper#onCloseDocument(PdfWriter, Document)
     */
    @Override
    public void onCloseDocument(final PdfWriter writer, final Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(
                String.valueOf(writer.getPageNumber())), 2, 2, 0);
    }
}
