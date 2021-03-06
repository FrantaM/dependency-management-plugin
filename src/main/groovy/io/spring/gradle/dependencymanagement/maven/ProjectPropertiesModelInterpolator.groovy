/*
 * Copyright 2014 the original author or authors.
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

package io.spring.gradle.dependencymanagement.maven

import org.gradle.api.Project
import org.gradle.mvn3.org.apache.maven.model.Model
import org.gradle.mvn3.org.apache.maven.model.building.ModelBuildingRequest
import org.gradle.mvn3.org.apache.maven.model.building.ModelProblemCollector
import org.gradle.mvn3.org.apache.maven.model.interpolation.ModelInterpolator
import org.gradle.mvn3.org.apache.maven.model.interpolation.StringSearchModelInterpolator
import org.gradle.mvn3.org.apache.maven.model.path.DefaultPathTranslator
import org.gradle.mvn3.org.apache.maven.model.path.DefaultUrlNormalizer
import org.gradle.mvn3.org.codehaus.plexus.interpolation.MapBasedValueSource
import org.gradle.mvn3.org.codehaus.plexus.interpolation.PropertiesBasedValueSource
import org.gradle.mvn3.org.codehaus.plexus.interpolation.ValueSource

/**
 * A {@link ModelInterpolator} that uses a Gradle project's properties as a {@link ValueSource}
 *
 * @author Andy Wilkinson
 */
class ProjectPropertiesModelInterpolator extends StringSearchModelInterpolator {

    private final Project project

    ProjectPropertiesModelInterpolator(Project project) {
        this.project = project
        setUrlNormalizer(new DefaultUrlNormalizer());
        setPathTranslator(new DefaultPathTranslator());
    }

    List<ValueSource> createValueSources(Model model, File projectDir,
            ModelBuildingRequest request, ModelProblemCollector collector) {
        List valueSources = [
                new MapBasedValueSource(project.properties),
                new PropertiesBasedValueSource(System.getProperties())]
        valueSources.
                addAll(super.createValueSources(model, projectDir, request, collector))
        valueSources
    }
}
