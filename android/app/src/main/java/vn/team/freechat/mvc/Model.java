package vn.team.freechat.mvc;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})
public class Model implements IModel {

    protected final Map map;

    public Model() {
        this.map = new HashMap();
    }

    @Override
    public <T> T put(Object key, Object value) {
        synchronized (map) {
            Object old = map.put(key, value);
            return (T)old;
        }
    }

    @Override
    public <T> T get(Object key) {
        synchronized (map) {
            Object value = map.get(key);
            return (T)value;
        }
    }

    @Override
    public <T> T remove(Object key) {
        synchronized (map) {
            Object current = map.remove(key);
            return (T)current;
        }
    }

    @Override
    public IModel newChild(Object key) {
        IModel child = new Model();
        synchronized (map) {
            map.put(key, child);
        }
        return child;
    }

    @Override
    public IModel getChild(Object key) {
        return get(key);
    }
}
