package no.akvaplan.postsense.datatypes;

import java.util.ArrayList;
import java.util.List;

public class BucketListData {
	private List<BucketData> buckets;
	
	public BucketListData() {
		buckets = new ArrayList<>();
	}
	
	public List<BucketData> getBuckets() {
		return buckets;
	}
	
	public void setBuckets(List<BucketData> buckets) {
		this.buckets = buckets;
	}
}
