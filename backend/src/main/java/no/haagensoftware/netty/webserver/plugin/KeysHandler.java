package no.haagensoftware.netty.webserver.plugin;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.handler.codec.http.HttpRequest;

import com.google.gson.Gson;

import no.akvaplan.postsense.datatypes.BucketData;
import no.akvaplan.postsense.datatypes.BucketListData;
import no.akvaplan.postsense.datatypes.KeyData;
import no.akvaplan.postsense.datatypes.KeyListData;
import no.haagensoftware.netty.webserver.handler.FileServerHandler;
import no.haagensoftware.riak.RiakEnv;
import no.haagensoftware.util.UriUtil;

public class KeysHandler extends FileServerHandler {
	private static Logger logger = Logger.getLogger(KeysHandler.class.getName());
	private RiakEnv riakEnv;
	public KeysHandler(String rootPath, RiakEnv riakEnv) {
		super(rootPath);
		this.riakEnv = riakEnv;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		HttpRequest request = (HttpRequest) e.getMessage();
		String jsonResponse = "";
		String uri = request.getUri();
        String id = UriUtil.getIdFromUri(uri, "keys");
        logger.info("id: " + id);
        
		String messageContent = getHttpMessageContent(e);
        logger.info(messageContent);
        
        if (isGet(e) && id != null) {
        	KeyListData keyListData = new KeyListData();
        	for (KeyData kd : riakEnv.getRiakBucketDao().getKeysForBucket(id)){
        		keyListData.getKeys().add(kd);
        	}
        	
        	jsonResponse = new Gson().toJson(keyListData);
        }        
        
        logger.info("jsonResponse: " + jsonResponse);
        writeContentsToBuffer(ctx, jsonResponse, "text/json");
	}
}
