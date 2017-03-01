package com.sdt.sdb.utils;

import java.awt.image.BufferedImage;

public interface IImageDPIProcessor {
	/**
     * 根据文件后缀扩展名判断是否能进行处理
     * 
     * @param fileName
     * @return
     */
    public boolean canHandle(String fileName);

    /**
     * 
     * @param path
     * @param dpi
     *            dot per inch
     * @return
     * @throws IOException
     * @throws MalformedURLException
     */
    public byte[] process(final BufferedImage srcimage, final int dpi) throws Exception;

}
