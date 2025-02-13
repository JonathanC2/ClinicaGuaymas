/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import Modelo.ModeloServicio;
import Modelo.Servicio;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jonathan Cabrera
 */
public class CrudServicio extends HttpServlet {
 String consultar = "ServicioDetalles.jsp";
    String editar = "ServicioEditar.jsp";
    String nuevo = "ServicioNuevo.jsp";
    Servicio servicio;
    String eliminar = "ServicioEliminar.jsp";
    ModeloServicio modelC = new ModeloServicio();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CrudServicio</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CrudServicio at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String acceso = "";
        String action = request.getParameter("accion");
        if (action.equalsIgnoreCase("editar")) {
            modelC.reconect();
            request.setAttribute("idper", request.getParameter("id"));
            acceso = editar;
        } else if (action.equalsIgnoreCase("mostrar")) {
            
            modelC.reconect();
            request.setAttribute("idper", request.getParameter("id"));
            acceso = consultar;
        } else if (action.equalsIgnoreCase("Actualizar")) {
            modelC.reconect();
            
            int id = Integer.parseInt(request.getParameter("idNuevo"));
            String nombre = request.getParameter("nombreNuevo");
            String descripcion = request.getParameter("descripcionNuevo");
            
            double precio = Float.parseFloat(request.getParameter("precioNuevo"));
            String img = request.getParameter("imgNuevo");
            servicio = new Servicio(id, nombre, descripcion, precio, img);
            modelC.actualizarCliente(servicio);
            response.sendRedirect("TablaServicios.jsp");
            return;
        } else if (action.equalsIgnoreCase("Eliminar")) {
            modelC.reconect();
            int id = Integer.parseInt(request.getParameter("id"));
           
            modelC.getServicio(id);
            response.sendRedirect("TablaServicios.jsp");
            return;
        }  
        RequestDispatcher vista = request.getRequestDispatcher(acceso);
        vista.forward(request, response);
    }
    

    /**
     * Handles the HTTP <code>POST</code> method.
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
