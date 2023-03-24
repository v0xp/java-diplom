package graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;


public class Converter implements TextGraphicsConverter {
    private int width;
    private int height;
    private double maxRatio;
    private TextColorSchema schema;


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        int newWidth = 0;
        int newHeight = 0;
        double ratio = 0;
        double kW = 0;
        double kH = 0;

        Schema schema = new Schema();
        BufferedImage img = ImageIO.read(new URL(url));

        if (img.getWidth() / img.getHeight() > img.getHeight() / img.getWidth()) {
            ratio = (double) img.getWidth() / (double) img.getHeight();
        } else {
            ratio = (double) img.getHeight() / (double) img.getWidth();
        }
        if (ratio > maxRatio && maxRatio != 0) throw new BadImageSizeException(ratio, maxRatio);
        if (img.getWidth() > width || img.getHeight() > height) {
            if (width != 0) {
                kW = img.getWidth() / width;
            } else kW = 1;
            if (height != 0) {
                kH = img.getHeight() / height;
            } else kH = 1;
            if (kW > kH) {
                newWidth = (int) (img.getWidth() / kW);
                newHeight = (int) (img.getHeight() / kW);
            } else {
                newWidth = (int) (img.getWidth() / kH);
                newHeight = (int) (img.getHeight() / kH);
            }
        } else {
            newWidth = img.getWidth();
            newHeight = img.getHeight();
        }

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();

        char[][] array = new char[newHeight][newWidth];
        StringBuilder sb = new StringBuilder();
        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                array[h][w] = c;
                sb.append(c);
                sb.append(c);
                if (w == newWidth - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.width = width;
    }

    @Override
    public void setMaxHeight(int height) {
        this.height = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema colorSchema) {
        this.schema = colorSchema;
    }
}