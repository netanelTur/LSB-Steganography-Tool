package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    @FXML
    ImageView imgView;
    @FXML
    TextArea hideMsgTextArea;
    @FXML
    TextArea extractMsgTextArea;

    BufferedImage bufferedImage;

    @FXML
    public void openImageFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilterJPG =
                new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg =
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG =
                new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng =
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters()
                .addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);

        File file = fileChooser.showOpenDialog(null);

        try {
            bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgView.setImage(image);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void hideTextInImage(ActionEvent event) throws IOException {
        Stega.encodeImage(bufferedImage, hideMsgTextArea.getText());
    }

    @FXML
    public void extractTextFromImage(ActionEvent event) {
        extractMsgTextArea.setText(Stega.decodeImage(bufferedImage, 50));
    }

}
