// IBinderPool.aidl
package com.guo.rong;


interface IBinderPool {
    IBinder getBinder(in int type);
}
