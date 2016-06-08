package lab;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.ElementHandler;
import com.itextpdf.tool.xml.Writable;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.WritableElement;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.web.util.HtmlUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.atomic.AtomicInteger;

import static lab.PdfConstructionHelper.FONT_HELVETICA_10;
import static lab.PdfConstructionHelper.FONT_HELVETICA_10_BOLD;
import static lab.PdfConstructionHelper.FONT_HELVETICA_10_BOLD_UNDER;
import static lab.PdfConstructionHelper.FONT_HELVETICA_12_BOLD;

import static lab.PdfConstructionHelper.createPdfTable;
import static lab.PdfConstructionHelper.createPdfTableCell;
import static lab.PdfConstructionHelper.createPdfTableCellWithNull;

/**
 * Created by gc2563 on 08/22/15
 */
public abstract class AbstractConsentFormDatasheetService {
    public ByteArrayOutputStream createPDF(ConsentHeader cfh) throws DocumentException, IOException {
        Document document = PDFUtil.newDocument();
        document.setMargins(30f, 30f, 30f, 100f);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = PDFUtil.newWriter(document, baos);

        document.open();
        addConsentFormNumberOnFooter(cfh, writer);
        buildPDFDocument(cfh, document, writer);
        document.close();

        return baos;
    }

    protected void addConsentFormNumberOnFooter(final ConsentHeader cfh, final PdfWriter writer) {
        // TODO: Need to do this when we have more specs
        // cfh.getFooterInformation()+ T
        // ODO:  Need to decide what to do with this

        final String cfNumber = String.format("Consent Form Number: CF-%s", cfh.getConsentNumber());
        final String footer = cfh.getPreviousConsentHeaderOid() != null ?
                String.format("%s  Copied From: CF-", cfNumber, cfh.getPreviousConsentHeaderOid()) : cfNumber;
        final String printedOn = String.format("%s%nPrinted On: %s", footer, DateTime.now().toString("MM/dd/yyyy hh:mm:ss a"));
        writer.setPageEvent(new ConsentFormFooter(printedOn, writer.getDirectContent().createTemplate(30, 16)));
    }


    public abstract void buildPDFDocument(ConsentHeader cfh, Document document, PdfWriter writer) throws DocumentException, IOException;


    /******************************************************************************************************************/
    /**                                             Utility methods                                                   */
    /******************************************************************************************************************/
    protected AtomicInteger chapterNumber = new AtomicInteger(1);
    protected LineSeparator lineSeperator = new LineSeparator();

    protected void addHorizontalLine(Chapter chapter) {
        Paragraph p = new Paragraph();
        p.add(" ");
        p.add(new LineSeparator());
        p.add(" ");
        chapter.add(p);
    }

    protected String getSubmittingTo(IrbProtocolHeader header) {
        return "SubmittingTo";
    }

    protected String getDepartment(String deptCode) {
        return "dept name";
    }

    protected Chapter getChapterWithHeader(String chapterHeading, ConsentHeader cfh) {
        Paragraph paragraph = new Paragraph();

        if(chapterNumber.get() == 1) paragraph.add(addTitle(cfh));   //TODO I'm not sure if we have to do this
        PdfPTable tableSection = createPdfTable(1);
        PdfPCell cellTitle = createPdfTableCellWithNull(chapterHeading, FONT_HELVETICA_12_BOLD, Element.ALIGN_CENTER);
        cellTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTitle.setBorderWidth(1);
        cellTitle.setFixedHeight(20f);

        tableSection.addCell(cellTitle);
        paragraph.add(" ");
        paragraph.add(tableSection);
        paragraph.add(" ");

        Chapter chapter = new Chapter(paragraph, chapterNumber.incrementAndGet());
        chapter.setBookmarkTitle(chapterHeading);
        chapter.setNumberDepth(0);
        chapter.setTriggerNewPage(false);
        return chapter;
    }

    private PdfPTable addTitle(ConsentHeader cfh) {
        PdfPTable tableTitle = createPdfTable(1);
        String title = "Columbia University " + cfh.getFormType() + " Form Data Sheet";
        PdfPCell cellTitle1 = createPdfTableCell(title, FONT_HELVETICA_12_BOLD, Element.ALIGN_CENTER);
        cellTitle1.setFixedHeight(20f);
        tableTitle.addCell(cellTitle1);
        return tableTitle;
    }


    protected void addNewParagraphToChapter(Chapter chapter, String text, float indent, boolean bold) {
        if (!StringUtils.isBlank(text)) {
            Font font = FONT_HELVETICA_10;
            if (bold) {
                font = FONT_HELVETICA_10_BOLD;
            }
            addParagraphToChapter(chapter, text, indent, font);
        }
    }

    private void addParagraphToChapter(Chapter chapter, String text, float indent, Font font) {
        text = HtmlUtils.htmlUnescape(text);
        Paragraph p = new Paragraph(text, font);
        p.setIndentationLeft(indent);
        chapter.add(p);
    }

    protected void addNewParagraphToChapterUnderline(Chapter chapter, String text, float indent, boolean underline) {
        if (!StringUtils.isBlank(text)) {
            Font font = FONT_HELVETICA_10_BOLD_UNDER;
            addParagraphToChapter(chapter, text, indent, font);
        }
    }

    protected void addNewParagraphToChapter(Chapter chapter, String text, float indent) {
        addNewParagraphToChapter(chapter, text, indent, false);
    }

    protected void addHtmlToChapter(Chapter chapter, String string, float indent) throws IOException {
        Font font = FONT_HELVETICA_10;
        if (StringUtils.isNotBlank(string)) {
            final Paragraph paragraph = new Paragraph();
            paragraph.setFont(font);
            XMLWorkerHelper.getInstance().parseXHtml(new ElementHandler() {
                @Override
                public void add(Writable writable) {
                    // process your element here
                    if (writable instanceof WritableElement) {
                        java.util.List<Element> elements = ((WritableElement) writable).elements();
                        for (Element e : elements) {
                            paragraph.add(e);
                        }
                    }
                }
            }, new StringReader(string));
            paragraph.setIndentationLeft(indent);
            chapter.add(paragraph);
        }
    }

    protected Chunk newChunk(final String text, final boolean bool) {
        final Font font = bool ? FONT_HELVETICA_10_BOLD : FONT_HELVETICA_10;
        return new Chunk(text, font);
    }

    protected Paragraph newParagraph(final float intent) {
        final Paragraph paragraph = new Paragraph();
        paragraph.setIndentationLeft(intent);
        return paragraph;
    }

}
