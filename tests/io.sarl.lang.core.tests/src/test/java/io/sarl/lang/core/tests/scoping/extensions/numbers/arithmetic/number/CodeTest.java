/*
 * $Id$
 *
 * SARL is an general-purpose agent programming language.
 * More details on http://www.sarl.io
 *
 * Copyright (C) 2014-2020 the original authors or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.sarl.lang.core.tests.scoping.extensions.numbers.arithmetic.number;

import static io.sarl.lang.scoping.extensions.numbers.arithmetic.NumberArithmeticExtensions.operator_divide;
import static io.sarl.lang.scoping.extensions.numbers.arithmetic.NumberArithmeticExtensions.operator_minus;
import static io.sarl.lang.scoping.extensions.numbers.arithmetic.NumberArithmeticExtensions.operator_modulo;
import static io.sarl.lang.scoping.extensions.numbers.arithmetic.NumberArithmeticExtensions.operator_multiply;
import static io.sarl.lang.scoping.extensions.numbers.arithmetic.NumberArithmeticExtensions.operator_plus;
import static io.sarl.lang.scoping.extensions.numbers.arithmetic.NumberArithmeticExtensions.operator_power;
import static io.sarl.tests.api.tools.TestAssertions.assertEpsilonEquals;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.util.concurrent.AtomicDouble;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.sarl.tests.api.AbstractSarlTest;

/**
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @see "https://github.com/eclipse/xtext-extras/issues/186"
 */
@SuppressWarnings("all")
@DisplayName("Calling Number arithmetic operators")
public class CodeTest extends AbstractSarlTest {

	private static Number left = new AtomicDouble(4);

	private static int right = 3;

	@Test
	public void operator_minus_Number() throws Exception {
		assertEpsilonEquals(-4, operator_minus(left));
	}

	@Test
	public void operator_minus_Number_Number() throws Exception {
		assertEpsilonEquals(1., operator_minus(left, (Number) Double.valueOf(right)));
	}

	@Test
	public void operator_minus_Number_long() throws Exception {
		assertEpsilonEquals(1, operator_minus(left, (long) right));
	}

	@Test
	public void operator_minus_Number_Long() throws Exception {
		assertEpsilonEquals(1, operator_minus(left, Long.valueOf(right)));
	}

	@Test
	public void operator_minus_Number_byte() throws Exception {
		assertEpsilonEquals(1, operator_minus(left, (byte)right));
	}

	@Test
	public void operator_minus_Number_Byte() throws Exception {
		assertEpsilonEquals(1, operator_minus(left, Byte.valueOf((byte)right)));
	}

	@Test
	public void operator_minus_Number_float() throws Exception {
		assertEpsilonEquals(1f, operator_minus(left, (float) right));
	}

	@Test
	public void operator_minus_Number_Float() throws Exception {
		assertEpsilonEquals(1f, operator_minus(left, Float.valueOf(right)));
	}

	@Test
	public void operator_minus_Number_int() throws Exception {
		assertEpsilonEquals(1, operator_minus(left, (int) right));
	}

	@Test
	public void operator_minus_Number_Integer() throws Exception {
		assertEpsilonEquals(1, operator_minus(left, Integer.valueOf(right)));
	}

	@Test
	public void operator_minus_Number_short() throws Exception {
		assertEpsilonEquals(1, operator_minus(left, (short) right));
	}

	@Test
	public void operator_minus_Number_Short() throws Exception {
		assertEpsilonEquals(1, operator_minus(left, Short.valueOf((short) right)));
	}

	@Test
	public void operator_minus_Number_AtomicInteger() throws Exception {
		assertEpsilonEquals(1, operator_minus(left, new AtomicInteger(right)));
	}

	@Test
	public void operator_minus_Number_AtomicLong() throws Exception {
		assertEpsilonEquals(1l, operator_minus(left, new AtomicLong(right)));
	}

	@Test
	public void operator_plus_Number_long() throws Exception {
		assertEpsilonEquals(7l, operator_plus(left, (long) right));
	}

	@Test
	public void operator_plus_Number_Long() throws Exception {
		assertEpsilonEquals(7l, operator_plus(left, Long.valueOf(right)));
	}

	@Test
	public void operator_plus_Number_byte() throws Exception {
		assertEpsilonEquals(7, operator_plus(left, (byte) right));
	}

	@Test
	public void operator_plus_Number_Byte() throws Exception {
		assertEpsilonEquals(7, operator_plus(left, Byte.valueOf((byte) right)));
	}

	@Test
	public void operator_plus_Number_float() throws Exception {
		assertEpsilonEquals(7f, operator_plus(left, (float) right));
	}

	@Test
	public void operator_plus_Number_Float() throws Exception {
		assertEpsilonEquals(7f, operator_plus(left, Float.valueOf(right)));
	}

	@Test
	public void operator_plus_Number_int() throws Exception {
		assertEpsilonEquals(7, operator_plus(left, (int) right));
	}

	@Test
	public void operator_plus_Number_Integer() throws Exception {
		assertEpsilonEquals(7, operator_plus(left, Integer.valueOf(right)));
	}

	@Test
	public void operator_plus_Number_short() throws Exception {
		assertEpsilonEquals(7, operator_plus(left, (short) right));
	}

	@Test
	public void operator_plus_Number_Short() throws Exception {
		assertEpsilonEquals(7, operator_plus(left, Short.valueOf((short) right)));
	}

	@Test
	public void operator_plus_Number_AtomicInteger() throws Exception {
		assertEpsilonEquals(7, operator_plus(left, new AtomicInteger(right)));
	}

	@Test
	public void operator_plus_Number_AtomicLong() throws Exception {
		assertEpsilonEquals(7l, operator_plus(left, new AtomicLong(right)));
	}

	@Test
	public void operator_plus_Number_Number() throws Exception {
		assertEpsilonEquals(7., operator_plus(left, (Number) Double.valueOf(right)));
	}

	@Test
	public void operator_power_Number_Number() throws Exception {
		assertEpsilonEquals(64., operator_power(left, (Number) Integer.valueOf(right)));
	}

	@Test
	public void operator_power_Number_byte() throws Exception {
		assertEpsilonEquals(64., operator_power(left, (byte) right));
	}

	@Test
	public void operator_power_Number_short() throws Exception {
		assertEpsilonEquals(64., operator_power(left, (short) right));
	}

	@Test
	public void operator_power_Number_int() throws Exception {
		assertEpsilonEquals(64., operator_power(left, (int) right));
	}

	@Test
	public void operator_power_Number_long() throws Exception {
		assertEpsilonEquals(64., operator_power(left, (long) right));
	}

	@Test
	public void operator_power_Number_float() throws Exception {
		assertEpsilonEquals(64., operator_power(left, (float) right));
	}

	@Test
	public void operator_power_Number_double() throws Exception {
		assertEpsilonEquals(64., operator_power(left, (double) right));
	}

	@Test
	public void operator_divide_Number_long() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, (long) right));
	}

	@Test
	public void operator_divide_Number_Long() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, Long.valueOf(right)));
	}

	@Test
	public void operator_divide_Number_byte() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, (byte) right));
	}

	@Test
	public void operator_divide_Number_Byte() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, Byte.valueOf((byte) right)));
	}

	@Test
	public void operator_divide_Number_float() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, (float) right));
	}

	@Test
	public void operator_divide_Number_Float() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, Float.valueOf(right)));
	}

	@Test
	public void operator_divide_Number_int() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, (int) right));
	}

	@Test
	public void operator_divide_Number_Integer() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, Integer.valueOf(right)));
	}

	@Test
	public void operator_divide_Number_Number() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, (Number) Long.valueOf(right)));
	}

	@Test
	public void operator_divide_Number_short() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, (short) right));
	}

	@Test
	public void operator_divide_Number_Short() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, Short.valueOf((short) right)));
	}

	@Test
	public void operator_divide_Number_AtomicInteger() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, new AtomicInteger(right)));
	}

	@Test
	public void operator_divide_Number_AtomicLong() throws Exception {
		assertEpsilonEquals(1.333333333333333333333, operator_divide(left, new AtomicLong(right)));
	}

	@Test
	public void operator_multiply_Number_long() throws Exception {
		assertEpsilonEquals(12l, operator_multiply(left, (long) right));
	}

	@Test
	public void operator_multiply_Number_Long() throws Exception {
		assertEpsilonEquals(12l, operator_multiply(left, Long.valueOf(right)));
	}

	@Test
	public void operator_multiply_Number_byte() throws Exception {
		assertEpsilonEquals(12, operator_multiply(left, (byte) right));
	}

	@Test
	public void operator_multiply_Number_Byte() throws Exception {
		assertEpsilonEquals(12, operator_multiply(left, Byte.valueOf((byte) right)));
	}

	@Test
	public void operator_multiply_Number_float() throws Exception {
		assertEpsilonEquals(12f, operator_multiply(left, (float) right));
	}

	@Test
	public void operator_multiply_Number_Float() throws Exception {
		assertEpsilonEquals(12f, operator_multiply(left, Float.valueOf(right)));
	}

	@Test
	public void operator_multiply_Number_int() throws Exception {
		assertEpsilonEquals(12, operator_multiply(left, (int) right));
	}

	@Test
	public void operator_multiply_Number_Integer() throws Exception {
		assertEpsilonEquals(12, operator_multiply(left, Integer.valueOf(right)));
	}

	@Test
	public void operator_multiply_Number_Number() throws Exception {
		assertEpsilonEquals(12., operator_multiply(left, (Number) Long.valueOf(right)));
	}

	@Test
	public void operator_multiply_Number_short() throws Exception {
		assertEpsilonEquals(12, operator_multiply(left, (short) right));
	}

	@Test
	public void operator_multiply_Number_Short() throws Exception {
		assertEpsilonEquals(12, operator_multiply(left, Short.valueOf((short) right)));
	}

	@Test
	public void operator_multiply_Number_AtomicInteger() throws Exception {
		assertEpsilonEquals(12, operator_multiply(left, new AtomicInteger(right)));
	}

	@Test
	public void operator_multiply_Number_AtomicLong() throws Exception {
		assertEpsilonEquals(12l, operator_multiply(left, new AtomicLong(right)));
	}

	@Test
	public void operator_modulo_Number_long() throws Exception {
		assertEpsilonEquals(1l, operator_modulo(left, (long) right));
	}

	@Test
	public void operator_modulo_Number_Long() throws Exception {
		assertEpsilonEquals(1l, operator_modulo(left, Long.valueOf(right)));
	}

	@Test
	public void operator_modulo_Number_byte() throws Exception {
		assertEpsilonEquals(1l, operator_modulo(left, (byte) right));
	}

	@Test
	public void operator_modulo_Number_Byte() throws Exception {
		assertEpsilonEquals(1l, operator_modulo(left, Byte.valueOf((byte) right)));
	}

	@Test
	public void operator_modulo_Number_float() throws Exception {
		assertEpsilonEquals(1f, operator_modulo(left, (float) right));
	}

	@Test
	public void operator_modulo_Number_Float() throws Exception {
		assertEpsilonEquals(1f, operator_modulo(left, Float.valueOf(right)));
	}

	@Test
	public void operator_modulo_Number_int() throws Exception {
		assertEpsilonEquals(1, operator_modulo(left, (int) right));
	}

	@Test
	public void operator_modulo_Number_Integer() throws Exception {
		assertEpsilonEquals(1, operator_modulo(left, Integer.valueOf(right)));
	}

	@Test
	public void operator_modulo_Number_Number() throws Exception {
		assertEpsilonEquals(1., operator_modulo(left, (Number) Integer.valueOf(right)));
	}

	@Test
	public void operator_modulo_Number_short() throws Exception {
		assertEpsilonEquals(1, operator_modulo(left, (short) right));
	}

	@Test
	public void operator_modulo_Number_Short() throws Exception {
		assertEpsilonEquals(1, operator_modulo(left, Short.valueOf((short) right)));
	}

	@Test
	public void operator_modulo_Number_AtomicInteger() throws Exception {
		assertEpsilonEquals(1, operator_modulo(left, new AtomicInteger(right)));
	}

	@Test
	public void operator_modulo_Number_AtomicLong() throws Exception {
		assertEpsilonEquals(1l, operator_modulo(left, new AtomicLong(right)));
	}

}
