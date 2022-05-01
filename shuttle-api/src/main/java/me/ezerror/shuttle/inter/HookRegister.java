package me.ezerror.shuttle.inter;

import com.sun.istack.internal.NotNull;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

public interface HookRegister {
    

    void register(@NotNull Collection<Method> methods);
}
