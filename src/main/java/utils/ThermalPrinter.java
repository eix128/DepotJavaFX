package utils;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobPriority;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kadir.basol on 31.3.2016.
 */
public class ThermalPrinter {
    private static final ConcurrentHashMap<String, String> tokens;
    private static final Pattern pattern;

    static {
        tokens = new ConcurrentHashMap<String,String>();

        tokens.put("\u00C0", "A");
        tokens.put("\u00C1", "A");
        tokens.put("\u00C2", "A");
        tokens.put("\u00C3", "A");
        tokens.put("\u00C4", "A");
        tokens.put("\u00C5", "A");
        tokens.put("\u00C6", "AE");
        tokens.put("\u00C7", "C");
        tokens.put("\u00C8", "E");
        tokens.put("\u00C9", "E");
        tokens.put("\u00CA", "E");
        tokens.put("\u00CB", "E");
        tokens.put("\u00CC", "I");
        tokens.put("\u00CD", "I");
        tokens.put("\u00CE", "I");
        tokens.put("\u00CF", "I");
        tokens.put("\u00D0", "D");
        tokens.put("\u00D1", "N");
        tokens.put("\u00D2", "O");
        tokens.put("\u00D3", "O");
        tokens.put("\u00D4", "O");
        tokens.put("\u00D5", "O");
        tokens.put("\u00D6", "O");
        tokens.put("\u00D8", "O");
        tokens.put("\u00D9", "U");
        tokens.put("\u00DA", "U");
        tokens.put("\u00DB", "U");
        tokens.put("\u00DC", "U");
        tokens.put("\u00DD", "Y");
        tokens.put("\u00DF", "s");
        tokens.put("\u00E0", "a");
        tokens.put("\u00E1", "a");
        tokens.put("\u00E2", "a");
        tokens.put("\u00E3", "a");
        tokens.put("\u00E4", "a");
        tokens.put("\u00E5", "a");
        tokens.put("\u00E6", "ae");
        tokens.put("\u00E7", "c");
        tokens.put("\u00E8", "e");
        tokens.put("\u00E9", "e");
        tokens.put("\u00EA", "e");
        tokens.put("\u00EB", "e");
        tokens.put("\u00EC", "i");
        tokens.put("\u00ED", "i");
        tokens.put("\u00EE", "i");
        tokens.put("\u00EF", "i");
        tokens.put("\u00F1", "n");
        tokens.put("\u00F2", "o");
        tokens.put("\u00F3", "o");
        tokens.put("\u00F4", "o");
        tokens.put("\u00F5", "o");
        tokens.put("\u00F6", "o");
        tokens.put("\u00F8", "o");
        tokens.put("\u00F9", "u");
        tokens.put("\u00FA", "u");
        tokens.put("\u00FB", "u");
        tokens.put("\u00FC", "u");
        tokens.put("\u00FD", "y");
        tokens.put("\u00FF", "y");
        tokens.put("\u0100", "A");
        tokens.put("\u0101", "a");
        tokens.put("\u0102", "A");
        tokens.put("\u0103", "a");
        tokens.put("\u0104", "A");
        tokens.put("\u0105", "a");
        tokens.put("\u0106", "C");
        tokens.put("\u0107", "c");
        tokens.put("\u0108", "C");
        tokens.put("\u0109", "c");
        tokens.put("\u010A", "C");
        tokens.put("\u010B", "c");
        tokens.put("\u010C", "C");
        tokens.put("\u010D", "c");
        tokens.put("\u010E", "D");
        tokens.put("\u010F", "d");
        tokens.put("\u0110", "D");
        tokens.put("\u0111", "d");
        tokens.put("\u0112", "E");
        tokens.put("\u0113", "e");
        tokens.put("\u0114", "E");
        tokens.put("\u0115", "e");
        tokens.put("\u0116", "E");
        tokens.put("\u0117", "e");
        tokens.put("\u0118", "E");
        tokens.put("\u0119", "e");
        tokens.put("\u011A", "E");
        tokens.put("\u011B", "e");
        tokens.put("\u011C", "G");
        tokens.put("\u011D", "g");
        tokens.put("\u011E", "G");
        tokens.put("\u011F", "g");
        tokens.put("\u0120", "G");
        tokens.put("\u0121", "g");
        tokens.put("\u0122", "G");
        tokens.put("\u0123", "g");
        tokens.put("\u0124", "H");
        tokens.put("\u0125", "h");
        tokens.put("\u0126", "H");
        tokens.put("\u0127", "h");
        tokens.put("\u0128", "I");
        tokens.put("\u0129", "i");
        tokens.put("\u012A", "I");
        tokens.put("\u012B", "i");
        tokens.put("\u012C", "I");
        tokens.put("\u012D", "i");
        tokens.put("\u012E", "I");
        tokens.put("\u012F", "i");
        tokens.put("\u0130", "I");
        tokens.put("\u0131", "i");
        tokens.put("\u0132", "IJ");
        tokens.put("\u0133", "ij");
        tokens.put("\u0134", "J");
        tokens.put("\u0135", "j");
        tokens.put("\u0136", "K");
        tokens.put("\u0137", "k");
        tokens.put("\u0139", "L");
        tokens.put("\u013A", "l");
        tokens.put("\u013B", "L");
        tokens.put("\u013C", "l");
        tokens.put("\u013D", "L");
        tokens.put("\u013E", "l");
        tokens.put("\u013F", "L");
        tokens.put("\u0140", "l");
        tokens.put("\u0141", "l");
        tokens.put("\u0142", "l");
        tokens.put("\u0143", "N");
        tokens.put("\u0144", "n");
        tokens.put("\u0145", "N");
        tokens.put("\u0146", "n");
        tokens.put("\u0147", "N");
        tokens.put("\u0148", "n");
        tokens.put("\u0149", "n");
        tokens.put("\u014C", "O");
        tokens.put("\u014D", "o");
        tokens.put("\u014E", "O");
        tokens.put("\u014F", "o");
        tokens.put("\u0150", "O");
        tokens.put("\u0151", "o");
        tokens.put("\u0152", "OE");
        tokens.put("\u0153", "oe");
        tokens.put("\u0154", "R");
        tokens.put("\u0155", "r");
        tokens.put("\u0156", "R");
        tokens.put("\u0157", "r");
        tokens.put("\u0158", "R");
        tokens.put("\u0159", "r");
        tokens.put("\u015A", "S");
        tokens.put("\u015B", "s");
        tokens.put("\u015C", "S");
        tokens.put("\u015D", "s");
        tokens.put("\u015E", "S");
        tokens.put("\u015F", "s");
        tokens.put("\u0160", "S");
        tokens.put("\u0161", "s");
        tokens.put("\u0162", "T");
        tokens.put("\u0163", "t");
        tokens.put("\u0164", "T");
        tokens.put("\u0165", "t");
        tokens.put("\u0166", "T");
        tokens.put("\u0167", "t");
        tokens.put("\u0168", "U");
        tokens.put("\u0169", "u");
        tokens.put("\u016A", "U");
        tokens.put("\u016B", "u");
        tokens.put("\u016C", "U");
        tokens.put("\u016D", "u");
        tokens.put("\u016E", "U");
        tokens.put("\u016F", "u");
        tokens.put("\u0170", "U");
        tokens.put("\u0171", "u");
        tokens.put("\u0172", "U");
        tokens.put("\u0173", "u");
        tokens.put("\u0174", "W");
        tokens.put("\u0175", "w");
        tokens.put("\u0176", "Y");
        tokens.put("\u0177", "y");
        tokens.put("\u0178", "Y");
        tokens.put("\u0179", "Z");
        tokens.put("\u017A", "z");
        tokens.put("\u017B", "Z");
        tokens.put("\u017C", "z");
        tokens.put("\u017D", "Z");
        tokens.put("\u017E", "z");
        tokens.put("\u017F", "s");
        tokens.put("\u0192", "f");
        tokens.put("\u01A0", "O");
        tokens.put("\u01A1", "o");
        tokens.put("\u01AF", "U");
        tokens.put("\u01B0", "u");
        tokens.put("\u01CD", "A");
        tokens.put("\u01CE", "a");
        tokens.put("\u01CF", "I");
        tokens.put("\u01D0", "i");
        tokens.put("\u01D1", "O");
        tokens.put("\u01D2", "o");
        tokens.put("\u01D3", "U");
        tokens.put("\u01D4", "u");
        tokens.put("\u01D5", "U");
        tokens.put("\u01D6", "u");
        tokens.put("\u01D7", "U");
        tokens.put("\u01D8", "u");
        tokens.put("\u01D9", "U");
        tokens.put("\u01DA", "u");
        tokens.put("\u01DB", "U");
        tokens.put("\u01DC", "u");
        tokens.put("\u01FA", "A");
        tokens.put("\u01FB", "a");
        tokens.put("\u01FC", "AE");
        tokens.put("\u01FD", "ae");
        tokens.put("\u01FE", "O");
        tokens.put("\u01FF", "o");

        String patternString = "(\u00C0|\u00C1|\u00C2|\u00C3|\u00C4|\u00C5|\u00C6|\u00C7|\u00C8|\u00C9|\u00CA|\u00CB|\u00CC|\u00CD|\u00CE|\u00CF|\u00D0|\u00D1|\u00D2|\u00D3|\u00D4|\u00D5|\u00D6|\u00D8|\u00D9|\u00DA|\u00DB|\u00DC|\u00DD|\u00DF|\u00E0|\u00E1|\u00E2|\u00E3|\u00E4|\u00E5|\u00E6|\u00E7|\u00E8|\u00E9|\u00EA|\u00EB|\u00EC|\u00ED|\u00EE|\u00EF|\u00F1|\u00F2|\u00F3|\u00F4|\u00F5|\u00F6|\u00F8|\u00F9|\u00FA|\u00FB|\u00FC|\u00FD|\u00FF|\u0100|\u0101|\u0102|\u0103|\u0104|\u0105|\u0106|\u0107|\u0108|\u0109|\u010A|\u010B|\u010C|\u010D|\u010E|\u010F|\u0110|\u0111|\u0112|\u0113|\u0114|\u0115|\u0116|\u0117|\u0118|\u0119|\u011A|\u011B|\u011C|\u011D|\u011E|\u011F|\u0120|\u0121|\u0122|\u0123|\u0124|\u0125|\u0126|\u0127|\u0128|\u0129|\u012A|\u012B|\u012C|\u012D|\u012E|\u012F|\u0130|\u0131|\u0132|\u0133|\u0134|\u0135|\u0136|\u0137|\u0139|\u013A|\u013B|\u013C|\u013D|\u013E|\u013F|\u0140|\u0141|\u0142|\u0143|\u0144|\u0145|\u0146|\u0147|\u0148|\u0149|\u014C|\u014D|\u014E|\u014F|\u0150|\u0151|\u0152|\u0153|\u0154|\u0155|\u0156|\u0157|\u0158|\u0159|\u015A|\u015B|\u015C|\u015D|\u015E|\u015F|\u0160|\u0161|\u0162|\u0163|\u0164|\u0165|\u0166|\u0167|\u0168|\u0169|\u016A|\u016B|\u016C|\u016D|\u016E|\u016F|\u0170|\u0171|\u0172|\u0173|\u0174|\u0175|\u0176|\u0177|\u0178|\u0179|\u017A|\u017B|\u017C|\u017D|\u017E|\u017F|\u0192|\u01A0|\u01A1|\u01AF|\u01B0|\u01CD|\u01CE|\u01CF|\u01D0|\u01D1|\u01D2|\u01D3|\u01D4|\u01D5|\u01D6|\u01D7|\u01D8|\u01D9|\u01DA|\u01DB|\u01DC|\u01FA|\u01FB|\u01FC|\u01FD|\u01FE|\u01FF)";
        pattern = Pattern.compile(patternString);
    }

    public static String latinString(String string) {
        Matcher matcher = pattern.matcher(string);
        StringBuffer sb = new StringBuffer();

        while(matcher.find()) {
            matcher.appendReplacement(sb, tokens.get(matcher.group(1)));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }


    private static String lastString = null;
    private static long   lastTime;

    public static synchronized void PrintLastString() throws UnsupportedEncodingException, PrintException {
        if(lastTime > System.currentTimeMillis())
            return;
        if(lastString != null)
            if(!lastString.trim().equals(""))
                PrintString(lastString);
        lastTime = System.currentTimeMillis() + 1000;
    }

    public static void PrintString(String string) throws UnsupportedEncodingException, PrintException {
        System.out.println("Output");

        char ESC = (char) 27;
        char GS = ((char) 29);
        String UnderlineOn = new String(new char[]{ESC, (char) 0x2D, (char) 0x31});
        String UnderlineOff = new String(new char[]{ESC, (char) 0x2D, (char) 0x30});
//Print #1, Chr(27) & "(0Y" & Chr(27) & "(p4" & MyString
        String printDataStr = string + "\n\n\n\n\n\n\n\n\n\n\n";
        printDataStr = printDataStr.replaceAll("\r", " ");


        String COMMAND = "";
        COMMAND = ESC + "@";
        COMMAND += GS + "V" + (char) 1;
        printDataStr += COMMAND;

        DocPrintJob job = PrintServiceLookup.lookupDefaultPrintService().createPrintJob();
        //Get's the default printer so it must be set.
        System.out.println(printDataStr);
//        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
//        byte[] bytes = {(byte) 128,(byte)128};
        String s = latinString(printDataStr);
        lastString = string;

        byte[] bytes = s.getBytes("US-ASCII");
//        System.out.println(s);
//        byte[] b = s.getBytes("CP857");
        //Includes the content to print(b) and what kind of content it is (flavor, in this case a String turned into a byte array).
        //Get's the bytes from the String(So that characters such as å ä ö may be printed).
        Doc doc = new SimpleDoc(bytes, flavor, null);

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new Copies(1));
        aset.add(new JobPriority(1));

        job.print(doc, null);
    }


/*    public static void PrintString(String string) throws UnsupportedEncodingException, PrintException {
        char ESC = (char) 27;
        char GS = ((char) 29);
        JTextArea jtp = new JTextArea();
        jtp.setBackground(Color.white);
        String COMMAND = "";
        COMMAND = ESC + "@";
        COMMAND += GS + "V" + (char) 1;
        String printDataStr = "\n\n\n\n\n\n\n\n\n\n\n"+COMMAND;
        StyleContext context = new StyleContext();
        StyledDocument document = new DefaultStyledDocument(context);

        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
        System.out.println(string);
        jtp.setText(string);
        jtp.setFont(new Font("Courier New", Font.PLAIN, 8));
        jtp.setSize(512,512);
//        jtp.setContentType("text/plain");
        boolean show = false;
        try {
            jtp.print(null, null, show, null, null, show);
        } catch (java.awt.print.PrinterException ex) {
            ex.printStackTrace();
        }
//Print #1, Chr(27) & "(0Y" & Chr(27) & "(p4" & MyString
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
//        byte[] bytes = {(byte) 128,(byte)128};
        byte[] bytes = new byte[0];
        bytes = printDataStr.getBytes("US-ASCII");
        DocPrintJob job = PrintServiceLookup.lookupDefaultPrintService().createPrintJob();

//        System.out.println(s);
//        byte[] b = s.getBytes("CP857");
        //Includes the content to print(b) and what kind of content it is (flavor, in this case a String turned into a byte array).
        //Get's the bytes from the String(So that characters such as å ä ö may be printed).
        Doc doc = new SimpleDoc(bytes, flavor, null);

*//*        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new Copies(1));
        aset.add(new JobPriority(1));*//*

        job.print(doc, null);
    }*/
}
