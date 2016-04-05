package hardware.printer;


import java.io.FileInputStream;
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 * @author Majid
 */
public class Printing {

    public static void main(String[] args) {
        // TODO code application logic here
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        /* locate a print service that can handle it */
        PrintService[] pservices = PrintServiceLookup.lookupPrintServices(flavor, aset);

        try {
            int printer = getPrinter(pservices);
            if (printer == -1) {
                throw new Exception("No network printer found");
            }
            DocPrintJob pj = pservices[2].createPrintJob();
            FileInputStream fis = new FileInputStream("C:\\Users\\Kadir.basol.OZIDAS\\Documents\\test.txt");
            Doc doc = new SimpleDoc(fis, flavor, null);
            pj.print(doc, aset);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static int getPrinter(PrintService[] pservices) {
        int printer = -1;
        for (int i = 0; i < pservices.length ; i++) {
            if (pservices[i].getName().contains("Generic")) {
                System.out.println("network printer: " + pservices[i].toString());
                printer = i;
                break;
            }
        }
        return printer;
    }
}