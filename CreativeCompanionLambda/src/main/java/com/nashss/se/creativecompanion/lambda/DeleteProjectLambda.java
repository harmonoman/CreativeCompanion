package com.nashss.se.creativecompanion.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.creativecompanion.requests.DeleteProjectRequest;
import com.nashss.se.creativecompanion.requests.GetProjectRequest;
import com.nashss.se.creativecompanion.results.DeleteProjectResult;
import com.nashss.se.creativecompanion.results.GetProjectResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class DeleteProjectLambda extends LambdaActivityRunner<DeleteProjectRequest, DeleteProjectResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteProjectRequest>, LambdaResponse>{

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteProjectRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    DeleteProjectRequest unauthenticatedRequest = input.fromPath(path ->
                            DeleteProjectRequest.builder()
                                    .withProjectId(path.get("projectId"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            DeleteProjectRequest.builder()
                                    .withProjectId(unauthenticatedRequest.getProjectId())
                                    .withUserId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteProjectActivity().handleRequest(request)
        );
    }
}
