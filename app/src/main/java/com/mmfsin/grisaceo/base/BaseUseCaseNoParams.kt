package com.mmfsin.grisaceo.base

abstract class BaseUseCaseNoParams<T> {
    abstract suspend fun execute(): T
}