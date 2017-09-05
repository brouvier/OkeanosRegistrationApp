package okeanos.model;

import java.util.Date;

public class AdherentDocument {

	private Long id;
	private String file_type;
	private String content_disposition;
	private byte[] data;
	private Date createdOn;

	public AdherentDocument(Long id, String file_type, String content_disposition, byte[] data, Date createdOn) {
		super();
		this.id = id;
		this.file_type = file_type;
		this.content_disposition = content_disposition;
		this.data = data;
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		return "AdherentDocument [id=" + id + ", file_type=" + file_type + ", content_disposition="
				+ content_disposition + ", createdOn=" + createdOn + "]";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getContent_disposition() {
		return content_disposition;
	}

	public void setContent_disposition(String content_disposition) {
		this.content_disposition = content_disposition;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
