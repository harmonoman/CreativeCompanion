package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.activity.request.UpdateWordPoolRequest;
import com.nashss.se.creativecompanion.activity.result.UpdateWordPoolResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class UpdateWordPoolLambda extends LambdaActivityRunner<UpdateWordPoolRequest, UpdateWordPoolResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateWordPoolRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateWordPoolRequest> input, Context context) {
        return super.runActivity(
            () -> {
                UpdateWordPoolRequest unauthenticatedRequest = input.fromBody(UpdateWordPoolRequest.class);
                return input.fromUserClaims(claims ->
                    UpdateWordPoolRequest.builder()
                            .withUserId(claims.get("email"))
                            .withWordPoolId(unauthenticatedRequest.getWordPoolId())
                            .withWordPoolName(unauthenticatedRequest.getWordPoolName())
                            .withWordPool(unauthenticatedRequest.getWordPool())
                            .build());
            }, (request, serviceComponent) ->
                serviceComponent.provideUpdateWordPoolActivity().handleRequest(request)
        );
    }
}
