package ma.emsi.tpcustomermeliane.jsf;

import jakarta.ejb.EJB;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;
import ma.emsi.tpcustomermeliane.Customer;
import ma.emsi.tpcustomermeliane.DiscountCode;
import ma.emsi.tpcustomermeliane.ejb.CustomerManager;
import ma.emsi.tpcustomermeliane.ejb.DiscountCodeManager;

/**
 * Backing bean pour la page customerDetails.xhtml.
 */
@Named(value = "customerDetailsBean")
@ViewScoped

public class CustomerDetailsBean implements Serializable {

    private int idCustomer;
    private Customer customer;
    private List<DiscountCode> discountCodes;

    @EJB
    private CustomerManager customerManager;
    @EJB
    private DiscountCodeManager discountCodeManager;

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    /**
     * Retourne les détails du client courant (contenu dans l'attribut customer
     * de cette classe).
     */
    public Customer getDetails() {
        return customer;
    }

    /**
     * Action handler - met à jour dans la base de données les données du client
     * contenu dans la variable d'instance customer.
     *
     * @return la prochaine page à afficher, celle qui affiche la liste des
     * clients.
     */
    public String update() {
        // Modifie la base de données.
        // Il faut affecter à customer (sera expliqué dans le cours).
        customer = customerManager.update(customer);
        return "customerList";
    }

    public void loadCustomer() {
        this.customer = customerManager.getCustomer(idCustomer);
    }

    /**
     * Retourne la liste de tous les DiscountCode.
     *
     * @return
     */
    public List<DiscountCode> getDiscountCodes() {
        discountCodes = discountCodeManager.getAllDiscountCodes();
        return discountCodes;
    }

    /**
     * getter pour la propriété discountCodeConverter.
     */
    public Converter<DiscountCode> getDiscountCodeConverter() {
        return new Converter<DiscountCode>() {
            /**
             * Convertit une String en DiscountCode.
             *
             * @param value valeur à convertir
             */
            @Override
            public DiscountCode getAsObject(FacesContext context, UIComponent component, String value) {
                return discountCodeManager.findById(value);
            }

            /**
             * Convertit un DiscountCode en String.
             *
             * @param value valeur à convertir
             */
            @Override
            public String getAsString(FacesContext context, UIComponent component, DiscountCode value) {
                return value.getDiscountCode();
            }
        };
    }
}
