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
import no.akvaplan.postsense.datatypes.KeyValue;
import no.akvaplan.postsense.datatypes.KeyValueObject;
import no.haagensoftware.netty.webserver.handler.FileServerHandler;
import no.haagensoftware.riak.RiakEnv;
import no.haagensoftware.util.UriUtil;

public class KeyValueHandler extends FileServerHandler {
	private static Logger logger = Logger.getLogger(KeyValueHandler.class.getName());
	private RiakEnv riakEnv;
	public KeyValueHandler(String rootPath, RiakEnv riakEnv) {
		super(rootPath);
		this.riakEnv = riakEnv;
	}
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		HttpRequest request = (HttpRequest) e.getMessage();
		String jsonResponse = "";
		String uri = request.getUri();
        String id = UriUtil.getIdFromUri(uri, "keyValue");
        logger.info("id: " + id);
        
		String messageContent = getHttpMessageContent(e);
        logger.info(messageContent);
        
        if (isGet(e) && id != null) {
        	String[] idParts = id.split("___");
        	KeyValue keyValue = riakEnv.getRiakBucketDao().getKeyValue(idParts[0], idParts[1]);
        	
        	jsonResponse = "{\"keyValue\": " + new Gson().toJson(keyValue) + "}";
        } else if ((isPost(e) || isPut(e)) && id != null) {
        	logger.info("Persisiting KeyValue: " + messageContent);
        	
        	KeyValueObject keyValueObject = new Gson().fromJson(messageContent, KeyValueObject.class);
        	String[] idParts = id.split("___");
        	riakEnv.getRiakBucketDao().persistKeyValue(idParts[0], idParts[1], keyValueObject.getKeyValue());
        } else if (isDelete(e) && id != null) {
        	String[] idParts = id.split("___");
        	riakEnv.getRiakBucketDao().deleteKeyValue(idParts[0], idParts[1]);
        }
        
        logger.info("jsonResponse: " + jsonResponse);
        writeContentsToBuffer(ctx, jsonResponse, "text/json");
	}
}
