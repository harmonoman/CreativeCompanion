package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.activity.request.UpdateProjectRequest;
import com.nashss.se.creativecompanion.activity.result.UpdateProjectResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateProjectLambda extends LambdaActivityRunner<UpdateProjectRequest, UpdateProjectResult>
        implements RequestHandler<AuthenticatedLambdaRequest<UpdateProjectRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<UpdateProjectRequest> input, Context context) {
        return super.runActivity(() -> {
                UpdateProjectRequest unauthenticatedRequest = input.fromBody(UpdateProjectRequest.class);
                return input.fromUserClaims(claims ->
                            UpdateProjectRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withProjectId(unauthenticatedRequest.getProjectId())
                                    .withProjectName(unauthenticatedRequest.getProjectName())
                                    .withWordPool(unauthenticatedRequest.getWordPool())
                                    .withWorkspace(unauthenticatedRequest.getWorkspace())
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideUpdateProjectActivity().handleRequest(request)
        );
    }
}
