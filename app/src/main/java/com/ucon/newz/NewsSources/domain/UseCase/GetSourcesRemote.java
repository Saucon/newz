package com.ucon.newz.NewsSources.domain.UseCase;

import com.ucon.newz.NewsSources.domain.model.Sources;
import com.ucon.newz.UseCase;
import com.ucon.newz.data.NewsDataRepository;
import com.ucon.newz.data.NewzRepository;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

/**
 * Created by saucon on 10/2/17.
 */

public class GetSourcesRemote extends UseCase<GetSourcesRemote.RequestValues, GetSourcesRemote.ResponseValues> {

    private final NewzRepository mNewzRepository;

    public GetSourcesRemote(NewzRepository newzRepository){
        mNewzRepository = newzRepository;
    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        try {
            mNewzRepository.getSourcesRemoteOnly(new NewsDataRepository.LoadSourceCallback() {
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

    public static final class ResponseValues implements UseCase.ResponseValues{
        List<Sources> mSources;

        public ResponseValues(List<Sources> sources){
            mSources = sources;
        }

        public List<Sources> getSources(){
            return mSources;
        }
    }

    public static final class RequestValues implements UseCase.RequestValues{

    }
}
