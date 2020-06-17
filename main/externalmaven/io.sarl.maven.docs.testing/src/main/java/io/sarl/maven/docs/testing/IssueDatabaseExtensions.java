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

package io.sarl.maven.docs.testing;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.arakhne.afc.vmutil.FileSystem;
import org.arakhne.afc.vmutil.json.JsonBuffer;
import org.arakhne.afc.vmutil.json.JsonableObject;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;
import org.slf4j.LoggerFactory;

import io.sarl.lang.validation.IssueCodes;
import io.sarl.lang.validation.SyntaxIssueCodes;

/** Extended Functions for obtaining information on SARL issues.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 0.12
 */
public final class IssueDatabaseExtensions {

	private static String DEFAULT_ISSUE_DESCRIPTION_FILENAME = "issue_descriptions.json"; //$NON-NLS-1$
	
	private static org.slf4j.Logger LOGGER;
	
	private static org.slf4j.Logger getLogger() {
		synchronized (IssueDatabaseExtensions.class) {
			if (LOGGER == null) {
				LOGGER = LoggerFactory.getLogger(IssueDatabaseExtensions.class);
			}
			return LOGGER;
		}
	}
	
	/** Read the default property file that describes the issues of the SARL compiler.
	 *
	 * @return the description of the issues.
	 */
	@Pure
	public static List<IssueDescription> readIssueDescriptions() {
		return readIssueDescriptions(DEFAULT_ISSUE_DESCRIPTION_FILENAME);
	}
	/** Read a property file that describes the issues of the SARL compiler.
	 *
	 * @param filename the file to read.
	 * @return the description of the issues.
	 */
	@Pure
	public static List<IssueDescription> readIssueDescriptions(String filename) {
		File file = new File(filename);
		if (!file.isAbsolute()) {
			final File folder = FileSystem.convertStringToFile(System.getProperty(ScriptExecutor.PROP_CURRENT_FOLDER));
			assert folder != null;
			file  = FileSystem.makeAbsolute(file, folder);
		}
		return readIssueDescriptions(file);
	}

	/** Read a property file that describes the issues of the SARL compiler.
	 *
	 * @param filename the file to read.
	 * @return the description of the issues.
	 */
	@Pure
	public static List<IssueDescription> readIssueDescriptions(File filename) {
		final List<IssueDescription> props = new ArrayList<>();
		final Gson gson = new Gson();
		try (final FileReader stream = new FileReader(filename)) {
			final JsonReader reader = gson.newJsonReader(stream);
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				String code = null;
				String message = null;
				String cause = null;
				String solution = null;
				String levelStr = null;
				while (reader.hasNext()) {
					final String name = reader.nextName();
					switch (Strings.emptyIfNull(name)) {
					case "code":
						code = reader.nextString();
						break;
					case "message":
						message = reader.nextString();
						break;
					case "cause":
						cause = reader.nextString();
						break;
					case "solution":
						solution = reader.nextString();
						break;
					case "level":
						levelStr = reader.nextString();
						break;
					default:
						reader.skipValue();
					}
				}
				reader.endObject();
				if (!Strings.isEmpty(code)) {
					IssueLevel level = null;
					String delegate = null;
					IssueLevel defaultLevel = null;
					level = IssueLevel.fromJson(levelStr);
					if (level == null) {
						throw new IllegalStateException("Error when reading the Json file: Illegal issue level for issue '"
								+ code + "': " + levelStr);
					}
					if (level == IssueLevel.DELEGATE) {
						delegate = level.getDelegate(levelStr);
						if (Strings.isEmpty(delegate)) {
							throw new IllegalStateException("Error when reading the Json file: Illegal delegate issue code for issue '"
									+ code + "': " + levelStr);
						}
					}
					if (level == IssueLevel.CONFIGURABLE || level == IssueLevel.DELEGATE) {
						defaultLevel = level.getDefaultLevel(levelStr);
						if (defaultLevel == null) {
							throw new IllegalStateException("Error when reading the Json file: Illegal default configurable issue level for issue '"
									+ code + "': " + levelStr);
						}
					}
					if (level != IssueLevel.IGNORE) {
						if (Strings.isEmpty(message)) {
							throw new IllegalStateException("Error when reading the Json file: Unspecified message for issue '"
									+ code + "'");
						}
						if (Strings.isEmpty(cause)) {
							throw new IllegalStateException("Error when reading the Json file: Unspecified cause description for issue '"
									+ code + "'");
						}
						if (Strings.isEmpty(levelStr)) {
							throw new IllegalStateException("Error when reading the Json file: Unspecified level for issue '"
									+ code + "'");
						}
					}
					final IssueDescription description = new IssueDescription(code);
					description.message = message;
					description.cause = cause;
					description.solution = solution;
					description.level = level;
					description.delegate = Strings.isEmpty(delegate) ? null : delegate;
					description.defaultLevel = defaultLevel;
					props.add(description);
				}
			}
			reader.endArray();
		} catch (IOException e) {
			throw new RuntimeException("Error when reading the Json file: " + e.getLocalizedMessage(), e);
		}
		getLogger().info("Read " + props.size() + " descriptions of issues from " + filename.getName());
		return props;
	}

	/** Replies a table description for the list of issues.
	 *
	 * <p>The result is a table with 3 columns:<ul>
	 * <li>Column 1: index of the issue</li>
	 * <li>Column 2: the code of the issue</li>
	 * <li>Column 3: the message and description of the issue</li>
	 * <li>Column 4: the importance level of the issue</li>
	 * </ul>
	 *
	 * @param descriptions the list of issue descriptions to render.
	 * @param sort indicates if the table rows should be sorted on simples codes.
	 * @return the table content.
	 */
	@Pure
	public static List<List<String>> asTable(List<IssueDescription> descriptions) {
		final List<List<String>> content = new ArrayList<>();
		int major = 0;
		int minor = 1;
		IssueDescription prevIssue = null;
		for (int i = 0; i < descriptions.size(); ++i) {
			final IssueDescription description = descriptions.get(i);
			if (description.level != IssueLevel.IGNORE) {
				final List<String> line = new ArrayList<>();
	
				if (Strings.equal(prevIssue == null ? null : prevIssue.getCode(), description.getCode())) {
					++minor;
				} else {
					++major;
					minor = 1;
				}
	
				final IssueDescription nextIssue;
				if (i < descriptions.size() - 1) {
					nextIssue = descriptions.get(i + 1);
				} else {
					nextIssue = null;
				}
	
				line.add(formatIndex(major, minor, prevIssue, description, nextIssue));
				
				line.add(description.getDisplayCode());
				final StringBuilder msg = new StringBuilder();
				msg.append(head("Message:")).append(description.message).append(nl());
				msg.append(head("Cause:")).append(description.cause);
				if (!Strings.isEmpty(description.solution)) {
					msg.append(nl()).append(head("Solving:")).append(description.solution);
				}
				if (!Strings.isEmpty(description.delegate)) {
					msg.append(nl()).append(head("Delegated to:")).append(description.delegate);
				}
				line.add(msg.toString());
				line.add(description.level.getLabel(description.delegate, description.defaultLevel));
				content.add(line);
	
				prevIssue = description;
			}
		}
		getLogger().info("Generate table for " + descriptions.size() + " messages and " + major + " issues");
		return content;
	}

	private static String formatIndex(int major, int minor, IssueDescription prevIssue, IssueDescription currentIssue,
			IssueDescription nextIssue) {
		String pcode = prevIssue == null ? null : prevIssue.getCode();
		String ccode = currentIssue == null ? null : currentIssue.getCode();
		String ncode = nextIssue == null ? null : nextIssue.getCode();
		if (Strings.equal(pcode, ccode) || Strings.equal(ccode, ncode)) {
			// Long numbering
			final char c = (char) ((int) 'a' + (minor - 1));
			return major + Character.toString(c);
		}
		// Short numbering
		return Integer.toString(major);
	}

	/** Sorts the given list and replies it.
	 *
	 * @param descriptions the list to sort.
	 * @return {@code descriptions}.
	 */
	@Pure
	public static List<IssueDescription> sort(List<IssueDescription> descriptions) {
		descriptions.sort((a, b) -> {
			int cmp = a.getDisplayCode().compareTo(b.getDisplayCode());
			if (cmp != 0) {
				return cmp;
			}
			cmp = a.getCodePrefix().compareTo(b.getCodePrefix());
			if (cmp != 0) {
				return cmp;
			}
			cmp = a.getSortLevel().compareTo(b.getSortLevel());
			if (cmp != 0) {
				return cmp;
			}
			return a.message.compareTo(b.message);
		});
		return descriptions;
	}

	private static String head(String value) {
		return "**" + value + "** ";
	}

	private static String nl() {
		return "\n";
	}

	/** Validate the issue codes in order they are found into the SARL, Xtend, Xbase and Xtext issue codes.
	 *
	 * @param descriptions the list of issue descriptions to render.
	 * @return {@code descriptions}
	 */
	@Pure
	@Inline("validate($1, true)")
	public static List<IssueDescription> validate(List<IssueDescription> descriptions) {
		return validate(descriptions, true);
	}

	/** Validate the issue codes in order they are found into the SARL, Xtend, Xbase and Xtext issue codes.
	 *
	 * @param descriptions the list of issue descriptions to render.
	 * @param failIfNotDocumented indicates if the function failed if an issue code is not documented.
	 * @return {@code descriptions}
	 */
	@Pure
	public static List<IssueDescription> validate(List<IssueDescription> descriptions, boolean failIfNotDocumented) {
		final Set<String> definedCodes = buildIssueCodeList();
		getLogger().info("Found " + definedCodes.size() + " issue codes from the SARL compiler source code");
		if (!validateIssueCodes(descriptions, failIfNotDocumented, definedCodes)) {
			throw new IllegalStateException("Error when validating the documented issue codes. See error(s) above for details.");
		}
		return descriptions;
	}

	private static boolean validateIssueCodes(List<IssueDescription> descriptions, boolean failIfNotDocumented, Set<String> definedCodes) {
		boolean success = true;
		final Set<String> notDocumentedCodes = new TreeSet<>();
		notDocumentedCodes.addAll(definedCodes);
		for (final IssueDescription description : descriptions) {
			notDocumentedCodes.remove(description.getCode());
			if (!definedCodes.contains(description.getCode())) {
				success = false;
				getLogger().error("Too much documented: '" + description.getCode() + "'. This issue code is not found into the SARL source code");
			}
		}
		if (!success) {
			return false;
		}
		for (String notDocumentedCode : notDocumentedCodes) {
			success = false;
			getLogger().error("Not enough documented: '" + notDocumentedCode + "'. This issue code has no documentation");
		}
		return success;
	}

	private static Set<String> buildIssueCodeList() {
		final Set<String> codes = new TreeSet<>();
		// Validation issues
		extractIssueCodes(IssueCodes.class, codes);
		extractIssueCodes(org.eclipse.xtend.core.validation.IssueCodes.class, codes);
		extractIssueCodes(org.eclipse.xtext.xbase.validation.IssueCodes.class, codes);
		// Generation issues
		extractIssueCodes(org.eclipse.xtext.validation.IssueCodes.class, codes);
		// Syntax issues
		extractIssueCodes(SyntaxIssueCodes.class, codes);
		codes.add(Diagnostic.SYNTAX_DIAGNOSTIC);
		codes.add(Diagnostic.SYNTAX_DIAGNOSTIC_WITH_RANGE);
		// Linking issues
		codes.add(Diagnostic.LINKING_DIAGNOSTIC);
		return codes;
	}

	private static void extractIssueCodes(Class<?> definitions, Set<String> codes) {
		for (final Field field : definitions.getDeclaredFields()) {
			if (String.class.equals(field.getType()) && Modifier.isStatic(field.getModifiers())
					&& Modifier.isPublic(field.getModifiers())) {
				try {
					final String value = (String) field.get(null);
					if (!Strings.isEmpty(value) && !value.endsWith(".")) {
						codes.add(value);
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/** Description of an issue.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 0.12
	 */
	public static final class IssueDescription implements JsonableObject {

		/** Internal code of the issue.
		 */
		private final String rawCode;
	
		/** Internal display code of the issue.
		 */
		private final String displayCode;

		/** Error message associated to the issue.
		 */
		public String message;

		/** Explanation of the issue's cause.
		 */
		public String cause;

		/** Guideline for solving the issue.
		 */
		public String solution;

		/** Level of importance for the issue.
		 */
		public IssueLevel level;

		/** Code of the issue to which the importance level determination is delegated to.
		 */
		public String delegate;

		/** Default level of importance for the issue when the {@link #level} is {@link IssueLevel#CONFIGURABLE}.
		 */
		public IssueLevel defaultLevel;

		/** Constructor.
		 *
		 * @param code the code; with possible {@code &amp;dot;} inside.
		 */
		IssueDescription(final String code) {
			final int lastIndex = code.lastIndexOf('.');
			this.rawCode = code.replaceAll(Pattern.quote("&dot;"), ".");
			if (lastIndex >= 0 && lastIndex < code.length() - 1) {
				this.displayCode = code.substring(lastIndex + 1).replaceAll(Pattern.quote("&dot;"), ".");
			} else {
				this.displayCode = code;
			}
		}
		
		
		@Override
		public String toString() {
			final JsonBuffer buf = new JsonBuffer();
			toJson(buf);
			return buf.toString();
		}

		@Override
		public void toJson(JsonBuffer buf) {
			buf.add("rawCode", this.rawCode);
			buf.add("displayCode", this.displayCode);
			buf.add("message", this.message);
			buf.add("cause", this.cause);
			buf.add("solving", this.solution);
			buf.add("level", this.level.toJson(this.delegate, this.defaultLevel.toJson(null, null)));
		}

		/** Replies the raw code.
		 *
		 * @return the raw code.
		 */
		public String getCode() {
			return this.rawCode;
		}
		
		/** Replies the prefix of the code.
		 *
		 * @return the prefix.
		 */
		public String getCodePrefix() {
			final int lastIndex = this.rawCode.lastIndexOf('.');
			if (lastIndex > 0) {
				return this.rawCode.substring(0, lastIndex);
			}
			return ""; //$NON-NLS-1$
		}

		/** Replies the base of the code to be displayed.
		 *
		 * @return the display code.
		 */
		public String getDisplayCode() {
			return this.displayCode;
		}

		private IssueLevel getSortLevel() {
			if (this.level == IssueLevel.CONFIGURABLE) {
				return this.defaultLevel;
			}
			return this.level;
		}

	}

	/** Level of an issue.
	 *
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 0.12
	 */
	public enum IssueLevel {
		/** It is an error.
		 */
		ERROR {
			@Override
			public String toJson(String delegate, String defaultLevel) {
				return "error";
			}
			@Override
			public String getLabel(String delegate, IssueLevel defaultLevel) {
				return "Error";
			}
			@Override
			public String getDelegate(String json) {
				return null;
			}
			@Override
			public IssueLevel getDefaultLevel(String json) {
				return null;
			}
		},
		/** It is a warning.
		 */
		WARNING {
			@Override
			public String toJson(String delegate, String defaultLevel) {
				return "warning";
			}
			@Override
			public String getLabel(String delegate, IssueLevel defaultLevel) {
				return "Warning";
			}
			@Override
			public String getDelegate(String json) {
				return null;
			}
			@Override
			public IssueLevel getDefaultLevel(String json) {
				return null;
			}
		},
		/** It is an information message.
		 */
		INFO {
			@Override
			public String toJson(String delegate, String defaultLevel) {
				return "info";
			}
			@Override
			public String getLabel(String delegate, IssueLevel defaultLevel) {
				return "Information";
			}
			@Override
			public String getDelegate(String json) {
				return null;
			}
			@Override
			public IssueLevel getDefaultLevel(String json) {
				return null;
			}
		},
		/** Issue is ignored.
		 */
		IGNORE {
			@Override
			public String toJson(String delegate, String defaultLevel) {
				return "ignore";
			}
			@Override
			public String getLabel(String delegate, IssueLevel defaultLevel) {
				return "Ignored";
			}
			@Override
			public String getDelegate(String json) {
				return null;
			}
			@Override
			public IssueLevel getDefaultLevel(String json) {
				return null;
			}
		},
		/** Level is configurable by the SARL user into the compiler configuration.
		 */
		CONFIGURABLE {
			@Override
			public String toJson(String delegate, String defaultLevel) {
				return "c/" + defaultLevel.toLowerCase();
			}
			@Override
			public String getLabel(String delegate, IssueLevel defaultLevel) {
				if (defaultLevel != null) {
					return "Configurable; Default is: " + defaultLevel.getLabel(null, null);
				}
				return "Configurable";
			}
			@Override
			public String getDelegate(String json) {
				return null;
			}
			@Override
			public IssueLevel getDefaultLevel(String json) {
				try {
					if (json.startsWith("c/")) {
						return fromJson(json.substring(2));
					}
				} catch (Throwable e) {
					//
				}
				return null;
			}
		},
		/** Level is delegated to another issue (usually inside the Eclipse Java Compiler) in order
		 * to determine the issue level. A default level may be specified in the delegate cannot provide
		 * one.
		 */
		DELEGATE {
			@Override
			public String toJson(String delegate, String defaultLevel) {
				assert delegate != null;
				return "d/" + delegate + "/" + defaultLevel.toLowerCase();
			}
			@Override
			public String getLabel(String delegate, IssueLevel defaultLevel) {
				if (defaultLevel != null) {
					return "Delegated; Default is: " + defaultLevel.getLabel(null, null);
				}
				return "Delegated";
			}
			@Override
			public String getDelegate(String json) {
				try {
					if (json.startsWith("d/")) {
						final int idx = json.indexOf('/', 2);
						if (idx > 2) {
							return json.substring(2, idx);
						}
					}
				} catch (Throwable e) {
					//
				}
				return null;
			}
			@Override
			public IssueLevel getDefaultLevel(String json) {
				try {
					if (json.startsWith("d/")) {
						final int idx = json.indexOf('/', 2);
						if (idx >= 2 && idx < json.length() - 1) {
							return fromJson(json.substring(idx + 1));
						}
					}
				} catch (Throwable e) {
					//
				}
				return null;
			}
		};

		/** Replies the id that is usefull for Json storage.
		 *
		 * @param delegate the issue code to which the level is delegate to. It may be {@code null}.
		 * @param defaultLevel the default level in case of configurable level. It may be {@code null}.
		 * @return the Json id.
		 */
		public abstract String toJson(String delegate, String defaultLevel);

		/** Replies enumeration from a Json string.
		 *
		 * @param label the Json label.
		 * @return the enumeration.
		 */
		public static IssueLevel fromJson(String label) {
			try {
				if (label.startsWith("c/")) {
					return CONFIGURABLE;
				}
				if (label.startsWith("d/")) {
					return DELEGATE;
				}
				return valueOf(label.toUpperCase());
			} catch (Throwable e) {
				//
			}
			return null;
		}

		/** Parse from a Json string the delegate issue code for a configurable level.
		 *
		 * @param label the Json label.
		 * @return the delegat eissue coe.
		 */
		public abstract String getDelegate(String label);

		/** Parse from a Json string the default level for a configurable level.
		 *
		 * @param label the Json label.
		 * @return the default level.
		 */
		public abstract IssueLevel getDefaultLevel(String label);

		/** Replies the human-readable label.
		 *
		 * @param delegate the issue code to which the level is delegate to. It may be {@code null}.
		 * @param defaultLevel the default level in case of configurable level.
		 * @return the label.
		 */
		public abstract String getLabel(String delegate, IssueLevel defaultLevel);

	}

}