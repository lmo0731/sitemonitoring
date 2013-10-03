package mn.mobicom.sitemonitoring.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Developer
 */
public class JsonTool {

    private static String toJson(Map<Object, Object> map, int tab) {
        String ret = "";
        int i = 0;
        for (Entry<Object, Object> e : map.entrySet()) {
            ret += tabs(tab + 1) + String.format("\"%s\" : %s", e.getKey().toString(), toJson(e.getValue(), tab + 1));
            if (i < (map.entrySet().size() - 1)) {
                ret += ",";
            }
            ret += "\n";
            i++;
        }
        return "{\n" + ret + "" + tabs(tab) + "}";
    }

    private static String tabs(int tab) {
        String ret = "";
        for (int i = 0; i < tab; i++) {
            ret += "  ";
        }
        return ret;
    }

    private static String toJson(Object[] l, int tab) {
        String ret = "";
        int i = 0;
        for (Object e : l) {
            ret += tabs(tab + 1) + toJson(e, tab + 1);
            if (i < l.length - 1) {
                ret += ",";
            }
            ret += "\n";
            i++;
        }
        return "[\n" + ret + tabs(tab) + "]";
    }

    private static String toJson(Collection<Object> l, int tab) {
        return toJson(l.toArray(), tab);
    }

    public static String toJson(Object o) {
        return toJson(o, 0);
    }

    private static String toJson(Object o, int tab) {
        if (o == null) {
            return "null";
        } else if (o instanceof Date) {
            return "new Date(" + ((Date) o).getTime() + ")";
        } else if (o instanceof Boolean) {
            return o.toString();
        } else if (o instanceof Collection) {
            return toJson((Collection) o, tab);
        } else if (o.getClass().isArray()) {
            return toJson((Object[]) o, tab);
        } else if (o instanceof Number) {
            return o.toString();
        } else if (o instanceof Map) {
            return toJson((Map) o, tab);
        } else if (o instanceof NullValue) {
            return "null";
        } else if (o instanceof String) {
            if (o.toString().isEmpty()) {
                return "\"\"";
            }
            return "\"" + escapeJavaStyleString(o.toString()) + "\"";
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            for (Field f : o.getClass().getFields()) {
                if (!f.isAnnotationPresent(XmlTransient.class)
                        && (f.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC
                        && !f.isAnnotationPresent(Transient.class)) {
                    try {
                        Object v = f.get(o);
                        String name = f.getName();
                        if (v == null) {
                            v = new NullValue();
                        }

                        map.put(name, v);
                    } catch (Exception ex) {
                    }
                }
            }
            for (Method m : o.getClass().getMethods()) {
                String name = m.getName();
                if (!name.startsWith("getClass")
                        && name.startsWith("get") && m.getParameterTypes().length == 0
                        && m.getReturnType() != Void.class
                        && !m.isAnnotationPresent(XmlTransient.class)
                        && (m.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC
                        && !m.isAnnotationPresent(Transient.class)) {
                    name = name.substring(3, 4).toLowerCase() + name.substring(4);
                    try {
                        Object v = m.invoke(o);
                        if (v == null) {
                            v = new NullValue();
                        }
                        map.put(name, v);
                    } catch (Exception ex) {
                    }
                }
            }
            return toJson(map, tab);
        }
    }

    private static String escapeJavaStyleString(String str) {
        String ret = "";
        if (str == null) {
            return ret;
        }
        int sz;
        sz = str.length();
        for (int i = 0; i < sz; i++) {
            char ch = str.charAt(i);

            // handle unicode
            if (ch > 0xfff) {
                ret += ("\\u" + hex(ch));
            } else if (ch > 0xff) {
                ret += ("\\u0" + hex(ch));
            } else if (ch > 0x7f) {
                ret += ("\\u00" + hex(ch));
            } else if (ch < 32) {
                switch (ch) {
                    case '\b':
                        ret += ('\\');
                        ret += ('b');
                        break;
                    case '\n':
                        ret += ('\\');
                        ret += ('n');
                        break;
                    case '\t':
                        ret += ('\\');
                        ret += ('t');
                        break;
                    case '\f':
                        ret += ('\\');
                        ret += ('f');
                        break;
                    case '\r':
                        ret += ('\\');
                        ret += ('r');
                        break;
                    default:
                        /*if (ch > 0xf) {
                         ret += ("\\u00" + hex(ch));
                         } else {
                         ret += ("\\u000" + hex(ch));
                         }*/
                        ret += ch;
                        break;
                }
            } else {
                switch (ch) {
                    case '\'':
                        ret += ('\\');
                        ret += ('\'');
                        break;
                    case '"':
                        ret += ('\\');
                        ret += ('"');
                        break;
                    case '\\':
                        ret += ('\\');
                        ret += ('\\');
                        break;
                    default:
                        ret += (ch);
                        break;
                }
            }
        }
        return ret;
    }

    private static String hex(char ch) {
        return Integer.toHexString(ch).toUpperCase();
    }

    public static void main(String args[]) throws Exception {
        String xml = ""
                + "    <table>\n"
                + "        <tr>\n"
                + "            <th>id</th>\n"
                + "            <th>trip_id</th>\n"
                + "            <th>date</th>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td>2</td>\n"
                + "            <td/>\n"
                + "            <td>2013-07-25 22:12:15</td>\n"
                + "        </tr>\n"
                + "        <tr>\n"
                + "            <td>1</td>\n"
                + "            <td/>\n"
                + "            <td>2013-07-25 22:12:12</td>\n"
                + "        </tr>\n"
                + "    </table>\n";
        Boolean t = new Boolean(true);
        System.out.println(JsonTool.toJson(t));
    }

    public static class NullValue {

        @Override
        public String toString() {
            return "null";
        }
    }
}
