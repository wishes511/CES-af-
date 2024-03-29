/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Departamento;
import Modelo.Movimiento;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistencia.CES;
import persistencia.CES_depa;
import persistencia.CES_movs;
import persistencia.CES_prov;

/**
 *
 * @author michell
 */
@WebServlet(name = "Getfields", urlPatterns = {"/Getfields"})
public class Getfields extends HttpServlet {

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
        processRequest(request, response);
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
        HttpSession objSesion = request.getSession(false);
        String empresa = (String) objSesion.getAttribute("empresa");
        String codigo = request.getParameter("codigo");
        
        ArrayList<String> arr = new ArrayList<>();
        ArrayList<String> arr_depa = new ArrayList<>();
        CES_prov prov = new CES_prov();
        CES_depa depa = new CES_depa();
        String area = "";
        String d4 = "";
        try {
            if (codigo.length() == 6 || codigo.length() == 7) {
                codigo += "00";
            }
            if (codigo.length() == 8 || codigo.length() == 9) { //[1,1,9999,01,$]
                for (int i = 0; i < codigo.length(); i++) {
                    if (i > 1 && i < 6) {
                        d4 += codigo.charAt(i);
                    }
                }
                Calendar fecha = Calendar.getInstance();
                int año = fecha.get(Calendar.YEAR);
                int mes = fecha.get(Calendar.MONTH) + 1;
                int dia = fecha.get(Calendar.DAY_OF_MONTH);
                String fechac = año + "-" + mes + "-" + dia;
                int hora = fecha.get(Calendar.HOUR_OF_DAY);
                int minuto = fecha.get(Calendar.MINUTE);
                String horas =formatofecha(hora,minuto);
                   //System.out.println(d4);
                String n_tarjeta = String.valueOf(codigo.charAt(6)) + codigo.charAt(7);// tomamos los ultimos valores del codigo
                CES_movs mov = new CES_movs();// declaramos e instanciamos el objeto mov
                area = depa.busca_area_cod(codigo.charAt(0));// busca el area de acuerdo al codigo
                ArrayList<String> array; // declaramos una lista
                PrintWriter out = response.getWriter();
                switch (Integer.parseInt(d4)) {// verifica los 4 digitos
                    // proveedores
                    case 9999:
                        if (codigo.charAt(1) == '1' && Integer.parseInt(n_tarjeta)!=0) {// verifica el segundo digito del codigo y el ultimo por la tarjeta
                            array = mov.search_lastmov(area, fechac, n_tarjeta, "prov"); //busca ultimo movimiento al uso de la tarjeta
                            select_tipo_user(n_tarjeta, array, out, mov, arr, arr_depa, prov, depa, codigo, area, "9999", horas,empresa);
                        } else {
                            out.print("<label>Error en el codigo asignado intentelo de nuevo</label>");
                            out.print("<script>document.getElementById('codigo').value='';</script>");
                        }
                        break;
                    // Sr pablo
                    case 9998:
                        if (codigo.charAt(0) == '6' && codigo.charAt(1) == '6' && Integer.parseInt(n_tarjeta)!=0) {
                            array = mov.search_lastmov(area, fechac, n_tarjeta, "invitado"); //busca ultimo movimiento al uso de la tarjeta
                            select_tipo_user(n_tarjeta, array, out, mov, arr, arr_depa, prov, depa, codigo, area, "9997", horas,empresa);
                        } else {
                            out.print("<label>Error en el codigo asignado, intentelo de nuevo</label>");
                            out.print("<script>document.getElementById('codigo').value='';</script>");
                        }
                        break;
                    // invitados
                    case 9997:
                        // invitado_fields(area,out,depa);
                        if (codigo.charAt(1) == '7' && codigo.charAt(1) != '6' && Integer.parseInt(n_tarjeta)!=0) {
                            array = mov.search_lastmov(area, fechac, n_tarjeta, "invitado"); //busca ultimo movimiento al uso de la tarjeta
                            select_tipo_user(n_tarjeta, array, out, mov, arr, arr_depa, prov, depa, codigo, area, "9997", horas,empresa);
                        } else {
                            out.print("<label>Error rn el codigo asignado intentelo de nuevo</label>");
                            out.print("<script>document.getElementById('codigo').value='';</script>");
                        }
                        break;
                    // Personal y maquiladores
                    default:
                        String resp = "";
                        String cods=codigo.charAt(0)+""+codigo.charAt(1)+""+d4;
                        //System.out.println((codigo.charAt(1) == '8')+" "+(codigo.charAt(1) == '3') );
                        if (codigo.charAt(1) == '8' || codigo.charAt(1) == '3' || codigo.charAt(1) == '9') {
                            CES u = new CES();
                            String asunto = request.getParameter("motivo").toUpperCase();
                            String id_depa = request.getParameter("depa_cambio");
//                            ArrayList<String> arru = u.buscaru_clave(Integer.parseInt(d4), "");
                            ArrayList<String> arru = u.buscaru_cod(cods, "");
                            ArrayList<String> arr_maq = depa.busca_dep_pt();
                            if (!arru.isEmpty() && area.equals(arru.get(4))) {// verifica si hay un ultimo registro 
                                ArrayList<String> arrmov = new ArrayList<>();
                                if (arru.get(5).equals("MAQUILA") || arru.get(5).equals("ESPECIAL")) {// verifica si ese espacio en los registros que encontro es maquila
                                    arrmov = mov.searchlast_movmaq(arru, fechac);// recupera datos del ultimo registro
                                    if (!arrmov.isEmpty()) {// comprueba que no este vacio
                                        // arru.set(2, arrmov.get(2));
                                        String aux = arrmov.get(arrmov.size() - 1);
                                        arru.set(2, aux);// modifica el arreglo de acuerdo a la posicion y el valor que lo sustituira
                                    }
                                } else {
                                    arrmov = mov.searchlast_movuser(arru, fechac);// recupera datos que no sea maquilas, solo personal
                                }
                                if (!arrmov.isEmpty() && !asunto.equals("")) {// verificar si hay entradas o salidas y el asunto no esta vacio
                                    tipo_usuario_pm(arru, area, fechac, horas, out, mov, asunto, id_depa,empresa);
                                } else if (!asunto.equals("")) {// si el asunto es diferente de vacio
                                    tipo_usuario_pm(arru, area, fechac, horas, out, mov, asunto, id_depa,empresa);
                                } else {// si ninguna de las opciones anteriores entra
                                    if (arrmov.isEmpty()) {// verificar si mi lista de ultimo moviminto esta vacia
                                        if(codigo.charAt(1)=='9'){
                                            tipo_usuario_pm(arru, area, fechac, horas, out, mov, asunto, id_depa,empresa);
                                        }else if (codigo.charAt(1) == '8') {// verificar si es personal y despliega un menu con el campo asunto
                                            resp = "<main class=\"col-sm-12 pt-2\" ><section><label class=\"ln\" >Asunto , Motivo </label><br>"
                                                    + "<input type=text id=motivo class=\"ln form-control\" onchange=\"searchuser1()\"><script>document.getElementById('motivo').focus();</script></section></main>";
                                        } else {// si no despliega un menu con el campo asunto y el Almacen de PT
                                            int cont = 0;
                                            resp = "<main class=\"col-sm-12 pt-2\" ><section><label class=\"ln\" >Departamento </label><br>"
                                                    + "<select id=depa_maq class=form-control>";
                                            for (int i = 0; i < arr_maq.size(); i++) {
                                                if (cont == 1) {
                                                    resp += "<option value=" + arr_maq.get(i - 1) + ">" + arr_maq.get(i) + "</option>";
                                                    cont = 0;
                                                } else {
                                                    cont++;
                                                }
                                            }
                                            resp += "</select><br><label class=\"ln\" >Asunto , Motivo </label><br><input type=text id=motivo class=\"ln form-control\" onchange=\"searchuser1()\"><script>document.getElementById('motivo').focus();</script></section></main>";
                                        }
                                    } else {// si ya hay registros acerca del usuario comparar para saber que tipo de accion se va a realizar
                                        int cont = 0;
                                        if (arrmov.get(arrmov.size() - 2).equals("M") || arrmov.get(arrmov.size() - 2).equals("Z")) {// verifica si el tipo de usuario fue una maquila
                                            //  ArrayList<String> arr=mov.searchlast_movuser(arru, fechac) ;
                                            if (arrmov.get(arrmov.size() - 3).equals("E")) {// y si ese movimiento fue una entrada
                                                tipo_usuario_pm(arru, area, fechac, horas, out, mov, asunto, id_depa,empresa);
                                            } else {
                                                cont = 0;
                                                resp = "<main class=\"col-sm-12 pt-2\" ><section><label class=\"ln\" >Departamento </label><br>"
                                                        + "<select id=depa_maq class=form-control>";
                                                for (int i = 0; i < arr_maq.size(); i++) {
                                                    if (cont == 1) {
                                                        resp += "<option value=" + arr_maq.get(i - 1) + ">" + arr_maq.get(i) + "</option>";
                                                        cont = 0;
                                                    } else {
                                                        cont++;
                                                    }
                                                }
                                                resp += "</select><br><label class=\"ln\" >Asunto , Motivo </label><br><input type=text id=motivo class=\"ln form-control\" onchange=\"searchuser1()\"><script>document.getElementById('motivo').focus();</script></section></main>";
                                            }
                                        } else {
                                            if (arrmov.get(arrmov.size() - 2).equals("S")) {
                                                tipo_usuario_pm(arru, area, fechac, horas, out, mov, asunto, id_depa,empresa);
                                            } else {
                                                resp = "<main class=\"col-sm-12 pt-2\" ><section><label class=\"ln\" >Asunto , Motivo </label><br><input type=text id=motivo class=\"ln form-control\" onchange=\"searchuser1()\"><script>document.getElementById('motivo').focus();</script></section></main>";
                                            }
                                        }
                                    }
                                }
                            } else {
                                resp = "<label>No se encontro Usuario o codigo registrado</label><script>document.getElementById('codigo').value='';</script>";
//                                out.print("<label>No se encontro Usuario o codigo registrado</label>");
//                                out.print("<script>document.getElementById('codigo').value='';</script>");
                            }
                        } else {
                            resp = "<label>No se encontro Usuario o codigo registrado</label><script>document.getElementById('codigo').value='';</script>";
//                            out.print("<label>No se encontro Usuario o codigo registrado</label>");
//                            out.print("<script>document.getElementById('codigo').value='';</script>");
                        }
                        out.print(resp);
                        resp = "";
                        break;
                }
            } else {
                PrintWriter oute = response.getWriter();
                oute.print("<div class= container-fluid align=center><label>Escriba o pase su numero de credencial adecuada</label><div class=col-sm-3 align=center>");
                oute.print("<script>document.getElementById('codigo').value='';</script>");
            }

        } catch (ClassNotFoundException c) {
            System.out.println(c);
            PrintWriter out = response.getWriter();
            out.print("<label>Codigo 2: Falta algun archivo por cargar o no se encuentra alguno, llame a un administrador<br>" + c + "</label>");
        } catch (IOException i) {
            System.out.println(i);
            PrintWriter out = response.getWriter();
            out.print("<label>Codigo 2.1: Problemas al ingresar correctamente un dato</label>");
        } catch (SQLException ex) {
            Logger.getLogger(Getfields.class.getName()).log(Level.SEVERE, null, ex);
            PrintWriter out = response.getWriter();
            out.print("<label>Codigo 2.2: Problemas con la base de datos, llame a un administrador<br>" + ex + "</label>");
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("<label>Codigo 2.3: Problemas al ingresar correctamente los datos<br>" + e + "</label>");
            Logger.getLogger(Getfields.class.getName()).log(Level.SEVERE, null, e);
        }
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
    
    private String formatofecha(int hora,int minuto){
    String horas = "";
                if (hora > 9) {
                    horas = hora + ":";
                }
                if (hora < 10) {
                    horas = "0" + hora + ":";
                }
                if (minuto < 10) {
                    horas = horas + "0" + minuto;
                }
                if (minuto > 9) {
                    horas += minuto;
                }
                System.out.println(horas);
                horas=(horas.length()==5)?horas:"0"+horas;
                return horas;
    }
    
    private void select_tipo_user(String n_tarjeta, ArrayList<String> array, PrintWriter out, CES_movs mov, ArrayList<String> arr,
            ArrayList<String> arr_depa, CES_prov prov, CES_depa depa, String codigo, String area, String cod_usuario, String hora_salida,String empresa) throws ClassNotFoundException, SQLException {
        //System.out.println(array.size());
        if (!array.isEmpty()) {// verifica si encontro algun registro con anterioridad en el uso de la tarjeta
            Departamento d = new Departamento();
            Movimiento m = new Movimiento();
            for (int i = 0; i < array.size(); i++) {//contara el numero total de elementos guardados
                if (i == 11 && (array.size() / 12) == 1) {// i=11 ejecutara al tener el numero de elementos para cada fila
                    if (array.get(i - 6).equals("S")) {
                        if (cod_usuario.equals("9999")) {
                            arr = prov.buscarprov(arr);//carga de proveedores
                            arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                            prov_fields(arr, arr_depa, area, out);// carga de menu de proveedor 
                        } else if (cod_usuario.equals("9997")) {
                            arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                            invitado_fields(area, out, arr_depa);// carga menu de invitados
                        }
                    } else {
                        d.setClaveDepartamento(Integer.parseInt(array.get(i - 4)));
                        m.setFolio(Integer.parseInt(array.get(i - 11)));
                        m.setClaveUsuario(Integer.parseInt(array.get(i - 10)));
                        m.setClaveProveedor(Integer.parseInt(array.get(i - 9)));
                        m.setClaveAutorizado(Integer.parseInt(array.get(i - 8)));
                        m.setNombre(array.get(i - 7));
                        m.setTipoMov(array.get(i - 6));
                        m.setArea(array.get(i - 5));
                        m.setDepartamento(d);
                        m.setObservaciones(array.get(i - 3));
                        m.setFecha(array.get(i - 2));
                        String credencial = array.get(i);
                        out.print("<label>" + mov.nuevomov(m, hora_salida, credencial,empresa) + "</label>");
                        out.print("<script>document.getElementById('codigo').value='';</script>");
                    }
                } else if ((array.size() / 12) > 1 && i > 11) {// si es mas de 12 espacios
                    int h = 12 * ((array.size() / 12) - 1); //12 x ((tamano de la lista / 12)-1) marca el inicio del ultimo renglon
                    if (array.get(h + 5).equals("E")) {
                        //System.out.println("entre a E");
                        d.setClaveDepartamento(Integer.parseInt(array.get(h + 7)));
                        m.setFolio(Integer.parseInt(array.get(h)));
                        m.setClaveUsuario(Integer.parseInt(array.get(h + 1)));
                        m.setClaveProveedor(Integer.parseInt(array.get(h + 2)));
                        m.setClaveAutorizado(Integer.parseInt(array.get(h + 3)));
                        m.setNombre(array.get(h + 4));
                        m.setTipoMov(array.get(h + 5));
                        m.setArea(array.get(h + 6));
                        m.setDepartamento(d);
                        m.setObservaciones(array.get(h + 8));
                        m.setFecha(array.get(h + 9));
                        String credencial = array.get(h + 11);
                        out.print("<label>" + mov.nuevomov(m, hora_salida, credencial,empresa) + "</label>"); //nuevasalida
                        out.print("<script>document.getElementById('codigo').value='';</script>");
                    } else {
                        if (cod_usuario.equals("9999")) {
                            arr = prov.buscarprov(arr);//carga de proveedores
                            arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                            prov_fields(arr, arr_depa, area, out);// carga de menu de proveedor 
                        } else if (cod_usuario.equals("9997")) {
                            arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                            invitado_fields(area, out, arr_depa);// carga menu de invitados
                        }
                    }
                    //System.out.println(i+" h: "+h+"e.e /"+array.get(h)+","+array.get(h+1)+","+array.get(h+2)+","+array.get(h+3)+","+array.get(h+4)+","+array.get(h+5)+","+array.get(h+6)+","+array.get(h+7)+","+array.get(h+8)+","+array.get(h+9)+","+array.get(h+10)+","+array.get(h+11));
                    i = array.size();
                }
            }
        } else {// sino encontro registros, nueva entrada
            if (cod_usuario.equals("9999")) {
                arr = prov.buscarprov(arr);//carga de proveedores
                arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                prov_fields(arr, arr_depa, area, out);
            } else if (cod_usuario.equals("9997")) {
                arr_depa = depa.busca_depa_cod(arr_depa, codigo.charAt(0)); //carga de departamentos de acuerdo al area
                invitado_fields(area, out, arr_depa);
            }
        }
    }

    public void tipo_usuario_pm(ArrayList<String> arru, String area, String fecha, String horas, PrintWriter out, CES_movs movs, String asunto, String newdepa,String empresa) throws ClassNotFoundException, SQLException {
        Movimiento m = new Movimiento();
        CES_depa depa = new CES_depa();
        Departamento dep = new Departamento();
        boolean nulo = true;
        if (newdepa != (null)) {
            nulo = false;
        } else {
            dep.setClaveDepartamento(Integer.parseInt(arru.get(2)));
        }
        if (nulo) {
            dep.setClaveDepartamento(Integer.parseInt(arru.get(2)));
        } else {
            if (newdepa.equals("")) {
                dep.setClaveDepartamento(Integer.parseInt(arru.get(2)));
            } else {
                dep.setClaveDepartamento(Integer.parseInt(newdepa));
                area = depa.busca_nombrearea(Integer.parseInt(newdepa));
            }
        }
        m.setClaveUsuario(Integer.parseInt(arru.get(0)));
        m.setClaveProveedor(0);
        m.setClaveAutorizado(0);
        m.setNombre(arru.get(1));
        m.setArea(area);
        m.setDepartamento(dep);
        m.setObservaciones("");
        m.setFecha(fecha);
        m.setDirigido("");
        m.setAsunto(asunto);
        m.setTipo_transporte("AUTOMOVIL");
        if (arru.get(5).equals("PERSONAL") || arru.get(5).equals("ADMIN")) {
            m.setTipo_usuario("U");
        }else if(arru.get(5).equals("ESPECIAL") ){
            m.setTipo_usuario("Z");
            m.setAsunto("ESPECIAL");
        } else {
            m.setTipo_usuario("M");
        }
        String credencial = "";

        if (arru.get(2).equals("") || arru.get(0).equals("")) {
            out.print("<label>Algun campo esta vacio,no se puede continuar</label>");
            out.print("<script>document.getElementById('codigo').value='';</script>");
        } else {
            out.print("<div>" + movs.nuevomov_maqp(m, horas, credencial,empresa) + "</div>");
            out.print("<script>document.getElementById('codigo').value='';</script>");
        }
    }

    private void prov_fields(ArrayList<String> arr, ArrayList<String> arr_depa, String area, PrintWriter out) {// menu proveedor
        int cont = 0;
        out.print("<div class=\" \" align=\"center\"> "
                + "<main class=\"col-sm-12 pt-2\" >"
                + "<section><label class=\"ln\" style=\"color: red\" >Area : </label><input type=text id=\"area\" class=\"ln non-input\" value=" + area + " disabled>\n"
                + "</section>"
                + "<section class=\"row text-center fondos redondeado\" id=section_prov align=center  >\n"
                + "<div class=\"col-4 col-sm-4 placeholder\" align=\"center\"  >\n"
                + "<label>Proveedor :</label><select id=\"proveedor\" name=\"proveedor\" onchange=\"searchactivo_id()\" class=\"form-control\"><option></option>");
        for (int i = 0; i < arr.size(); i++) {
            if (cont == 1) {
                out.print("<option onchange=searchactivo_id() value=" + arr.get(i) + ">" + arr.get(i) + "</option>");
                cont = 0;
            } else {
                cont++;
            }
        }
        out.print("                             </select>\n"
                + "                                <br><input type=text id=\"proveed\" class=\"ln form-control\" placeholder=\"Nuevo Proveedor\" onchange=saltopa()>\n"
                + "                        </div>\n"
                + "                        "
                + "                        <div class=\"col-4 col-sm-3 placeholder\" align=\"center\" >\n"
                //                + "                            <label>Persona Autorizada :</label><select id=\"pa\" class=\"form-control\">\n"
                + "                            <div id=\"p_activos\"></div>"
                + "                              <br>  <input type=text id=\"p_activos_n\" class=\"ln form-control\" placeholder=\"Nombre\" onchange=saltodepa()>\n"
                + "                        </div>\n"// div persona autorizada
                + "                        <div class=\"col-4 col-sm-4 placeholder\">\n"
                + "                                <label>Departamento : </label><select id=\"depa\" onchange=\"ida()\" class=\"form-control\"><option></option>\n");
        for (int i = 0; i < arr_depa.size(); i++) {
            if (cont == 1) {
                out.print("<option onchange=searchactivo_id() value=" + arr_depa.get(i - 1) + ">" + arr_depa.get(i) + "</option>");
                cont = 0;
            } else {
                cont++;
            }
        }
        out.print("                            </select>\n"
                + "                         <label>Se dirige con : </label><input type=text id=\"observacion\" class=\"ln form-control\" placeholder=\"Nombre\" onchange=saltotrans()>\n"
                + "                            </div>\n"
                + "                      <section class= \"col-sm-6 offset-sm-3\"><br><label>Tipo de transporte:</label><br><select onchange=saltoinvis() id=\"transporte\" class=form-control><option></option><option value=SN>SIN TRANSPORTE</option><option value=AUTOMOVIL>AUTOMOVIL</option><option value=BICICLETA>BICICLETA</option><option value=MOTOCICLETA>MOTOCICLETA</option></select></section><br>\n"
                + "                      <section class= \"col-sm-6 offset-sm-3\"><br><input type=\"button\" class=\"btn\" value=\"Iniciar I/O\" onclick=\"inicio_io()\" id=bi><br></section><br>\n"
                + "                        <div id=ensa></div>\n"
                + "                    </section></main></div>");
    }

    private void invitado_fields(String area, PrintWriter out, ArrayList<String> arr_depa) {//menu invitado
        int cont = 0;
        out.print("<div >\n"
                + "<main class=\"col-sm-12 pt-2\" >"
                + "<section><label class=\"ln\" style=\"color: red\" >Area : </label><input type=text id=\"area\" class=\"ln non-input\" value=" + area + " disabled>\n"
                + "</section>"
                + "<section class=\"row text-center fondos redondeado\" id=section_prov align=center >"
                + "<div class=\"col-4 col-sm-3 placeholder\" style=\"\" align=center >\n"
                + "     <label>Procedencia :</label><br><input type=text id=\"procedencia\" class=\"ln form-control\" placeholder='' onchange=saltoprocedencia()>\n"
                + "</div><br>\n"
                + "<div class=\"col-4 col-sm-3 placeholder\" align=\"center\" >\n"
                + "     <label>Nombre :</label>"
                + "     <input type=text id=\"nombre\" class=\"ln form-control\" onchange=saltodepinvi()></div>\n"
                + "<div class=\"col-4 col-sm-3 placeholder\">\n"
                + "     <label>Departamento :</label><br><select id=\"depa\" onchange=\"saltoobs()\" class=\"form-control\"><option></option>\n");
        for (int i = 0; i < arr_depa.size(); i++) {
            if (cont == 1) {
                out.print("<option onchange=searchactivo_id() value=" + arr_depa.get(i - 1) + ">" + arr_depa.get(i) + "</option>");
                cont = 0;
            } else {
                cont++;
            }
        }
        out.print("     </select><br></div><br>"
                + "<div class=\"col-4 col-sm-3 placeholder\" style=\"\" align=center>\n"
                + "<label>Visita a:</label><br><input type=text id=\"visita\" class=\"ln form-control\" placeholder='nombre' onchange=saltoasunto()>\n"
                + "</div><br>\n"
                + "<section class= \"col-sm-12 col-sm-10\"><label>Asunto :</label><br><input type=\"text\" class=\"form-control\" id=asunto name=asunto onchange=saltotrans()></section><br>\n"
                + "<section class= \"col-sm-6 offset-sm-3\"><br><label>Tipo de transporte:</label><br><select onchange=saltoinvis(); id=\"transporte\" class=form-control><option></option><option value=SN>SIN TRANSPORTE</option><option value=AUTOMOVIL>AUTOMOVIL</option><option value=BICICLETA>BICICLETA</option><option value=MOTOCICLETA>MOTOCICLETA</option></select></section><br>\n"
                + "<section class= \"col-sm-6 offset-sm-3\"><br><input type=\"button\" class=\"btn btn-success\" value=\"Iniciar I/O\" onclick=\"inicio_io_invi()\" id=bi><br></section><br>\n"
                + "<div id=ensa></div>\n"
                + "</section></main></div>");
    }

}
