package com.nashss.se.creativecompanion.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.creativecompanion.requests.CreateProjectRequest;
import com.nashss.se.creativecompanion.results.CreateProjectResult;

public class CreateProjectLambda extends LambdaActivityRunner<CreateProjectRequest, CreateProjectResult> implements
        RequestHandler<AuthenticatedLambdaRequest<CreateProjectRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateProjectRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromBody(CreateProjectRequest.class),
//            {
//                CreateProjectRequest unauthenticatedRequest = input.fromBody(CreateProjectRequest.class);
//                return input.fromUserClaims(claims ->
//                            CreateProjectRequest.builder()
//                                    .withUserId(claims.get("email"))
//                                    .withProjectName(unauthenticatedRequest.getProjectName())
//                                    .build());
//            },
            (request, serviceComponent) ->
                        serviceComponent.provideCreateProjectActivity().handleRequest(request)
        );
    }
}
