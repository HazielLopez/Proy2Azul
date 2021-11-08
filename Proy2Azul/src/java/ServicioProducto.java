
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(name = "servicioProducto")
@ApplicationScoped

public class ServicioProducto extends Servicio {

    public List<Product> demeTodosLosProductos() {
        Statement stmt = null;
        ResultSet rs = null;
        List<Product> listaRetorno = new ArrayList<>();

        try {

            stmt = super.getConexion().createStatement();
            String sql = "SELECT * FROM products";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setCode(rs.getString("code"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setImage(rs.getString("image"));
                p.setPrice(rs.getInt("price"));
                p.setCategory(rs.getString("category"));
                p.setQuantity(rs.getInt("quantity"));
                p.setInventoryStatus(InventoryStatus.valueOf(rs.getString("inventoryStatus")));
                p.setRating(rs.getInt("rating"));
                listaRetorno.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarResultSet(rs);
            cerrarStatement(stmt);
            desconectar();
        }
        return listaRetorno;
    }

    public void guardar(Product product) {//agrega un nuevo producto a la bd       
        PreparedStatement st = null;
        try {
            this.conectar();
            
            st = this.getConexion().prepareStatement("INSERT INTO products (id,code,name,description,image,price,category,quantity,inventoryStatus,rating) VALUES (?,?,?,?,?,?,?,?,?,?)");
            st.setInt(1, product.getId());
            st.setString(2, product.getCode());
            st.setString(3, product.getName());
            st.setString(4, product.getDescription());
            st.setString(5, product.getImage());
            st.setDouble(6, product.getPrice());
            st.setString(7, product.getCategory());
            st.setInt(8, product.getQuantity());
            st.setString(9, InventoryStatus.valueOf("INSTOCK").getName());
            st.setInt(10, product.getRating());

            st.executeUpdate();
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            cerrarPreparedStatement(st);
            desconectar();
        }

    }
    

    public void eliminar(Product product) {
        PreparedStatement st = null;
        try {
            this.conectar();
            st = this.getConexion().prepareStatement("DELETE FROM products WHERE id = ?");
            st.setInt(1, product.getId());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrarPreparedStatement(st);
            desconectar();
        }

    }
}
