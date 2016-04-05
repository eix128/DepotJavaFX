package utils;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobPriority;
import java.io.UnsupportedEncodingException;

/**
 * Created by kadir.basol on 31.3.2016.
 */
public class ThermalPrinter {
    public void PrintString(String string) throws UnsupportedEncodingException, PrintException {
        char ESC = (char) 27;
        char GS = ((char) 29);
        String UnderlineOn = new String(new char[]{ESC, (char) 0x2D, (char) 0x31});
        String UnderlineOff = new String(new char[]{ESC, (char) 0x2D, (char) 0x30});
        String s = string;

        String COMMAND = "";
        COMMAND = ESC + "@";
        COMMAND += GS + "V" + (char) 1;
        s += COMMAND;

        DocPrintJob job = PrintServiceLookup.lookupDefaultPrintService().createPrintJob();
        //Get's the default printer so it must be set.
        System.out.println(job + " <- printer");
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
//            byte[] b = s.getBytes("US-ASCII");
        byte[] b = s.getBytes("US-ASCII");
        //Get's the bytes from the String(So that characters such as å ä ö may be printed).
        Doc doc = new SimpleDoc(b, flavor, null);
        //Includes the content to print(b) and what kind of content it is (flavor, in this case a String turned into a byte array).

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new Copies(1));
        aset.add(new JobPriority(1));
        job.print(doc, null);
    }
}
