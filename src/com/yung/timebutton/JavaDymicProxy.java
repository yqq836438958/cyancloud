
package com.yung.timebutton;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JavaDymicProxy {
    public interface IInterService {
        public void onExecute(int param);
    }

    public class ServiceImpl implements IInterService {

        @Override
        public void onExecute(int param) {
            Log.d("yqq", "ServiceImpl onExecute--:" + param);

        }

    }

    public interface ITestBinder {
        public void onBind(int num);
    }

    public class TestBinderImpl implements ITestBinder {

        @Override
        public void onBind(int num) {
            Log.d("yqq", "TestBinderImpl onBind--:" + num);

        }
    }

    public static void test() {

    }

    public class ServiceProxy implements InvocationHandler {
        private IInterService mBaseService = null;

        public ServiceProxy(IInterService _service) {
            mBaseService = _service;
        }

        @Override
        public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
            return arg1.invoke(mBaseService, arg2);
        }

    }

    public class BinderProxy implements InvocationHandler {
        private ITestBinder mBinder = null;

        public BinderProxy(ITestBinder _binder) {
            mBinder = _binder;
        }

        @Override
        public Object invoke(Object arg0, Method arg1, Object[] arg2) throws Throwable {
            return arg1.invoke(mBinder, arg2);
        }

    }
}
