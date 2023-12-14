package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.activity.request.GetProjectByNameRequest;
import com.nashss.se.creativecompanion.activity.result.GetProjectByNameResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetProjectByNameLambda extends LambdaActivityRunner<GetProjectByNameRequest, GetProjectByNameResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetProjectByNameRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetProjectByNameRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
            () -> {
                GetProjectByNameRequest unauthenticatedRequest = input.fromPath(path ->
                            GetProjectByNameRequest.builder()
                                    .withProjectName(path.get("projectName"))
                                    .build());
                return input.fromUserClaims(claims ->
                            GetProjectByNameRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .withProjectName(unauthenticatedRequest.getProjectName())
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideGetProjectByNameActivity().handleRequest(request)
        );

    }

}
