package com.sdt.sdb.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;

public class ImageDpiFactory {
	private ImageDpiFactory(){}
	static private IImageDPIProcessor pngProcessor;
	static private IImageDPIProcessor jpegProcessor;
	public static IImageDPIProcessor getProcessor(String sFilePath){
		IImageDPIProcessor processor = null;
		if(sFilePath.isEmpty() || sFilePath.equals("") || sFilePath.length() == 0)return processor;
		
		File file = new File(sFilePath);
		if(!file.exists() || !file.isFile())return processor;
		String lowPath = sFilePath.toLowerCase();
		if(lowPath.endsWith(".jpg")){
			return getJPGDPIProcess();
		} 
		if(lowPath.endsWith(".png")){
			return getPNGPIProcess();
		}
		return processor;
	}
	
	private static synchronized IImageDPIProcessor getJPGDPIProcess(){
		if(jpegProcessor == null){
			jpegProcessor = new JpegDPIProcessor();
		}
		return jpegProcessor;
	}
	
	private static synchronized IImageDPIProcessor getPNGPIProcess(){
		if(pngProcessor == null){
			pngProcessor = new PngDPIProcessor();
		}
		return pngProcessor;
	}
	
	public static void process(File source, File dir){
		IImageDPIProcessor processor = ImageDpiFactory.getProcessor(source.getName());
		if(processor != null){
			try {
				BufferedImage image = ImageIO.read(new FileInputStream(source));
				byte[] result = processor.process(image, 64);
				FileImageOutputStream imageOutput = new FileImageOutputStream(new File(dir, source.getName()));  
		 		imageOutput.write(result, 0, result.length);  
		 		imageOutput.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("cannot support");
		}
	}
	static public boolean canHandle(String name){
		if(name == null) return false;
		name = name.toLowerCase();
		return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith("jpeg");
	}
}
