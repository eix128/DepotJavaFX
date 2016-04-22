package gui.controller;

import com.google.common.util.concurrent.FutureCallback;
import com.google.inject.Inject;
import formatters.*;
import formatters.TextFormatter;
import globals.interfaces.ControllerNetworkInit;
import gui.AutoCompleteComboBoxListener;
import gui.FXPanels;
import gui.utils.ComboBoxUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import jpa.converters.enums.CustomerType;
import jpa.converters.enums.PriceType;
import jpa.converters.enums.UnitType;
import kernel.actors.DepotManager;
import kernel.actors.SQLServices;
import kernel.events.ConnectionException;
import kernel.network.DBManager;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.spreadsheet.StringConverterWithFormat;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import utils.ThermalPrinter;
import utils.guava.LazyCache;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import static java.time.LocalDate.now;
import static javafx.collections.FXCollections.observableList;
import static jpa.CompanyUsersEntity.getListByCompany;
import static jpa.DepotsEntity.getDepotNames;
import static jpa.ProductsEntity.getProductsList;
import static jpa.converters.enums.CustomerType.PERSONAL;

/**
 * Created by kadir.basol on 29.3.2016.
 */
public class ProductInputController implements Initializable {

    @FXML
    private RadioButton radioTedarikci;

    @FXML
    private RadioButton radioSahis;

    @FXML
    private RadioButton radioArtisan;

    @FXML
    private RadioButton radioOther;


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
    private ComboBox<CompanyUsersEntity> comboMusteriCalisani;
    @FXML
    private TextField textExplain;
    @FXML
    private Button buttonExit;
    @FXML
    private Button saveAndPrint;
    @FXML
    private Button buttonEkle;


    @Inject
    private TextFormatterFactory textFormatterFactory;

    private Stage dialog;
    private TextFormatter textureFormatter;

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


    private DepotsController depotsController;


    public void refreshData() {
        final LazyCache<List<CustomerEntity>> listLazyCache = CustomerEntity.getCustomersList();
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
                    CustomerEntity value = comboCustomer.getValue();
                    CustomerType type = value.getType();
                    if (type != null) {
                        final Toggle toggle = convertCustomerTypeToToggle(type);
                        boolean b = managerCustomers(toggle, newValue.getId());
                        if (b) {
                            final ObservableList<CompanyUsersEntity> companyUsersEntities = FXCollections.observableList(CompanyUsersEntity.getListByCompany(newValue.getId()));
                            comboMusteriCalisani.setItems(companyUsersEntities);
                            comboMusteriCalisani.setDisable(false);
                            comboMusteriCalisani.getSelectionModel().select(0);
                        }
                    } else {
                        comboMusteriCalisani.setItems(null);
                        comboMusteriCalisani.setDisable(true);
                    }
                }
            }
        });

        comboCustomer.setConverter(new StringConverterWithFormat<CustomerEntity>() {
            @Override
            public String toString(CustomerEntity object) {
                if (object != null)
                    return object.getTitle();
                else
                    return "";
            }

            @Override
            public CustomerEntity fromString(String string) {
                return CustomerEntity.getCustomersByTitle(string);
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                comboCustomer.getSelectionModel().select(0);

                CustomerEntity selectedItem = comboCustomer.getSelectionModel().getSelectedItem();
                if(selectedItem != null) {
                    if(selectedItem.getId() != -1) {
                        final ObservableList<CompanyUsersEntity> companyUsersEntities = FXCollections.observableList(CompanyUsersEntity.getListByCompany(selectedItem.getId()));
                        comboMusteriCalisani.setItems(companyUsersEntities);
                        comboMusteriCalisani.setDisable(false);
                        comboMusteriCalisani.getSelectionModel().select(0);
                    }
                }
                comboDepot.getSelectionModel().select(0);
                comboProducts.getSelectionModel().select(0);
            }
        });
    }


    @ControllerNetworkInit
    public final void postLoad() {
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
                boolean b = refreshUsers();
                final Toggle selectedToggle = companyType.getSelectedToggle();
                onToggleSelected(selectedToggle, b);
            }
        });
        refreshData();
//        new AutoCompleteComboBoxListener(comboCustomer);
//        new AutoCompleteComboBoxListener(comboDepot);
//        new AutoCompleteComboBoxListener(comboMusteriCalisani);
//        new AutoCompleteComboBoxListener(comboProducts);
        boolean b = refreshUsers();
        final Toggle selectedToggle = companyType.getSelectedToggle();
        onToggleSelected(selectedToggle, b);
        radioTedarikci.setSelected(true);
        datePickerBegin.setValue(now());
        onToggleSelect(radioTedarikci);
    }


    public synchronized boolean refreshUsers() {
        Toggle selectedToggle = companyType.getSelectedToggle();
        CustomerType customerType = CustomerType.OTHER;
        if (selectedToggle == radioTedarikci) {
            customerType = CustomerType.SUPPLIER;
        } else if (selectedToggle == radioSahis) {
            customerType = CustomerType.PERSONAL;
        } else if (selectedToggle == radioArtisan) {
            customerType = CustomerType.ARTISAN;
        } else if (selectedToggle == radioOther) {
            customerType = CustomerType.OTHER;
        }
        ConcurrentHashMap<CustomerType, List<CustomerEntity>> customersMapByCompanyType = CustomerEntity.getCustomersMapByCompanyType();
        List<CustomerEntity> customerEntities = customersMapByCompanyType.get(customerType);
        if (customerEntities != null && customerEntities.size() > 0) {
            ObservableList<CustomerEntity> objects = FXCollections.observableArrayList(customerEntities);
            comboCustomer.setDisable(false);
            comboCustomer.setItems(objects);
            SingleSelectionModel<CustomerEntity> selectionModel = comboCustomer.getSelectionModel();
            if (selectionModel != null && comboCustomer.getItems() != null && comboCustomer.getItems().size() > 0) {
                boolean value = comboCustomer.getItems().get(0) != null;
                if (value) {
                    selectionModel.select(0);
                }
            }
            return true;
        } else {
            comboCustomer.setDisable(true);
            comboCustomer.setItems(null);
            return false;
        }
    }


    public void onToggleSelected(Toggle selectedToggle, boolean selected) {
        if (selectedToggle == radioSahis) {
            comboMusteriCalisani.setDisable(true);
            comboMusteriCalisani.setItems(null);
        } else {
            comboMusteriCalisani.setDisable(false);
        }


//        managerCustomers(selectedToggle);
        if (!selected) {
            comboMusteriCalisani.setDisable(true);
            comboMusteriCalisani.setItems(null);
        }
    }


    public final Toggle convertCustomerTypeToToggle(CustomerType customerTypes) {
        switch (customerTypes) {
            case ARTISAN:
                return radioArtisan;
            case SUPPLIER:
                return radioTedarikci;
            case OTHER:
                return radioOther;
            case PERSONAL:
                return radioSahis;
        }
        return null;
    }


    public final boolean managerCustomers(Toggle selectedToggle, int id) {
        List<CompanyUsersEntity> listByCompany = CompanyUsersEntity.getListByCompany(id);
        if (listByCompany != null && listByCompany.size() > 0) {
            final ObservableList<CompanyUsersEntity> customerEntities = FXCollections.observableList(listByCompany);
            comboMusteriCalisani.setDisable(false);
            comboMusteriCalisani.setItems(customerEntities);
            if (comboMusteriCalisani.getSelectionModel() != null)
                try {
                    comboMusteriCalisani.getSelectionModel().select(0);
                } catch (NullPointerException e) {
                }
            return true;
        } else {
            comboMusteriCalisani.setDisable(true);
            comboMusteriCalisani.setItems(null);
        }
        return false;
    }


    public void onToggleSelect(Toggle selectedToggle) {
        if (selectedToggle == radioSahis) {
            comboMusteriCalisani.setDisable(true);
        } else {
            comboMusteriCalisani.setDisable(false);
        }

        CustomerType customerType = CustomerType.OTHER;
        if (selectedToggle == radioTedarikci) {
            customerType = CustomerType.SUPPLIER;
        } else if (selectedToggle == radioSahis) {
            customerType = PERSONAL;
        } else if (selectedToggle == radioArtisan) {
            customerType = CustomerType.ARTISAN;
        } else if (selectedToggle == radioOther) {
            customerType = CustomerType.OTHER;
        }
        final ConcurrentHashMap<CustomerType, List<CustomerEntity>> customersMapByCompanyType = CustomerEntity.getCustomersMapByCompanyType();
        final List<CustomerEntity> customerEntity = customersMapByCompanyType.get(customerType);
        if (customerEntity != null) {
            final ObservableList<CustomerEntity> customerEntities = FXCollections.observableList(customerEntity);
            comboCustomer.setDisable(false);
            comboCustomer.setItems(customerEntities);
        } else {
            comboCustomer.setDisable(true);
            comboCustomer.setItems(null);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            textureFormatter = textFormatterFactory.createTextureFormatter("/texts/InputVoucher.ftl");
        } catch (IOException e) {
            dialog.close();
            e.printStackTrace();
            return;
        }

        new AutoCompleteComboBoxListener(comboCustomer);
        new AutoCompleteComboBoxListener(comboDepot);
        new AutoCompleteComboBoxListener(comboMusteriCalisani);
        new AutoCompleteComboBoxListener(comboProducts);

        Pair<Pane, Object> stockView = fxPanels.getByName("StockView");
        Object value1 = stockView.getValue1();
        depotsController = (DepotsController) value1;

        comboCustomer.setConverter(new StringConverterWithFormat<CustomerEntity>() {
            @Override
            public String toString(CustomerEntity object) {
                if (object != null)
                    return object.toString();
                else
                    return "";
            }

            @Override
            public CustomerEntity fromString(String string) {
                return CustomerEntity.getCustomersByTitle(string);
            }
        });

        comboDepot.setConverter(new StringConverterWithFormat<DepotsEntity>() {
            @Override
            public String toString(DepotsEntity object) {
                if (object != null)
                    return object.toString();
                else
                    return "";
            }

            @Override
            public DepotsEntity fromString(String string) {
                ConcurrentHashMap<String, DepotsEntity> depotsByNameMap = DepotsEntity.getDepotsByNameMap();
                return depotsByNameMap.get(string);
            }
        });

        comboMusteriCalisani.setConverter(new StringConverterWithFormat<CompanyUsersEntity>() {
            @Override
            public String toString(CompanyUsersEntity object) {
                if (object != null)
                    return object.toString();
                else
                    return "";
            }

            @Override
            public CompanyUsersEntity fromString(String string) {
                ConcurrentHashMap<String, CompanyUsersEntity> companyUsersMap = CompanyUsersEntity.getCompanyUsersByName();
                return companyUsersMap.get(string);
            }
        });

        comboProducts.setConverter(new StringConverterWithFormat<ProductsEntity>() {
            @Override
            public String toString(ProductsEntity object) {
                if (object != null)
                    return object.toString();
                else
                    return "";
            }

            @Override
            public ProductsEntity fromString(String string) {
                ConcurrentHashMap<String, ProductsEntity> productsMapByName = ProductsEntity.getProductsMapByName();
                return productsMapByName.get(string);
            }
        });

        radioTedarikci.setSelected(true);
        LocalTime now1 = LocalTime.now();
//        textEnterHour.setText(now1.getHour() + ":" + now1.getMinute());
//        AutoNext.setAutoNext(mainPanel, new AutoNext.Selector() {
//            @Override
//            public boolean getSelected() {
//                return false;
//            }
//        });

        companyType.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                final ToggleGroup toggleGroup = newValue.getToggleGroup();
                final Toggle selectedToggle = toggleGroup.getSelectedToggle();
//                final Object userData = toggleGroup.getSelectedToggle().getUserData();
                boolean companies = refreshUsers();
                onToggleSelected(selectedToggle, companies);
            }
        });


        saveAndPrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("Deneme 1 23");
                final EntityManager entityManager = dbManager.get();
                Object value2 = comboProducts.getValue();
                final ProductsEntity product = (ProductsEntity) ComboBoxUtils.getValue(comboProducts);
                final CustomerEntity customer = (CustomerEntity) ComboBoxUtils.getValue(comboCustomer);
                final DepotsEntity depot = (DepotsEntity) ComboBoxUtils.getValue(comboDepot);
                final CompanyUsersEntity value = (CompanyUsersEntity) ComboBoxUtils.getValue(comboMusteriCalisani);
                Long uUnits = Long.valueOf(textUnits.getText());
                String text = textPayment.getText();
                int partNo = sqlServices.generatePartNo();
                try {
                    String userName = sqlServices.getUserName();
                    depotManager.productIn(stackPane, partNo, customer.getId(), value.getId(), product.getId(), uUnits,
                            UnitType.BOX, depot.getId(), Long.valueOf(text), PriceType.TL, textExplain.getText()
                            , new FutureCallback<Object>() {
                                @Override
                                public void onSuccess(Object o) {
                                    /**
                                     * Triplet with Success , Message , processId
                                     * */

                                    Triplet<Integer, String, Long> triplet = (Triplet<Integer, String, Long>) o;
                                    final Integer success = triplet.getValue0();
                                    final String message = triplet.getValue1();
                                    final Long processId = triplet.getValue2();

                                    if (success == 1) {
                                        depotsController.invalidateAll();
                                    } else {
                                        return;
                                    }

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            String companyName = customer.getTitle();
                                            String dateNow = sqlServices.getTime();
                                            String fisNo = processId.toString();
                                            String partiNo = String.valueOf(partNo);

                                            String productName = product.getProductName();
                                            String depotName = depot.getDepotName();

//                                            companyName Date VoucherNo PartNo product Units  unitType DepotName

//                                            resources.getString("inputPanel.companyName") + companyName +
//                                                    resources.getString("input") + ""
                                            try {
//            companyName Date VoucherNo PartNo product Units  unitType DepotName
                                                HashMap<String, String> stringHashMap = new HashMap<>();
                                                stringHashMap.put("companyName", companyName);
                                                stringHashMap.put("Date", dateNow);
                                                stringHashMap.put("VoucherNo", fisNo);
                                                stringHashMap.put("PartNo", partiNo);
                                                stringHashMap.put("product", productName);
                                                stringHashMap.put("Units", uUnits.toString());
                                                stringHashMap.put("unitType", "Kutu");
                                                stringHashMap.put("DepotName", depotName);
                                                stringHashMap.put("TeslimEden", value.getUserName() + " " + value.getUserSurname());
                                                stringHashMap.put("TeslimAlan", userName);
//                                                stringHashMap.put("msg", message == null ? "" : message);
                                                String process = textureFormatter.process(stringHashMap);


                                                ThermalPrinter.PrintString(process);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onFailure(Throwable throwable) {
                                    System.out.println(throwable);
                                }
                            });
                    clearAll();
                    dialog.close();
                } catch (ConnectionException e) {
                    NotificationPane notificationPane = new NotificationPane(mainPanel);
                    notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                    notificationPane.setText("Veri tabanı yada bağlantı sorunu oluştu");
                    notificationPane.show();
                    e.printStackTrace();
                } catch (Exception e) {
                    NotificationPane notificationPane = new NotificationPane(mainPanel);
                    notificationPane.getStyleClass().add(NotificationPane.STYLE_CLASS_DARK);
                    notificationPane.setText("Yazılımsal bir sorun oluştu , lütfen tekrar deneyin.");
                    notificationPane.show();


//                    Notifier.INSTANCE.notify(info);
                    e.printStackTrace();
                }
                final Pair<Pane, Object> mainPanel = fxPanels.getByName("MainPanel");
                StackPane borderPane = (StackPane) mainPanel.getValue0();
                final Object value1 = mainPanel.getValue1();
                MainPanelController mainPanelController = (MainPanelController) value1;
                mainPanelController.refreshTable();


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
        String resultHour = ((now1.getHour() <= 9) ? "0" + now1.getHour() : now1.getHour()) + ":" + ((now1.getMinute() <= 9) ? "0" + now1.getMinute() : now1.getMinute());
        textEnterHour.setText( resultHour );

        onToggleSelect(radioTedarikci);

    }


    public void clearAll() {
        companyType.selectToggle(radioTedarikci);
        comboCustomer.setItems(null);
        comboDepot.setItems(null);
        comboProducts.setValue(null);
        textUnits.setText("");
        datePickerBegin.setValue(LocalDate.now());
        final LocalTime now1 = LocalTime.now();
        String resultHour = ((now1.getHour() <= 9) ? "0" + now1.getHour() : now1.getHour()) + ":" + ((now1.getMinute() <= 9) ? "0" + now1.getMinute() : now1.getMinute());
        textEnterHour.setText( resultHour );
//       comboCustomerWorker.setValue(null);
        comboMusteriCalisani.setValue(null);
        textExplain.setText("");
        textPayment.setText("");
    }
}