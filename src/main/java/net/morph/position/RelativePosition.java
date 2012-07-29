/*
 *  Copyright the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package net.morph.position;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Type;

/**
 * {@link Position} relative to some other {@link Position.Readable}.
 * 
 * @param <P>
 * @param <T>
 */
public abstract class RelativePosition<P, T> implements Position<T> {

    /**
     * Describes a {@link RelativePosition.Mixin}.
     */
    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface Implements {
        @SuppressWarnings("rawtypes")
        Class<? extends Position>[] value();
    }

    /**
     * Uses an {@link Implements} annotation to declare the {@link Position} interface(s) for which it provides an
     * implementation, defining corresponding methods prepending the parent position as an argument.
     * 
     * @param <P>
     * @param <T>
     */
    public interface Mixin<P, T> {
    }

    /**
     * Get type relative to parent position.
     * 
     * @param <P>
     * @param <T>
     */
    @Implements(Position.class)
    public interface GetType<P, T> extends Mixin<P, T> {
        Type getType(Position.Readable<? extends P> parentPosition);
    }

    /**
     * Get value relative to parent position.
     * 
     * @param <P>
     * @param <T>
     */
    @Implements(Position.Readable.class)
    public interface GetValue<P, T> extends Mixin<P, T> {
        T getValue(Position.Readable<? extends P> parentPosition);
    }

    /**
     * Set value relative to parent position.
     * 
     * @param <P>
     * @param <T>
     */
    @Implements(Position.Writable.class)
    public interface SetValue<P, T> extends Mixin<P, T> {
        void setValue(Position.Readable<? extends P> parentValue, T value);
    }

    private final Position.Readable<? extends P> parentPosition;
    private final RelativePositionFactory<P, T, ? extends Position<T>> factory;

    protected RelativePosition(Position.Readable<? extends P> parentPosition,
        RelativePositionFactory<P, T, ? extends Position<T>> factory) {
        super();
        this.parentPosition = parentPosition;
        this.factory = factory;
    }

    public final Position.Readable<? extends P> getParentPosition() {
        return parentPosition;
    }

    public final RelativePositionFactory<P, T, ? extends Position<T>> getFactory() {
        return factory;
    }
}
