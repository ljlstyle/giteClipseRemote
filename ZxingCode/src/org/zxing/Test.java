package org.zxing;

import java.io.File;
import java.io.IOException;

import com.google.zxing.WriterException;

public class Test {
	public static void main(String[] args) throws Exception {
		String imgPath = "src/二维码.gif";
		String content = "http://www.baidu.com";
		String logo = "src/ljl.jpg";
		ZXingUtil.encodeImg(imgPath, "gif", content, 430, 430,logo);
		//解密：二维码->文字
		ZXingUtil.decodeImg(new File(imgPath));
		
	}
}
