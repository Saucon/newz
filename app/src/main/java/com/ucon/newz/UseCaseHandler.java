package com.ucon.newz;

/**
 * Created by saucon on 9/22/17.
 */

public class UseCaseHandler {
    private static UseCaseHandler INSTANCE;
    private final UseCaseScheduler mUseCaseScheduler;

    public UseCaseHandler(UseCaseScheduler useCaseScheduler){
        mUseCaseScheduler = useCaseScheduler;
    }

    public static UseCaseHandler getInstance(){
     if (INSTANCE == null) {
        INSTANCE = new UseCaseHandler(new UseCaseThreadPoolScheduler());
    }
        return INSTANCE;
    }

    public <Q extends UseCase.RequestValues,R extends UseCase.ResponseValues> void execute(
            final UseCase<Q,R> useCase, Q values, UseCase.UseCaseCallback<R> useCaseCallback){

        useCase.setRequestValues(values);
        useCase.setUseCaseCallback(new UiCallbackWrapper<R>(useCaseCallback, this));

        mUseCaseScheduler.execute(new Runnable() {
            @Override
            public void run() {
                useCase.run();

            }
        });

    }

    public <V extends UseCase.ResponseValues> void notifyResponse(final V response,
                                                                 final UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.notifyResponse(response, useCaseCallback);
    }

    private <V extends UseCase.ResponseValues> void notifyError(
            final UseCase.UseCaseCallback<V> useCaseCallback) {
        mUseCaseScheduler.onError(useCaseCallback);
    }

    private static final class UiCallbackWrapper<V extends UseCase.ResponseValues> implements
            UseCase.UseCaseCallback<V> {
        private final UseCase.UseCaseCallback<V> mCallback;
        private final UseCaseHandler mUseCaseHandler;

        public UiCallbackWrapper(UseCase.UseCaseCallback<V> callback,
                                 UseCaseHandler useCaseHandler) {
            mCallback = callback;
            mUseCaseHandler = useCaseHandler;
        }

        @Override
        public void onSuccess(V response) {
            mUseCaseHandler.notifyResponse(response, mCallback);
        }

        @Override
        public void onError() {
            mUseCaseHandler.notifyError(mCallback);
        }
    }

}
