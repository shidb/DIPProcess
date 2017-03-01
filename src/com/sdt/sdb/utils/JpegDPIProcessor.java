package com.sdt.sdb.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageOutputStream;

import org.w3c.dom.Element;

public class JpegDPIProcessor implements IImageDPIProcessor{

	private String formatName = "jpeg";

    @Override
    public byte[] process(BufferedImage image, int dpi) throws IOException {
        for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatName); iw.hasNext();) {
            ImageWriter writer = iw.next();

            ImageWriteParam writeParams = writer.getDefaultWriteParam();
            writeParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            //调整图片质量
            writeParams.setCompressionQuality(1f);

            IIOMetadata data = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), writeParams);
            Element tree = (Element) data.getAsTree("javax_imageio_jpeg_image_1.0");
            Element jfif = (Element) tree.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("Xdensity", dpi + "");
            jfif.setAttribute("Ydensity", dpi + "");
            jfif.setAttribute("resUnits", "1"); // density is dots per inch

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageOutputStream stream = null;
            try {
                stream = ImageIO.createImageOutputStream(out);
                writer.setOutput(stream);
                writer.write(data, new IIOImage(image, null, null), writeParams);
            } finally {
                stream.close();
            }

            return out.toByteArray();
        }
        return null;

    }

    @Override
    public boolean canHandle(String fileName) {
        assert fileName != null : "fileName should not be null";
        String name = fileName.toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith("jpeg");
    }

}
