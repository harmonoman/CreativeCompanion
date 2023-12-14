package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.activity.request.GetWordPoolListRequest;
import com.nashss.se.creativecompanion.activity.result.GetWordPoolListResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetWordPoolListLambda extends LambdaActivityRunner<GetWordPoolListRequest, GetWordPoolListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetWordPoolListRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetWordPoolListRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> input.fromUserClaims(claims ->
                        GetWordPoolListRequest.builder()
                                .withUserId(claims.get("email"))
                                .build()),
            (request, serviceComponent) ->
                        serviceComponent.provideGetWordPoolListActivity().handleRequest(request)
        );
    }
}
