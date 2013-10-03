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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
            Data data = new Data();
            data.map = new HashMap<String, Object>();
            data.additional = new Date();
            String[] tmp = header.split("[\\t]");
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
                        data.map.put(device + "_batt_v1", tmp[1]);
                        data.map.put(device + "_batt_v2", tmp[2]);
                        data.map.put(device + "_batt_i1", tmp[3]);
                        data.map.put(device + "_batt_i2", tmp[4]);
                        data.map.put(device + "_batt_t1", tmp[5]);
                        data.map.put(device + "_batt_t2", tmp[6]);
                    } else if (tmp[0].toLowerCase().equals("smro")) {
                        data.map.put(device + "_smro_v", tmp[1]);
                        data.map.put(device + "_smro_i1", tmp[2]);
                        data.map.put(device + "_smro_i2", tmp[3]);
                        data.map.put(device + "_smro_i3", tmp[4]);
                        data.map.put(device + "_smro_i4", tmp[5]);
                        data.map.put(device + "_smro_i5", tmp[6]);
                        data.map.put(device + "_smro_i6", tmp[7]);
                    } else if (tmp[0].toLowerCase().equals("dcou")) {
                        data.map.put(device + "_dcou_v", tmp[1]);
                        data.map.put(device + "_dcou_i", tmp[2]);
                    } else if (tmp[0].toLowerCase().equals("alrm")) {
                        data.map.put(device + "_alarm_info_" + r, tmp[1]);
                        data.map.put(device + "_alarm_date_" + r, tmp[2]);
                    } else {
                        if (tmp.length > 2) {
                            for (int i = 1; i < tmp.length; i++) {
                                data.map.put(device + "_" + tmp[0] + "_" + i, tmp[i]);
                            }
                        } else if (tmp.length == 2) {
                            data.map.put(device + "_" + tmp[0], tmp[1]);
                        } else if (!tmp[0].trim().isEmpty()) {
                            data.map.put(tmp[0], "-");
                        }
                    }
                }
                try {
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
                    bw.write("OK\n");
                    bw.flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                register(data);
            } else {
                System.out.println("Unsupported request: " + tmp[0]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
                        int k = key.indexOf("_");
                        bw.write("\t");
                        bw.write(key.substring(0, k));
                        bw.write("\t");
                        bw.write(key.substring(k + 1));
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
                        int k = e.getKey().indexOf("_");
                        bw.write("\t");
                        bw.write(e.getKey().substring(0, k));
                        bw.write("\t");
                        bw.write(e.getKey().substring(k + 1));
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
