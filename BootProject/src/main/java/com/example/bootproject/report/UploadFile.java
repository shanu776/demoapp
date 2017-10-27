package com.example.bootproject.report;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;


public class UploadFile {
	
	public void uploadFile(MultipartFile multipartFile , HttpServletRequest servletRequest, String directoryName, String fileName) throws IOException
	{
		String filePath=null;
		File directory=null;
		File saveFile=null;
		
		if(multipartFile.getSize() > 0)
		{
			filePath=servletRequest.getSession().getServletContext().getRealPath(directoryName);
						
			directory=new File(filePath);
			if(!directory.exists())
			{
				directory.mkdir();
			}
			saveFile=new File(filePath,fileName);
			multipartFile.transferTo(saveFile);
		}
	}


}
