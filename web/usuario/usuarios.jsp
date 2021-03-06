<%-- 
    Document   : index
    Created on : Feb 14, 2018, 11:16:48 AM
    Author     : mich
--%>
<%
    HttpSession objSesion = request.getSession(false);
    boolean estado;
    String usuario = (String) objSesion.getAttribute("usuario");
    try {
        String tipos = (String) objSesion.getAttribute("tipo");
        String ids = String.valueOf(objSesion.getAttribute("i_d"));
        if (usuario != null && tipos != null && (tipos.equals("ADMIN") || tipos.equals("USUARIO") || tipos.equals("ADMINPROV"))) {
            // out.println(usuario);
        } else {
            response.sendRedirect("../index.jsp");
        }
%>
<!DOCTYPE html>
<html >
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0" />
        <title>CES/<%=tipos%></title>
        <link rel="shortcut icon" type="image/x-icon" href="../images/icono.png" />
        <link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../css/dashboard.css">
        <link rel="stylesheet" type="text/css" href="../css/fondos.css">
        <script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
        <script type="text/javascript" src="../js/tether.min.js"></script>
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/jquery-3.1.1.min.js"></script>
        <script>
            $(document).ready(function () {
                document.getElementById("search_prov").focus();
            });
        </script>
    </head>
    <body >
        <div class="">
            <nav class="navbar navbar-toggleable-md navbar-inverse fixed-top bg-inverse">
                <button class="navbar-toggler navbar-toggler-right hidden-lg-up" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <a class="navbar-brand" ><img src="../images/AF.png;base64" width="170px" height="50"></a>

                <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                    <ul class="navbar-nav mr-auto">
                        <%
                            if (tipos.equals("ADMIN") || tipos.equals("ADMINPROV")) {
                                out.print("<a class=\"nav-link\" href=index.jsp>Proveedores</a>");
                                out.print("<a class=\"nav-link active\">Usuarios<span class=\"sr-only\">(current)</span></a>"
                                        + "<a class=\"nav-link\" href=\"reportes.jsp\">Reportes</a>");
                            } else {
                                out.print("<a class=\"nav-link active\" href=\"reportes.jsp\">Reportes<span class=\"sr-only\">(current)</span></a>");
                                //pendiente al usuario
                            }
                        %>



                    </ul>
                    <div class="row">
                        <!--<input class="form-control mr-sm-2" type="text" placeholder="Search">-->
                        <a href="../Logout"><button class="btn btn-outline-secondary my-2 my-sm-0" >Cerrar Sesion</button></a>
                    </div>
                </div>
            </nav> 
        </div>
        <div class="container-fluid " align="center">
            <div class="row">
                <%
                    if (tipos.equals("ADMIN") || tipos.equals("ADMINPROV")) {

                        out.print("<nav class=\"col-sm-3 col-md-2 hidden-xs-down bg-faded sidebar\">\n"
                                + "          <ul class=\"nav nav-pills flex-column\" id=barra_nav>\n"
                                + "            <li class=\"nav-item\">\n"
                                + "              <a class=\"nav-link active\" href=\"\">Busqueda <span class=\"sr-only\">(current)</span></a>\n"
                                + "            </li>\n"
                                + "            <li class=\"nav-item\">\n"
                                + "              <a class=\"nav-link btn\" onclick=nuevo_prov_data()>Nuevo usuario</a>\n"
                                + "            </li>\n"
                                + "        </nav>");
                    } else {
                        out.print("<nav class=\"col-sm-3 col-md-2 hidden-xs-down bg-faded sidebar\">\n"
                                + "          <ul class=\"nav nav-pills flex-column\">\n"
                                + "            \n"
                                + "          </ul>\n"
                                + "        </nav>");
                    }
                    if (tipos.equals("ADMIN") || tipos.equals("ADMINPROV")) {
                        out.print("<main class=\"col-sm-9 offset-sm-3 col-md-10 offset-md-2 pt-3\" id=main_prov>\n"
                                + "          <h1>Usuarios</h1>\n"
                                + "          <section class=\"row text-center placeholders\" id=section_prov align=center>\n"
                                + "            <div class=\"col-6 col-sm-3 placeholder\">\n"
                                + "              <h4>Busqueda</h4>\n"
                                + "              <input type=\"text\" class=\"form-control\" id=\"search_prov\" onkeypress=\"autosearch_provs()\">\n"
                                + "            </div>\n"
                                + "              <div class=\"col-6 col-sm-3 placeholder\">\n"
                                + "                  <br><div class=\"text-muted\">Activos</div><input type=\"radio\" name=\"gg\" id=\"gg\" value=\"A\" checked=\"checked\" />\n"
                                + "              </div>\n"
                                + "              <div class=\"col-6 col-sm-3 placeholder\">\n"
                                + "                  <br><div class=\"text-muted\">Inactivos</div><input type=\"radio\" name=\"gg\" id=\"gg1\" value=\"I\" />\n"
                                + "              </div>\n"
                                + "          </section>\n"
                                + "          <div id=table_prov class=\"table-responsive\">\n"
                                + "            <table class=\"table table-striped\">\n"
                                + "              <thead>\n"
                                + "                <tr align=\"center\">\n"
                                + "                  <td>Nombre</td>\n"
                                + "                  <td>Departamento</td>\n"
                                + "                  <td>Tipo</td>\n"
                                + "                  <td>Empresa</td>\n"
                                + //"                  <td>Modificar</td>\n" +
                                "                  <td>Mover</td>\n"
                                + "                </tr>\n"
                                + "              </thead>\n"
                                + "              <tbody id=\"body_table\">\n"
                                + "              </tbody>\n"
                                + "            </table>\n"
                                + "          </div>\n"
                                + "        </main>");
                    }
                %>

                <!-- Modal -->
                <div id="myModal" class="modal fade" role="dialog">
                    <div class="modal-dialog">
                        <!-- Modal content-->
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                <h4 class="modal-title">Iniciar Sesion</h4>
                            </div>
                            <div class="modal-body">
                                <form method="post" action="">
                                    <div align="center"><button class="btn btn-success">Iniciar</button></div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>
                <script type="text/javascript" src="../js/functions_js_1.js"></script>
                </body>
                </html>
                <%
                    } catch (Exception e) {
                        System.out.println(e);
                        out.println("<script type=\"text/javascript\">");
                        out.println("location='../index.jsp';");
                        out.println("</script>");
                    }
                %>