package com.example.rickandmorty.common

interface BaseSideEffect

data class IsErrorData(var errorMessage: String) : BaseSideEffect
class IsEmptyFilter : BaseSideEffect
class IsLoadData : BaseSideEffect
