package com.sdt.sdb.utils;

import java.awt.image.BufferedImage;

public interface IImageDPIProcessor {
	/**
     * �����ļ���׺��չ���ж��Ƿ��ܽ��д���
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
