import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
//hola
@ManagedBean(name = "loginController")
@SessionScoped

public class LoginController extends Servicio implements Serializable  {

    private String usuario;
    private String contrasenna;
    
    
    public void ingresarAdmin() {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = super.getConexion().createStatement();
            String sql = "SELECT * FROM usuarios";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String tempPassword = rs.getNString("password");     
                String tempID = rs.getString("nickname"); 
                String tempTipo = rs.getString("tipo"); 
                
                if (usuario.equals(tempID) && contrasenna.equals(tempPassword) && tempTipo.equals("Admin")){
                    this.redireccionar("/faces/paginaadmin.xhtml");
                    break; 
                }
           }
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contrasena u Usuario incorrectos", "Message Content."));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }

    }

    public void redireccionar(String ruta) {
        HttpServletRequest request;
        try {
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            FacesContext.getCurrentInstance().getExternalContext().redirect(request.getContextPath() + ruta);
        } catch (Exception e) {

        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LoginController other = (LoginController) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.contrasenna, other.contrasenna)) {
            return false;
        }
        return true;
        
        
    }

    @Override
    public String toString() {
        return "LoginController{" + "usuario=" + usuario + ", contrasenna=" + contrasenna + '}';
    }

    public void ingresar() {
        
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = super.getConexion().createStatement();
            String sql = "SELECT * FROM usuarios";
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String tempPassword = rs.getNString("password");     
                String tempID = rs.getString("nickname"); 
                String tempTipo = rs.getString("tipo"); 
                
                if (usuario.equals(tempID) && contrasenna.equals(tempPassword) && tempTipo.equals("NoAdmin")){
                    this.redireccionar("/faces/paginacliente.xhtml");
                    break; 
                }
           }
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contrasena u Usuario incorrectos", "Message Content."));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }
    }



    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

}