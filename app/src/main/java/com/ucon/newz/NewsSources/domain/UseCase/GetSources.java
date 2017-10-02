package com.ucon.newz.NewsSources.domain.UseCase;

import android.support.annotation.NonNull;

import com.google.common.reflect.ClassPath;
import com.ucon.newz.NewsSources.domain.model.Sources;
import com.ucon.newz.UseCase;
import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.NewzRepository;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okio.Source;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by saucon on 9/22/17.
 */

public class GetSources extends UseCase<GetSources.RequestValues, GetSources.ResponseValues> {

    private final NewzRepository mNewzRepository;

    public GetSources(@NonNull NewzRepository newzRepository){
        mNewzRepository = checkNotNull(newzRepository, "NewzRepository can't be null ");
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {

        try {
            mNewzRepository.getSources(new NewsDataRepository.LoadSourceCallback() {
                @Override
                public void OnTaskLoaded(List<Sources> cursor) throws IOException, JSONException {
                    ResponseValues responseValues = new ResponseValues(cursor);
                    getUseCaseCallback().onSuccess(responseValues);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            getUseCaseCallback().onError();
        } catch (JSONException e) {
            e.printStackTrace();
            getUseCaseCallback().onError();
        }
    }

    public static final class RequestValues implements UseCase.RequestValues{

    }

    public static final class ResponseValues implements UseCase.ResponseValues{
        List<Sources> mSources;

        public ResponseValues(@NonNull List<Sources> sources){
            mSources = sources;
        }

        public List<Sources> getSources(){
            return mSources;
        }
    }
    
}
