package me.ezerror.shuttle.inter;

import java.io.Serializable;

public class Holder<T> implements Cloneable, Serializable {

    private T value;

    public Holder(T value) {
        this.value = value;
    }


    public T get() {
        return this.value;
    }

    public void set(final T value) {
        this.value = value;
    }

    /**
     * 重写clone方法，保证一致性
     *
     */
    public Object clone() {
        Holder<?> o = null;
        try {
            o = (Holder<?>) super.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (this.getClass() == obj.getClass()) {
            final Holder<?> that = (Holder<?>) obj;
            return this.value.equals(that.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value == null ? 0 : value.hashCode();
    }

}
