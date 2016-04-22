package gui.controller;

import com.google.common.util.concurrent.FutureCallback;
import com.google.inject.Inject;
import com.jcabi.aspects.Loggable;
import globals.interfaces.ControllerNetworkInit;
import gui.AutoCompleteComboBoxListener;
import gui.items.TotalProductsItem;
import gui.jpa.DynamicJPADepots;
import gui.jpa.DynamicJPAFilteredDepots;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import jpa.*;
import jpa.converters.enums.ProcessType;
import kernel.network.DBManager;
import kernel.threads.network.NetworkThread;
import language.LangUtils;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    private TableView<TotalProductsItem> tableDepots;

    @FXML
    private Pane mainPanel;


    private DynamicJPADepots dynamicJPAViewAll;
    private DynamicJPAFilteredDepots dynamicJPAFilteredDepots;

    private SortedList<TotalProductsItem> sortedListViewAll;
    private SortedList<TotalProductsItem> sortedListFilter;


    @FXML
    private TableColumn<TotalProductsItem, String> columnPartNo;

    @FXML
    private TableColumn<TotalProductsItem, String> columnCustomer;

    @FXML
    private TableColumn<TotalProductsItem, String> columnProducts;

    @FXML
    private TableColumn<TotalProductsItem, String> columnDepots;

    @FXML
    private TableColumn<TotalProductsItem, String> columnUnits;


    @Inject
    private LangUtils langUtils;

    @Inject
    private DBManager dbManager;


    @Inject
    private NetworkThread networkThread;


    private Lock lock = new ReentrantLock();

    private AutoCompleteComboBoxListener autoComboCustomers;
    private AutoCompleteComboBoxListener autoComboDepots;
    private AutoCompleteComboBoxListener autoComboProducts;

    public void invalidateAll() {
        if(dynamicJPAFilteredDepots != null)
            dynamicJPAFilteredDepots.clear();
        if(dynamicJPAViewAll != null)
            dynamicJPAViewAll.clear();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                postLoad();
                final boolean visible = tableDepots.getColumns().get(0).isVisible();
                tableDepots.getColumns().get(0).setVisible(false);
                tableDepots.getColumns().get(0).setVisible(visible);
                tableDepots.refresh();
            }
        });
    }

    public synchronized void refreshQuery() {
        try {
            boolean b = lock.tryLock();
            if (!b)
                return;

            DepotsEntity depotsEntity = null;
            ProductsEntity productsEntity = null;
            CustomerEntity customerEntity = null;
            ProcessType processType = null;
            try {
                depotsEntity = comboDepots.getValue();
                productsEntity = comboProducts.getValue();
                customerEntity = comboCustomers.getValue();
            } catch (Exception e) {
                if (depotsEntity == null) {
                    autoComboDepots.selectClosestResultBasedOnTextFieldValue(true, true);
                }
                if (productsEntity == null) {
                    autoComboProducts.selectClosestResultBasedOnTextFieldValue(true, true);
                }
                if (customerEntity == null) {
                    autoComboCustomers.selectClosestResultBasedOnTextFieldValue(true, true);
                }
                refreshTableOnly();
                return;
            }

            dynamicJPAFilteredDepots.filter(depotsEntity, productsEntity, customerEntity);

            if ((depotsEntity == null || depotsEntity.getId() == -1) && (productsEntity == null || productsEntity.getId() == -1) && (customerEntity == null || customerEntity.getId() == -1)) {
                sortedListViewAll = new SortedList<TotalProductsItem>(dynamicJPAViewAll, new Comparator<TotalProductsItem>() {
                    @Override
                    public int compare(TotalProductsItem o1, TotalProductsItem o2) {
                        return o1.compareTo(o2);
                    }
                });
                sortedListViewAll.comparatorProperty().bind(tableDepots.comparatorProperty());
                tableDepots.setItems(sortedListViewAll);
            } else {
                sortedListFilter = new SortedList<TotalProductsItem>(dynamicJPAFilteredDepots, new Comparator<TotalProductsItem>() {
                    @Override
                    public int compare(TotalProductsItem o1, TotalProductsItem o2) {
                        return o1.compareTo(o2);
                    }
                });
                sortedListFilter.comparatorProperty().bind(tableDepots.comparatorProperty());
                tableDepots.setItems(sortedListFilter);
            }
            refreshTableOnly();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    private void refreshTableOnly() {
        tableDepots.refresh();
    }

//    @Loggable(Loggable.INFO)
    @ControllerNetworkInit
    public final void postLoad() {
        try {
            System.out.println("PostLoad called");
            final List<ProductsEntity> productsEntities = ProductsEntity.getProductsList().get();
            if(productsEntities.size() > 0) {
                ArrayList<ProductsEntity> arrayList = new ArrayList(productsEntities.size() + 1);
                arrayList.add(new ProductsEntity());
                arrayList.addAll(productsEntities);
                final ObservableList<ProductsEntity> objects = FXCollections.observableList(arrayList);
                comboProducts.setItems(objects);
            } else {
                comboProducts.setItems(null);
            }

            List<CustomerEntity> customerEntities = CustomerEntity.getCustomersList().get();
            if(customerEntities.size() > 0) {
                ArrayList<CustomerEntity> arrayList = new ArrayList(customerEntities.size() + 1);
                arrayList.add(new CustomerEntity());
                arrayList.addAll(customerEntities);
                final ObservableList<CustomerEntity> objects = FXCollections.observableList(arrayList);
                comboCustomers.setItems(objects);
            } else {
                comboCustomers.setItems(null);
            }

            List<DepotsEntity> depotsEntity = DepotsEntity.getDepotNames().get();
            if(depotsEntity.size() > 0) {
                ArrayList<DepotsEntity> arrayList = new ArrayList(depotsEntity.size() + 1);
                arrayList.add(new DepotsEntity());
                arrayList.addAll(depotsEntity);
                final ObservableList<DepotsEntity> objects = FXCollections.observableList(arrayList);
                comboDepots.setItems(objects);
            } else {
                comboDepots.setItems(null);
            }
            final EntityManager entityManager = dbManager.get();
/*
        stackPane
                comboProducts
        comboDepots
                comboCustomers*/

            dynamicJPAViewAll = new DynamicJPADepots(langUtils, entityManager);
            dynamicJPAFilteredDepots = new DynamicJPAFilteredDepots(langUtils, entityManager);

            SortedList<TotalProductsItem> sortedList = new SortedList<TotalProductsItem>(
                    dynamicJPAViewAll, new Comparator<TotalProductsItem>() {
                @Override
                public int compare(TotalProductsItem o1, TotalProductsItem o2) {
                    return o1.compareTo(o2);
                }
            });
//            sortedList.getSource().setAll(dynamicJPAViewAll);
            sortedList.comparatorProperty().bind(tableDepots.comparatorProperty());

            columnPartNo.setCellValueFactory(new PropertyValueFactory<TotalProductsItem, String>("partiNo"));
            columnCustomer.setCellValueFactory(new PropertyValueFactory<TotalProductsItem, String>("customerName"));
            columnProducts.setCellValueFactory(new PropertyValueFactory<TotalProductsItem, String>("productName"));
            columnDepots.setCellValueFactory(new PropertyValueFactory<TotalProductsItem, String>("depotName"));
            columnUnits.setCellValueFactory(new PropertyValueFactory<TotalProductsItem, String>("mevcut"));

            tableDepots.setItems(sortedList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void onButtonClear(ActionEvent event) {
        comboCustomers.getSelectionModel().select(0);
        comboDepots.getSelectionModel().select(0);
        comboProducts.getSelectionModel().select(0);
    }

    @FXML
    public void onButtonPrint(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        mainPanel.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) ->
        {
            if (event.getCode() == KeyCode.ENTER) {
                KeyEvent newEvent
                        = new KeyEvent(
                        null,
                        null,
                        KeyEvent.KEY_PRESSED,
                        "",
                        "\t",
                        KeyCode.TAB,
                        event.isShiftDown(),
                        event.isControlDown(),
                        event.isAltDown(),
                        event.isMetaDown()
                );

                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });


        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                networkThread.aSyncOperation(stackPane, new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                refreshQuery();
                            }
                        });
                        return true;
                    }
                }, new FutureCallback<Object>() {
                    @Override
                    public void onSuccess(Object result) {

                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        };
        comboProducts.setOnAction(eventHandler);
        comboCustomers.setOnAction(eventHandler);
        comboDepots.setOnAction(eventHandler);


        autoComboCustomers = new AutoCompleteComboBoxListener(comboCustomers);
        autoComboDepots = new AutoCompleteComboBoxListener(comboDepots);
        autoComboProducts = new AutoCompleteComboBoxListener(comboProducts);
    }
}
