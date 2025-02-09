package com.Caching.CachingLearning.Caching.in.spring.boot.Exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
