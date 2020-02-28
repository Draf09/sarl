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
package io.sarl.core.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.sarl.lang.core.AgentContext;
import io.sarl.lang.core.Capacity;
import io.sarl.lang.core.Event;
import io.sarl.lang.core.EventSpace;
import io.sarl.lang.core.Scope;
import io.sarl.lang.core.Space;
import io.sarl.lang.core.SpaceID;

/**
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public class ExternalContextAccessTest extends AbstractSarlCoreTest<Capacity> {

	/**
	 */
	@BeforeEach
	public void setUp() {
		loadSARL("io.sarl.core.ExternalContextAccess", Capacity.class); //$NON-NLS-1$
	}

	/**
	 */
	@Test
	public void memberCount() {
		assertEquals(10, this.type.getDeclaredMethods().length);
	}

	/**
	 */
	@Test
	public void getAllContexts() {
		assertMethod("getAllContexts", ConcurrentLinkedDeque.class); //$NON-NLS-1$
	}

	/**
	 */
	@Test
	public void getContext() {
		assertMethod("getContext", AgentContext.class, UUID.class); //$NON-NLS-1$
	}

	/**
	 */
	@Test
	public void getUniverseContext() {
		assertMethod("getUniverseContext", AgentContext.class); //$NON-NLS-1$
	}

	/**
	 */
	@Test
	public void join() {
		assertMethod("join", boolean.class, UUID.class, UUID.class); //$NON-NLS-1$
	}

	/**
	 */
	@Test
	public void leave() {
		assertMethod("leave", boolean.class, UUID.class); //$NON-NLS-1$
	}

	/**
	 */
	@Test
	public void emit_EventSpaceEventScope() {
		assertMethod("emit", void.class, EventSpace.class, Event.class, Scope.class); //$NON-NLS-1$
	}

	/**
	 */
	@Test
	public void emit_EventSpaceEvent() {
		assertMethod("emit", void.class, EventSpace.class, Event.class); //$NON-NLS-1$
	}

	/**
	 */
	@Test
	public void isInSpace_EventSpace() {
		assertMethod("isInSpace", boolean.class, Event.class, Space.class); //$NON-NLS-1$
	}

	/**
	 */
	@Test
	public void isInSpace_EventSpaceID() {
		assertMethod("isInSpace", boolean.class, Event.class, SpaceID.class); //$NON-NLS-1$
	}
	  
	/**
	 */
	@Test
	public void isInSpace_EventUUID() {
		assertMethod("isInSpace", boolean.class, Event.class, UUID.class); //$NON-NLS-1$
	}

}
