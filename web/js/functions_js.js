/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function searchuser(){
                var codigo =$('#codigo').val();
                var motivo ="";
                var depa="";
                var uso="check";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,codigo:codigo,motivo:motivo,depa_cambio:depa},
                    url: '../Getfields',
                    success: function (result) {
                        $('#respuesta').html(result);
                        //document.getElementById("proveedor").focus;
                    }
                });
                uso="foto"
                $.ajax({
                    type: 'post',
                    data: {uso: uso,codigo:codigo},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        if(result!=""){
                             $('#main_foto').html("<div id=\"\" class=\"container-fluid \" align=\"center\">\n" +
"                <div class=\"btn\" >\n" +
"                    <div class=\"\" align=\"center\" >\n" +                  
"                        <br><img class=imagens1 src=\"../imageuser/"+result+"\">\n" +
"                    </div>\n" +
"                </div>  \n" +
"            </div>");
                        }
                       
                    }
                });
            }
function searchuser1(){
                var codigo =$('#codigo').val();
                var motivo =$('#motivo').val();
                var depa =$('#depa_maq').val();
                var uso="check";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,codigo:codigo,motivo:motivo,depa_cambio:depa},
                    url: '../Getfields',
                    success: function (result) {
                        $('#respuesta').html(result);
                        document.getElementById("codigo").focus();
                        //document.getElementById("proveedor").focus;
                    }
                });
            }
//

function autosearch_provs(){
                var proveedor =$('#search_prov').val();
                var prov_activo=$('input:radio[name=gg]:checked').val();
                var uso="buscar";
               $.ajax({
                    type: 'post',
                    data: {uso: uso,proveedor:proveedor,activo:prov_activo},
                    url: '../Getdata_prov',
                    success: function (result) {
                        $('#body_table').html(result);
                    }
                });                
}
function delete_prov(id){
                var uso="delete";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov',
                    success: function (result) {
                        // document.form2.uso.focus();
                        alert(result);
                       
                      }
                });                
}
function mod_prov(id){
                var uso="modificar";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov',
                    success: function (result) {
                        // document.form2.uso.focus();
                        //alert("-"+result+"-");
                        $('#body_table').html(result);
                        document.getElementById("codigo").value = "";
                    }
                });                
}
function down_prov(id){
                var uso="baja";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov',
                    success: function (result) {
                        alert(result);
                        document.getElementById('search_prov').focus();                    
                    }
                });                
}
function up_prov(id){
                var uso="alta";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov',
                    success: function (result) {
                        alert(result);
                        document.getElementById('search_prov').focus();                    
                    }
                });                
}
function searchactivo_id(){
                var pactivo =$('#proveedor').val();
                var uso="buscar";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,activo:pactivo},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        $('#p_activos').html(result);
                        //obtener valor del selected document.getElementById('proveedor').value;
                        var auto=document.getElementById('proveedor');
                        var selected=auto.options[auto.selectedIndex].text;
                        document.getElementById('proveed').value=selected;
                        document.getElementById("proveed").focus();
                    }
                });
            }
function saltotrans(){
    document.getElementById("transporte").focus();
}
function saltoasunto(){
    document.getElementById("asunto").focus();
} 
function saltodepa(){
    document.getElementById("depa").focus();
}            
function saltopa(){
    document.getElementById("p_activos_n").focus();
}
function saltobi(){
    document.getElementById("bi").focus();
}
function saltoprocedencia(){
    document.getElementById("nombre").focus();
}
function ida(){
    document.getElementById("observacion").focus();
}
function saltop(){
    var auto=document.getElementById('autorizada');
    var selected=auto.options[auto.selectedIndex].text;
    document.getElementById('p_activos_n').value=selected;
    document.getElementById('depa').focus();
}            
function reporte_last_usuario(){
                var f=new Date();
                var mes = f.getMonth()+1
                var f1 =f.getDate() + "-" + mes+ "-" + f.getFullYear();
                var f2 =f1;
                var nombre ="";
                var area ="";
                var dep = "";
                var tipo= "";
                var transporte= "";
                var destino= "";       
                //alert(f1);
    location='reporte_movs_usu.jsp?f1='+f1+"&f2="+f2+"&nombre="+nombre+"&area="+area+"&depa="+dep+"&tipo="+tipo+"&transporte="+transporte+"&destino="+destino;
}
function nuevo_prov_data(){
                var pactivo =$('#prov_new').val();
                var uso="nuevo";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,activo:pactivo},
                    url: '../Getdata_prov',
                    success: function (result) {
                        $('#section_prov').html(result);
                        $('#table_prov').html("");
                        llenabarra_np();
                        document.getElementById('prov_new1').focus();
                    }
                });
            }
function nuevo_prov(){
                var prov =$('#prov_new1').val();
                var uso="nuevo_row";
                if(prov==""){
                    document.getElementById('prov_new1').focus();
                    $('#response_nprov').html("<br>El campo de proveedores no debe ir vacio");
                }else{
                 $.ajax({
                    type: 'post',
                    data: {uso: uso,prov:prov},
                    url: '../Getdata_prov',
                    success: function (result) {
                        $('#response_nprov').html(result);
                        document.getElementById('prov_new1').value="";
                    }
                });   
                }  
}

function field_autosearch_activos(){
    var uso="getfield_activos";
    $.ajax({
                    type: 'post',
                    data: {uso: uso},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        $('#main_prov').html(result);
                        document.getElementById('search_prov').focus();
                        llenabarra_pa();
                    }
                });
}
function autosearch_provs_activos(){ //autobusqueda de proveedores activos
                var proveedor =$('#search_prov').val();
                var prov_activo=$('input:radio[name=gg]:checked').val();
                var uso="buscar_activo";
               $.ajax({
                    type: 'post',
                    data: {uso: uso,proveedor:proveedor,activo:prov_activo},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        $('#body_table').html(result);
                    }
                });
                }                
function nuevo_prov_adata(){
                var pactivo =$('#prov_new').val();
                var uso="nuevo";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,activo:pactivo},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        $('#section_prov').html(result);
                        $('#table_prov').html("");
                        llenabarra_npa();
                        document.getElementById('prov_new1').focus();
                    }
                });
            }
function nuevo_prov_autorizado(){
                var prov =$('#prov_new1').val();
                var id_prov=$('#proveedor').val();
                var uso="nuevo_row";
                if(prov=="" || id_prov==""){
                    document.getElementById('prov_new1').focus();
                    $('#response_nprov').html("<br>El campo personal del proveedor y proveedor no debe ir vacio");
                }else{
                 $.ajax({
                    type: 'post',
                    data: {uso: uso,prov:prov,id_prov:id_prov},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        $('#response_nprov').html(result);
                        llenabarra_npa();
                        document.getElementById('prov_new1').value="";
                    }
                });   
                }  
}
function delete_prov_a(id){
                var uso="delete";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        // document.form2.uso.focus();
                        alert(result);
                       
                      }
                });                
}
function mod_prov_a(id){
                var uso="modificar";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        // document.form2.uso.focus();
                        //alert("-"+result+"-");
                        $('#body_table').html(result);
                        document.getElementById("codigo").value = "";
                    }
                });                
}
function down_prov_a(id){
                var uso="baja";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        alert(result);
                        document.getElementById('search_prov').focus();                    
                    }
                });                
}
function up_prov_a(id){
                var uso="altas";
                $.ajax({
                    type: 'post',
                    data: {uso: uso,id:id},
                    url: '../Getdata_prov_personal',
                    success: function (result) {
                        alert(result);
                        document.getElementById('search_prov').focus();                    
                    }
                });                
}

function inicio_io(){
    var prov=$('#proveed').val();
    var autorizada=$('#p_activos_n').val();
    var depa=$('#depa').val();
    var area=$('#area').val();
    var codigo=$('#codigo').val();
    var obs=$('#observacion').val();
    var asunto="";
    var transporte=$('#transporte').val();
    var uso="proveedor";
    if(prov=="" || autorizada=="" || depa=="" || area=="" || obs =="" || transporte==""){
        alert("no pueden ir campos en blanco favor de rectificarlo");
        document.getElementById('proveedor').focus();
    }else{
    $.ajax({
                    type: 'post',
                    data: {uso: uso,prov:prov,autorizada:autorizada,depa:depa,area:area,codigo:codigo,observacion:obs,asunto:asunto,transporte:transporte},
                    url: '../Movimientos',
                    success: function (result) {
                        $('#respuesta').html(result);
                        document.getElementById('codigo').value="";
                        document.getElementById('codigo').focus();
                    }
                });
            }
}

function inicio_io_invi(){
    var depa=$('#depa').val();
    var nombre=$('#nombre').val();
    var obs=$('#visita').val();
    var area=$('#area').val();
    var codigo=$('#codigo').val();
    var procedencia=$('#procedencia').val();
    var asunto=$('#asunto').val();
    var transporte=$('#transporte').val();
    var uso="invitado";
    if(nombre=="" || obs=="" || area=="" || procedencia=="" || depa=="" || transporte==""){
        alert("no pueden ir campos en blanco favor de rectificarlo");
    }else{
    $.ajax({
                    type: 'post',
                    data: {uso: uso,nombre:nombre,area:area,codigo:codigo,obs:obs,depa:depa,procedencia:procedencia,asunto:asunto,transporte:transporte},
                    url: '../Movimientos',
                    success: function (result) {
                        $('#respuesta').html(result);
                        document.getElementById('codigo').value="";
                        document.getElementById('codigo').focus();
                    }
                });
            }
}
function saltoinvi(){
    document.getElementById('binvi').focus();
}
function saltoinvis(){
    document.getElementById('bi').focus();
}
function saltoob(){
    document.getElementById('observaciones').focus();
}
function saltoobs(){
    document.getElementById('visita').focus();
}
function saltodepinvi(){
    document.getElementById('depa').focus();
}
function llenabarra_np(){
    document.getElementById('barra_nav').innerHTML="<li class=\"nav-item\">\n" +
"              <a class=\"nav-link\" href=\"\">Busqueda </a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link active\" onclick=nuevo_prov_data()>Nuevo Proveedor</a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link btn\" onclick=field_autosearch_activos()>Busca Autorizados</a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link btn\" onclick=nuevo_prov_adata()>Nuevo Aurotizado</a>\n" +
"            </li>\n";
}
function llenabarra_pa(){
        document.getElementById('barra_nav').innerHTML="<li class=\"nav-item\">\n" +
"              <a class=\"nav-link\" href=\"\">Busqueda </a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link btn\" onclick=nuevo_prov_data()>Nuevo Proveedor</a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link active\" onclick=field_autosearch_activos()>Busca Autorizados</a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link btn\" onclick=nuevo_prov_adata()>Nuevo Aurotizado</a>\n" +
"            </li>\n";
}
function llenabarra_npa(){
        document.getElementById('barra_nav').innerHTML="<li class=\"nav-item\">\n" +
"              <a class=\"nav-link\" href=\"\">Busqueda </a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link btn\" onclick=nuevo_prov_data()>Nuevo Proveedor</a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link \" onclick=field_autosearch_activos()>Busca Autorizados</a>\n" +
"            </li>\n" +
"            <li class=\"nav-item\">\n" +
"              <a class=\"nav-link active\" onclick=nuevo_prov_adata()>Nuevo Aurotizado</a>\n" +
"            </li>\n";
}








/* 
 * 
  catch(ClassNotFoundException c){
            System.out.println(c);
        }catch(IOException i){
        System.out.println(i);
        }catch (SQLException ex) {
            Logger.getLogger(Getfields.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception e){
        System.out.println(e);
        }
 * 
 * 
 * */