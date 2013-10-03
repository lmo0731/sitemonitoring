/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mn.mobicom.sitemonitoring.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mn.mobicom.sitemonitoring.entity.Device;
import mn.mobicom.sitemonitoring.entity.Event;
import mn.mobicom.sitemonitoring.facade.DeviceFacadeInterface;
import mn.mobicom.sitemonitoring.facade.EventFacadeInterface;
import mn.mobicom.sitemonitoring.jndi.ControlServiceInterface;
import mn.mobicom.sitemonitoring.object.Data;
import mn.mobicom.sitemonitoring.util.JsonTool;
import mn.mobicom.sitemonitoring.util.ParmMapDeserializer;

/**
 *
 * @author munkhochir
 */
public class ControlServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    public DeviceFacadeInterface deviceFacade;
    @EJB
    public EventFacadeInterface eventFacade;
    @EJB
    public ControlServiceInterface controlService;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Control c = new Control();
        try {
            /* TODO output your page here. You may use following sample code. */
            String device = request.getParameter("_device");
            String name = request.getParameter("_name");
            if (name == null) {
                c.info = ("_name parameter missed or invalid");
            }
            if(request.getParameter("_test")!=null){
                System.out.println(eventFacade);
                System.out.println(deviceFacade);
                Event e = new Event();
                e.setDate(new Date());
                eventFacade.create(e);
            }
            if (device != null) {
                Device d = deviceFacade.find(device);
                if (d != null) {
                    Data data = new Data();
                    data.device = d.getUdid();
                    data.additional = d.getUri();
                    data.map = ParmMapDeserializer.deserialize(request.getParameterMap());
                    System.out.println(data.map);
                    data.name = name;
                    try {
                        c.result = controlService.control(data);
                        c.info = "OK";
                    } catch (Throwable ex) {
                        c.info = ex.getMessage();
                    }
                } else {
                    c.info = "device not found";
                }
            } else {
                c.info = "_device parameter missing";
            }
        } finally {
            out.write(JsonTool.toJson(c));
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
