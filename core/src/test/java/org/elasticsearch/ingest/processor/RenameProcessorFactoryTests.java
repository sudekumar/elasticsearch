/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.ingest.processor;

import org.elasticsearch.ingest.core.AbstractProcessorFactory;
import org.elasticsearch.ingest.core.Processor;
import org.elasticsearch.test.ESTestCase;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class RenameProcessorFactoryTests extends ESTestCase {

    public void testCreate() throws Exception {
        RenameProcessor.Factory factory = new RenameProcessor.Factory();
        Map<String, Object> config = new HashMap<>();
        config.put("field", "old_field");
        config.put("to", "new_field");
        String processorTag = randomAsciiOfLength(10);
        config.put(AbstractProcessorFactory.TAG_KEY, processorTag);
        RenameProcessor renameProcessor = factory.create(config);
        assertThat(renameProcessor.getTag(), equalTo(processorTag));
        assertThat(renameProcessor.getOldFieldName(), equalTo("old_field"));
        assertThat(renameProcessor.getNewFieldName(), equalTo("new_field"));
    }

    public void testCreateNoFieldPresent() throws Exception {
        RenameProcessor.Factory factory = new RenameProcessor.Factory();
        Map<String, Object> config = new HashMap<>();
        config.put("to", "new_field");
        try {
            factory.create(config);
            fail("factory create should have failed");
        } catch(ConfigurationPropertyException e) {
            assertThat(e.getMessage(), equalTo("[field] required property is missing"));
        }
    }

    public void testCreateNoToPresent() throws Exception {
        RenameProcessor.Factory factory = new RenameProcessor.Factory();
        Map<String, Object> config = new HashMap<>();
        config.put("field", "old_field");
        try {
            factory.create(config);
            fail("factory create should have failed");
        } catch(ConfigurationPropertyException e) {
            assertThat(e.getMessage(), equalTo("[to] required property is missing"));
        }
    }
}
