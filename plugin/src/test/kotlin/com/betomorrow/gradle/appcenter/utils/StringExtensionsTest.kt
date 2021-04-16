package com.betomorrow.gradle.appcenter.utils

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `test truncate`() {
        assertThat("Hello World".truncate(5000)).isEqualTo("Hello World")
        assertThat("Hello World".truncate(5)).isEqualTo("Hello")
    }
}