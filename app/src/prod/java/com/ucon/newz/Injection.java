package com.ucon.newz;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.NewzRepository;
import com.ucon.newz.data.Remote.NewzRemoteDataRepository;
import com.ucon.newz.data.local.NewzLocalDataRepository;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Created by saucon on 9/9/17.
 */

public class Injection {
    public static NewsDataRepository provideRepository(@NonNull Context context) {

        return NewzRepository.getInstance(
                NewzLocalDataRepository.getInstance(context),
                NewzRemoteDataRepository.getInstance(context));
    }
}
