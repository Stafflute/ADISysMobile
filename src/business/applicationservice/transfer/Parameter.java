package business.applicationservice.transfer;

import business.applicationservice.exception.UnavaliableKeyException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Parameter implements Serializable {
    private static final long serialVersionUID = 5447770582952687574L;
    private Map<String, Object> dictionary = new HashMap<String, Object>();
    public static final transient String PARAMETER = "parameter";


    public void setValue(String key, Object value) {
        dictionary.put(key, value);
    }

    public void removeNotSerializable() {
        Set<Entry<String, Object>> entrySet = dictionary.entrySet();
        Iterator<Entry<String, Object>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object object = entry.getValue();
            if (object != null) {
                Class<?> serializable = Serializable.class;
                Class<?> objectClass = object.getClass();

                if (!serializable.isAssignableFrom(objectClass)) {
                    iterator.remove();
                }
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

    public boolean contains(String key) {
        return dictionary.containsKey(key);
    }
}
