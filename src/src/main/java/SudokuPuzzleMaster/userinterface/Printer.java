package SudokuPuzzleMaster.userinterface;

import java.awt.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;

public class Printer implements Printable {
    BufferedImage img = ImageIO.read(new File("Screenshot.png"));

    public Printer() throws IOException {
    }

    public int print(Graphics g, PageFormat pf, int page) throws
        PrinterException {

        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.drawImage(img,0,0,null);

        /* Now we perform our rendering */

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }
    public void printing(BufferedImage image) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                /* The job did not successfully complete */
            }
        }
    }
}
