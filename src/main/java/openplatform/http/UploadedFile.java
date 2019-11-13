package openplatform.http;

import java.io.InputStream;

/**
 * Class UploadedFile
 *
 * @author Thai DANG<BR/>
 * Source code is copyright
 **/
public class UploadedFile
{
    private String fieldName;
    private String fileName;
    private String contentType;
    private long size;
    private InputStream input;
    
    
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InputStream getInput() {
		return input;
	}
	public void setInput(InputStream input) {
		this.input = input;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
    

    
}
