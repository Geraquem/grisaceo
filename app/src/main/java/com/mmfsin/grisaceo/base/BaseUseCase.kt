package com.mmfsin.grisaceo.base

abstract class BaseUseCase<params, T> {
    abstract suspend fun execute(params: params): T
}