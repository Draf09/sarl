/*
 * Copyright (C) 2014-2017 the original authors or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.sarl.lang.tests.bugs;

import com.google.inject.Inject;
import org.eclipse.xtend.core.validation.IssueCodes;
import org.eclipse.xtext.xbase.XbasePackage;
import org.eclipse.xtext.xbase.testing.CompilationTestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import io.sarl.lang.SARLVersion;
import io.sarl.lang.sarl.SarlPackage;
import io.sarl.lang.sarl.SarlScript;
import io.sarl.tests.api.AbstractSarlTest;

/** Testing class for issue: Generic type not found
 *
 * <p>https://github.com/sarl/sarl/issues/593
 *
 * @author $Author: sgalland$
 * @version $Name$ $Revision$ $Date$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
@RunWith(Suite.class)
@SuiteClasses({
	Bug593.ImplementsInAbstractClassTypeTest.class,
	Bug593.ExtendsInAbstractClassTypeTest.class,
	Bug593.ImplementsInConcreteClassTypeTest.class,
	Bug593.ExtendsInConcreteClassTypeTest.class,
	Bug593.ExtendsInInterfaceTypeTest.class,
})
@SuppressWarnings("all")
public class Bug593 {

	public static class ImplementsInAbstractClassTypeTest extends AbstractSarlTest {

		private static final String SNIPSET1 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"abstract class Tuple2f<T> implements Serializable {",
				"}");

		private static final String SNIPSET2 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"abstract class Tuple2f<T> implements Serializable, Cloneable {",
				"}");

		private static final String SNIPSET3 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"abstract class Tuple2f<T> implements Serializable, Cloneable, Comparable<T> {",
				"}");

		private static final String SNIPSET4 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"abstract class Tuple2f<T extends Tuple2f<?>> implements Serializable, Cloneable, Comparable<T> {",
				"}");

		@Inject
		private CompilationTestHelper compiler;

		@Test
		public void parsing_01() throws Exception {
			SarlScript mas = file(SNIPSET1);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_01() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET1, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.io.Serializable;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public abstract class Tuple2f<T extends Object> implements Serializable {",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 1L;",
					"}",
					""));
		}

		@Test
		public void parsing_02() throws Exception {
			SarlScript mas = file(SNIPSET2);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_02() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET2, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.io.Serializable;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public abstract class Tuple2f<T extends Object> implements Serializable, Cloneable {",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 1L;",
					"}",
					""));
		}

		@Test
		public void parsing_03() throws Exception {
			SarlScript mas = file(SNIPSET3);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_03() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET3, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.io.Serializable;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public abstract class Tuple2f<T extends Object> implements Serializable, Cloneable, Comparable<T> {",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 1L;",
					"}",
					""));
		}

		@Test
		public void parsing_04() throws Exception {
			SarlScript mas = file(SNIPSET4);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_04() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET4, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.io.Serializable;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public abstract class Tuple2f<T extends Tuple2f<?>> implements Serializable, Cloneable, Comparable<T> {",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 1L;",
					"}",
					""));
		}

	}

	public static class ExtendsInAbstractClassTypeTest extends AbstractSarlTest {

		private static final String SNIPSET1 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"abstract class Tuple2f<T> extends Object {",
				"}");

		private static final String SNIPSET2 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.util.ArrayList",
				"abstract class Tuple2f<T> extends ArrayList<T> {",
				"}");

		private static final String SNIPSET3 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.util.ArrayList",
				"abstract class Tuple2f<T extends Tuple2f<?>> extends ArrayList<T> {",
				"}");

		@Inject
		private CompilationTestHelper compiler;

		@Test
		public void parsing_01() throws Exception {
			SarlScript mas = file(SNIPSET1);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_01() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET1, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public abstract class Tuple2f<T extends Object> {",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"}",
					""));
		}

		@Test
		public void parsing_02() throws Exception {
			SarlScript mas = file(SNIPSET2);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_02() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET2, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.util.ArrayList;",
					"import java.util.Collection;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public abstract class Tuple2f<T extends Object> extends ArrayList<T> {",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f(final int arg0) {",
					"    super(arg0);",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f(final Collection<? extends ?> arg0) {",
					"    super(arg0);",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 1L;",
					"}",
					""));
		}

		@Test
		public void parsing_03() throws Exception {
			SarlScript mas = file(SNIPSET3);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_03() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET3, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.util.ArrayList;",
					"import java.util.Collection;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public abstract class Tuple2f<T extends Tuple2f<?>> extends ArrayList<T> {",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f(final int arg0) {",
					"    super(arg0);",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f(final Collection<? extends ?> arg0) {",
					"    super(arg0);",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 1L;",
					"}",
					""));
		}

	}

	public static class ImplementsInConcreteClassTypeTest extends AbstractSarlTest {

		private static final String SNIPSET1 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"class Tuple2f<T> implements Serializable {",
				"}");

		private static final String SNIPSET2 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"class Tuple2f<T> implements Serializable, Cloneable {",
				"}");

		private static final String SNIPSET3 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"class Tuple2f<T> implements Serializable, Cloneable, Comparable<T> {",
				"  def compareTo(t : T) : int { return 0 }",
				"}");

		private static final String SNIPSET4 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"class Tuple2f<T extends Tuple2f<?>> implements Serializable, Cloneable, Comparable<T> {",
				"  def compareTo(t : T) : int { return 0 }",
				"}");

		@Inject
		private CompilationTestHelper compiler;

		@Test
		public void parsing_01() throws Exception {
			SarlScript mas = file(SNIPSET1);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_01() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET1, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.io.Serializable;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public class Tuple2f<T extends Object> implements Serializable {",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 1L;",
					"}",
					""));
		}

		@Test
		public void parsing_02() throws Exception {
			SarlScript mas = file(SNIPSET2);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_02() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET2, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.io.Serializable;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public class Tuple2f<T extends Object> implements Serializable, Cloneable {",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 1L;",
					"}",
					""));
		}

		@Test
		public void parsing_03() throws Exception {
			SarlScript mas = file(SNIPSET3);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_03() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET3, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.io.Serializable;",
					"import org.eclipse.xtext.xbase.lib.Inline;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public class Tuple2f<T extends Object> implements Serializable, Cloneable, Comparable<T> {",
					"  @Inline(value = \"0\", constantExpression = true)",
					"  public int compareTo(final T t) {",
					"    return 0;",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = -844578811L;",
					"}",
					""));
		}

		@Test
		public void parsing_04() throws Exception {
			SarlScript mas = file(SNIPSET4);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_04() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET4, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.io.Serializable;",
					"import org.eclipse.xtext.xbase.lib.Inline;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public class Tuple2f<T extends Tuple2f<?>> implements Serializable, Cloneable, Comparable<T> {",
					"  @Inline(value = \"0\", constantExpression = true)",
					"  public int compareTo(final T t) {",
					"    return 0;",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = -844578811L;",
					"}",
					""));
		}

	}

	public static class ExtendsInConcreteClassTypeTest extends AbstractSarlTest {

		private static final String SNIPSET1 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"class Tuple2f<T> extends Object {",
				"  public var myvar : T",
				"  def fct : T { null }",
				"}");

		private static final String SNIPSET2 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.util.ArrayList",
				"class Tuple2f<T> extends ArrayList<T> {",
				"  public var myvar : T",
				"  def fct : T { null }",
				"}");

		private static final String SNIPSET3 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.util.ArrayList",
				"class Tuple2f<T extends Tuple2f<?>> extends ArrayList<T> {",
				"  public var myvar : T",
				"  def fct : T { null }",
				"}");

		@Inject
		private CompilationTestHelper compiler;

		@Test
		public void parsing_01() throws Exception {
			SarlScript mas = file(SNIPSET1);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_01() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET1, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import org.eclipse.xtext.xbase.lib.Inline;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public class Tuple2f<T extends Object> {",
					"  public T myvar;",
					"  ",
					"  @Inline(value = \"null\", constantExpression = true)",
					"  public T fct() {",
					"    return null;",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"}",
					""));
		}

		@Test
		public void parsing_02() throws Exception {
			SarlScript mas = file(SNIPSET2);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_02() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET2, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.util.ArrayList;",
					"import java.util.Collection;",
					"import org.eclipse.xtext.xbase.lib.Inline;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public class Tuple2f<T extends Object> extends ArrayList<T> {",
					"  public T myvar;",
					"  ",
					"  @Inline(value = \"null\", constantExpression = true)",
					"  public T fct() {",
					"    return null;",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f(final int arg0) {",
					"    super(arg0);",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f(final Collection<? extends ?> arg0) {",
					"    super(arg0);",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 107525711L;",
					"}",
					""));
		}

		@Test
		public void parsing_03() throws Exception {
			SarlScript mas = file(SNIPSET3);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_03() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET3, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import io.sarl.lang.annotation.SyntheticMember;",
					"import java.util.ArrayList;",
					"import java.util.Collection;",
					"import org.eclipse.xtext.xbase.lib.Inline;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public class Tuple2f<T extends Tuple2f<?>> extends ArrayList<T> {",
					"  public T myvar;",
					"  ",
					"  @Inline(value = \"null\", constantExpression = true)",
					"  public T fct() {",
					"    return null;",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f() {",
					"    super();",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f(final int arg0) {",
					"    super(arg0);",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  public Tuple2f(final Collection<? extends ?> arg0) {",
					"    super(arg0);",
					"  }",
					"  ",
					"  @SyntheticMember",
					"  private final static long serialVersionUID = 107525711L;",
					"}",
					""));
		}

	}

	public static class ExtendsInInterfaceTypeTest extends AbstractSarlTest {

		private static final String SNIPSET1 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"interface Tuple2f<T> extends Serializable {",
				"  def fct : T",
				"}");

		private static final String SNIPSET2 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"interface Tuple2f<T> extends Serializable, Cloneable {",
				"  def fct : T",
				"}");

		private static final String SNIPSET3 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"interface Tuple2f<T> extends Serializable, Cloneable, Comparable<T> {",
				"  def fct : T",
				"}");

		private static final String SNIPSET4 = multilineString(
				"package io.sarl.lang.tests.bug593",
				"import java.io.Serializable",
				"interface Tuple2f<T extends Tuple2f<?>> extends Serializable, Cloneable, Comparable<T> {",
				"  def fct : T",
				"}");

		@Inject
		private CompilationTestHelper compiler;

		@Test
		public void parsing_01() throws Exception {
			SarlScript mas = file(SNIPSET1);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_01() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET1, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import java.io.Serializable;",
					"",
					"@FunctionalInterface",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public interface Tuple2f<T extends Object> extends Serializable {",
					"  public abstract T fct();",
					"}",
					""));
		}

		@Test
		public void parsing_02() throws Exception {
			SarlScript mas = file(SNIPSET2);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_02() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET2, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import java.io.Serializable;",
					"",
					"@FunctionalInterface",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public interface Tuple2f<T extends Object> extends Serializable, Cloneable {",
					"  public abstract T fct();",
					"}",
					""));
		}

		@Test
		public void parsing_03() throws Exception {
			SarlScript mas = file(SNIPSET3);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_03() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET3, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import java.io.Serializable;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public interface Tuple2f<T extends Object> extends Serializable, Cloneable, Comparable<T> {",
					"  public abstract T fct();",
					"}",
					""));
		}

		@Test
		public void parsing_04() throws Exception {
			SarlScript mas = file(SNIPSET4);
			final Validator validator = validate(mas);
			validator.assertNoIssues();
		}

		@Test
		public void compiling_04() throws Exception {
			this.compiler.assertCompilesTo(SNIPSET4, multilineString(
					"package io.sarl.lang.tests.bug593;",
					"",
					"import io.sarl.lang.annotation.SarlSpecification;",
					"import java.io.Serializable;",
					"",
					"@SarlSpecification(\"" + SARLVersion.SPECIFICATION_RELEASE_VERSION_STRING + "\")",
					"@SuppressWarnings(\"all\")",
					"public interface Tuple2f<T extends Tuple2f<?>> extends Serializable, Cloneable, Comparable<T> {",
					"  public abstract T fct();",
					"}",
					""));
		}

	}

}