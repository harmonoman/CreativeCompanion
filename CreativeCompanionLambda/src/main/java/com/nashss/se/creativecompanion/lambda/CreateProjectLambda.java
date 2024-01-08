package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.activity.request.CreateProjectRequest;
import com.nashss.se.creativecompanion.activity.result.CreateProjectResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;



public class CreateProjectLambda extends LambdaActivityRunner<CreateProjectRequest, CreateProjectResult> implements
        RequestHandler<AuthenticatedLambdaRequest<CreateProjectRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateProjectRequest> input, Context context) {
        return super.runActivity(
            () -> {
                CreateProjectRequest unauthenticatedRequest = input.fromBody(CreateProjectRequest.class);
                return input.fromUserClaims(claims ->
                            CreateProjectRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withProjectName(unauthenticatedRequest.getProjectName())
                                    .replaceSpaces()
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideCreateProjectActivity().handleRequest(request)
        );
    }
}
