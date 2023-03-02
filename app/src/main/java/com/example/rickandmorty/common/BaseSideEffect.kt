package com.example.rickandmorty.common

interface BaseSideEffect

data class IsErrorData(var errorMessage: Int) : BaseSideEffect
class IsClickBackButton : BaseSideEffect
class IsLoadData : BaseSideEffect
