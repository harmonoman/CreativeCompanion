package com.nashss.se.creativecompanion.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.creativecompanion.requests.UpdateProjectRequest;
import com.nashss.se.creativecompanion.requests.UpdateWordPoolRequest;
import com.nashss.se.creativecompanion.results.UpdateWordPoolResult;

public class UpdateWordPoolLambda extends LambdaActivityRunner<UpdateWordPoolRequest, UpdateWordPoolResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateWordPoolRequest>, LambdaResponse>{

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
                },
                (request, serviceComponent) ->
                        serviceComponent.provideUpdateWordPoolActivity().handleRequest(request)
        );
    }
}
