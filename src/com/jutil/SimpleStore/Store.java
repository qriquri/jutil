package com.jutil.SimpleStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 状態管理用クラス 必要に応じてステートを追加して使う
 *
 */
final public class Store {
	private static final String TAG = Store.class.getName();
    private static Map<Class<? extends State>, State> stateMap = new HashMap<>();
	/**
	 * ステートを束ねる
	 * @param stateList
	 */
	final public static void bind(ArrayList<State> stateList) {
    	for (int i = 0; i < stateList.size(); i++) {
    		stateMap.put(stateList.get(i).getClass(), stateList.get(i));
    	}
    }

	final public static void reset() {
	    stateMap = new HashMap<>();
	}

	/**
     * ステートの取得 キャスト推奨
     * @param klass ほしいステートのクラス
     * @return ステート
     */
    @SuppressWarnings("unchecked")
	final public static <T extends State> T getState(Class<T> klass){
    	// null だったら例外を出す
    	Optional<State> stateOpt = Optional.of(stateMap.get(klass));
        return (T) stateOpt.get();
    }
}
