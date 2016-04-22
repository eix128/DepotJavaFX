package gui.controller;

import com.google.common.util.concurrent.FutureCallback;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import globals.GlobalDatas;
import globals.interfaces.ControllerNetworkInit;
import globals.interfaces.PostInit;
import gui.AutoCompleteComboBoxListener;
import gui.FXPanels;
import gui.GUITimeFormatter;
import gui.PairValueCell;
import gui.jpa.DynamicFilterJPA;
import gui.jpa.DynamicJPA;
import gui.jpa.ProcessItem;
import gui.utils.ComboBoxUtils;
import gui.utils.FxUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Cell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import jpa.CustomerEntity;
import jpa.DepotsEntity;
import jpa.ProcessEntity;
import jpa.ProductsEntity;
import jpa.converters.enums.ProcessType;
import jpa.converters.enums.UnitType;
import kernel.actors.SQLServices;
import kernel.network.DBManager;
import kernel.threads.network.NetworkThread;
import language.LangUtils;
import main.gui.ComboList;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.javatuples.Pair;
import utils.ThermalPrinter;
import utils.guava.LazyCache;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.print.PrintException;
import java.io.*;
import java.net.URL;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by kadir.basol on 25.3.2016.
 */
public class MainPanelController implements Initializable {

    @FXML
    private StackPane stackPane;

    @FXML
    private ComboBox<ProcessType> comboTransactionType;


    @FXML
    private ComboBox<CustomerEntity> comboCustomer;

    @FXML
    private ComboBox<DepotsEntity> comboDepo;

    @FXML
    private ComboBox<ProductsEntity> comboProducts;


    @FXML
    private Button buttonClear;

    @FXML
    private Button buttonRefresh;

    @FXML
    private TableView<ProcessItem> tableView;

    @FXML
    private TextField labelTotalBox;


    @FXML
    private DatePicker dateEnterDate;


    @Inject
    private DBManager dbManager;


    @FXML
    private Pane mainPanel;

    @FXML
    private MenuItem menuProductIn;

    @FXML
    private MenuItem productOut;

    @FXML
    private MenuItem depoStockClicked;


    @FXML
    private TableColumn<ProcessEntity, String> columnPartiNo;

    @FXML
    private TableColumn<ProcessEntity, String> columnCustomer;
    @FXML
    private TableColumn<ProcessEntity, String> columnDepot;
    @FXML
    private TableColumn<ProcessEntity, String> columnProductName;
    @FXML
    private TableColumn<ProcessEntity, String> columnUnits;
    @FXML
    private TableColumn<ProcessEntity, String> columnDate;
    @FXML
    private TableColumn<ProcessEntity, LocalTime> columnTime;
    @FXML
    private TableColumn< String , ProcessType> columnType;
    @FXML
    private TableColumn<ProcessEntity, String> columnCompanyWorker;
    @FXML
    private TableColumn<ProcessEntity, String> columnPrice;
    @FXML
    private TableColumn<ProcessEntity, String> columnText;


    @Inject
    private NetworkThread networkThread;


    @Inject
    private FXPanels fxPanels;


    @Inject
    private LangUtils langUtils;


    @Inject
    private ComboList comboList;

    @Inject
    private SQLServices sqlServices;



    private AutoCompleteComboBoxListener    autoComboCustomer;
    private AutoCompleteComboBoxListener    autoComboTransactionType;
    private AutoCompleteComboBoxListener    autoComboDepo;
    private AutoCompleteComboBoxListener    autoComboProducts;
    private DynamicJPA                      dynamicJPA;
    private DynamicFilterJPA                dynamicFilterJPA;

    private SortedList<ProcessItem>         sortedListViewAll;
    private SortedList<ProcessItem>         sortedListFilter;


    @PostInit
    public static void init() {

    }

    @FXML
    public void onDepoStockClicked(ActionEvent event) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(new Group(
                fxPanels.getByName("StockView").getValue0()
        ));
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner( fxPanels.getPrimaryStage() );
        dialog.show();
    }


    @FXML
    public void onProductInput(ActionEvent event) {
//        DialogPane dialog = new DialogPane();
//        dialog.setContent( fxPanels.getByName("ProductInView") );
//        dialog.setPrefSize( 980 , 554 );
//        dialog.setVisible(true);

        Stage dialog = new Stage();
        final Pair<Pane, Object> productInView = fxPanels.getByName("ProductInView");
        dialog.initStyle(StageStyle.UTILITY);
        final ProductInputController value1 = (ProductInputController) productInView.getValue1();
        Scene scene = new Scene(new Group(
                productInView.getValue0()
        ));
        dialog.setTitle("Ürün Giriş Paneli");
        dialog.setScene(scene);
        value1.setStage(dialog);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner( fxPanels.getPrimaryStage() );
        dialog.show();
    }


    @FXML
    public void onProductOutput(ActionEvent event) {
//        DialogPane dialog = new DialogPane();
//        dialog.setContent( fxPanels.getByName("ProductOutView") );
//        dialog.setPrefSize( 980 , 554 );
//        dialog.setVisible(true);

        Stage dialog = new Stage();
        final Pair<Pane, Object> productOutView1 = fxPanels.getByName("ProductOutView");
        final ProductOutputController productOutView = (ProductOutputController) productOutView1.getValue1();
        final Pane value0 = productOutView1.getValue0();

        dialog.initStyle(StageStyle.UTILITY);
        productOutView.setStage(dialog);
        Scene scene = new Scene(new Group(value0));
        dialog.setTitle("Ürün Çıkış Paneli");
        dialog.setScene(scene);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner( fxPanels.getPrimaryStage() );

        dialog.show();
    }


    @FXML
    public void onSortClicked(ActionEvent event) {

    }

    private Lock lock = new ReentrantLock();

    public void refreshQuery() {
        try {
            boolean b = lock.tryLock();
            if (!b)
                return;


            DepotsEntity depotsEntity = null;
            ProductsEntity productsEntity = null;
            CustomerEntity customerEntity = null;
            ProcessType processType = null;
            try {
//                depotsEntity = (DepotsEntity) ComboBoxUtils.getValue(comboDepo);
                depotsEntity = (DepotsEntity) ComboBoxUtils.getValue(comboDepo);
                productsEntity = (ProductsEntity) ComboBoxUtils.getValue(comboProducts);
//                productsEntity = (ProductsEntity) ComboBoxUtils.getValue(comboProducts);
//                customerEntity = (CustomerEntity) ComboBoxUtils.getValue(comboCustomer);
                customerEntity = (CustomerEntity) ComboBoxUtils.getValue(comboCustomer);
//                processType = comboTransactionType.getValue();
                processType = (ProcessType) ComboBoxUtils.getValue(comboTransactionType);
            } catch (Exception e) {
                if (depotsEntity == null) {
                    autoComboDepo.selectClosestResultBasedOnTextFieldValue(true, true);
                }
                if (productsEntity == null) {
                    autoComboProducts.selectClosestResultBasedOnTextFieldValue(true, true);
                }
                if (customerEntity == null) {
                    autoComboCustomer.selectClosestResultBasedOnTextFieldValue(true, true);
                }
                if (processType == null) {
                    autoComboTransactionType.selectClosestResultBasedOnTextFieldValue(true, true);
                }
                refreshTableOnly();
                return;
            }

            if (processType == ProcessType.UNKNOWN) {
                processType = ProcessType.UNKNOWN;
            }
            dynamicFilterJPA.filter(depotsEntity, productsEntity, customerEntity, processType);

            if ((depotsEntity == null || depotsEntity.getId() == -1) && (productsEntity == null || productsEntity.getId() == -1) && (customerEntity == null || customerEntity.getId() == -1) && (processType == null || processType == ProcessType.ALL)) {
                sortedListViewAll = new SortedList<ProcessItem>(dynamicJPA, new Comparator<ProcessItem>() {
                    @Override
                    public int compare(ProcessItem o1, ProcessItem o2) {
                        return o1.compareTo(o2);
                    }
                });
                sortedListViewAll.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedListViewAll);

            } else {
                sortedListFilter = new SortedList<ProcessItem>(dynamicFilterJPA, new Comparator<ProcessItem>() {
                    @Override
                    public int compare(ProcessItem o1, ProcessItem o2) {
                        return o1.compareTo(o2);
                    }
                });
                sortedListFilter.comparatorProperty().bind(tableView.comparatorProperty());
                tableView.setItems(sortedListFilter);

            }
            refreshTableOnly();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        CustomerEntity value1 = comboCustomer.getValue();
        ProductsEntity value2 = comboProducts.getValue();
        DepotsEntity value = comboDepo.getValue();
        Integer integer = (value == null || value.getId() == -1) ? null : value.getDepotId();
        if(integer == null)
            integer = 0;
        long totalUnitsBy = sqlServices.getTotalUnitsBy(
                UnitType.BOX, ProcessType.ALL,
                integer,
                value1 == null ? null : value1.getId(),
                value2 == null ? null : value2.getId());
        labelTotalBox.setText(String.valueOf(totalUnitsBy));
//        String s = String.valueOf();
//        labelTotalBox.setText(s);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        autoComboCustomer = new AutoCompleteComboBoxListener(comboCustomer, this);
//        autoComboTransactionType = new AutoCompleteComboBoxListener(comboTransactionType, this);
//        autoComboDepo = new AutoCompleteComboBoxListener(comboDepo, this);
//        autoComboProducts = new AutoCompleteComboBoxListener(comboProducts, this);

        tableView.getStylesheets().add("/css/tableStyle.css");


        comboList.setByName("Main.customer",comboCustomer);
        comboList.setByName("Main.transactionType",comboTransactionType);
        comboList.setByName("Main.depo",comboDepo);
        comboList.setByName("Main.products",comboProducts);

//        EventHandler<KeyEvent> eventHandler1 = new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                System.out.println("Enter pressed");
//                if (event.getCode() == KeyCode.ENTER) {
//                    System.out.println("Enter pressed");
//                    event.consume();
//                } else {
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            refreshQuery();
//                        }
//                    });
//                }
//            }
//        };
//
//        comboCustomer.setOnKeyPressed(eventHandler1);
//        comboTransactionType.setOnKeyPressed(eventHandler1);
//        comboDepo.setOnKeyPressed(eventHandler1);
//        comboProducts.setOnKeyPressed(eventHandler1);
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
        comboCustomer.setOnAction(eventHandler);
        comboTransactionType.setOnAction(eventHandler);
        comboDepo.setOnAction(eventHandler);


//        EventHandler<KeyEvent> eventHandler1 = new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCode() == KeyCode.ENTER)
//                    event.consume();
//
//            }
//        };
//        comboTransactionType.setOnKeyPressed(eventHandler1);
//        comboTransactionType.setOnKeyReleased(eventHandler1);
//        comboDepo.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if(event.getCode() == KeyCode.ENTER)
//                    event.consume();
//
//            }
//        });
//        comboProducts.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if(event.getCode() == KeyCode.ENTER)
//                    event.consume();
//            }
//        });


        columnDepot.setCellValueFactory(new PropertyValueFactory<ProcessEntity, String>("depot"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<ProcessEntity, String>("product"));
        columnCustomer.setCellValueFactory(new PropertyValueFactory<ProcessEntity, String>("company"));
        columnCompanyWorker.setCellValueFactory(new PropertyValueFactory<ProcessEntity, String>("companyUserId"));
        columnType.setCellValueFactory(new PropertyValueFactory<String,ProcessType>("processType"));

//        PairValueCell pairValueCell =
        columnType.setCellFactory(new Callback<TableColumn<String, ProcessType>, TableCell<String, ProcessType>>() {
            @Override
            public TableCell<String,ProcessType> call(TableColumn<String,ProcessType> param) {
                return new PairValueCell();
            }
        });


        columnTime.setCellFactory(new Callback<TableColumn<ProcessEntity, LocalTime>, TableCell<ProcessEntity, LocalTime>>() {

            @Override
            public TableCell<ProcessEntity, LocalTime> call(TableColumn<ProcessEntity, LocalTime> param) {
                return new GUITimeFormatter();
            }
        });

        columnUnits.setCellValueFactory(new PropertyValueFactory<ProcessEntity, String>("units"));
        columnPartiNo.setCellValueFactory(new PropertyValueFactory<ProcessEntity, String>("partNo"));
        columnDate.setCellValueFactory(new PropertyValueFactory<ProcessEntity, String>("actionDate"));
        columnTime.setCellValueFactory(new PropertyValueFactory<ProcessEntity, LocalTime>("actionTime"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<ProcessEntity, String>("price"));
        columnText.setCellValueFactory(new PropertyValueFactory<ProcessEntity, String>("description"));

        ObservableList<ProcessType> strings = FXCollections.observableArrayList( ProcessType.ALL, ProcessType.INPUT, ProcessType.OUTPUT , ProcessType.UNKNOWN );
        comboTransactionType.setItems(strings);
        comboTransactionType.getSelectionModel().select(0);
//        ObservableList<CustomerEntity> objects = FXCollections.observableList( CustomerEntity.getDepotsList().get() );
//        comboCustomer.setItems(objects);


    }


    @ControllerNetworkInit
    public final void postLoad() {
        final LazyCache<List<CustomerEntity>> listLazyCache = CustomerEntity.getCustomersList();
        final List<CustomerEntity> customerEntities = listLazyCache.get();
        if(customerEntities.size() > 0) {
            ArrayList<CustomerEntity> arrayList = new ArrayList(customerEntities.size() + 1);
            arrayList.add(new CustomerEntity());
            arrayList.addAll(customerEntities);
            final ObservableList<CustomerEntity> objects = FXCollections.observableList(arrayList);
            comboCustomer.setItems(objects);
        } else {
            comboCustomer.setItems(null);
        }

        final LazyCache<List<DepotsEntity>> depotsList = DepotsEntity.getDepotNames();
        final List<DepotsEntity> depotsEntities = depotsList.get();
        if(depotsEntities.size() > 0) {
            ArrayList<DepotsEntity> arrayList = new ArrayList(depotsEntities.size() + 1);
            arrayList.add(new DepotsEntity());
            arrayList.addAll(depotsEntities);
            final ObservableList<DepotsEntity> objects = FXCollections.observableList(arrayList);
            objects.addListener(new ListChangeListener<DepotsEntity>() {
                @Override
                public void onChanged(Change<? extends DepotsEntity> c) {
                    comboDepo.setVisibleRowCount( c.getList().size() );
                }
            });
            comboDepo.setItems(objects);
        } else {
            comboDepo.setItems(null);
        }

        final LazyCache<List<ProductsEntity>> productsList = ProductsEntity.getProductsList();
        final List<ProductsEntity> productsEntity = productsList.get();
        if(productsEntity.size() > 0) {
            ArrayList<ProductsEntity> arrayList = new ArrayList(productsEntity.size() + 1);
            arrayList.add(new ProductsEntity());
            arrayList.addAll(productsEntity);
            final ObservableList<ProductsEntity> objects = FXCollections.observableList(arrayList);
            comboProducts.setItems(objects);
        } else {
            comboProducts.setItems(null);
        }
        SingleSelectionModel<CustomerEntity> selectionModel = comboCustomer.getSelectionModel();
        if(selectionModel != null) selectionModel.select(0);

        SingleSelectionModel<DepotsEntity> selectionModel2 = comboDepo.getSelectionModel();
        if(selectionModel2 != null)selectionModel2.select(0);

        SingleSelectionModel<ProductsEntity> selectionModel3 = comboProducts.getSelectionModel();
        if (selectionModel3 != null) {
            selectionModel3.select(0);
        }

        final EntityManager entityManager = dbManager.get();
        dynamicJPA = new DynamicJPA(langUtils, entityManager);
//        dynamicFilterJPA = new DynamicFilterJPA( dbManager.get() );
//        FilteredList<ProcessItem> filteredData = new FilteredList<ProcessItem>( dynamicJPA , p -> true);
        sortedListViewAll = new SortedList<ProcessItem>(dynamicJPA,
                new Comparator<ProcessItem>() {
                    @Override
                    public int compare(ProcessItem o1, ProcessItem o2) {
                        return o1.compareTo(o2);
                    }
                });
        sortedListViewAll.comparatorProperty().bind(tableView.comparatorProperty());
//        sortedList.getSource().setAll(dynamicJPA);
        tableView.setItems(sortedListViewAll);

        dynamicFilterJPA = new DynamicFilterJPA(langUtils, entityManager);
        sortedListFilter = new SortedList<ProcessItem>(dynamicFilterJPA, new Comparator<ProcessItem>() {
            @Override
            public int compare(ProcessItem o1, ProcessItem o2) {
                return o1.compareTo(o2);
            }
        });
        sortedListFilter.comparatorProperty().bind(tableView.comparatorProperty());

/*        FxUtil.autoCompleteComboBox(comboDepo, FxUtil.AutoCompleteMode.CONTAINING);
        FxUtil.autoCompleteComboBox(comboCustomer, FxUtil.AutoCompleteMode.CONTAINING);
        FxUtil.autoCompleteComboBox(comboTransactionType, FxUtil.AutoCompleteMode.CONTAINING);
        FxUtil.autoCompleteComboBox(comboProducts, FxUtil.AutoCompleteMode.CONTAINING);*/
        autoComboCustomer = new AutoCompleteComboBoxListener(comboCustomer, this);
        autoComboTransactionType = new AutoCompleteComboBoxListener(comboTransactionType, this);
        autoComboDepo = new AutoCompleteComboBoxListener(comboDepo, this);
        autoComboProducts = new AutoCompleteComboBoxListener(comboProducts, this);
        String s = String.valueOf(sqlServices.getTotalUnits());
        labelTotalBox.setText(s);

    }


    @FXML
    public void comboTransactionTypeClicked(ActionEvent event) {

    }

    @FXML
    public void onClearClicked(ActionEvent event) {
        tableView.setItems(null);
        comboTransactionType.getSelectionModel().select(0);
        refreshTable();
    }

    @FXML
    public void onCustomerChanged(ActionEvent event) {

    }

    @FXML
    public void onDateChanged(ActionEvent event) {

    }

    @FXML
    public void onDepoChanged(ActionEvent event) {

    }

    @FXML
    public void onPartNoChanged(ActionEvent event) {

    }

    @FXML
    public void onProductChanged(ActionEvent event) {

    }

    @FXML
    public void onRefreshClicked(ActionEvent event) {
        dbManager.refreshDB();
        refreshTable();
    }


    public final void refreshTable() {
        final boolean visible = tableView.getColumns().get(0).isVisible();
        tableView.getColumns().get(0).setVisible(false);
        tableView.getColumns().get(0).setVisible(visible);
        tableView.refresh();
        postLoad();
    }





    public final void refreshTableOnly() {

//        final boolean visible = tableView.getColumns().get(0).isVisible();
//        tableView.getColumns().get(0).setVisible(false);
//        tableView.getColumns().get(0).setVisible(visible);
        tableView.refresh();
    }


    private final void onQueryChanged() {

    }


}
