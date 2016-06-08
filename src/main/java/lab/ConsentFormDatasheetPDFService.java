package lab;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static lab.PdfConstructionHelper.BORDER_WIDTH_ONE;
import static lab.PdfConstructionHelper.FONT_HELVETICA_10;
import static lab.PdfConstructionHelper.FONT_HELVETICA_10_BOLD;
import static lab.PdfConstructionHelper.FONT_HELVETICA_12_BOLD;
import static lab.PdfConstructionHelper.INDENT_0;
import static lab.PdfConstructionHelper.INDENT_1;
import static lab.PdfConstructionHelper.createPdfTable;
import static lab.PdfConstructionHelper.createPdfTableCell;
import static lab.PdfConstructionHelper.createPdfTableCellWithNull;

@Service
public class ConsentFormDatasheetPDFService extends AbstractConsentFormDatasheetService {

    Logger log = LoggerFactory.getLogger(ConsentFormDatasheetPDFService.class);

    public static final String FOOTER_HEALTHSCIENCE = "Medical Center Institutional Review Board: 212-305-5883";
    public static final String FOOTER_MORNINGSIDE = "Morningside Institutional Review Board: 212-851-7040";
    public static final String FOOTER_OTHERINSTITUTE = "";


    @Override
    public void buildPDFDocument(ConsentHeader cfh, Document document, PdfWriter writer) throws DocumentException, IOException {

        // If the CF is attached to a IRB Protocol, then merge the data
        // document.setPageSize(PageSize.LETTER);
        document.setPageSize(PageSize.A4);

        addConsentFormNumberOnFooter(cfh, writer);
        chapterNumber.set(1);
        //
        addConsentFormTitle(cfh, document);
        //
        addGeneralInfo(cfh, document);
        //
        addContacts(cfh, document);
        //
        addDynamicParagraphs(cfh, document);
        //
        addSignatureLines(cfh, document);

        addDynamicParagraphs(cfh, document);
        addSignatureLines(cfh, document);
    }


    private void addConsentFormTitle(final ConsentHeader header, final Document doc) throws DocumentException {
        final PdfPTable tableTitle = createPdfTable(1);
        String title = header.getFormType();
        log.info("title={}", title);
        PdfPCell cellTitle1 = createPdfTableCell(title, FONT_HELVETICA_12_BOLD, Element.ALIGN_CENTER);
        cellTitle1.setFixedHeight(20f);
        tableTitle.addCell(cellTitle1);
        doc.add(tableTitle);
    }

    private void addGeneralInfo(final ConsentHeader header, final Document doc) throws DocumentException {
        final Chapter chapter1 = getChapterWithHeader("General Information",header);
        PdfPTable table = createPdfTable(4, 10f, 10f);
        table.setHeaderRows(1);
        doc.add(chapter1);

        /*
        final Chapter chapter2 = newChapter();
        addNewParagraphToChapter(chapter2, "Consent Number: ", INDENT_1, true);
        addNewParagraphToChapter(chapter2, "AAAA1700\n", INDENT_1, false);
        addNewParagraphToChapter(chapter2, "Participation Duration: ", INDENT_1, true);
        addNewParagraphToChapter(chapter2, "6 months\n", INDENT_1, false);
        addNewParagraphToChapter(chapter2, "Anticipated Number of Subjects: ", INDENT_1, true);
        addNewParagraphToChapter(chapter2, "5\n\n", INDENT_1, false);
        addNewParagraphToChapter(chapter2, "Research Purpose\n", INDENT_1, true);
        addNewParagraphToChapter(chapter2, "foo bar purpose\n", INDENT_1, false);
        doc.add(chapter2);
        */
        //final Chapter chapter2 = newChapter();
        /*
        doc.add( newChunk("\tConsent Number: ", true) );
        doc.add( newChunk("AAAA1700\n", false) );
        */

        //Paragraph p = newParagraph(INDENT_1);
        Paragraph p = newParagraph(INDENT_0);
        p.add(newChunk("Consent Number: ", true));
        p.add(newChunk("AAAA1700\n", false));
        p.add(newChunk("Participation Duration: ", true));
        p.add(newChunk("6 months\n", false));
        doc.add(p);
    }


    public void addContacts(ConsentHeader header, Document doc) throws DocumentException {

        Chapter chapter = getChapterWithHeader("Contacts", header);
        PdfPTable table = createPdfTable(3, 10f, 10f);
        table.setHeaderRows(1);

        table.addCell(createPdfTableCell("Contact", FONT_HELVETICA_10_BOLD, BORDER_WIDTH_ONE));
        table.addCell(createPdfTableCell("Title", FONT_HELVETICA_10_BOLD, BORDER_WIDTH_ONE));
        table.addCell(createPdfTableCell("Numbers", FONT_HELVETICA_10_BOLD, BORDER_WIDTH_ONE));

        String contactName = "YY229";
        StringBuffer allContactNumbersBuffer = new StringBuffer();
        String phoneNum = "Phone: 111-111-1111";
        String pagerNum = "Pager: 222-222-2222";
        String cellNum = "Cell:  333-333-3333";
        allContactNumbersBuffer.append(phoneNum).append('\n').append(pagerNum).append('\n').append(cellNum).append('\n');
        table.addCell(createPdfTableCellWithNull(contactName, FONT_HELVETICA_10, BORDER_WIDTH_ONE));
        table.addCell(createPdfTableCellWithNull("Role", FONT_HELVETICA_10, BORDER_WIDTH_ONE));
        table.addCell(createPdfTableCellWithNull(allContactNumbersBuffer.toString(), FONT_HELVETICA_10, BORDER_WIDTH_ONE));

        chapter.add(table);
        doc.add(chapter);
    }


    private void addDynamicParagraphs(final ConsentHeader header, final Document doc) throws DocumentException, IOException {
        final PdfPTable table = createPdfTable(4, 10f, 10f);
        table.setHeaderRows(2);

        String prevType = "";
        String currType = "Risk";
        String descr = "Description";
        String paraText = "paragraph text fsdsafaf dfasdfdsafa dsfafasdfa sdfassdfasdfa dfasdfa" +
                "dafdadfsa dafdsafafsd dfdadsaf\n" +
        "paragraph text fsdsafaf dfasdfdsafa dsfafasdfa sdfassdfasdfa dfasdfa\n" +
                "dafdadfsa dafdsafafsd dfdadsaf\n" +
        "paragraph text fsdsafaf dfasdfdsafa dsfafasdfa sdfassdfasdfa dfasdfa\n" +
                "dafdadfsa dafdsafafsd dfdadsaf\n"+
        "paragraph text fsdsafaf dfasdfdsafa dsfafasdfa sdfassdfasdfa dfasdfa\n" +
                "dafdadfsa dafdsafafsd dfdadsaf\n" ;

        final Chapter chapter = StringUtils.equals(currType, prevType) ? newChapter() : getChapterWithHeader(currType, header);
        // If this is not the first section of content, insert a line return
        // to put space between the two sections
        final String lineReturn = StringUtils.equals(currType, prevType) ? "\n\n" : "";
        addNewParagraphToChapterUnderline(chapter, descr, INDENT_1, true);
        addNewParagraphToChapter(chapter, paraText + lineReturn, INDENT_1, false);
        doc.add(chapter);
    }


    public void addSignatureLines(ConsentHeader header, Document doc) throws DocumentException {

        Chapter chapter = getChapterWithHeader("Signatures", header);

        //Participant Signature Lines
        addNewParagraphToChapter(chapter, "\n" + "Participant Signature Lines", INDENT_0, true);
        drawSignatureLine(null, chapter, false);

        // Research Signature Lines
        addNewParagraphToChapter(chapter, "\n" + "Research Signature Lines", INDENT_0, true);
        drawSignatureLine(null, chapter, false);

        // Witness Signature Lines
        addNewParagraphToChapter(chapter, "\n" + "Witness Signature Lines", INDENT_0, true);
        drawSignatureLine(null, chapter, false);

        doc.add(chapter);


    }


    public void drawSignatureLine(String signatureLines, Chapter chapter, boolean time) {

        String description = "";
        int counterOfGroup = 0;
        int i = 0;

        String sigString = "String";
        String sigString1 = "Print Name ____________________________  Signature ____________________________  " + (time ? "Date & Time" : "Date") + "___________\n\n";
        String sigString2 = "Print Name ____________________________\n";

        addNewParagraphToChapter(chapter, "\n", INDENT_1, false);
        addNewParagraphToChapter(chapter, "\n" + description + "\n", INDENT_1, true);


        addNewParagraphToChapter(chapter, "\n\n", INDENT_1, false);
        addNewParagraphToChapter(chapter, sigString, INDENT_1, false);
    }


    public String getNotNullString(String aString, String aReplace) {
        aReplace = ((aReplace == null) ? "" : aReplace);
        aString = ((aString == null) ? aReplace : aString);
        return aString;
    }

    public void addSpecialOnNewPage(ConsentHeader cfh, PdfWriter writer) {
        addConsentFormNumberOnFooter(cfh, writer); // the first page will be added in the construct method
        // addWaterMark();
    }


    private Chapter newChapter() {
        final Chapter chapter = new Chapter(new Paragraph(), chapterNumber.incrementAndGet());
        chapter.setNumberDepth(0);
        chapter.setTriggerNewPage(false);
        return chapter;
    }

}