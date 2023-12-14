package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.activity.request.GetWordPoolRequest;
import com.nashss.se.creativecompanion.activity.result.GetWordPoolResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class GetWordPoolLambda extends LambdaActivityRunner<GetWordPoolRequest, GetWordPoolResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetWordPoolRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetWordPoolRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(() -> {
                GetWordPoolRequest unauthenticatedRequest = input.fromPath(path ->
                        GetWordPoolRequest.builder()
                                .withWordPoolId(path.get("wordPoolId"))
                                .build());
                return input.fromUserClaims(claims ->
                    GetWordPoolRequest.builder()
                            .withWordPoolId(unauthenticatedRequest.getWordPoolId())
                            .withUserId(claims.get("email"))
                            .build());
            }, (request, serviceComponent) ->
                serviceComponent.provideGetWordPoolActivity().handleRequest(request)
        );
    }

}
