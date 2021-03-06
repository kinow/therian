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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import therian.TherianModule;
import therian.operation.Convert;
import therian.operator.ELCoercionConverter;
import therian.position.Ref;
import therian.testfixture.MetasyntacticVariable;


public class ELCoercionConverterTest extends TransformerTest {

    @Override
    protected TherianModule[] modules() {
        return new TherianModule[] { TherianModule.create().withOperators(new ELCoercionConverter()) };
    }

    @Test
    public void testCoerciontoString() {
        assertEquals("666", therianContext.eval(Convert.to(String.class, Ref.to(Integer.valueOf(666)))));
    }

    @Test
    public void testCoerciontoEnum() {
        assertNull(therianContext.eval(Convert.to(MetasyntacticVariable.class, new Ref<Object>(null) {})));
        assertNull(therianContext.eval(Convert.to(MetasyntacticVariable.class, Ref.to(""))));
        assertSame(MetasyntacticVariable.FOO, therianContext.eval(Convert.to(MetasyntacticVariable.class, Ref.to("FOO"))));
    }

    @Test
    public void testCoercionToBoolean() {
        assertFalse(therianContext.eval(Convert.<Object, Boolean> to(Boolean.class, new Ref<Object>(null) {}))
            .booleanValue());
        assertFalse(therianContext.eval(Convert.<String, Boolean> to(Boolean.class, Ref.to(""))).booleanValue());
        assertFalse(therianContext.eval(Convert.<String, Boolean> to(Boolean.class, Ref.to("false"))).booleanValue());
        assertFalse(therianContext.eval(Convert.<String, Boolean> to(Boolean.class, Ref.to("whatever"))).booleanValue());
        assertTrue(therianContext.eval(Convert.<String, Boolean> to(Boolean.class, Ref.to("true"))).booleanValue());
    }

}
