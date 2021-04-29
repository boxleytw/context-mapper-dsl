/*
 * Copyright 2021 The Context Mapper Project Team
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
package org.contextmapper.dsl.generator.plantuml;

import org.contextmapper.dsl.contextMappingDSL.Aggregate;
import org.contextmapper.dsl.validation.ValidationMessages;
import org.contextmapper.tactic.dsl.tacticdsl.SimpleDomainObject;
import org.eclipse.xtext.EcoreUtil2;

import com.google.common.collect.Lists;

public class PlantUMLAggregateClassDiagramCreator extends AbstractPlantUMLClassDiagramCreator<Aggregate> implements PlantUMLDiagramCreator<Aggregate> {

	@Override
	protected void printDiagramContent(Aggregate aggregate) {
		this.relationships = Lists.newArrayList();
		this.extensions = Lists.newArrayList();
		this.domainObjects = EcoreUtil2.<SimpleDomainObject>getAllContentsOfType(aggregate, SimpleDomainObject.class);
		if (this.domainObjects.size() <= 0) {
			printEmptyDiagramNote();
			return;
		}
		printAggregate(aggregate, 0);
		printReferences(0);
	}

	private void printEmptyDiagramNote() {
		sb.append("note").append(" ").append("\"").append(ValidationMessages.EMPTY_UML_CLASS_DIAGRAM_MESSAGE_AGGREGATE).append("\"").append(" as EmptyDiagramError");
		linebreak();
	}

}
