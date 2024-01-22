package com.jutil.SimpleStore;

/**
 * ステートのスーパークラス action state getterを好きに追加して使う
 *
 */
abstract public class State {
    public abstract <T> void dispatch(Enum<?> action, T val);
}
