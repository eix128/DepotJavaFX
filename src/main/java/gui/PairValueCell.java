package gui;

import gui.controller.MainPanelController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;
import jpa.ProcessEntity;
import jpa.converters.enums.ProcessType;

import java.io.InputStream;

/**
 * Created by Kadir on 4/11/2016.
 */
public class PairValueCell extends TableCell<String, ProcessType> {


    private final Image imageDownload;
    private final Image imageUpload;
    private final ImageView imageView;
    private final ImageView imageViewDownload;
    private final Text text;
    private final FlowPane flowPane;

    public PairValueCell() {
        InputStream resourceAsStream = MainPanelController.class.getClass().getResourceAsStream("/icon/up.png");
        imageUpload = new Image(resourceAsStream);
        InputStream resourceAsStream1 = MainPanelController.class.getClass().getResourceAsStream("/icon/down.png");
        imageDownload = new Image(resourceAsStream1);
        imageViewDownload = new ImageView(imageUpload);
        imageView = new ImageView(imageDownload);

        final Glow glow = new Glow(1.0);
        imageView.setEffect(glow);

        Timeline timeline = new Timeline();
        final boolean b = timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, // set start position at 0
                        new KeyValue(glow.levelProperty(), 0.0)
                ),
                new KeyFrame(new Duration(1000), // set end position at 40s
                        new KeyValue(glow.levelProperty(), 2 )
                )
        );
        timeline.setAutoReverse(true);
        timeline.setCycleCount(-1);
        timeline.playFromStart();

        text = new Text("Çıkış");
        text.setEffect(glow);

        flowPane = new FlowPane();
        flowPane.getChildren().addAll( imageView , text );
    }

    @Override
    protected void updateItem(ProcessType item, boolean empty) {
//        super.updateItem(item, empty);
        setTextFill(Color.BLACK);
        if (item != null) {
            if(item == ProcessType.INPUT) {
                setGraphic(imageViewDownload);
                setText("Giriş");
            }
            else {
                setTextFill(Color.RED);
                setGraphic(flowPane);
            }
        } else {
            setText(null);
            setGraphic(null);
        }
    }
}