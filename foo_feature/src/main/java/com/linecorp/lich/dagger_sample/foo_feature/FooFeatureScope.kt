package com.linecorp.lich.dagger_sample.foo_feature

import javax.inject.Scope

/**
 * A [Scope] for singleton objects in the `foo_feature` module.
 */
@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FooFeatureScope
