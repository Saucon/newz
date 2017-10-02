package com.ucon.newz;

/**
 * Created by saucon on 10/2/17.
 */

public class TestUseCaseScheduler implements UseCaseScheduler {
    @Override
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Override
    public <R extends UseCase.ResponseValues> void onError(UseCase.UseCaseCallback<R> useCaseCallback) {
       useCaseCallback.onError();
    }

    @Override
    public <R extends UseCase.ResponseValues> void notifyResponse(R response, UseCase.UseCaseCallback<R> useCaseCallback) {
        useCaseCallback.onSuccess(response);
    }
}
