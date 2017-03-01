package com.sdt.sdb.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class DPIWindows {
	private JFrame  frame = null;
	private JMenuBar menuBar;  
	private JFileChooser openDialog;
	private JFileChooser saveDialog;
	private String title;
	//private File fSelectedFile; 
	private File fSaveFile; 
	private Mode mode; 
	FileSelectedListener listener;
	private JCheckBoxMenuItem  modeDir;
	private JCheckBoxMenuItem modeSigle;
	private JCheckBoxMenuItem  modeMulty;
	private JMenuItem saveMenu;
	
	public DPIWindows(FileSelectedListener listener){
		this("",listener);
	}
	
	public DPIWindows(String mTitle,FileSelectedListener listener){
		this.title = mTitle;
		if(frame == null)frame = new JFrame(title);
		frame.setSize(500, 500);
		frame.setResizable(false);
		this.listener = listener;
	}
	
	public void init(){
		openDialog = new JFileChooser();
		openDialog.setDialogTitle("open");
		
		saveDialog = new JFileChooser();
		saveDialog.setDialogTitle("save");
		saveDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		initMenu();

	}
	
	public void show(){
		check();
		frame.setVisible(true);
	}
	
	public void release(){
		frame.dispose();
	}
	
	private void check(){
		if(frame == null){
			throw new RuntimeException("please invoke the init first!");
		}
	}
	
	private void initMenu(){
		menuBar = new JMenuBar();
		
		initFileMenu(menuBar);
		initModeMenu(menuBar);
		
		frame.setJMenuBar(menuBar);
		
	}
	
	public File getSelectedFile(){
		return openDialog.getSelectedFile();
	}
	
	public File getSaveDir(){
		return saveDialog.getSelectedFile();
	}
	
	public File[] getSelectedFiles(){
		return openDialog.getSelectedFiles();
	}
	
	public File getSelectedDir(){
		return openDialog.getSelectedFile();
	}
	
	public Mode getMode(){
		return this.mode;
	}
	
	/**
	 * initialize the Mode menu
	 * @param menuBar
	 */
	private void initModeMenu(JMenuBar menuBar){
		JMenu menuMode = new JMenu("Mode");
		menuMode.setMnemonic('M');
		modeSigle = new JCheckBoxMenuItem("Single");
		modeDir = new JCheckBoxMenuItem("Directonary");
		modeMulty = new JCheckBoxMenuItem("Multy");
		menuMode.add(modeSigle);
		menuMode.addSeparator();
		menuMode.add(modeMulty);
		menuMode.addSeparator();
		menuMode.add(modeDir);
		menuBar.add(menuMode);
		modeSigle.setSelected(true);
		mode = Mode.SIGLE; 
		modeDir.setSelected(false);
		modeSigle.addActionListener(modeSigleAction);
		modeDir.addActionListener(modeDirAction);
		modeMulty.addActionListener(modeMulAction);
	}
	
	/**
	 * initialize the File mode
	 * @param menuBar
	 */
	private void initFileMenu(JMenuBar menuBar){
		JMenu menuFile = new JMenu("File");
		JMenuItem mOpen = new JMenuItem("open");
		saveMenu = new JMenuItem("save");
		mOpen.addActionListener(openAction);
		menuFile.setMnemonic('F');
		menuFile.add(mOpen);
		menuFile.addSeparator();
		menuFile.add(saveMenu);
		saveMenu.setEnabled(false);
		saveMenu.addActionListener(saveAction);
		menuBar.add(menuFile);
	}
	
	public interface FileSelectedListener{
		public void handle();
	}
	
	public enum Mode{
		SIGLE,MULITY,DIR,
	}
	
	private final ActionListener openAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(mode == Mode.SIGLE){
				openDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
				openDialog.setMultiSelectionEnabled(false);
			}else if(mode == Mode.MULITY){
				openDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
				openDialog.setMultiSelectionEnabled(true);
			}else{
				openDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				openDialog.setMultiSelectionEnabled(false);
			}
			openDialog.showOpenDialog(frame); 
			
			if(mode == Mode.SIGLE){
				if(openDialog.getSelectedFile() == null) return;
			}
			
			if(mode == Mode.DIR){
				if(openDialog.getSelectedFiles() == null || openDialog.getSelectedFiles().length == 0)return;
			}
			saveMenu.setEnabled(true);
			
		}
	};
	
	private final ActionListener modeSigleAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			mode = Mode.SIGLE;
			modeMulty.setSelected(false);
			modeDir.setSelected(false);
			modeSigle.setSelected(true);
		}
	};
	
	/**
	 * mode Mult click event
	 */
	private final ActionListener modeMulAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			mode = Mode.MULITY;
			modeMulty.setSelected(true);
			modeDir.setSelected(false);
			modeSigle.setSelected(false);
		}
	};
	
	/**
	 * mode Dir click event
	 */
	private final ActionListener modeDirAction = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mode = Mode.DIR;
				modeMulty.setSelected(false);
				modeDir.setSelected(true);
				modeSigle.setSelected(false);
			}
		};
	
	/**
	 * menu save click event  
	 */
	private final ActionListener saveAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			saveDialog.showOpenDialog(frame);
			fSaveFile = saveDialog.getSelectedFile();
			if(!fSaveFile.isDirectory()){
				saveDialog.setDialogTitle("must select a directory");
				saveDialog.showOpenDialog(frame);
			}
			
			if(fSaveFile == null) return;
			if(listener != null){
				listener.handle();
			}
			saveMenu.setEnabled(false);
			System.out.println( fSaveFile.getAbsolutePath());
		}
	};
}
