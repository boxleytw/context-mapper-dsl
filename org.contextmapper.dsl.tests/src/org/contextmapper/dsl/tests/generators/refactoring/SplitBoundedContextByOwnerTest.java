/*
 * Copyright 2018 The Context Mapper Project Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.contextmapper.dsl.tests.generators.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.contextmapper.dsl.contextMappingDSL.ContextMappingModel;
import org.contextmapper.dsl.refactoring.SplitBoundedContextByOwner;
import org.contextmapper.dsl.refactoring.SplitBoundedContextByUseCases;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterators;

public class SplitBoundedContextByOwnerTest extends AbstractRefactoringTest {

	@Test
	void canSplitByOwner() throws IOException {
		// given
		String inputModelName = "split-bc-by-owner-test-1-input.cml";
		Resource input = getResourceCopyOfTestCML(inputModelName);

		// when
		SplitBoundedContextByOwner ar = new SplitBoundedContextByOwner("CustomerManagement");
		ar.doRefactor(input);

		// then
		List<ContextMappingModel> contextMappingModels = IteratorExtensions
				.<ContextMappingModel>toList(Iterators.<ContextMappingModel>filter(reloadResource(input).getAllContents(), ContextMappingModel.class));
		assertEquals(4, contextMappingModels.get(0).getBoundedContexts().size());
		List<String> boundedContextNames = contextMappingModels.get(0).getBoundedContexts().stream().map(bc -> bc.getName()).collect(Collectors.toList());
		assertTrue(boundedContextNames.contains("CustomerManagement"));
		assertTrue(boundedContextNames.contains("NewBoundedContext1"));
	}
	
	@Test
	void canSplitWithMultipleAggregatesPerOwner() throws IOException {
		// given
		String inputModelName = "split-bc-by-owner-test-2-input.cml";
		Resource input = getResourceCopyOfTestCML(inputModelName);

		// when
		SplitBoundedContextByOwner ar = new SplitBoundedContextByOwner("CustomerManagement");
		ar.doRefactor(input);

		// then
		List<ContextMappingModel> contextMappingModels = IteratorExtensions
				.<ContextMappingModel>toList(Iterators.<ContextMappingModel>filter(reloadResource(input).getAllContents(), ContextMappingModel.class));
		assertEquals(4, contextMappingModels.get(0).getBoundedContexts().size());
		List<String> boundedContextNames = contextMappingModels.get(0).getBoundedContexts().stream().map(bc -> bc.getName()).collect(Collectors.toList());
		assertTrue(boundedContextNames.contains("CustomerManagement"));
		assertTrue(boundedContextNames.contains("NewBoundedContext1"));
	}
	
	@Test
	void canSplitIfThereIsNothingToSplit() throws IOException {
		// given
		String inputModelName = "split-bc-by-owner-test-3-input.cml";
		Resource input = getResourceCopyOfTestCML(inputModelName);

		// when
		SplitBoundedContextByOwner ar = new SplitBoundedContextByOwner("CustomerManagement");
		ar.doRefactor(input);

		// then
		List<ContextMappingModel> contextMappingModels = IteratorExtensions
				.<ContextMappingModel>toList(Iterators.<ContextMappingModel>filter(reloadResource(input).getAllContents(), ContextMappingModel.class));
		assertEquals(1, contextMappingModels.get(0).getBoundedContexts().size());
	}

}
