package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.requests.CreateWordPoolRequest;
import com.nashss.se.creativecompanion.results.CreateWordPoolResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class CreateWordPoolLambda extends LambdaActivityRunner<CreateWordPoolRequest, CreateWordPoolResult> implements
        RequestHandler<AuthenticatedLambdaRequest<CreateWordPoolRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateWordPoolRequest> input, Context context) {
        return super.runActivity(
            () ->
            {
                CreateWordPoolRequest unauthenticatedRequest = input.fromBody(CreateWordPoolRequest.class);
                return input.fromUserClaims(claims ->
                        CreateWordPoolRequest.builder()
                                .withUserId(claims.get("email"))
                                .withWordPoolName(unauthenticatedRequest.getWordPoolName())
                                .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideCreateWordPoolActivity().handleRequest(request)
        );
    }
}
