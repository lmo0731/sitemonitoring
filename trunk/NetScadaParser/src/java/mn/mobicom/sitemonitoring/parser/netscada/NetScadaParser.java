/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.parser.netscada;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import mn.mobicom.jndi.service.JNDIService;
import mn.mobicom.sitemonitoring.object.Data;
import mn.mobicom.sitemonitoring.parser.tcp.ParserCore;

/**
 *
 * @author munkhochir
 */
@Singleton
@Startup
public class NetScadaParser extends ParserCore implements JNDIService {

    @Override
    public int getTimeout() {
        return 0;
    }

    @Override
    public int getPort() {
        return 13002;
    }

    @Override
    public void parse(InputStream in, OutputStream out) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String header = br.readLine();
            logger.info("Header:  " + header);
            Data data = new Data();
            data.map = new HashMap<String, Object>();
            data.additional = new Date();
            String[] tmp = header.split("[\\t]");
            logger.info(tmp.length);
            if (tmp.length > 2) {
                data.name = tmp[0];
                String device = tmp[2];
                if (tmp.length > 2) {
                    data.device = tmp[1];
                }
                int r = 0;
                while (true) {
                    String line = br.readLine();
                    r++;
                    logger.info("Received: " + line);
                    if (line == null || line.trim().equals("")) {
                        break;
                    }
                    tmp = line.split("[\\t]");
                    if (tmp[0].toLowerCase().equals("batt")) {
                        data.map.put(device + ".batt.v1", tmp[1]);
                        data.map.put(device + ".batt.v2", tmp[2]);
                        data.map.put(device + ".batt.i1", tmp[3]);
                        data.map.put(device + ".batt.i2", tmp[4]);
                        data.map.put(device + ".batt.t1", tmp[5]);
                        data.map.put(device + ".batt.t2", tmp[6]);
                    } else if (tmp[0].toLowerCase().equals("smro")) {
                        data.map.put(device + ".smro.v", tmp[1]);
                        data.map.put(device + ".smro.i1", tmp[2]);
                        data.map.put(device + ".smro.i2", tmp[3]);
                        data.map.put(device + ".smro.i3", tmp[4]);
                        data.map.put(device + ".smro.i4", tmp[5]);
                        data.map.put(device + ".smro.i5", tmp[6]);
                        data.map.put(device + ".smro.i6", tmp[7]);
                    } else if (tmp[0].toLowerCase().equals("dcou")) {
                        data.map.put(device + ".dcou.v", tmp[1]);
                        data.map.put(device + ".dcou.i", tmp[2]);
                    } else if (tmp[0].toLowerCase().equals("alrm")) {
                        data.map.put(device + ".alrm.info_" + r, tmp[1]);
                        data.map.put(device + ".alrm.date_" + r, tmp[2]);
                    } else if (tmp[0].toLowerCase().equals("acin")) {
                        data.map.put(device + ".acin.a", tmp[1]);
                        if (tmp.length > 2) {
                            data.map.put(device + ".acin.b", tmp[2]);
                        }
                        if (tmp.length > 3) {
                            data.map.put(device + ".acin.c", tmp[3]);
                        }
                    } else {
                        if (tmp.length > 2) {
                            for (int i = 1; i < tmp.length; i++) {
                                data.map.put(device + "." + tmp[0] + "." + i, tmp[i]);
                            }
                        } else if (tmp.length == 2) {
                            data.map.put(device + "." + tmp[0], tmp[1]);
                        } else if (!tmp[0].trim().isEmpty()) {
                            data.map.put(device + "." + tmp[0], "");
                        }
                    }
                }
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                    bw.write("OK\n");
                    bw.flush();
                } catch (Exception ex) {
                    logger.info("", ex);
                }
                register(data);
            } else {
                logger.info("Unsupported request: " + header);
            }
        } catch (Exception ex) {
            logger.info("", ex);
        }
    }

    @Override
    public Data control(InputStream in, OutputStream out, Data d) {
        Data ret = new Data();
        try {
            if (d.name.equals("get")) {
                if (d.map.size() == 1) {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                    String siteId = d.device;
                    bw.write(d.name);
                    bw.write("\t");
                    bw.write(siteId);
                    logger.info(d.map);
                    for (String key : d.map.keySet()) {
                        int k = key.indexOf(".");
                        int e = key.lastIndexOf(".");
                        if (k >= e) {
                            e = key.length();
                        }
                        bw.write("\t");
                        bw.write(key.substring(0, Math.max(k, 0)));
                        bw.write("\t");
                        bw.write(key.substring(k + 1, e));
                        break;
                    }
                    bw.write("\n\n");
                    bw.flush();
                    logger.info("data sent: " + d.map);
                    d.additional = "OK";
                } else {
                    ret.additional = "Invalid data to control: " + d.map;
                }
            } else if (d.name.equals("set")) {
                if (d.map.size() == 1) {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                    String siteId = d.device;
                    bw.write(d.name);
                    bw.write("\t");
                    bw.write(siteId);
                    for (Entry<String, Object> e : d.map.entrySet()) {
                        String key = e.getKey();
                        int k = key.indexOf(".");
                        int j = key.lastIndexOf(".");
                        if (k >= j) {
                            j = key.length();
                        }
                        bw.write("\t");
                        bw.write(key.substring(0, Math.max(k, 0)));
                        bw.write("\t");
                        bw.write(key.substring(k + 1, j));
                        bw.write("\t");
                        bw.write(e.getValue().toString());
                        break;
                    }
                    bw.write("\n\n");
                    bw.flush();
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    logger.info("data sent: " + d);
                    ret.additional = br.readLine();
                    logger.info(ret.additional);
                } else {
                    ret.additional = "Invalid data to control: " + d;
                }
            } else {
                ret.additional = "Unsupported data type: " + d;
            }
        } catch (Exception ex) {
            ret.additional = ex.getMessage();
        }
        return ret;
    }
}
