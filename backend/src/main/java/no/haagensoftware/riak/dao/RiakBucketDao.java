package no.haagensoftware.riak.dao;

import java.util.ArrayList;
import java.util.List;

import no.akvaplan.postsense.datatypes.BucketData;
import no.akvaplan.postsense.datatypes.KeyData;
import no.akvaplan.postsense.datatypes.KeyValue;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakRetryFailedException;
import com.basho.riak.client.bucket.Bucket;
import com.basho.riak.client.query.indexes.BucketIndex;

public class RiakBucketDao {
	private IRiakClient riakClient;
	
	public RiakBucketDao(IRiakClient riakClient) {
		this.riakClient = riakClient;
	}
	
	public List<BucketData> getBuckets() {
		List<BucketData> bucketList = new ArrayList<>();
		try {
			for (String bucket : riakClient.listBuckets()) {
				BucketData bd = new BucketData(bucket);
				List<String> keyIds = new ArrayList<>();
				for (KeyData kd : getKeysForBucket(bucket)) {
					keyIds.add(bucket + "___" + kd.getId());
				}
				bd.setKeyIds(keyIds);
				bucketList.add(bd);
			}
		} catch (RiakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bucketList;
	}
	
	public List<KeyData> getKeysForBucket(String bucketName) {
		List<KeyData> keyList = new ArrayList<>();
		
		Bucket bucket = null;
		try {
			bucket = riakClient.fetchBucket(bucketName).execute();
			
			for (String key : bucket.fetchIndex(BucketIndex.index).withValue("$key").execute()) {
				keyList.add(new KeyData(key));
			}
		} catch (RiakRetryFailedException rrfe) {
            rrfe.printStackTrace();
        } catch (RiakException e) {
            e.printStackTrace();
        }
		
		return keyList;
	}
	
	public KeyValue getKeyValue(String bucketName, String key) {
		KeyValue keyValue = new KeyValue();
		keyValue.setId(bucketName + "___" + key);
		
		Bucket bucket = null;
		try {
			bucket = riakClient.fetchBucket(bucketName).execute();
			keyValue.setValue(bucket.fetch(key).execute().getValueAsString());
		} catch (RiakRetryFailedException rrfe) {
            rrfe.printStackTrace();
        }   
		
		return keyValue;
	}
}
