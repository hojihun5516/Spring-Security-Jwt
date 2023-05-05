package com.example.springsecurityjwt.support


import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.decorator.optional.AlwaysOptionalStrategy
import com.appmattus.kotlinfixture.decorator.optional.optionalStrategy
import com.appmattus.kotlinfixture.decorator.recursion.NullRecursionStrategy
import com.appmattus.kotlinfixture.decorator.recursion.recursionStrategy
import com.appmattus.kotlinfixture.kotlinFixture

object Support {
    /**
     * recursionStrategy: 객체 안에서 무한 재귀를 방지하기 위해 최초 생성된 객체 이후에는 null이 반환됩니다.
     * optionalStrategy: nullable 타입은 null 값을 포함하는 랜덤 데이터가 생성됩니다.
     * nullabilityStrategy: 생성된 객체들의 프로퍼티 중에서 null이 될 수 없는(non-null) 타입은 null을 포함하지 않는 랜덤 데이터가 생성되며,
     */
    val fixture = kotlinFixture {
        recursionStrategy(NullRecursionStrategy)
        optionalStrategy(AlwaysOptionalStrategy)
        nullabilityStrategy(NeverNullStrategy)
    }
}

