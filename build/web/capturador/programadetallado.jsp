<%-- 
    Document   : depcosto
    Created on : Sep 15, 2017, 9:51:16 AM
    Author     : gateway1
--%>

<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="persistencia.Avances"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% HttpSession objSesion = request.getSession(false);
    Avances db = new Avances();
    try {
        boolean estado;
        String usuario = (String) objSesion.getAttribute("usuario");
        String tipos = (String) objSesion.getAttribute("tipo");
        String ids = String.valueOf(objSesion.getAttribute("i_d"));

        if (usuario != null && tipos != null && tipos.equals("INTERMEDIO")) {

        } else {
            response.sendRedirect("../index.jsp");
        }
        String f1 = request.getParameter("prog");
        String mes = request.getParameter("mes");
        String year = request.getParameter("year");

        String patt = "\\d{0,7}";
        Pattern pat = Pattern.compile(patt);
        Matcher match = pat.matcher(f1);
        if (match.matches()) {
            db.abrir();
            // reporte detallado de un progama
            File reportfile = new File(application.getRealPath("usuario/programa_lote.jasper"));
            Map para = new HashMap();
            para.put("program", new String(f1));
            para.put("mes", new String(mes));
            para.put("years", new String(year));
            byte[] bytes = JasperRunManager.runReportToPdf(reportfile.getPath(), para, db.getConexion());
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outputstream = response.getOutputStream();
            outputstream.write(bytes, 0, bytes.length);
            outputstream.flush();
            outputstream.close();

        } else {
            System.out.println("NO ES FECHA");
            // response.sendRedirect("index.jsp");
        }

%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>


    </body>

</html>
<%    } catch (Exception e) {
        e.printStackTrace();
        out.print("<script>location='pedido.jsp';</script>");

    } finally {
        //if (db.getConexion() != null) {
        //    db.cerrar();
            // response.sendRedirect("verpares.jsp");
        //}
    }
%>