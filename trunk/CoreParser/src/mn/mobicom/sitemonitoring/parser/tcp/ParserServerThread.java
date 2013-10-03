/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.parser.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import mn.mobicom.sitemonitoring.parser.Parser;
import org.apache.log4j.Logger;

/**
 *
 * @author Developer
 */
public class ParserServerThread implements Runnable {

    private Logger logger = Logger.getLogger(ParserServerThread.class);
    private int port = 0;
    private int stopRequest = 0;
    private boolean running = false;
    private Thread thread = null;
    private ServerSocket serverSocket = null;
    private Parser parser;

    public ParserServerThread(Parser p) {
        this.parser = p;
        this.running = false;
    }

    public void start() {
        if (this.isRunning()) {
            return;
        }
        try {
            stopRequest = 0;
            this.port = parser.getPort();
            serverSocket = new ServerSocket(parser.getPort());
            thread = new Thread(this, "Parser on port:" + port);
            thread.start();
        } catch (IOException ex) {
            logger.error("open socket with port:" + port, ex);
        } catch (IllegalThreadStateException ex) {
            logger.error("starting thread with port:" + port, ex);
        } catch (NumberFormatException ex) {
            logger.error("port parse error: ", ex);
        } catch (Exception ex) {
            logger.error("Unknown error", ex);
        }
    }

    public void stop() {
        if (!this.isRunning()) {
            return;
        }
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                logger.error("On closing socket", ex);
            }
        }
        stopRequest = 1;
    }

    public boolean isRunning() {
        if (thread == null) {
            return false;
        }
        if (!thread.isAlive()) {
            return false;
        }
        return running;
    }

    @Override
    public void run() {
        Socket clientSocket;
        running = true;
        logger.info("Starting server socket on port: " + port);
        try {
            while (stopRequest == 0) {
                clientSocket = serverSocket.accept();
                new ParserClientThread(parser, clientSocket).start();
            }
        } catch (Exception ex) {
            if (!ex.getMessage().toLowerCase().contains("socket closed")) {
                logger.error("Parser that listening port: " + port, ex);
            }
        }
        logger.info("Closing server socket on port: " + port);
        running = false;
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException ex) {
                logger.error("Closing server socket on port: " + port, ex);
            }
        }
    }
}
