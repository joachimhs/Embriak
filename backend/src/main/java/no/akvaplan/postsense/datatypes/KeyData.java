package no.akvaplan.postsense.datatypes;

public class KeyData {
	private String id;
	private String keyName;
	
	public KeyData() {
		// TODO Auto-generated constructor stub
	}
	
	public KeyData(String id) {
		this.id = id;
		this.keyName = keyName;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getKeyName() {
		return keyName;
	}
	
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
}
