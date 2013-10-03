package mn.mobicom.sitemonitoring.parser;

import java.io.InputStream;
import java.io.OutputStream;
import mn.mobicom.sitemonitoring.object.Data;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author munkhochir
 */
public interface Parser {

    public int getTimeout();

    public int getPort();

    public void register(Data d);

    public void parse(InputStream in, OutputStream out);

    public Data control(InputStream in, OutputStream out, Data d);
}