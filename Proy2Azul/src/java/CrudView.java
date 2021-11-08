
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

@ManagedBean(name = "crudView")
@ViewScoped
public class CrudView implements Serializable {

    private List<Product> products;

    private Product selectedProduct;

    private List<Product> selectedProducts;

    @ManagedProperty("#{servicioProducto}")
    private ServicioProducto servicioProducto;

    @PostConstruct
    public void init() {
        this.products = this.servicioProducto.demeTodosLosProductos();
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public List<Product> getSelectedProducts() {
        return selectedProducts;
    }

    public void setSelectedProducts(List<Product> selectedProducts) {
        this.selectedProducts = selectedProducts;
    }


    public void openNew() {
        this.selectedProduct = new Product();
        this.selectedProduct.setName("");
    }

    public void saveProduct() {
        if (this.selectedProduct.getCode() == null) {
            this.servicioProducto.guardar(this.selectedProduct);

            this.init();
            this.products.add(this.selectedProduct);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto agregado"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto actualizado"));
        }

        PrimeFaces.current().executeScript("PF('manageProductDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public void deleteProduct() {
        this.servicioProducto.eliminar(this.selectedProduct);
        this.init();
        this.selectedProduct = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto eliminado"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedProducts()) {
            int size = this.selectedProducts.size();
            return size > 1 ? size + " productos seleccionados" : "1 producto seleccionado";
        }

        return "Eliminar";
    }
   
    public void rdmCodigo() {//genera un codigo aleatorio
        String letras = "A1B2C3D4zE5Fy6G7xH8wI9vuJtK0sLr1qM2pN3Oo4nPm5lQ6kR7jSi8hT9gUf0Ve1X2dY3cZb4a";
        String numeros = "1234567890";
        int tamanno = 9;
        String rdmString = "";

        char[] texto = new char[tamanno];//un array para meter un caracter en cada casilla
        Random rdm = new Random();//un objeto de tipo random

        for (int i = 0; i < tamanno; i++) {//un for que recorre el array hasta que sea 5
            texto[i] = letras.charAt(rdm.nextInt(letras.length()));
        }

        for (int i = 0; i < texto.length; i++) {
            rdmString += texto[i];
        }

    }
   
    public boolean hasSelectedProducts() {
        return this.selectedProducts != null && !this.selectedProducts.isEmpty();
    }

    public void deleteSelectedProducts() {
        this.products.removeAll(this.selectedProducts);
        this.selectedProducts = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Products Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        PrimeFaces.current().executeScript("PF('dtProducts').clearFilters()");
    }

    public ServicioProducto getServicioProducto() {
        return servicioProducto;
    }

    public void setServicioProducto(ServicioProducto servicioProducto) {
        this.servicioProducto = servicioProducto;
    }

}
