package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.activity.request.GetProjectRequest;
import com.nashss.se.creativecompanion.activity.result.GetProjectResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetProjectLambda
        extends LambdaActivityRunner<GetProjectRequest, GetProjectResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetProjectRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetProjectRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                GetProjectRequest unauthenticatedRequest = input.fromPath(path ->
                            GetProjectRequest.builder()
                                    .withProjectId(path.get("projectId"))
                                    .build());
                return input.fromUserClaims(claims ->
                            GetProjectRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withProjectId(unauthenticatedRequest.getProjectId())
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideGetProjectActivity().handleRequest(request)
        );

    }
}
