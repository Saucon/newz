package com.ucon.newz;

/**
 * Created by saucon on 9/22/17.
 */

public abstract class UseCase<Q extends UseCase.RequestValues,R extends UseCase.ResponseValues> {


    private UseCaseCallback<R> mUseCaseCallback;

    private Q mRequestValues;


    void run() {
        executeUseCase(mRequestValues);
    }

    protected abstract void executeUseCase(Q requestValues);



    public UseCaseCallback<R> getUseCaseCallback() {
        return mUseCaseCallback;
    }

    public void setUseCaseCallback(UseCaseCallback<R> mUseCaseCallback) {
        this.mUseCaseCallback = mUseCaseCallback;
    }

    public Q getRequestValues() {
        return mRequestValues;
    }

    public void setRequestValues(Q mRequestValues) {
        this.mRequestValues = mRequestValues;
    }


    public interface RequestValues{};
    public interface ResponseValues{};

    public interface UseCaseCallback<R> {
        void onSuccess(R response);
        void onError();
    }

}
