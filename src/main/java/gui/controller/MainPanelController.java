package gui.controller;

import com.google.inject.Inject;
import globals.interfaces.ControllerNetworkInit;
import globals.interfaces.PostInit;
import gui.AutoCompleteComboBoxListener;
import gui.FXPanels;
import gui.jpa.DynamicJPA;
import gui.jpa.ProcessItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jpa.CustomerEntity;
import jpa.DepotsEntity;
import jpa.ProcessEntity;
import jpa.ProductsEntity;
import jpa.converters.enums.ProcessType;
import kernel.network.DBManager;
import kernel.threads.network.NetworkThread;
import org.javatuples.Pair;
import utils.guava.LazyCache;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by kadir.basol on 25.3.2016.
 */
public class MainPanelController implements Initializable {

    @FXML
    private StackPane        stackPane;

    @FXML
    private ComboBox<String> comboTransactionType;


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
    private Label labelTotalBox;


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
    private TableColumn<ProcessEntity,String> columnPartiNo;

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
    private TableColumn<ProcessEntity, String> columnTime;
    @FXML
    private TableColumn<ProcessEntity, String> columnType;
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


    private AutoCompleteComboBoxListener<CustomerEntity> autoComboCustomer;
    private AutoCompleteComboBoxListener<String> autoComboTransactionType;
    private AutoCompleteComboBoxListener<DepotsEntity> autoComboDepo;
    private AutoCompleteComboBoxListener<ProductsEntity> autoComboProducts;

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
        dialog.show();
    }


    @FXML
    public void onSortClicked(ActionEvent event) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        autoComboCustomer = new AutoCompleteComboBoxListener( comboCustomer );
        autoComboTransactionType  = new AutoCompleteComboBoxListener<String>( comboTransactionType );
        autoComboDepo = new AutoCompleteComboBoxListener<DepotsEntity>(comboDepo);
        autoComboProducts = new AutoCompleteComboBoxListener<ProductsEntity>(comboProducts);


        columnDepot.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("depot"));
        columnProductName.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("product"));
        columnCustomer.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("company"));
        columnCompanyWorker.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("companyUserId"));
        columnType.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("processType"));
        columnUnits.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("units"));
        columnPartiNo.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("partNo"));
        columnDate.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("actionDate"));
        columnTime.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("actionTime"));
        columnPrice.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("price"));
        columnText.setCellValueFactory(new PropertyValueFactory<ProcessEntity,String>("description"));

        ObservableList<String> strings = FXCollections.observableArrayList( "" , "GİRİŞ", "ÇIKIŞ");
        comboTransactionType.setItems(strings);

//        ObservableList<CustomerEntity> objects = FXCollections.observableList( CustomerEntity.getDepotsList().get() );
//        comboCustomer.setItems(objects);


    }


    @ControllerNetworkInit
    public final void postLoad() {
        final LazyCache<List<CustomerEntity>> listLazyCache = CustomerEntity.getCustomersList();
        final List<CustomerEntity> customerEntities = listLazyCache.get();

        final ObservableList<CustomerEntity> objects = FXCollections.observableList( customerEntities);

        comboCustomer.setItems( objects );

        final LazyCache<List<DepotsEntity>> depotsList = DepotsEntity.getDepotNames();
        final List<DepotsEntity> depotsEntities = depotsList.get();
        final ObservableList<DepotsEntity> depotsEntities1 = FXCollections.observableList(depotsEntities);
        comboDepo.setItems(depotsEntities1);


        final LazyCache<List<ProductsEntity>> productsList = ProductsEntity.getProductsList();
        final List<ProductsEntity> productsEntity = productsList.get();
        final ObservableList<ProductsEntity> prodctsLists = FXCollections.observableList(productsEntity);
        comboProducts.setItems(prodctsLists);

        comboCustomer.getSelectionModel().select(0);
        comboDepo.getSelectionModel().select(0);
        comboProducts.getSelectionModel().select(0);

        DynamicJPA dynamicJPA = new DynamicJPA( dbManager.get() );
        SortedList<ProcessItem> sortedList = new SortedList<ProcessItem>( dynamicJPA );
        sortedList.getSource().setAll(dynamicJPA);
        tableView.setItems(sortedList);
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






    private final void refreshTable() {
        final boolean visible = tableView.getColumns().get(0).isVisible();
        tableView.getColumns().get(0).setVisible(false);
        tableView.getColumns().get(0).setVisible(visible);
        tableView.refresh();
        postLoad();
    }


    private final void onQueryChanged() {
        labelTotalBox.setText("Total");
    }


}
