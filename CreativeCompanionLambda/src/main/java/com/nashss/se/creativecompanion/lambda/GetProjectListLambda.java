package com.nashss.se.creativecompanion.lambda;

import com.nashss.se.creativecompanion.requests.GetProjectListRequest;
import com.nashss.se.creativecompanion.results.GetProjectListResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetProjectListLambda extends LambdaActivityRunner<GetProjectListRequest, GetProjectListResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetProjectListRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetProjectListRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(() -> input.fromUserClaims(claims ->
                        GetProjectListRequest.builder()
                                .withUserId(claims.get("email"))
                                .build()), (request, serviceComponent) ->
                        serviceComponent.provideGetProjectListActivity().handleRequest(request));
    }
}
