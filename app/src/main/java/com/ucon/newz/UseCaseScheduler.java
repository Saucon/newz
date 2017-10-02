package com.ucon.newz;

/**
 * Created by saucon on 9/22/17.
 */

public interface UseCaseScheduler {
    void execute(Runnable runnable);

    <R extends UseCase.ResponseValues> void notifyResponse(
            final R response,
            final UseCase.UseCaseCallback<R> useCaseCallback);

    <R extends UseCase.ResponseValues> void onError(
            final UseCase.UseCaseCallback<R> useCaseCallback);
}
