package uelbox;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ValueExpression;
import javax.el.VariableMapper;

import org.apache.commons.lang3.Validate;

/**
 * ELContext wrapper which wraps the ELResolver and may shadow variables, locale settings, and context objects.
 */
public abstract class ELContextWrapper extends ELContext {
    private final ELResolver elResolver;
    private final VariableMapper variableMapper;
    protected final ELContext wrapped;

    /**
     * Create a new ELContextWrapper.
     *
     * @param wrapped
     */
    protected ELContextWrapper(ELContext wrapped) {
        this.wrapped = Validate.notNull(wrapped);
        this.elResolver = Validate.notNull(wrap(wrapped.getELResolver()));
        this.variableMapper = new SimpleVariableMapper() {
            @Override
            public ValueExpression resolveVariable(String variable) {
                if (containsVariable(variable)) {
                    return super.resolveVariable(variable);
                }
                return ELContextWrapper.this.wrapped.getVariableMapper().resolveVariable(variable);
            }
        };
        setLocale(wrapped.getLocale());
    }

    protected abstract ELResolver wrap(ELResolver elResolver);

    @Override
    public ELResolver getELResolver() {
        return elResolver;
    }

    @Override
    public FunctionMapper getFunctionMapper() {
        return wrapped.getFunctionMapper();
    }

    @Override
    public VariableMapper getVariableMapper() {
        return variableMapper;
    }

    @Override
    public Object getContext(Class<?> key) {
        Object result = super.getContext(key);
        return result == null ? wrapped.getContext(key) : result;
    }

    /**
     * Convenience method to return a typed context object
     * when key resolves per documented convention to an object of the same type.
     *
     * @param key
     * @param <T>
     * @return T
     * @see ELContext#getContext(Class)
     */
    public final <T> T getTypedContext(Class<T> key) {
        return (T) getContext(key);
    }

}
