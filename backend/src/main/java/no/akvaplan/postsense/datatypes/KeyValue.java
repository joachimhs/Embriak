package no.akvaplan.postsense.datatypes;

public class KeyValue {
	String id;
	String keyName;
	String value;
	
	public KeyValue() {
		// TODO Auto-generated constructor stub
	}
	
	public KeyValue(String id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public String getKeyName() {
		return keyName;
	}
	
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
