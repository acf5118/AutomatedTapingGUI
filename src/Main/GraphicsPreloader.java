package Main;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by Adam Fowles on 1/22/2016.
 */
public class GraphicsPreloader extends Preloader
{
    ProgressBar bar;
    Stage stage;

    private Scene createPreloaderScene() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        ImageView iv = new ImageView();
        iv.setImage(new Image(getClass().getResourceAsStream("/Resources/MexicanFlag.png")));
        p.setCenter(bar);
        p.setTop(iv);
        return new Scene(p);
    }

    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(createPreloaderScene());
        stage.show();
    }

    @Override
    public void handleProgressNotification(javafx.application.Preloader.ProgressNotification pn) {
        bar.setProgress(pn.getProgress());
    }

    @Override
    public void handleStateChangeNotification(javafx.application.Preloader.StateChangeNotification evt) {
        if (evt.getType() == javafx.application.Preloader.StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }
    }
}