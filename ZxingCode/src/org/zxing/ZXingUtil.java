package org.zxing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.InvertedLuminanceSource;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import jp.sourceforge.qrcode.util.Color;

public class ZXingUtil {
	//����:   ����->��ά��ͼƬ
	public static void encodeImg(String imgPath,String format,String content,int width,int height,String logo) throws WriterException, IOException {
		
		Map<EncodeHintType,Object> hints = new HashMap<>();
		//�Ŵ��� L<M<Q<H
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		//����
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		//��߾ࣺmargin
		hints.put(EncodeHintType.MARGIN, 1);
		
		File file = new File(imgPath);
		/*
		 * content:��Ҫ���ܵ�����
		 * BarcodeFormat.QR_CODE:Ҫ���������ͣ���ά�룩
		 * */
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		//�ڴ��е�һ��ͼƬ����ʱ��Ҫ��ͼƬ�Ƕ�ά��-����Ҫһ��boolean[][] -�� BitMatrix
		BufferedImage im = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		
		for(int x = 0;x<width;x++) {
			for(int y = 0; y < height;y++) {
				int z = bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE;
				im.setRGB(x, y , z );
			}
		}
		
		BufferedImage img = LogoUtil.logoMatrix(im, logo);
		
		//����ͼƬ
		ImageIO.write(img, format, file);
	}
	
	//���ܣ���ά��->����
		public static void decodeImg(File file) throws Exception {
			if(!file.exists()) return ;
			//file->�ڴ��е�һ��ͼƬ
			 BufferedImage imge = ImageIO.read(file)  ;
			 
			 MultiFormatReader formatReader = new MultiFormatReader() ;
			 LuminanceSource source = new BufferedImageLuminanceSource(imge);
			 Binarizer binarizer = new HybridBinarizer(source);
			 BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			 //ͼƬ ->result
			 Map map = new HashMap();
			 map.put(EncodeHintType.CHARACTER_SET, "utf-8") ;
			 Result result = formatReader.decode(binaryBitmap  ,map ) ;
			 System.out.println("���������"+ result.toString());
		}
}
