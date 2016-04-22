package gui.controller;

import com.google.common.util.concurrent.FutureCallback;
import com.google.inject.Inject;
import formatters.*;
import formatters.TextFormatter;
import globals.interfaces.ControllerNetworkInit;
import gui.AutoCompleteComboBoxListener;
import gui.FXPanels;
import gui.items.TotalProductsItem;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import jpa.*;
import jpa.converters.enums.CustomerType;
import jpa.converters.enums.UnitType;
import kernel.actors.DepotManager;
import kernel.actors.SQLServices;
import kernel.events.ConnectionException;
import kernel.network.DBManager;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.spreadsheet.StringConverterWithFormat;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import utils.ThermalPrinter;
import utils.guava.LazyCache;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.print.PrintException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ProductOutputController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private RadioButton radioTedarikci;


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

    @FXML
    private TextField textExplain;

    @FXML
    private RadioButton radioSahis;


    @FXML
    private RadioButton radioArtisan;

    @FXML
    private RadioButton radioOther;


    private Stage dialog;


    @Inject
    private DBManager dbManager;

    @Inject
    private SQLServices sqlServices;

    @Inject
    private DepotManager depotManager;

    private TextFormatter textureFormatter;

    @Inject
    private TextFormatterFactory textFormatterFactory;

    @Inject
    private FXPanels fxPanels;

    private DepotsController depotsController;


    private Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>> collectionCallback;


    @FXML
    public void customerAdd(ActionEvent event) {

    }


    @FXML
    public void onExit(ActionEvent event) {

    }


    @FXML
    public void onSaveAndPrint(ActionEvent event) {
        final ProductsEntity product = (ProductsEntity) ComboBoxUtils.getValue(comboProducts);
        final CustomerEntity customer = (CustomerEntity) ComboBoxUtils.getValue(comboCustomer);
        final DepotsEntity depot = (DepotsEntity) ComboBoxUtils.getValue(comboDepot);
        final CompanyUsersEntity value = (CompanyUsersEntity) ComboBoxUtils.getValue(comboCustomerWorker);
        final int partNo = sqlServices.generatePartNo();
        final Long uUnits = Long.valueOf(textOutAmount.getText());
        final String depotName = depot.getDepotName();

        try {
            depotManager.productOut(stackPane, partNo, customer.getId(), value.getCompanyId(), product.getId(),
                    uUnits, depot.getId(), 0, textExplain.getText(),
                    new FutureCallback<Object>() {
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
/*                                    HashMap<String, String> stringHashMap = new HashMap<>();
                                    stringHashMap.put("companyName", companyName);
                                    stringHashMap.put("Date", dateNow);
                                    stringHashMap.put("VoucherNo", fisNo);
                                    stringHashMap.put("PartNo", partiNo);
                                    stringHashMap.put("product", productName);
                                    stringHashMap.put("Units", units);
                                    stringHashMap.put("unitType", "Kutu");
                                    stringHashMap.put("DepotName", depot.getDepotName());*/

                                    HashMap<String, String> stringHashMap = new HashMap<>();
                                    stringHashMap.put("companyName", companyName);
                                    stringHashMap.put("Date", dateNow);
                                    stringHashMap.put("VoucherNo", fisNo);
                                    stringHashMap.put("PartNo", partiNo);
                                    stringHashMap.put("product", productName);
                                    stringHashMap.put("Units", uUnits.toString());
                                    stringHashMap.put("unitType", UnitType.BOX.toString());
                                    stringHashMap.put("DepotName", depotName);
                                    stringHashMap.put("TeslimEden", value.getUserName() + " " + value.getUserSurname());
                                    stringHashMap.put("TeslimAlan", customer.getName() + " " + customer.getSurname());
//                                                stringHashMap.put("msg", message == null ? "" : message);
                                    String process = textureFormatter.process(stringHashMap);

//                                            resources.getString("inputPanel.companyName") + companyName +
//                                                    resources.getString("input") + ""
                                    try {
                                        ThermalPrinter.PrintString(process);
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    } catch (PrintException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFailure(Throwable throwable) {

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


    }


    @ControllerNetworkInit
    public final void postLoad() {
        refreshData();

        final EntityManager entityManager = dbManager.get();
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        javax.persistence.criteria.CriteriaQuery<PartNoIndexEntity> cq2 = cb.createQuery(PartNoIndexEntity.class);
        final javax.persistence.criteria.Root<PartNoIndexEntity> from = cq2.from(PartNoIndexEntity.class);
        final javax.persistence.criteria.CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final javax.persistence.criteria.Path<String> city1 = from.get("partNo");


        CriteriaQuery<PartNoIndexEntity> all = cq2.select(from);


        collectionCallback = new Callback<AutoCompletionBinding.ISuggestionRequest, Collection<String>>() {
            @Override
            public Collection call(AutoCompletionBinding.ISuggestionRequest param) {
                final String userText = param.getUserText();
                ArrayList<String> liste = new ArrayList<String>();

                final CustomerEntity customerEntity = comboCustomer.getValue();
                final DepotsEntity depotsEntity = comboDepot.getValue();
                final ProductsEntity productsEntity = comboProducts.getValue();

//                final javax.persistence.criteria.Path<Integer> pathDepotId = from.get("depotId");
                final javax.persistence.criteria.Path<Integer> pathCompanyId = from.get("companyid");
                final javax.persistence.criteria.Path<Integer> pathProductId = from.get("productid");
                final javax.persistence.criteria.Path<String> pathStrLike = from.get("partNo");

                ArrayList<Triplet<Expression, ? , Predicate>> arrayList = new ArrayList<Triplet<Expression, ? , Predicate>>();
//                if (depotsEntity != null && depotsEntity.getId() != -1) {
//                    final Predicate equal = criteriaBuilder.equal(pathDepotId, depotsEntity.getId());
//                    arrayList.add(new Triplet<>(pathDepotId, depotsEntity.getDepotId(), equal));
//                }
                final Predicate predicateLike;
                if(pathStrLike != null) {
                    predicateLike = criteriaBuilder.like(pathStrLike, userText+"%" );
                } else
                    return null;

                if (customerEntity != null && customerEntity.getId() != -1) {
                    final Predicate equal2 = criteriaBuilder.equal(pathCompanyId, customerEntity.getId());
                    arrayList.add(new Triplet<>(pathCompanyId, customerEntity.getId(), equal2));
                }

                if (productsEntity != null && productsEntity.getId() != -1) {
                    final Predicate equal3 = criteriaBuilder.equal(pathProductId, productsEntity.getId());
                    arrayList.add(new Triplet<>(pathProductId, productsEntity.getId(), equal3));
                }


                Expression expression = null;
                for (int i = 0; i < arrayList.size(); i++) {
                    final Triplet<Expression, ? , Predicate> objects = arrayList.get(i);
                    final Expression value0 = objects.getValue0();
                    Object value1 = objects.getValue1();

                    if (expression != null)
                        expression = criteriaBuilder.and(criteriaBuilder.equal(value0, value1), expression);
                    else
                        expression = criteriaBuilder.equal(value0, value1);
                }
                Predicate and = criteriaBuilder.and(predicateLike, expression);
//        final Predicate and = criteriaBuilder.and(equal, equal1);
//        final Predicate and1 = criteriaBuilder.and(and, equal2);
//
//        final Predicate and2 = criteriaBuilder.and(and1, equal3);

                final CriteriaQuery<PartNoIndexEntity> where = all.where(and);
                final TypedQuery<PartNoIndexEntity> query1 = entityManager.createQuery(where);
                final List<PartNoIndexEntity> resultList = query1.getResultList();
                final List<String> collect = resultList.parallelStream().map(new Function<PartNoIndexEntity, String>() {
                    @Override
                    public String apply(PartNoIndexEntity partNoIndexEntity) {
                        return partNoIndexEntity.getPartNo();
                    }
                }).collect(Collectors.toList());
                //return null;
//                System.out.println(userText);
//                final javax.persistence.criteria.Predicate like = criteriaBuilder.like(city1, userText+"%");
//                final CriteriaQuery<PartNoIndexEntity> where = cq2.where(like);
//                final TypedQuery<PartNoIndexEntity> query1 = entityManager.createQuery(where);
//                final List<PartNoIndexEntity> resultList = query1.setMaxResults(8).getResultList();



//                liste.clear();
                List<String> obje = resultList.parallelStream().map(new Function<PartNoIndexEntity, String>() {
                    @Override
                    public String apply(PartNoIndexEntity cityEntity) {
                        return cityEntity.getPartNo();
                    }
                }).collect(Collectors.toList());
                obje.stream().forEachOrdered(liste::add);
                return liste;
            }
        };
        AutoCompletionBinding filterField2 = TextFields.bindAutoCompletion(textPartiNo, collectionCallback);
    }


    public void refreshData() {
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


        comboCustomer.getSelectionModel().select(0);
        comboDepot.getSelectionModel().select(0);
        comboProducts.getSelectionModel().select(0);


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
                            comboCustomerWorker.setItems(companyUsersEntities);
                            comboCustomerWorker.setDisable(false);
                            comboCustomerWorker.getSelectionModel().select(0);
                        }
                    } else {
                        comboCustomerWorker.setItems(null);
                        comboCustomerWorker.setDisable(true);
                    }
                }
            }
        });

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                CustomerEntity selectedItem = comboCustomer.getSelectionModel().getSelectedItem();
                if(selectedItem != null) {
                    if(selectedItem.getId() != -1) {
                        final ObservableList<CompanyUsersEntity> companyUsersEntities = FXCollections.observableList(CompanyUsersEntity.getListByCompany(selectedItem.getId()));
                        comboCustomerWorker.setItems(companyUsersEntities);
                        comboCustomerWorker.setDisable(false);
                        comboCustomerWorker.getSelectionModel().select(0);
                    }
                }

                comboCustomer.getSelectionModel().select(0);
                comboDepot.getSelectionModel().select(0);
                comboProducts.getSelectionModel().select(0);
            }
        });
    }


    public synchronized boolean refreshUsers() {
        Toggle selectedToggle = companyTypes.getSelectedToggle();
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
            comboCustomerWorker.setDisable(true);
            comboCustomerWorker.setItems(null);
        } else {
            comboCustomerWorker.setDisable(false);
        }


//        managerCustomers(selectedToggle);
        if (!selected) {
            comboCustomerWorker.setDisable(true);
            comboCustomerWorker.setItems(null);
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
            comboCustomerWorker.setDisable(false);
            comboCustomerWorker.setItems(customerEntities);
            if (comboCustomerWorker.getSelectionModel() != null)
                try {
                    comboCustomerWorker.getSelectionModel().select(0);
                } catch (NullPointerException e) {
                }
            return true;
        } else {
            comboCustomerWorker.setDisable(true);
            comboCustomerWorker.setItems(null);
        }
        return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            textureFormatter = textFormatterFactory.createTextureFormatter("/texts/OutputVoucher.ftl");
        } catch (IOException e) {
            dialog.close();
            e.printStackTrace();
            return;
        }

        Pair<Pane, Object> stockView = fxPanels.getByName("StockView");
        depotsController = (DepotsController) stockView.getValue1();
        new AutoCompleteComboBoxListener(comboCustomer);
        new AutoCompleteComboBoxListener(comboDepot);
        new AutoCompleteComboBoxListener(comboCustomerWorker);
        new AutoCompleteComboBoxListener(comboProducts);
        final LocalDate now = LocalDate.now();
        final LocalTime now1 = LocalTime.now();
        datePickerOutDate.setValue(now);
        String resultHour = ((now1.getHour() <= 9) ? "0" + now1.getHour() : now1.getHour()) + ":" + ((now1.getMinute() <= 9) ? "0" + now1.getMinute() : now1.getMinute());
        textHour.setText(resultHour);



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

        comboCustomerWorker.setConverter(new StringConverterWithFormat<CompanyUsersEntity>() {
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

        companyTypes = new ToggleGroup();
        radioTedarikci.setToggleGroup(companyTypes);
        radioSahis.setToggleGroup(companyTypes);
        radioArtisan.setToggleGroup(companyTypes);
        radioOther.setToggleGroup(companyTypes);

        companyTypes.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                final ToggleGroup toggleGroup = newValue.getToggleGroup();
                final Toggle selectedToggle = toggleGroup.getSelectedToggle();
//                final Object userData = toggleGroup.getSelectedToggle().getUserData();
//                System.out.println(selectedToggle);
                System.out.println("ABCD");
                boolean companies = refreshUsers();
                onToggleSelected(selectedToggle, companies);
            }
        });

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
                final EntityManager entityManager = dbManager.get();
            }
        });

        buttonExit.setFocusTraversable(false);
        radioTedarikci.setSelected(true);


    }

    public void setStage(Stage dialog) {
        this.dialog = dialog;
        dialog.setAlwaysOnTop(true);

        companyTypes.selectToggle(radioTedarikci);

        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                companyTypes.selectToggle(radioTedarikci);
                comboCustomer.setValue(null);
                comboDepot.setValue(null);
                comboProducts.setValue(null);
                comboCustomerWorker.setValue(null);
                boolean b = refreshUsers();
                onToggleSelected(radioTedarikci, b);

                textOutAmount.setText("");
                textHour.setText("");
                textPartiNo.setText("");

            }
        });
        refreshData();
        boolean b = refreshUsers();
        onToggleSelected(radioTedarikci, b);
        final LocalTime now1 = LocalTime.now();
        String resultHour = ((now1.getHour() <= 9) ? "0" + now1.getHour() : now1.getHour()) + ":" + ((now1.getMinute() <= 9) ? "0" + now1.getMinute() : now1.getMinute());
        textHour.setText(resultHour);
    }


    public void clearAll() {
        companyTypes.selectToggle(radioTedarikci);
        comboCustomer.setItems(null);
        comboDepot.setItems(null);
        comboProducts.setValue(null);
        textOutAmount.setText("");
        datePickerOutDate.setValue(LocalDate.now());
        final LocalTime now1 = LocalTime.now();
        String resultHour = ((now1.getHour() <= 9) ? "0" + now1.getHour() : now1.getHour()) + ":" + ((now1.getMinute() <= 9) ? "0" + now1.getMinute() : now1.getMinute());
        textHour.setText(resultHour);
//       comboCustomerWorker.setValue(null);
        comboCustomerWorker.setValue(null);
//        textExplain.setText("");
//        textPayment.setText("");
    }
}
