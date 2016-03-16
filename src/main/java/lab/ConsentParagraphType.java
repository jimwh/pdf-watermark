/** 
*    2015 Copyright Columbia University - RASCAL.  All Rights Reserved. 
**/
package lab;

import java.util.Hashtable;


/**
 * Insert the type's description here. Creation date: (2/11/2002 3:49:46 PM)
 *
 * @author   Halayn Hescock
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ConsentParagraphType {

    //~ Static fields/initializers -----------------------------------------------------------------

    /**
     * important: if you change any thing here please let frances know in order to make
     * corresponding change in her code. thank you.
     */
    // dictionary of paragraph types
    private static final Hashtable<String, String> typeTable = new Hashtable<String, String>();
    private static final Hashtable<String, String> staticDynamic = new Hashtable<String, String>();

    static { // dynamic
        typeTable.put("1", "Risks");
        typeTable.put("2", "Benefits");
        typeTable.put("3", "Additional Costs");
        typeTable.put("4", "Alternative Procedures");
        typeTable.put("5", "Additional Information");
        typeTable.put("12", "Compensation");
        typeTable.put("13", "Information on Research");
        typeTable.put("14", "Confidentiality");
        typeTable.put("15", "Voluntary Participation");
        typeTable.put("16", "Research Related Injuries");

        staticDynamic.put("1", "D");
        staticDynamic.put("2", "D");
        staticDynamic.put("3", "D");
        staticDynamic.put("4", "D");
        staticDynamic.put("5", "D");
        staticDynamic.put("12", "D");
        staticDynamic.put("13", "D");
        staticDynamic.put("14", "D");
        staticDynamic.put("15", "D");
        staticDynamic.put("16", "D");


        // static
        typeTable.put("6", "Statement of the Research");
        typeTable.put("7", "Confidentiality");                             // OBSOLETE, BUT KEEP
                                                                           // TO HANDLE OLD DATA
        typeTable.put("8", "Research Related Injuries");                   // OBSOLETE, BUT KEEP
                                                                           // TO HANDLE OLD DATA
        typeTable.put("9", "Questions");
        typeTable.put("10", "Voluntary Participation & Early Withdrawal"); // OBSOLETE, BUT KEEP
                                                                           // TO HANDLE OLD DATA
        typeTable.put("11", "Costs");                                      // OBSOLETE, BUT KEEP
                                                                           // TO HANDLE OLD DATA

        staticDynamic.put("6", "S");
        staticDynamic.put("7", "S");
        staticDynamic.put("8", "S");
        staticDynamic.put("9", "S");
        staticDynamic.put("10", "S");
        staticDynamic.put("11", "S");

    }

    private static final Hashtable<String, Integer> defaultSequence =
        new Hashtable<String, Integer>();

    static {
        defaultSequence.put("6", 100);   // Statement of Research is First
        defaultSequence.put("13", 500);  // Information of Research
        defaultSequence.put("1", 1000);  // Risks and Discomforts
        defaultSequence.put("2", 1500);  // Benefits
        defaultSequence.put("4", 2000);  // Alternative Procedures
        defaultSequence.put("14", 2500); // Confidentiality
        defaultSequence.put("16", 3000); // Injuries
        defaultSequence.put("12", 3500); // Compensation
        defaultSequence.put("3", 4000);  // Additional Costs
        defaultSequence.put("15", 4500); // Participation
        defaultSequence.put("5", 5000);  // Additional Information
        defaultSequence.put("7", 5500);  // Confidentiality (this is for holding OLD

        // DATA)
        defaultSequence.put("8", 6000);  // Research Related Injuries
        defaultSequence.put("11", 6500); // Costs
        defaultSequence.put("10", 7000); // Voluntary Participation (this is for

        // holding OLD DATA)
        defaultSequence.put("9", 7500); // Questions about Research

    }

    //~ Constructors -------------------------------------------------------------------------------

    /** ConsentType constructor comment. */
    public ConsentParagraphType() {
        super();
    }

    //~ Methods ------------------------------------------------------------------------------------

    /**
     * TODO: DOCUMENT ME!
     *
     * @return  TODO: DOCUMENT ME!
     */
    public static Hashtable<String, Integer> getDefaultsequence() {
        return defaultSequence;
    }

    /**
     * TODO: DOCUMENT ME!
     *
     * @return  TODO: DOCUMENT ME!
     */
    public static Hashtable<String, String> getStaticdynamic() {
        return staticDynamic;
    }

    /**
     * TODO: DOCUMENT ME!
     *
     * @return  TODO: DOCUMENT ME!
     */
    public static Hashtable<String, String> getTypetable() {
        return typeTable;
    }
}
