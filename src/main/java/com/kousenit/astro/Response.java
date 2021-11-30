package com.kousenit.astro;

public sealed interface Response<T>
    permits Success, Failure { }

record Success<T>(T data) implements Response<T> { }

record Failure<T>(RuntimeException exception) implements Response<T> { }
