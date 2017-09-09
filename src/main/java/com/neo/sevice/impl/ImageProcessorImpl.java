package com.neo.sevice.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.neo.sevice.ImageProcessor;

import storage.StorageProperties;

@Service
public class ImageProcessorImpl implements ImageProcessor {
	
	private final Path rootLocation;
	
	private final String COMPRESSED_SUFFIX = "_sm";
	
	@Autowired
	public ImageProcessorImpl(StorageProperties storageProperties) {
		this.rootLocation = Paths.get(storageProperties.getLocation());
	}

	@Override
	public String ImageCompress(String fileName) {
		File input = new File(this.rootLocation.toString(), fileName);
		
		String ext = StringUtils.getFilenameExtension(fileName);
		String name = StringUtils.stripFilenameExtension(fileName);
		String compressedImageName = name+COMPRESSED_SUFFIX+"."+ext;
		File compressedImageFile = new File(this.rootLocation.toString(), 
				compressedImageName);
		
		try {
			BufferedImage image = ImageIO.read(input);
			OutputStream oStream = new FileOutputStream(compressedImageFile);
			Iterator<ImageWriter>writers = ImageIO.getImageWritersByFormatName(ext);
			ImageWriter writer = (ImageWriter)writers.next();
			ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(oStream);
			writer.setOutput(imageOutputStream);
			
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(0.05f);
			writer.write(null, new IIOImage(image, null, null), param);
			
			oStream.close();
			imageOutputStream.close();
			writer.dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return compressedImageName;
	}

}
