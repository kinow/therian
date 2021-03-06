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
package therian.operator;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;


import org.apache.commons.lang3.reflect.TypeUtils;

import therian.Operator;
import therian.operation.Convert;

/**
 * {@link Convert} {@link Operator} superclass.
 * 
 * @param <SOURCE>
 * @param <DEST>
 */
public abstract class Converter<SOURCE, DEST> implements Operator<Convert<? extends SOURCE, ? super DEST>> {
    private static final TypeVariable<?>[] TYPE_PARAMS = Converter.class.getTypeParameters();

    public boolean supports(Convert<? extends SOURCE, ? super DEST> convert) {
        final Map<TypeVariable<?>, Type> typeArguments = TypeUtils.getTypeArguments(getClass(), Converter.class);
        return TypeUtils.isInstance(convert.getSourcePosition().getValue(), typeArguments.get(TYPE_PARAMS[0]))
            && TypeUtils.isAssignable(typeArguments.get(TYPE_PARAMS[1]), convert.getTargetPosition().getType());
    }
}
