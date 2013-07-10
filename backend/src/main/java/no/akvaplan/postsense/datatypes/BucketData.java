package no.akvaplan.postsense.datatypes;

import java.util.ArrayList;
import java.util.List;

public class BucketData {
	private String id;
	private String bucketName;
	private List<String> keyIds;
	
	public BucketData() {
		keyIds = new ArrayList<>();
	}
	
	public BucketData(String id) {
		this();
		this.id = id;
		this.bucketName = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getBucketName() {
		return bucketName;
	}
	
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}	
	
	public List<String> getKeyIds() {
		return keyIds;
	}
	
	public void setKeyIds(List<String> keyIds) {
		this.keyIds = keyIds;
	}
}

