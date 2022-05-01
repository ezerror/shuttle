package me.ezerror.shuttle.core.aspect;

import me.ezerror.shuttle.inter.HookRegister;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;

import static net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.RETRANSFORMATION;
import static net.bytebuddy.matcher.ElementMatchers.named;

@Component
@Order(2)
public class ShuttleAspectRegister implements HookRegister {

    /**
     * 应用于
     *
     * @param agentBuilder
     * @return
     */
    private static AgentBuilder applyHooks(AgentBuilder agentBuilder,Collection<Method> methods) {
        for (Method method : methods) {
            agentBuilder = agentBuilder
                    .type(ElementMatchers.hasSuperType(named(method.getDeclaringClass().getName())))
                    .transform((builder, typeDescription, classLoader, module) -> builder
                            .visit(Advice.to(ShuttleCoreAspect.class).on(named(method.getName()))));
        }
        return agentBuilder;
    }
    
    @Override
    public void register(Collection<Method> methods) {
        ByteBuddyAgent.install();
        AgentBuilder agentBuilder = new AgentBuilder.Default()
                .disableClassFormatChanges()
                .with(RETRANSFORMATION)
                .with(AgentBuilder.RedefinitionStrategy.Listener.StreamWriting.toSystemError())
                .with(AgentBuilder.Listener.StreamWriting.toSystemError().withTransformationsOnly())
                .with(AgentBuilder.InstallationListener.StreamWriting.toSystemError());
        agentBuilder = applyHooks(agentBuilder,methods);
        agentBuilder.installOnByteBuddyAgent();
    }
}
