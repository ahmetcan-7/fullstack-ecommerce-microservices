package com.ahmetcan7.userservice.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class LoginAttemptService {
    private static final int MAXIMUM_NUMBER_OF_ATTEMPTS = 5;
    private static final int ATTEMPT_INCREMENT = 1;
    private LoadingCache<String, Integer> loginAttemptCache;

    public LoginAttemptService() {
        super();
        loginAttemptCache = CacheBuilder.newBuilder().expireAfterWrite(15, MINUTES)
                .maximumSize(100).build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void evictUserFromLoginAttemptCache(String email) {
        loginAttemptCache.invalidate(email);
    }

    public void addUserToLoginAttemptCache(String email) {
        int attempts = 0;
        try {
            attempts = ATTEMPT_INCREMENT + loginAttemptCache.get(email);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        loginAttemptCache.put(email, attempts);
    }

    public boolean hasExceededMaxAttempts(String email) {
        try {
            return loginAttemptCache.get(email) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
