package gui.controller;

import globals.interfaces.ControllerNetworkInit;
import gui.AutoCompleteComboBoxListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import jpa.CompanyUsersEntity;
import jpa.CustomerEntity;
import jpa.DepotsEntity;
import jpa.ProductsEntity;
import utils.guava.LazyCache;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepotsController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private ComboBox<ProductsEntity> comboProducts;

    @FXML
    private ComboBox<DepotsEntity> comboDepots;

    @FXML
    private ComboBox<CustomerEntity> comboCustomers;

    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonPrint;

    @FXML
    private TableView<?> tableDepots;


    @ControllerNetworkInit
    public final void postLoad() {
        final List<ProductsEntity> productsEntities = ProductsEntity.getProductsList().get();
        final ObservableList<ProductsEntity> productsEntities1 = FXCollections.observableList(productsEntities);
        comboProducts.setItems(productsEntities1);

        final ObservableList<DepotsEntity> depotsEntities = FXCollections.observableList(DepotsEntity.getDepotNames().get());
        comboDepots.setItems(depotsEntities);

        final ObservableList<CustomerEntity> customerEntities = FXCollections.observableList(CustomerEntity.getCustomersList().get());
        comboCustomers.setItems(customerEntities);

    }


    @FXML
    public void onButtonClear(ActionEvent event) {

    }

    @FXML
    public void onButtonPrint(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new AutoCompleteComboBoxListener( comboCustomers );
        new AutoCompleteComboBoxListener<DepotsEntity>( comboDepots  );
        new AutoCompleteComboBoxListener<ProductsEntity>( comboProducts );
    }
}
