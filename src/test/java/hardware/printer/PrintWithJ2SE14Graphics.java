package hardware.printer;

import java.util.*;
import java.io.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

public class PrintWithJ2SE14Graphics {
    public static void main(String args[]) {
        new PrintWithJ2SE14Graphics(args);
    }

    public PrintWithJ2SE14Graphics(String args[]) {
        final String sCrLf = System.getProperty("line.separator");
        final String sPrintFile = "PrintFile.ps";
        final String sErrNoPrintService = sCrLf + "No Print Service Installed";

        // Commandline parameter:
        String s2ndParm = null; // second commandline parameter
        int idxPrintService = -1; // -1 means: no parameter
        if (null != args && 0 < args.length) {
            // Check first commandline parameter (PrintService index):
            if (null != args[0]) {
                idxPrintService = -2; // -2 means: not a number
                try { // nr means: PrintService index
                    idxPrintService = Integer.parseInt(args[0]);
                } catch (Exception ex) {}
            }
            // Check second commandline parameter ('np'):
            if (1 < args.length)
                s2ndParm = args[1];
        }

        // Set DocFlavor and print attributes:
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PRINTABLE;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A4);
        aset.add(new MediaPrintableArea(0.19f, 0, 900, 900, MediaPrintableArea.MM));

        try {

            if (-2 == idxPrintService) {
                // Print to Stream (here to PostScript File):
                StreamPrintServiceFactory[] prservFactories =
                        StreamPrintServiceFactory.lookupStreamPrintServiceFactories(
                                flavor, DocFlavor.BYTE_ARRAY.POSTSCRIPT.getMimeType());
                if (null == prservFactories || 0 >= prservFactories.length) {
                    System.err.println(sErrNoPrintService);
                    System.exit(2);
                }
                System.out.println("Stream-PrintService-Factory:");
                for (int i = prservFactories.length - 1; i >= 0; i--)
                    System.out.println("  " + prservFactories[i] + " (" + prservFactories[i].getOutputFormat() + ")");
                FileOutputStream fos = new FileOutputStream(sPrintFile);
                StreamPrintService sps = prservFactories[0].getPrintService(fos);
                System.out.println("Stream-PrintService:");
                System.out.println("  " + sps + " (" + sps.getOutputFormat() + ")");
                printPrintServiceAttributesAndDocFlavors(sps);
                DocPrintJob pj = sps.createPrintJob();
//                final DocPrintJob printJob = PrintServiceLookup.lookupDefaultPrintService().createPrintJob();
                String s = "ABCDDE EEE DDD";
                byte[] b = s.getBytes("CP437");
                Doc doc = new SimpleDoc(b, flavor, null);
                pj.print(doc, aset);
                fos.close();
                System.out.println("Output '" + sPrintFile + "' successful.");
            } else {
                // Print to PrintService (e.g. to Printer):
                PrintService prservDflt = PrintServiceLookup.lookupDefaultPrintService();
                PrintService[] prservices = PrintServiceLookup.lookupPrintServices(flavor, aset);
                if (null == prservices || 0 >= prservices.length)
                    if (null != prservDflt) {
                        System.err.println("Only default printer.");
                        prservices = new PrintService[] {
                                prservDflt
                        };
                    } else {
                        System.err.println(sErrNoPrintService);
                        System.exit(3);
                    }
                System.out.println("Print-Services:");
                for (int i = 0; i < prservices.length; i++)
                    System.out.println("  " + i + ":  " + prservices[i] +
                            ((prservDflt != null && prservDflt.equals(prservices[i])) ? " (Default)" : ""));
                PrintService prserv = null;
                if (0 <= idxPrintService && idxPrintService < prservices.length)
                    prserv = prservices[idxPrintService];
                else {
                    if (!Arrays.asList(prservices).contains(prservDflt)) prservDflt = null;
                    //IF THIS IS ENABLED EVERYTHING PRINTS FINE
                    //prserv = ServiceUI.printDialog( null, 50, 50, prservices, prservDflt, null, aset );
                    //IF THIS IS ENABLED FIRST TO LETTERS ARE CUT OFF in each line
                    prserv = prservDflt;

                    System.out.println("      " + prserv);

                }
                if (null != prserv) {
                    System.out.println("Print-Service:");
                    System.out.println("      " + prserv);
                    printPrintServiceAttributesAndDocFlavors(prserv);
                    if (null == s2ndParm || !s2ndParm.equalsIgnoreCase("np")) {
                        DocPrintJob pj = prserv.createPrintJob();
                        String s = "ABCDDE EEE DDD";
                        byte[] b = s.getBytes("CP437");
                        Doc doc = new SimpleDoc(b, flavor, null);
                        pj.print(doc, aset);
                        System.out.println("Print successful.");
                    }
                }
            }

        } catch (PrintException pe) {
            System.err.println(pe);
        } catch (IOException ie) {
            System.err.println(ie);
        }
        System.exit(0);
    }

    private void printPrintServiceAttributesAndDocFlavors(PrintService prserv) {
        String s1 = null, s2;
        Attribute[] prattr = prserv.getAttributes().toArray();
        DocFlavor[] prdcfl = prserv.getSupportedDocFlavors();
        if (null != prattr && 0 < prattr.length)
            for (int i = 0; i < prattr.length; i++)
                System.out.println("      PrintService-Attribute[" + i + "]: " + prattr[i].getName() + " = " + prattr[i]);
        if (null != prdcfl && 0 < prdcfl.length)
            for (int i = 0; i < prdcfl.length; i++) {
                s2 = prdcfl[i].getMimeType();
                if (null != s2 && !s2.equals(s1))
                    System.out.println("      PrintService-DocFlavor-Mime[" + i + "]: " + s2);
                s1 = s2;
            }
    }
}