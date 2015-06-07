package com.skubit.shared.dto;

import java.util.Date;


public class ComicFileDto implements Dto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -879213874054396661L; 

	private String cbid;
	
	private ComicFileClassifier classifier;

	private String fileName;
	
	private ArchiveFormat format;

	private String md5Hash;
	
	private int numPages;

	private long size;
	
	private Date updatedDate;

	public String getCbid() {
		return cbid;
	}

	public void setCbid(String cbid) {
		this.cbid = cbid;
	}

	public ComicFileClassifier getClassifier() {
		return classifier;
	}

	public void setClassifier(ComicFileClassifier classifier) {
		this.classifier = classifier;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ArchiveFormat getFormat() {
		return format;
	}

	public void setFormat(ArchiveFormat format) {
		this.format = format;
	}

	public String getMd5Hash() {
		return md5Hash;
	}

	public void setMd5Hash(String md5Hash) {
		this.md5Hash = md5Hash;
	}

	public int getNumPages() {
		return numPages;
	}

	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

    @Override
    public String toString() {
	return "ComicFileDto{" +
		"cbid='" + cbid + '\'' +
		", classifier=" + classifier +
		", fileName='" + fileName + '\'' +
		", format=" + format +
		", md5Hash='" + md5Hash + '\'' +
		", numPages=" + numPages +
		", size=" + size +
		", updatedDate=" + updatedDate +
		'}';
    }
}
