package no.akvaplan.postsense.datatypes;

import java.util.ArrayList;
import java.util.List;

public class KeyListData {
	private List<KeyData> keys;
	
	public KeyListData() {
		keys = new ArrayList<>();
	}
	
	public List<KeyData> getKeys() {
		return keys;
	}
	
	public void setKeys(List<KeyData> keys) {
		this.keys = keys;
	}
}
