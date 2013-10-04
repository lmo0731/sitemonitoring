/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.parser.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import mn.mobicom.jndi.service.JNDIService;
import mn.mobicom.jndi.service.JNDIServiceDelegate;
import mn.mobicom.sitemonitoring.object.Data;
import mn.mobicom.sitemonitoring.parser.Parser;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 *
 * @author munkhochir
 */
public abstract class ParserCore implements Parser, JNDIService {

    protected transient Logger logger = Logger.getLogger(Parser.class);
    private transient ParserServerThread server;

    @PostConstruct
    public final void start() {
        BasicConfigurator.configure(new ConsoleAppender(new PatternLayout("%c.%M(%C{1}.java:%L) %m")));
        try {
            DailyRollingFileAppender appender = new DailyRollingFileAppender(new PatternLayout("[%-5p] %d{HH:mm:ss} %C:(%3L): [" + this.getPort() + "] %m%n"), "../logs/parsers.log", "'.'yyyy-MM-dd");
            appender.setAppend(true);
            logger.addAppender(appender);
        } catch (IOException ex) {
        }
        server = new ParserServerThread(this);
        server.start();
    }

    @PreDestroy
    public final void stop() {
        server.stop();
    }

    @Override
    public final void register(Data req) {
        try {
            logger.info("Register request: " + req.toString());
            Object res = JNDIServiceDelegate.send("SiteMonitoringWeb", "RegisterService", req);
            logger.info("Register response: " + res.toString());
        } catch (Exception ex) {
            logger.error("Server side unknown error", ex);
        }
    }

    public Data control(Data d) throws IOException {
        Data res = new Data();
        if (d.device != null) {
            try {
                URI uri = new URI(d.additional.toString());
                logger.info(d.additional.toString());
                
                if (uri.getScheme().equals("tcp")) {
                    try {
                        Socket s = new Socket(uri.getHost(), uri.getPort());
                        s.setSoTimeout(getTimeout());
                        res = control(s.getInputStream(), s.getOutputStream(), d);
                        s.close();
                    } catch (Exception ex) {
                        res.additional = ex.getMessage();
                    } finally {
                        return res;
                    }
                } else {
                    res.additional = "Unsupported device uri scheme: " + uri.getScheme();
                }
            } catch (URISyntaxException ex) {
                res.additional = ex.getMessage();
            } finally {
                return res;
            }
        } else {
            res.additional = "Unknown device uri: " + d.device;
            return res;
        }
    }

    @Override
    public Object process(Object req) throws Throwable {
        if (req instanceof Data) {
            Data d = (Data) req;
            return control(d);
        }
        return "NOK";
    }
}
