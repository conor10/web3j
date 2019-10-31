/*
 * Copyright 2019 Web3 Labs Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.web3j.utils;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.web3j.utils.Bytes.trimLeadingZeroes;

public class BytesTest {

    @Test
    public void testTrimLeadingZeroes() {
        assertThat(trimLeadingZeroes(new byte[] {}), is(new byte[] {}));
        assertThat(trimLeadingZeroes(new byte[] {0}), is(new byte[] {0}));
        assertThat(trimLeadingZeroes(new byte[] {1}), is(new byte[] {1}));
        assertThat(trimLeadingZeroes(new byte[] {0, 1}), is(new byte[] {1}));
        assertThat(trimLeadingZeroes(new byte[] {0, 0, 1}), is(new byte[] {1}));
        assertThat(trimLeadingZeroes(new byte[] {0, 0, 1, 0}), is(new byte[] {1, 0}));
    }
}
