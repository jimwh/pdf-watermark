/*
 * Copyright (C) 2014 Columbia University - RASCAL. All Rights Reserved.
 */

package lab;

///import com.google.common.base.Preconditions;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.format.number.NumberFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.Locale;

@Component
public class PdfConstructionHelper {

	public final static Font FONT_HELVETICA_8            = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 8);
	public final static Font FONT_HELVETICA_8_BOLD       = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 8, Font.BOLD);
	public final static Font FONT_HELVETICA_8_BOLD_UNDER = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 8, Font.BOLD | Font.UNDERLINE);
	public final static Font FONT_HELVETICA_10            = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 10);
	public final static Font FONT_HELVETICA_10_BOLD       = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 10, Font.BOLD);
	public final static Font FONT_HELVETICA_10_BOLD_UNDER = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 10, Font.BOLD | Font.UNDERLINE, BaseColor.BLACK);
	public final static Font FONT_HELVETICA_10_BLUE_BOLD_UNDER = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 10, Font.BOLD | Font.UNDERLINE, BaseColor.BLUE);
	public final static Font FONT_HELVETICA_10_BLUE_BOLD = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 10, Font.BOLD , BaseColor.BLUE);
	public final static Font FONT_HELVETICA_12            = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 12);
	public final static Font FONT_HELVETICA_12_BOLD       = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 12, Font.BOLD);
	public final static Font FONT_HELVETICA_12_BOLD_UNDER = FontFactory.getFont(Font.FontFamily.HELVETICA.toString(), 12, Font.BOLD | Font.UNDERLINE);

	public static final float INDENT_0 = 0f;
	public static final float INDENT_1 = 15f;
	public static final float INDENT_1_5 = 25f;
	public static final float INDENT_2 = 30f;
	public static final float INDENT_2_5 = 40f;
	public static final float INDENT_3 = 45f;
	public static final float INDENT_3_5 = 55f;
	public static final float INDENT_4 = 60f;
	public static final float INDENT_5 = 75f;
	public static final float INDENT_6 = 90f;
	public static final float INDENT_7 = 105f;
	public static final float BORDER_WIDTH_ONE = 1f;
	
	public final static float WIDTH_PERCENTAGE     = 100f;
	public final static float BORDER_WIDTH         = 0f;
	
	public final static LineSeparator LINE_SEPARATOR  = new LineSeparator();  
	public final static Chunk CHUNK_LINE_SEPARATOR    = new Chunk(LINE_SEPARATOR);

	private static final DateTimeFormatter DATEFORMAT = DateTimeFormat
			.forPattern("MM/dd/yyyy");
    
    /**
     * 
     * @param numberOfColumn
     * @return
     */
	public static PdfPTable createPdfTable(final int numberOfColumn) {
		
		PdfPTable table = new PdfPTable(numberOfColumn);
		
		table.setWidthPercentage(WIDTH_PERCENTAGE);		
		return table;
	}
    /**
     * 
     * @param numberOfColumn
     * @param spacingBefore
     * @param spacingAfter
     * @return
     */
	public static PdfPTable createPdfTable(final int numberOfColumn, final float spacingBefore, final float spacingAfter) {
		if(numberOfColumn < 1 || spacingBefore < 0 || spacingAfter < 0)
			   throw new IllegalArgumentException("Check parameters. All parameters must positive");
		
		PdfPTable table = new PdfPTable(numberOfColumn);
		table.setSpacingBefore(spacingBefore);
        table.setSpacingAfter(spacingAfter);
		
		table.setWidthPercentage(100);		
		return table;
	}
	/**
	 * 
	 * @param numberOfColumn
	 * @param columnWidths
	 * @param spacingBefore
	 * @param spacingAfter
	 * @return
	 */
	public static PdfPTable createPdfTable(int numberOfColumn, float[] columnWidths, float spacingBefore, float spacingAfter) throws DocumentException {
		if(numberOfColumn < 1 || spacingBefore < 0 || spacingAfter < 0)
			   throw new IllegalArgumentException("Check parameters. All parameters must positive");
		
		PdfPTable table = new PdfPTable(numberOfColumn);
		table.setWidths(columnWidths);
		
		table.setSpacingBefore(spacingBefore);
        table.setSpacingAfter(spacingAfter);
		
		table.setWidthPercentage(100);		
		return table;
	}
    /**
     * 
     * @param autoindent
     * @param indentationLeft
     * @param listSymbol
     * @return
     */
	public static List createPdfList(final boolean autoindent, final float indentationLeft, final String listSymbol) {
		if(indentationLeft < 0 || listSymbol == null || listSymbol == null )
			   throw new IllegalArgumentException("Check parameters. All parameters must positive and not null");
		
		List list = new List();
		list.setAutoindent(autoindent);
		list.setIndentationLeft(indentationLeft);
		list.setListSymbol(listSymbol);
		return list;
	}
    /**
     * 
     * @param list
     * @param value
     * @param font
     */
	public static void setPdfListItem(final List list, final String value, final Font font) {
		if(list == null || value == null || font == null)
			   throw new IllegalArgumentException("Check parameters. All parameters must positive and not null");
		
		//list.add(new ListItem(new Paragraph(value, font)));
		list.add(new ListItem(new Chunk(value, font)));
	}
    /**
     * 	
     * @param value
     * @param font
     * @param horizontalAlignment
     * @return
     */
	public static PdfPCell createPdfTableCell(final String value, final Font font, final int horizontalAlignment) {
		if(value == null || font == null)
			   throw new IllegalArgumentException("Check parameters. All parameters must positive and not null");
		
		PdfPCell cell = new PdfPCell(new Paragraph(HtmlUtils.htmlUnescape(value), font));
		cell.setHorizontalAlignment(horizontalAlignment);
		cell.setBorderWidth(BORDER_WIDTH);		
		return cell;
	}
	
	public static PdfPCell createPdfTableCellWithNull(String value, final Font font, final int horizontalAlignment) {
		return createPdfTableCellWithNull(value, font, horizontalAlignment, 0);
	}
	public static PdfPCell createPdfTableCellWithNull(String value, final Font font, final int horizontalAlignment, int colspan) {
		return createPdfTableCellWithNull(value, font, horizontalAlignment, colspan, BORDER_WIDTH);
	}

	public static PdfPCell createPdfTableCellWithNull(String value, final Font font, final int horizontalAlignment, int colspan, float borderWidth) {
		if(font == null)
			   throw new IllegalArgumentException("Check parameters. All parameters must positive and not null");
		if(value == null)
			value = "";
		PdfPCell cell = new PdfPCell(new Paragraph(HtmlUtils.htmlUnescape(value), font));
		cell.setHorizontalAlignment(horizontalAlignment);
		cell.setBorderWidth(borderWidth);		
		cell.setColspan(colspan);
		return cell;
	}
	


	public static PdfPCell createPdfTableCellWithNullFirstStringBold(String boldValue, String value, final Font font, final int horizontalAlignment, int colspan, float borderWidth) {
		if(font == null)
			   throw new IllegalArgumentException("Check parameters. All parameters must positive and not null");
		if(value == null)
			value = "";
		Phrase boldText = new Phrase(HtmlUtils.htmlUnescape(boldValue), FONT_HELVETICA_10_BOLD);
		Phrase regText = new Phrase(HtmlUtils.htmlUnescape(value), font);
		Paragraph para = new Paragraph();
		para.add(boldText);
		para.add(regText);
		PdfPCell cell = new PdfPCell(para);
		cell.setHorizontalAlignment(horizontalAlignment);
		cell.setBorderWidth(borderWidth);		
		cell.setColspan(colspan);
		return cell;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */

	public static PdfPCell createPdfTableCell(final String value) {
		return createPdfTableCell(value, FONT_HELVETICA_10, 0, Element.ALIGN_LEFT, BORDER_WIDTH, 1);
	}
	public static PdfPCell createPdfTableCell(final String value, final Font font) {
		return createPdfTableCell(value, font, 0, Element.ALIGN_LEFT, BORDER_WIDTH, 1);
	}
	public static PdfPCell createPdfTableCell(final String value, final Font font, float borderWidth) {
		return createPdfTableCell(value, font, 0, Element.ALIGN_LEFT, borderWidth, 1);
	}
	public static PdfPCell createPdfTableCell(final String value, final Font font, int colspan, int horizonalAlignment) {
		return createPdfTableCell(value, font, colspan, horizonalAlignment, BORDER_WIDTH, 1);
	}
	public static PdfPCell createPdfTableCell(final String value, int colspan) {
		return createPdfTableCell(value, FONT_HELVETICA_10, colspan, Element.ALIGN_LEFT, BORDER_WIDTH, 1);
	}
    public static PdfPCell createPdfTableCell(final String value, int colspan, int rowspan) {
        return createPdfTableCell(value, FONT_HELVETICA_10, colspan, Element.ALIGN_LEFT, BORDER_WIDTH, rowspan);
    }

	public static PdfPCell createPdfTableCellDefineBorder(final String value, final Font font, float borderWidth, 
			boolean leftBorder, boolean rightBorder, boolean topBorder, boolean bottomBorder) {
		return createPdfTableCellDefineBorder(value, font, borderWidth, leftBorder, rightBorder, topBorder, bottomBorder, BaseColor.WHITE);
	}

	public static PdfPCell createPdfTableCellDefineBorder(final String value, final Font font, float borderWidth, 
			boolean leftBorder, boolean rightBorder, boolean topBorder, boolean bottomBorder, BaseColor backColor) {
		PdfPCell cell =  new PdfPCell(new Paragraph(HtmlUtils.htmlUnescape(value), font));
		if (!leftBorder)
			cell.setBorderWidthLeft(0f);
		else
			cell.setBorderWidthLeft(borderWidth);
		if (!rightBorder)
			cell.setBorderWidthRight(0f);
		else
			cell.setBorderWidthRight(borderWidth);
		if (!topBorder)
			cell.setBorderWidthTop(0f);
		else
			cell.setBorderWidthTop(borderWidth);
		if (!bottomBorder)
			cell.setBorderWidthBottom(0f);
		else
			cell.setBorderWidthBottom(borderWidth);
		
		cell.setBackgroundColor(backColor);
		return cell;
	}
	
	public static PdfPCell createPdfTableCell(final String value, final Font font, int colspan, int horizonalAlignment, float borderWidth, int rowspan) {
		if(value == null || font == null)
			   throw new IllegalArgumentException("Check parameters. All parameters must positive and not null");
		PdfPCell cell = new PdfPCell(new Paragraph(HtmlUtils.htmlUnescape(value), font));
		cell.setHorizontalAlignment(horizonalAlignment);
		cell.setBorderWidth(borderWidth);	
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		return cell;
	}
	
	public static PdfPCell createPdfTableCellWithNull(String value, final Font font) {
		if(font == null)
			   throw new IllegalArgumentException("Check parameters. All parameters must positive and not null");
		if(value == null)
			value = "";
		PdfPCell cell = new PdfPCell(new Paragraph(HtmlUtils.htmlUnescape(value), font));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderWidth(BORDER_WIDTH);		
		return cell;
	}
	
	public static PdfPCell createPdfTableCellWithNull(String value) {
		return createPdfTableCellWithNull(value, FONT_HELVETICA_10);
	}
	
	public static PdfPCell createPdfTableCellWithNull(String value, final Font font, float borderWidth) {
	    return createPdfTableCellWithNull(value, font, borderWidth, 1, 1);
	}
	
	public static PdfPCell createPdfTableCellWithNull(String value, final Font font, float borderWidth, int colspan, int rowspan) {
		if(font == null)
			   throw new IllegalArgumentException("Check parameters. All parameters must positive and not null");
		if(value == null)
			value = "";
		PdfPCell cell = new PdfPCell(new Paragraph(HtmlUtils.htmlUnescape(value), font));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderWidth(borderWidth);
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		return cell;
	}
	/**
	 * 
	 * @param table
	 * @param font
	 * @param alignment
	 * @param rows
	 */
	public static void setPdfTableRow(final PdfPTable table, final Font font, final int alignment, final String[] rows)   {
		if(rows == null || rows.length == 0 || table == null || font == null )
		   throw new IllegalArgumentException("Check parameters. All parameters must not be null");

		for (String row : rows) {
			PdfPCell cell = new PdfPCell(new Paragraph(row == null ? "" : row, font));
			cell.setHorizontalAlignment(alignment);
			cell.setBorderWidth(0);
			table.addCell(cell);
		}
	}
    /**
     * 
     * @param table
     * @param font
     * @param rows
     */
	public static void setPdfTableRow(final PdfPTable table, final Font font, final String[] rows)   {
		if(rows == null || rows.length == 0 || table == null || font == null )
		   throw new IllegalArgumentException("Check parameters. All parameters must not be null");

		for (String row : rows) {
			PdfPCell cell = new PdfPCell(new Paragraph(row == null ? "" : row, font));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(0);
			table.addCell(cell);
		}
	}
    /**
     * 
     * @param table
     * @param fonts
     * @param alignments
     * @param rows
     */
	public static void setPdfTableRow(final PdfPTable table, final Font[] fonts, final int[] alignments, final String[] rows)  {
		if(rows == null || rows.length == 0 || table == null || fonts == null || alignments == null)
		   throw new IllegalArgumentException("Check parameters. All parameters must not be null");
		
		for(int i=0; i<rows.length; i++) {
			PdfPCell cell = new PdfPCell(new Paragraph(rows[i]==null ?"":rows[i], fonts[i]));
			cell.setHorizontalAlignment(alignments[i]);
			cell.setBorderWidth(0);
			table.addCell(cell);
		}
	}
	/**
	 * 
	 * @param table
	 * @param fonts
	 * @param rows
	 */
	public static void setPdfTableRow(final PdfPTable table, final Font[] fonts, final String[] rows)   {
		if(rows == null || rows.length == 0 || table == null || fonts == null)
		   throw new IllegalArgumentException("Check parameters. All parameters must not be null");
		
		for(int i=0; i<rows.length; i++) {
			PdfPCell cell = new PdfPCell(new Paragraph(rows[i]==null ?"":rows[i], fonts[i]));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(0);
			table.addCell(cell);
		}
	}
	/**
	 * 
	 * @param table
	 * @param fonts
	 * @param alignments
	 * @param rows
	 * @param colSpan
	 */
	public static void setPdfTableRow(final PdfPTable table, final Font[] fonts, final int[] alignments, final String[] rows, final int[] colSpan) {
		if(rows == null || rows.length == 0 || table == null || fonts == null || alignments == null)
		   throw new IllegalArgumentException("Check parameters. All parameters must not be null");
		
		for(int i=0; i<rows.length; i++) {
			PdfPCell cell = new PdfPCell(new Paragraph(rows[i]==null ?"":rows[i], fonts[i]));
			cell.setHorizontalAlignment(alignments[i]);
			cell.setBorderWidth(0);
			cell.setColspan(colSpan[i]);
			table.addCell(cell);
		}
	}
	/**
	 * 
	 * @param table
	 * @param font
	 * @param alignment
	 * @param cellValue
	 */
	public static void setPdfTableCell(final PdfPTable table, final Font font, final int alignment, final String cellValue) {

		if(table == null ||  font == null) 
		   throw new IllegalArgumentException("Check parameters. All parameters must not be null");
		PdfPCell cell = new PdfPCell(new Paragraph(cellValue==null ?"":cellValue, font) );
		cell.setHorizontalAlignment(alignment);
		cell.setBorderWidth(0);
		table.addCell(cell);
	}
	/**
	 * 
	 * @param table
	 * @param font
	 * @param cellValue
	 */
	public static void setPdfTableCell(final PdfPTable table, final Font font, final String cellValue) {

		if(table == null ||  font == null || cellValue == null) 
		   throw new IllegalArgumentException("Check parameters. All parameters must not be null");
		PdfPCell cell = new PdfPCell(new Paragraph(cellValue==null ?"":cellValue, font) );
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderWidth(0);
		table.addCell(cell);
	}
	/**
	 * 
	 * @param table
	 * @param colSpan
	 * @param font
	 * @param alignment
	 * @param cellValue
	 */
	public static void setPdfTableCell(final PdfPTable table, final int colSpan, final Font font, final int alignment, final String cellValue) {
		if(table == null ||  font == null || colSpan < 1 || cellValue == null) 
		   throw new IllegalArgumentException("Check parameters. All parameters must not be null");
		
		PdfPCell cell = new PdfPCell(new Paragraph(cellValue==null ?"":cellValue, font) );
		cell.setHorizontalAlignment(alignment);
		cell.setBorderWidth(0);
		cell.setColspan(colSpan);
		table.addCell(cell);
	}
    /**
     * 
     * @param table
     * @param colSpan
     * @param font
     * @param cellValue
     */
	public static void setPdfTableCell(final PdfPTable table, final int colSpan, final Font font, final String cellValue) {
		if(table == null ||  font == null || colSpan < 1 || cellValue == null) 
		   throw new IllegalArgumentException("Check parameters. All parameters must not be null");
		
		PdfPCell cell = new PdfPCell(new Paragraph(cellValue==null ?"":cellValue, font) );
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorderWidth(0);
		cell.setColspan(colSpan);
		table.addCell(cell);
	}
    
    /**
     * Creates a cell with hyper-linked text.
     * 
     * @param url - reference url
     * @param value - Text to be displayed for link, if null, url will be used.
     * @param font
     * @return
     */
    public static PdfPCell createPdfTableCellWithAnchor(final String url, String value, final Font font) {
        //Preconditions.checkArgument(url != null);
        //Preconditions.checkArgument(font != null);
        
        Paragraph paragraph = new Paragraph();
        
        if(StringUtils.isBlank(value)) value = url;
        
        font.setColor(0, 0, 255);
        Chunk chunk = new Chunk(value, font);

        Anchor anchor = new Anchor(chunk);
        anchor.setReference(url);
        
        paragraph.add(anchor);
        
        PdfPCell cell = new PdfPCell(paragraph);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorderWidth(BORDER_WIDTH);      
        return cell;
    }
    
    public static String formatDate(Date date) {
  		if (date == null)
  			return "";
  		return DATEFORMAT.print(date.getTime());
  	}

    /**
     * @param number
     * @return
     */
    public static String formatNumber(Number number) {
        if(number == null) return "";
        return new NumberFormatter().print(number, Locale.US);
    }
	
}
