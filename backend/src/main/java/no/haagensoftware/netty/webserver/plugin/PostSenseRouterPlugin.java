package no.haagensoftware.netty.webserver.plugin;

import java.util.LinkedHashMap;

import no.haagensoftware.netty.webserver.ServerInfo;
import no.haagensoftware.netty.webserver.handler.FileServerHandler;
import no.haagensoftware.riak.RiakEnv;

import org.haagensoftware.netty.webserver.spi.NettyWebserverRouterPlugin;
import org.haagensoftware.netty.webserver.spi.PropertyConstants;
import org.haagensoftware.netty.webserver.util.IntegerParser;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

/**
 * A simple router plugin to be able to serve the Haagen-Software.no website. 
 * @author joahaa
 *
 */
public class PostSenseRouterPlugin extends NettyWebserverRouterPlugin {
	private LinkedHashMap<String, SimpleChannelUpstreamHandler> routes;
	private ServerInfo serverInfo;
	
	public PostSenseRouterPlugin(ServerInfo serverInfo, RiakEnv dbEnv) {
		//authenticationContext = new AuthenticationContext(dbEnv);
		int scriptCacheSeconds = IntegerParser.parseIntegerFromString(System.getProperty(PropertyConstants.SCRIPTS_CACHE_SECONDS), 0);
		
		routes = new LinkedHashMap<String, SimpleChannelUpstreamHandler>();
		routes.put("equals:/index.html", new CachedIndexHandler(serverInfo.getWebappPath(), scriptCacheSeconds));
        routes.put("equals:/", new CachedIndexHandler(serverInfo.getWebappPath(), scriptCacheSeconds));
        routes.put("startsWith:/json/buckets", new BucketsHandler(serverInfo.getWebappPath(), dbEnv));
        routes.put("startsWith:/json/keys", new KeysHandler(serverInfo.getWebappPath(), dbEnv));
        routes.put("startsWith:/json/keyValue", new KeyValueHandler(serverInfo.getWebappPath(), dbEnv));
        
        routes.put("startsWith:/stylesheets", new FileServerHandler(serverInfo.getWebappPath()));
        routes.put("startsWith:/img", new FileServerHandler(serverInfo.getWebappPath()));
        routes.put("startsWith:/mrkdwn", new FileServerHandler(serverInfo.getWebappPath()));
        
        routes.put("startsWith:/cachedScript", new CachedScriptHandler(serverInfo.getWebappPath()));
	}
	
	@Override
	public LinkedHashMap<String, SimpleChannelUpstreamHandler> getRoutes() {
		return routes;
	}

	@Override
	public ChannelHandler getHandlerForRoute(String route) {
        SimpleChannelUpstreamHandler handler = routes.get(route);

        if (handler == null) {
			handler = new CachedIndexHandler(serverInfo.getWebappPath(), 1);
		}

        return handler;
	}
	
	@Override
	public void setServerInfo(ServerInfo serverInfo) {
		

        
	}
}
