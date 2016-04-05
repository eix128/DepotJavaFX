package hardware.printer;

import org.junit.Test;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobPriority;
import javax.print.attribute.standard.PrinterState;
import java.awt.*;
import java.awt.print.*;
import java.io.UnsupportedEncodingException;

/**
 * Created by kadir.basol on 29.3.2016.
 */
public class TestPrinter {

    @Test
    public void PrintString() throws UnsupportedEncodingException, PrintException {
        char ESC = (char) 27;
        char GS = ((char) 29);
        String UnderlineOn = new String(new char[]{ESC, (char) 0x2D, (char) 0x31});
        String UnderlineOff = new String(new char[]{ESC, (char) 0x2D, (char) 0x30});
        String s = "Deneme 1 2 3\nKadir Deneme\n";
        for (int i = 0; i < 10; i++) {
            s += "\n";
        }

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

    @Test
    public void printCard() {
        final String bill = "Deneme 1 2 3";
        final PrinterJob job = PrinterJob.getPrinterJob();


        Printable contentToPrint = new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {


                Graphics2D g2d = (Graphics2D) graphics;

                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g2d.setFont(new Font("Monospaced", Font.BOLD, 7));


                if (pageIndex > 0) {
                    return NO_SUCH_PAGE;
                } //Only one page

                String Bill[] = bill.split(";");

                int y = 0;
                for (int i = 0; i < Bill.length; i++) {

                    g2d.drawString(Bill[i], 0, y);
                    y = y + 15;
                }

                return PAGE_EXISTS;

            }

        };

        PageFormat pageFormat = new PageFormat();
        pageFormat.setOrientation(PageFormat.PORTRAIT);
        Paper pPaper = pageFormat.getPaper();


        pPaper.setImageableArea(0, 0, pPaper.getWidth(), pPaper.getHeight() - 2);
        pageFormat.setPaper(pPaper);

        job.setPrintable(contentToPrint, pageFormat);


        try {
            job.print();

        } catch (PrinterException e) {
            System.err.println(e.getMessage());
        }
    }
}
