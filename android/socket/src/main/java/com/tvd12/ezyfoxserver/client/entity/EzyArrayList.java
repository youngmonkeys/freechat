package com.tvd12.ezyfoxserver.client.entity;

import com.tvd12.ezyfoxserver.client.io.EzyCollectionConverter;
import com.tvd12.ezyfoxserver.client.io.EzyInputTransformer;
import com.tvd12.ezyfoxserver.client.io.EzyOutputTransformer;
import com.tvd12.ezyfoxserver.client.util.EzyArrayToList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public class EzyArrayList extends EzyTransformable implements EzyArray {
    private static final long serialVersionUID = 5952111146742741007L;

    protected final ArrayList<Object> list = new ArrayList<>();

    protected final transient EzyCollectionConverter collectionConverter;

    public EzyArrayList(
            EzyInputTransformer inputTransformer,
            EzyOutputTransformer outputTransformer,
            EzyCollectionConverter collectionConverter) {
        super(inputTransformer, outputTransformer);
        this.collectionConverter = collectionConverter;
    }

    public EzyArrayList(
            Collection items,
            EzyInputTransformer inputTransformer,
            EzyOutputTransformer outputTransformer,
            EzyCollectionConverter collectionConverter) {
        this(inputTransformer, outputTransformer, collectionConverter);
        this.list.addAll(items);

    }

    @Override
    public <T> T get(int index) {
        T answer = (T) list.get(index);
        return answer;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#get(int, java.lang.Class)
     */
    @Override
    public <T> T get(int index, Class<T> type) {
        T answer = (T) getValue(index, type);
        return answer;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#getValue(int, java.lang.Class)
     */
    @Override
    public Object getValue(int index, Class type) {
        Object answer = transformOutput(list.get(index), type);
        return answer;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#getWithDefault(int, java.lang.Object)
     */
    public <T> T getWithDefault(int index, T def) {
        return size() > index ? (T) get(index) : def;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#get(int, java.lang.Class, java.lang.Object)
     */
    public <T> T get(int index, Class<T> type, T def) {
        return size() > index ? get(index, type) : def;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#getValue(int, java.lang.Class, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    public Object getValue(int index, Class type, Object def) {
        return size() > index ? getValue(index, type) : def;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#isNotNullIndex(int)
     */
    @Override
    public boolean isNotNullValue(int index) {
        boolean answer = false;
        int size = size();
        if (index < size)
            answer = list.get(index) != null;
        return answer;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#sub(int, int)
     */
    @Override
    public EzyArray sub(int fromIndex, int toIndex) {
        List<Object> subList = list.subList(fromIndex, toIndex);
        return new EzyArrayList(
                subList,
                inputTransformer,
                outputTransformer,
                collectionConverter);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyArray#add(java.lang.Object[])
     */
    @Override
    public void add(Object... items) {
        for (Object item : items)
            this.add(item);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyArray#add(java.util.Collection)
     */
    @Override
    public void add(Collection items) {
        for (Object item : items)
            this.add(item);
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#size()
     */
    @Override
    public int size() {
        int size = list.size();
        return size;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#isEmpty()
     */
    @Override
    public boolean isEmpty() {
        boolean answer = list.isEmpty();
        return answer;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyArray#set(int, java.lang.Object)
     */
    @Override
    public <T> T set(int index, Object item) {
        T answer = (T) list.set(index, transformInput(item));
        return answer;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyArray#remove(int)
     */
    @Override
    public <T> T remove(int index) {
        T answer = (T) list.remove(index);
        return answer;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyArray#iterator()
     */
    @Override
    public Iterator<Object> iterator() {
        Iterator<Object> it = list.iterator();
        return it;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#toList()
     */
    @Override
    public List toList() {
        EzyArrayToList arrayToList = EzyArrayToList.getInstance();
        List list = arrayToList.toList(this);
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#toList(java.lang.Class)
     */
    @Override
    public <T> List<T> toList(Class<T> type) {
        List<T> list = toList();
        return list;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyRoArray#toArray(java.lang.Class)
     */
    @Override
    public <T, A> A toArray(Class<T> type) {
        A array = collectionConverter.toArray(list, type);
        return array;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Collection listClone = (Collection) list.clone();
        EzyArrayList clone = new EzyArrayList(
                listClone,
                inputTransformer,
                outputTransformer,
                collectionConverter);
        return clone;
    }

    /*
     * (non-Javadoc)
     * @see com.tvd12.ezyfox.entity.EzyArray#duplicate()
     */
    @Override
    public EzyArray duplicate() {
        try {
            return (EzyArray) clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * add an item to the list
     *
     * @param item the item
     * @return add successful or not
     */
    @Override
    public void add(Object item) {
        list.add(transformInput(item));
    }

    /**
     * Transform input value
     *
     * @param input the input value
     * @return the transformed value
     */
    protected Object transformInput(Object input) {
        Object answer = inputTransformer.transform(input);
        return answer;
    }

    /**
     * Transform output value
     *
     * @param output the output value
     * @param type   the output type
     * @return the transformed value
     */
    private Object transformOutput(Object output, Class type) {
        Object answer = outputTransformer.transform(output, type);
        return answer;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return list.toString();
    }

}