package com.kousenit.astro;

public interface Gateway<T> {
    Response<T> getResponse();
}
