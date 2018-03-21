package Modelo;
// Generated Feb 27, 2018 4:37:28 PM by Hibernate Tools 4.3.1



/**
 * Usuario generated by hbm2java
 */
public class Usuario  implements java.io.Serializable {


     private int claveUsuario;
     private Departamento departamento;
     private TipoUsuario tipoUsuario;
     private String nombre;
     private String codigo;
     private Character statuo;
     private String pass;
     private String empresa;

    public Usuario() {
    }

	
    public Usuario(int claveUsuario) {
        this.claveUsuario = claveUsuario;
    }
    public Usuario(int claveUsuario, Departamento departamento, TipoUsuario tipoUsuario, String nombre, String codigo, Character statuo, String pass, String empresa) {
       this.claveUsuario = claveUsuario;
       this.departamento = departamento;
       this.tipoUsuario = tipoUsuario;
       this.nombre = nombre;
       this.codigo = codigo;
       this.statuo = statuo;
       this.pass = pass;
       this.empresa = empresa;
    }
   
    public int getClaveUsuario() {
        return this.claveUsuario;
    }
    
    public void setClaveUsuario(int claveUsuario) {
        this.claveUsuario = claveUsuario;
    }
    public Departamento getDepartamento() {
        return this.departamento;
    }
    
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    public TipoUsuario getTipoUsuario() {
        return this.tipoUsuario;
    }
    
    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCodigo() {
        return this.codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Character getStatuo() {
        return this.statuo;
    }
    
    public void setStatuo(Character statuo) {
        this.statuo = statuo;
    }
    public String getPass() {
        return this.pass;
    }
    
    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getEmpresa() {
        return this.empresa;
    }
    
    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }




}


