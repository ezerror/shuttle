package me.ezerror.shuttle.entity;

public class RunnableMethodParam {
    public Object[] invocationArguments;
    public String invocationPath;
    public String[] parameters;
    public Boolean[] isParam;

    public Object proxy;

    public RunnableMethodParam(String invocationPath, String[] parameters, Object[] invocationArguments, Boolean[] isParam) {
        this.invocationArguments = invocationArguments;
        this.invocationPath = invocationPath;
        this.parameters = parameters;
        this.isParam = isParam;
    }

    public void modifyBy(RunnableMethodParam afterRunnableMethodParam) {
        for (int i = 0; i < invocationArguments.length; i++) {
            invocationArguments[i] = afterRunnableMethodParam.invocationArguments[i];
        }
    }


    public Object getProxy() {
        return proxy;
    }

    public void setProxy(Object proxy) {
        this.proxy = proxy;
    }
}
