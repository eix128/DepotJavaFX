package gui.controller;

import globals.interfaces.ControllerNetworkInit;
import gui.AutoCompleteComboBoxListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jpa.CompanyUsersEntity;
import jpa.CustomerEntity;
import jpa.DepotsEntity;
import jpa.ProductsEntity;
import utils.guava.LazyCache;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

public class ProductOutputController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private RadioButton radioTedarikci;

    @FXML
    private ToggleGroup companyTypes;

    @FXML
    private ComboBox<CustomerEntity> comboCustomer;

    @FXML
    private ComboBox<DepotsEntity> comboDepot;

    @FXML
    private ComboBox<ProductsEntity> comboProducts;

    @FXML
    private TextField textOutAmount;

    @FXML
    private DatePicker datePickerOutDate;

    @FXML
    private TextField textHour;

    @FXML
    private ComboBox<CompanyUsersEntity> comboCustomerWorker;

    @FXML
    private Button buttonExit;

    @FXML
    private Pane mainPanel;

    @FXML
    private Button buttonEkle;

    @FXML
    private TextField textPartiNo;


    private Stage dialog;


    @FXML
    public void customerAdd(ActionEvent event) {

    }


    @FXML
    public void onExit(ActionEvent event) {

    }


    @FXML
    public void onSaveAndPrint(ActionEvent event) {
        System.out.println("Deneme 1 2 3");
    }


    @ControllerNetworkInit
    public final void postLoad() {
        System.out.println("Product Input Cagrildi");
        final LazyCache<List<CustomerEntity>> listLazyCache = CustomerEntity.getCustomersList();
        final List<CustomerEntity> customerEntities = listLazyCache.get();
        final ObservableList<CustomerEntity> objects = FXCollections.observableList(customerEntities);

        comboCustomer.setItems(objects);

        final LazyCache<List<DepotsEntity>> depotsList = DepotsEntity.getDepotNames();
        final List<DepotsEntity> depotsEntities = depotsList.get();
        final ObservableList<DepotsEntity> depotsEntities1 = FXCollections.observableList(depotsEntities);
        comboDepot.setItems(depotsEntities1);


        final LazyCache<List<ProductsEntity>> productsList = ProductsEntity.getProductsList();
        final List<ProductsEntity> productsEntity = productsList.get();
        final ObservableList<ProductsEntity> prodctsLists = FXCollections.observableList(productsEntity);
        comboProducts.setItems(prodctsLists);


        comboCustomerWorker.setItems(null);

        comboCustomer.valueProperty().addListener(new ChangeListener<CustomerEntity>() {
            @Override
            public void changed(ObservableValue<? extends CustomerEntity> observable, CustomerEntity oldValue, CustomerEntity newValue) {
                if (newValue != null) {
                    final ObservableList<CompanyUsersEntity> companyUsersEntities = FXCollections.observableList(CompanyUsersEntity.getListByCompany(newValue.getId()));
                    comboCustomerWorker.setItems(companyUsersEntities);
                }
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new AutoCompleteComboBoxListener(comboCustomer);
        new AutoCompleteComboBoxListener<DepotsEntity>(comboDepot);
        new AutoCompleteComboBoxListener<CompanyUsersEntity>(comboCustomerWorker);
        new AutoCompleteComboBoxListener<ProductsEntity>(comboProducts);
        final LocalDate now = LocalDate.now();
        final LocalTime now1 = LocalTime.now();
        datePickerOutDate.setValue(now);
        textHour.setText(now1.getHour() + ":" + now1.getMinute());

        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                companyTypes.selectToggle(radioTedarikci);
                comboCustomer.setValue(null);
                comboDepot.setValue(null);
                comboProducts.setValue(null);
                comboCustomerWorker.setValue(null);

                textOutAmount.setText("");
                textHour.setText("");
                dialog.close();
            }
        });

        buttonEkle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        buttonExit.setFocusTraversable(false);
        radioTedarikci.setSelected(true);
    }

    public void setStage(Stage dialog) {
        this.dialog = dialog;
        dialog.setAlwaysOnTop(true);
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                companyTypes.selectToggle(radioTedarikci);
                comboCustomer.setValue(null);
                comboDepot.setValue(null);
                comboProducts.setValue(null);
                comboCustomerWorker.setValue(null);

                textOutAmount.setText("");
                textHour.setText("");
                textPartiNo.setText("");

            }
        });
    }
}
