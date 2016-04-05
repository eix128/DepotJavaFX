package gui.controller;

import com.google.inject.Inject;
import globals.interfaces.ControllerNetworkInit;
import gui.AutoCompleteComboBoxListener;
import gui.FXPanels;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jpa.CompanyUsersEntity;
import jpa.CustomerEntity;
import jpa.DepotsEntity;
import jpa.ProductsEntity;
import jpa.converters.enums.PriceType;
import jpa.converters.enums.UnitType;
import kernel.actors.DepotManager;
import kernel.actors.SQLServices;
import kernel.events.ConnectionException;
import kernel.network.DBManager;
import org.controlsfx.control.NotificationPane;
import utils.guava.LazyCache;

import javax.persistence.EntityManager;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.System.out;
import static java.time.LocalDate.now;
import static javafx.collections.FXCollections.observableList;
import static jpa.CompanyUsersEntity.getListByCompany;
import static jpa.CustomerEntity.getCustomersList;
import static jpa.DepotsEntity.getDepotNames;
import static jpa.ProductsEntity.getProductsList;

/**
 * Created by kadir.basol on 29.3.2016.
 */
public class ProductInputController implements Initializable {
    @FXML
    RadioButton radioTedarikci;


    @Inject
    private FXPanels fxPanels;
    @Inject
    private DepotManager depotManager;

    @Inject
    private DBManager dbManager;
    @Inject
    private SQLServices sqlServices;
    @FXML
    private StackPane stackPane;
    @FXML
    private Pane mainPanel;
    @FXML
    private TextField textPayment;
    @FXML
    private ToggleGroup companyType;
    @FXML
    private ComboBox<CustomerEntity> comboCustomer;
    @FXML
    private ComboBox<DepotsEntity> comboDepot;
    @FXML
    private ComboBox<ProductsEntity> comboProducts;
    @FXML
    private TextField textUnits;
    @FXML
    private DatePicker datePickerBegin;
    @FXML
    private GridPane textEnterTime;
    @FXML
    private TextField textEnterHour;
    @FXML
    private GridPane comboCustomerWorker;
    @FXML
    private ComboBox<CompanyUsersEntity> comboMusteriCalisani;
    @FXML
    private TextField textExplain;
    @FXML
    private Button buttonExit;
    @FXML
    private Button saveAndPrint;
    @FXML
    private Button buttonEkle;
    private Stage dialog;

    @FXML
    public void onButtonExit(ActionEvent event) {
        dialog.close();
    }

    @FXML
    public void onDigerClicked(ActionEvent event) {

    }

    @FXML
    public void onKaydetPrintWrite(ActionEvent event) {

    }

    @FXML
    public void onMusteriEkle(ActionEvent event) {

    }

    @FXML
    public void onPazarciClicked(ActionEvent event) {

    }

    @FXML
    public void onSahisClicked(ActionEvent event) {

    }

    @FXML
    public void onTedarikciClicked(ActionEvent event) {

    }



    public void refreshData() {
        final LazyCache<List<CustomerEntity>> listLazyCache = getCustomersList();
        final List<CustomerEntity> customerEntities = listLazyCache.get();
        final ObservableList<CustomerEntity> objects = observableList(customerEntities);

        comboCustomer.setItems(objects);

        final LazyCache<List<DepotsEntity>> depotsList = getDepotNames();
        final List<DepotsEntity> depotsEntities = depotsList.get();
        final ObservableList<DepotsEntity> depotsEntities1 = observableList(depotsEntities);
        comboDepot.setItems(depotsEntities1);


        final LazyCache<List<ProductsEntity>> productsList = getProductsList();
        final List<ProductsEntity> productsEntity = productsList.get();
        final ObservableList<ProductsEntity> prodctsLists = observableList(productsEntity);
        comboProducts.setItems(prodctsLists);


        comboMusteriCalisani.setItems(null);

        comboCustomer.valueProperty().addListener(new ChangeListener<CustomerEntity>() {
            @Override
            public void changed(ObservableValue<? extends CustomerEntity> observable, CustomerEntity oldValue, CustomerEntity newValue) {
                if (newValue != null) {
                    final ObservableList<CompanyUsersEntity> companyUsersEntities = observableList(getListByCompany(newValue.getId()));
                    comboMusteriCalisani.setItems(companyUsersEntities);
                }
            }
        });
    }


    @ControllerNetworkInit
    public final void postLoad() {

        /*
            @FXML
    private ComboBox<?> comboCustomer;

    @FXML
    private ComboBox<?> comboDepot;

    @FXML
    private ComboBox<?> comboProducts;

    @FXML
    private ComboBox<?> comboMusteriCalisani;
         */
        refreshData();

    }


    public void setStage(Stage dialog) {
        this.dialog = dialog;
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
//                companyTypes.selectToggle(radioTedarikci);
//                comboCustomer.setValue(null);
//                comboDepot.setValue(null);
//                comboProducts.setValue(null);
//                comboCustomerWorker.set
//
//                textOutAmount
//                textHour
//                textPartiNo
                clearAll();
            }
        });
        refreshData();
        new AutoCompleteComboBoxListener(comboCustomer);
        new AutoCompleteComboBoxListener<DepotsEntity>(comboDepot);
        new AutoCompleteComboBoxListener<CompanyUsersEntity>(comboMusteriCalisani);
        new AutoCompleteComboBoxListener<ProductsEntity>(comboProducts);
        radioTedarikci.setSelected(true);
        datePickerBegin.setValue(now());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new AutoCompleteComboBoxListener(comboCustomer);
        new AutoCompleteComboBoxListener<DepotsEntity>(comboDepot);
        new AutoCompleteComboBoxListener<CompanyUsersEntity>(comboMusteriCalisani);
        new AutoCompleteComboBoxListener<ProductsEntity>(comboProducts);
        radioTedarikci.setSelected(true);
        LocalTime now1 = LocalTime.now();
//        textEnterHour.setText(now1.getHour() + ":" + now1.getMinute());
//        AutoNext.setAutoNext(mainPanel, new AutoNext.Selector() {
//            @Override
//            public boolean getSelected() {
//                return false;
//            }
//        });

        saveAndPrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("Deneme 1 23");
                final EntityManager entityManager = dbManager.get();

                final ProductsEntity product = comboProducts.getValue();
                final CustomerEntity customer = comboCustomer.getValue();
                final DepotsEntity depot = comboDepot.getValue();
                final CompanyUsersEntity value = comboMusteriCalisani.getValue();
                int i = sqlServices.generatePartNo();
                try {
                    depotManager.productIn( stackPane , i ,  customer.getId() ,  value.getId() , product.getId() ,  Long.valueOf(textUnits.getText()) ,
                            UnitType.BOX , depot.getId() , Long.valueOf(textPayment.getText()) , PriceType.TL , textExplain.getText() );
                    clearAll();
                    dialog.close();
                } catch (ConnectionException e) {
                    NotificationPane notificationPane = new NotificationPane( mainPanel );
                    notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                    notificationPane.setText("Veri tabanı yada bağlantı sorunu oluştu");
                    notificationPane.show();
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                } catch (Exception e) {
                    NotificationPane notificationPane = new NotificationPane( mainPanel );
                    notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                    notificationPane.setText("Yazılımsal bir sorun oluştu , lütfen tekrar deneyin.");
                    notificationPane.show();

//                    Notifier.INSTANCE.notify(info);
                    e.printStackTrace();
                }

//                ProcessEntity processEntity = new ProcessEntity( depot.getDepot() , ProcessType.INPUT , customer.getId() ,
//                        Integer.valueOf(textUnits.getText()) , PriceType.TL , textExplain.getText() ,   );
//                entityManager.persist(processEntity);
            }
        });

        datePickerBegin.setValue(now());

        buttonExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clearAll();
                dialog.close();
            }
        });

        buttonExit.setFocusTraversable(false);
//        final LocalTime now1 = LocalTime.now();

        textEnterHour.setText(now1.getHour() + ":" + now1.getMinute());

    }


    public void clearAll() {
        companyType.selectToggle(radioTedarikci);
        comboCustomer.setItems(null);
        comboDepot.setItems(null);
        comboProducts.setValue(null);
        textUnits.setText("");
        datePickerBegin.setValue(LocalDate.now());
        final LocalTime now1 = LocalTime.now();
        textEnterHour.setText(now1.getHour() + ":" + now1.getMinute());
//       comboCustomerWorker.setValue(null);
        comboMusteriCalisani.setValue(null);
        textExplain.setText("");
        textPayment.setText("");
    }
}