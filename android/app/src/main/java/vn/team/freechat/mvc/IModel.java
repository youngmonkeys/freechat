package vn.team.freechat.mvc;

import java.util.Collection;
import java.util.Map;

public interface IModel {

    <T> T put(Object key, Object value);

    <T> T get(Object key);

    <T> T remove(Object key);
}
