package com.ucon.newz;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ucon.newz.NewsSources.domain.UseCase.GetSources;
import com.ucon.newz.NewsSources.domain.UseCase.GetSourcesRemote;
import com.ucon.newz.data.FakeLocalDatsImpl;
import com.ucon.newz.data.FakeSourceDataImpl;
import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.NewzRepository;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by saucon on 9/9/17.
 */

public class Injection {

    public static NewsDataRepository provideRepository(@NonNull Context context) {

        return NewzRepository.getInstance(FakeLocalDatsImpl.getInstance(), FakeSourceDataImpl.getInstance());
    }

    public static GetSources provideGetSources(@NonNull Context context){
        return new GetSources((NewzRepository) provideRepository(context));
    }

    public static UseCaseHandler  provideUseCaseHandler(@NonNull Context context){
        return UseCaseHandler.getInstance();
    }

    public static GetSourcesRemote provideGetSourcesRemote(@NonNull Context context){
        return new GetSourcesRemote((NewzRepository) provideRepository(context));
    }
}
