package com.jutil.Logger;

public class Logger {
    /**
     *
     * @param <T>
     * @param LOG_TAG
     * @param items
     */
    @SuppressWarnings("unchecked")
    final public static <T> void info(String LOG_TAG, T ...items){
        System.out.print("[");
        System.out.print("info");
        System.out.print("]");

    	System.out.print("[");
        System.out.print(LOG_TAG);
        System.out.print("]");
        print(items);
        System.out.println("");
    }

    /**
     *
     * @param <T>
     * @param LOG_TAG
     * @param items
     */
    @SuppressWarnings("unchecked")
    final public static <T> void error(String LOG_TAG, T ...items){
        System.out.print("[");
        System.out.print("error");
        System.out.print("]");

    	System.out.print("[");
        System.out.print(LOG_TAG);
        System.out.print("]");
        print(items);
        System.out.println("");
    }

    @SuppressWarnings("unchecked")
    final private static <T> void print(T ...items){
        for(T item: items) {
			System.out.print(item);
		}
    }

}
