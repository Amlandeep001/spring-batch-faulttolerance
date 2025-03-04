package com.spring.batch.faulttolerance.config;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.stereotype.Component;

@Component
public class ExceptionSkipPolicy implements SkipPolicy
{
    @Override
    public boolean shouldSkip(Throwable throwable, long skipCount) throws SkipLimitExceededException
    {
        return throwable instanceof NumberFormatException;
    }
}
