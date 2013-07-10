package no.haagensoftware.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class UriUtil {
	public static String getIdFromUri(String uri, String key) {
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        String[] uriParts = uri.split("/");
        String id = null;
        int idPart = -1;
        for (int i = 0; i < uriParts.length; i++) {
            String part = uriParts[i];
            if (part.equals(key) && uriParts.length > (i + 1)) {
                idPart = i+1;
                break;
            }
        }

        if (idPart > 0 && idPart < uriParts.length) {
            id = uriParts[idPart];
        }
        
        if (id != null) {
        	id = id.replace("+", " ");
        	if (id.endsWith(".json")) {
        		id = id.substring(0, id.length()-5);
        	}
        }
        
        return id;
    }
}
