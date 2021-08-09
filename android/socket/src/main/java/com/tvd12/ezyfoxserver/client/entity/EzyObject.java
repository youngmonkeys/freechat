package com.tvd12.ezyfoxserver.client.entity;

import java.util.Map;

/**
 * Support to transport data between objects
 * 
 * @author tavandung12
 *
 */

public interface EzyObject extends EzyRoObject {

	/**
	 * @see java.util.Map#put(java.lang.Object, java.lang.Object)
	 * 
	 * @param <V> the value type
	 * @param key the key
	 * @param value the value
	 * @return the old value
	 */
	<V> V put(Object key, Object value);
	
    /**
     * @see java.util.Map#putAll(java.util.Map)
     * 
     * @param m the map value
     */
    @SuppressWarnings("rawtypes")
	void putAll(Map m);
    
    /**
     * @see java.util.Map#remove(java.lang.Object)
     * 
     * @param <V> the value type
     * @param key the key
     * @return the removed value
     */
    <V> V remove(Object key);
    
    /**
     * @see java.util.Map#clear()
     */
    void clear();
    
    /**
     * @see com.tvd12.ezyfoxserver.client.entity.EzyData#duplicate()
     */
    @Override
    EzyObject duplicate();

}
