package nl.oose.blackpool.domain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Cache {
    private HashMap<Integer, String> storedTokens;

    public Cache() {
        storedTokens = new HashMap<Integer, String>();
    }

    public void addToken(int id, String token) {
        storedTokens.put(id, token);
    }

    public boolean verifyToken(String token) {
        return storedTokens.containsValue(token);
    }

    public Integer getUserId(String token) {
        Iterator it = storedTokens.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if (pair.getValue().toString().equalsIgnoreCase(token)) {
                return Integer.parseInt(String.valueOf(pair.getKey()));
            }
        }
        return null;
    }

    public void revokeToken(int id) {
        if (storedTokens.containsKey(id))
            storedTokens.remove(id);
    }
}