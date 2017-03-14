// ILibrary.aidl
package com.guo.art;

// Declare any non-default types here with import statements

interface ILibrary {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<String> getBookList();
    boolean searchBook(in String name);
}
