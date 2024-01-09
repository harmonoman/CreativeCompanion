package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.activity.request.GetWordPoolByNameRequest;
import com.nashss.se.creativecompanion.activity.result.GetWordPoolByNameResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetWordPoolByNameLambda extends LambdaActivityRunner<GetWordPoolByNameRequest, GetWordPoolByNameResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetWordPoolByNameRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetWordPoolByNameRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    GetWordPoolByNameRequest unauthenticatedRequest = input.fromPath(path ->
                            GetWordPoolByNameRequest.builder()
                                    .withWordPoolName(path.get("wordPoolName"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            GetWordPoolByNameRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withWordPoolName(unauthenticatedRequest.getWordPoolName())
                                    .replaceSpaces('_')
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetWordPoolByNameActivity().handleRequest(request)
        );

    }

}

