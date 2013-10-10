/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author munkhochir
 */
public class Log implements Runnable {

    private static Log instance = null;
    private long lastByte = 0;
    private static int maxline = 300;
    private long addedLine = 0;
    private long removedLine = 0;
    private boolean stop = true;
    private Thread thread = null;
    private FileInputStream fis = null;
    private Queue<ByteArrayOutputStream> lines = new LinkedList<ByteArrayOutputStream>() {
        @Override
        public boolean add(ByteArrayOutputStream e) {
            while (this.size() >= maxline) {
                this.remove();
                removedLine++;
            }
            addedLine++;
            return super.add(e);
        }
    };

    public void start() {
        stop = false;
        System.out.println("Log monitoring started");
        new Thread(this.getInstance()).start();
    }

    public void stop() {
        stop = true;
    }

    private Log() {
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public long getLastByte() {
        return lastByte;
    }

    public String fromLine(long from) {

        ByteArrayOutputStream sb = new ByteArrayOutputStream();
        from = Math.min(Math.max(from - removedLine, 0), lines.size() - 1);
        int i = 0;
        try {
            sb.write(new String("" + (removedLine + from) + "\n").getBytes());
        } catch (Exception ex) {
        }
        try {
            sb.write(new String("" + (addedLine - (removedLine + from)) + "\n").getBytes());
        } catch (Exception ex) {
        }
        synchronized (lines) {
            Iterator<ByteArrayOutputStream> iter = lines.iterator();
            while (iter.hasNext()) {
                ByteArrayOutputStream k = iter.next();
                if (i >= from) {
                    try {
                        sb.write(k.toByteArray());
                        sb.write('\n');
                    } catch (IOException ex) {
                    }
                }
                i++;
            }
            try {
                return sb.toString("UTF-8");
            } catch (UnsupportedEncodingException ex) {
            }
        }
        return "";
    }

    @Override
    public void run() {
        while (!stop) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                File f = new File("../logs/parsers.log");
                fis = new FileInputStream(f);
                fis.skip(lastByte);
                int count = 0;
                while (true) {
                    int b = -1;
                    if (fis != null) {
                        b = fis.read();
                    }
                    if (b == -1) {
                        break;
                    } else if (b == '\r') {
                    } else if (b == '\n') {
                        synchronized (lines) {
                            lines.add(baos);
                            baos = new ByteArrayOutputStream();
                        }
                    } else {
                        baos.write(b);
                    }
                    this.lastByte++;

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (Exception ex) {
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
            }
        }
    }
}
