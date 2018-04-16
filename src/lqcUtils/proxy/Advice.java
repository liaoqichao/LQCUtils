package lqcUtils.proxy;

import java.lang.reflect.Method;

public interface Advice {

	abstract public void advice(Object proxy, Method method, Object[] args,Exception e) throws Throwable;
}
