package com.sdt.sdb;

import java.io.File;
import java.io.FilenameFilter;
import com.sdt.sdb.utils.ImageDpiFactory;
import com.sdt.sdb.views.DPIWindows;
import com.sdt.sdb.views.DPIWindows.Mode;

public class DPI {
	static private DPIWindows windows;
	
	public static void main(String[] args) {
		System.out.println("start");
		windows = new DPIWindows(listener);
		windows.init();
		windows.show();
		
		/*String[] sFiles = {"64.png","54.png","43.png","29.png"}; 
		try{
			for(String sFile : sFiles){
				IImageDPIProcessor processor = null;
				BufferedImage image = ImageIO.read(new FileInputStream("D:/icon/" + sFile));
				processor =   new PngDPIProcessor();
				
				byte[] result = processor.process(image,72);
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		System.out.println("end");*/
	}
	private static DPIWindows.FileSelectedListener listener = new DPIWindows.FileSelectedListener() {
		
		@Override
		public void handle() {
			// TODO Auto-generated method stub
			if(windows.getMode() == Mode.SIGLE){
				singleProcess();
				return;
			}
			
			if(windows.getMode() == Mode.MULITY){
				mulProcess();
				return;
			}
			if(windows.getMode() == Mode.DIR){
				dirProcess();
				return;
			}
		}
	};
	
	static private void singleProcess(){
		File selected = windows.getSelectedFile();
		File save = windows.getSaveDir();
		System.out.println(selected + ":" +  save);
		ImageDpiFactory.process(selected, save);
	}
	
	static private void mulProcess(){
		File[] selected = windows.getSelectedFiles();
		File save = windows.getSaveDir();
		for(File file : selected){
			if(ImageDpiFactory.canHandle(file.getName()))
			ImageDpiFactory.process(file, save);
		}
	}
	
	static private void dirProcess(){
		File selectedDir = windows.getSelectedDir();
		File save = windows.getSaveDir();
		File[] selected = selectedDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				return ImageDpiFactory.canHandle(name);
			}
		});
		for(File file : selected){
			ImageDpiFactory.process(file, save);
		}
	}
	
	

}
