package no.haagensoftware.netty.webserver.plugin;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpRequest;

import com.google.gson.Gson;

import no.akvaplan.postsense.datatypes.BucketData;
import no.akvaplan.postsense.datatypes.BucketListData;
import no.haagensoftware.netty.webserver.handler.FileServerHandler;
import no.haagensoftware.riak.RiakEnv;
import no.haagensoftware.util.UriUtil;

public class BucketsHandler extends FileServerHandler {
	private static Logger logger = Logger.getLogger(BucketsHandler.class.getName());
	private RiakEnv riakEnv;
	public BucketsHandler(String rootPath, RiakEnv riakEnv) {
		super(rootPath);
		this.riakEnv = riakEnv;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		HttpRequest request = (HttpRequest) e.getMessage();
		String uri = request.getUri();
        String id = UriUtil.getIdFromUri(uri, "buckets");
        logger.info("id: " + id);
        
        String jsonResponse = "";
		
		String messageContent = getHttpMessageContent(e);
        logger.info(messageContent);
        
        if (isGet(e) && id != null) {
        	BucketData foundBucket = null;
        	
        	for (BucketData bd : riakEnv.getRiakBucketDao().getBuckets()) {
        		if (bd.getId().equals(id)) {
        			foundBucket = bd;
        			break;
        		}
        	}
        	
        	if (foundBucket == null) {
        		jsonResponse = "{}";
        	} else {
        		jsonResponse = "{\"bucket\": " + new Gson().toJson(foundBucket) + "}";
        	}
        	
        } else if (isGet(e) && id == null) {
        	BucketListData bucketListData = new BucketListData();
        	for (BucketData bd : riakEnv.getRiakBucketDao().getBuckets()) {
        		bucketListData.getBuckets().add(bd);
        	}
        	
        	jsonResponse = new Gson().toJson(bucketListData);
        }        
        
        logger.info("jsonResponse: " + jsonResponse);
        writeContentsToBuffer(ctx, jsonResponse, "text/json");
	}
}
