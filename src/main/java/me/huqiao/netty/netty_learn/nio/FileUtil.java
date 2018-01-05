package me.huqiao.netty.netty_learn.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

import org.junit.Test;

/**
 * @author huqiao
 */
public class FileUtil {
	

	public static void cat(File file)throws Exception {
		//Charset utf8 = Charset.forName( "UTF-8" );
		
		FileInputStream fis = new FileInputStream(file);
		FileChannel inputChannel = fis.getChannel();
		
		ByteBuffer buffer = ByteBuffer.allocate(fis.available());
		
		int len = inputChannel.read(buffer);
		String content  = new String(buffer.array(),"UTF-8");
		System.out.println(content);
		fis.close();
		
	}
	
	public static void copy(File from,File to) throws Exception{
		FileInputStream fis = new FileInputStream(from);
		FileOutputStream fos = new FileOutputStream(to);
		
		FileChannel inputChannel = fis.getChannel();
		FileChannel outputChannel = fos.getChannel();
		
		ByteBuffer buffer =  ByteBuffer.allocate(102400);
		
		//直接定位
		//inputChannel.position(10);
		int len;
		//buffer.clear();
		while((len=inputChannel.read(buffer))!=-1) {
			buffer.flip();
			outputChannel.write(buffer);
			buffer.clear();
		}
		fis.close();
		fos.close();
	}
	
	public static void copyByTransfer(File from,File to) throws Exception{
		FileInputStream fis = new FileInputStream(from);
		FileOutputStream fos = new FileOutputStream(to);
		
		FileChannel inputChannel = fis.getChannel();
		FileChannel outputChannel = fos.getChannel();
		
		//ByteBuffer buffer =  ByteBuffer.allocate(10);
		
		
		inputChannel.transferTo(0, fis.available(), outputChannel);
		/*int len;
		//buffer.clear();
		while((len=inputChannel.read(buffer))!=-1) {
			buffer.flip();
			outputChannel.write(buffer);
			buffer.clear();
		}*/
		fis.close();
		fos.close();
	}
	
	@Test
	public void testCopy() throws Exception {
		long time = System.nanoTime();
		FileUtil.copy(new File("F:\\temp\\uploads.zip"),new File("F:\\temp\\uploads.copy.zip"));
		time = System.nanoTime() - time;
		System.out.println(time);
	}
	
	@Test
	public void testCopyFileByTransfer() throws Exception {
		long time = System.nanoTime();
		FileUtil.copyByTransfer(new File("F:\\temp\\uploads.zip"),new File("F:\\temp\\uploads.copy.zip"));
		time = System.nanoTime() - time;
		System.out.println(time);
	}
	
}
