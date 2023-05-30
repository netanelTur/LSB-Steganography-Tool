package sample;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Stega {
    /**
     * The function hides text inside an image using LSB steganography.
     * @param img - the image we want to hide the message in.
     * @param msg - the text message we want to hide in the image.
     * @throws IOException
     */
    public static void encodeImage(BufferedImage img, String msg) throws IOException {
        byte[] msgBytes = msg.getBytes(StandardCharsets.UTF_8);
        int x = 0, y = 0;
        Color currColor = null, newColor = null;
        int bitVal = 0;
        int blue = 0, newBlue = 0, temp = 0;

        // Traverse through each byte of the message
        for (byte b : msgBytes) {
            for (int k = 7; k >= 0; k--) {
                // Get pixel
                currColor = new Color(img.getRGB(x, y));

                // Get the blue part of the pixel
                blue = currColor.getBlue();

                // Get current bit of the message
                bitVal = (b >>> k) & 1;

                // Compare the LSB of the blue part of the pixel with the current bit of the message
                temp = (blue & 1) ^ bitVal;

                // Update the blue of the current pixel of output image + temp
                newBlue = blue == 255 ? ((blue - temp) & 0xFF) : ((blue + temp) & 0xFF);
                newColor = new Color(currColor.getRed(), currColor.getGreen(), newBlue);
                img.setRGB(x, y, newColor.getRGB());
                // Traverse through x axis of the image
                x++;
            }
            // Traverse through y axis of the image
            y++;
        }

        // Save the output image in a file
        File res = new File("encoded.png");
        ImageIO.write(img, "png", res);
    }

    /**
     * The function extracts a hidden message from an image using LSB steganography.
     * @param img - the image we want to extract the message from.
     * @param size - the length of the text message.
     * @return the hidden message as string.
     */
    public static String decodeImage(BufferedImage img, int size) {
        int x = 0, y = 0;
        byte[] extractedBytes = new byte[size];
        Color currColor = null;
        byte blue = 0;

        // Traverse through each pixel that has part of the message
        for (int i = 0; i < size; i++) {
            for (int k = 0; k < 8; k++) {
                // Get pixel
                currColor = new Color(img.getRGB(x, y));

                // Get the blue part of the pixel
                blue = (byte) currColor.getBlue();

                // Store the LSB of the pixel
                extractedBytes[i] = (byte) ((extractedBytes[i] << 1) ^ (blue & 1));

                // Traverse through x axis of the image
                x++;
            }
            // Traverse through y axis of the image
            y++;
        }

        // Convert the extracted bytes of the message to string and return it
        return new String(extractedBytes);
    }

}
