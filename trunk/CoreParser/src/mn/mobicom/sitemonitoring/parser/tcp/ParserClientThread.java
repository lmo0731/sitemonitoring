/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.parser.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import mn.mobicom.sitemonitoring.parser.Parser;
import org.apache.log4j.Logger;

/**
 *
 * @author Developer
 */
public class ParserClientThread extends Thread {

    transient Logger logger = Logger.getLogger(ParserClientThread.class);
    transient Socket socket = null;
    Parser parser;

    ParserClientThread(Parser parser, Socket clientSocket) {
        this.parser = parser;
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            logger.info("(Port: " + socket.getLocalPort() + ") Connection from " + socket.getInetAddress().toString());
            socket.setSoTimeout(parser.getTimeout());
            in = socket.getInputStream();
            out = socket.getOutputStream();
            parser.parse(in, out);
        } catch (SocketTimeoutException ex) {
        } catch (SocketException ex) {
        } catch (Exception ex) {
            logger.error("(Port: " + socket.getLocalPort() + ") ", ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
            }
        }
    }
}
