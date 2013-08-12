package util;

import android.util.Log;

import java.io.Serializable;
import java.util.*;
import java.util.Map.Entry;

public class Parameter implements Serializable {
    private static final long serialVersionUID = 5447770582952687574L;
    private Map<String, Object> dictionary = new HashMap<>();
    public static final transient String PARAMETER = "parameter";


    public void setValue(String key, Object value) {
	    dictionary.put(key, value);
    }

    public void removeNotSerializable() {
        Set<Entry<String, Object>> entrySet = dictionary.entrySet();
        Iterator<Entry<String, Object>> iterator = entrySet.iterator();
        while(iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object object = entry.getValue();
            Class<?> serializable = Serializable.class;
            Class<?> objectClass = object.getClass();

            if(!serializable.isAssignableFrom(objectClass)) {
                iterator.remove();
            }
        }
    }

    public Object getValue(String key) {
	
	Object result = null;
	
	if (dictionary.containsKey(key)) {
	    result = dictionary.get(key);
	} else {
	    throw new UnavaliableKeyException();
	}
	
	return result;
    }
}
