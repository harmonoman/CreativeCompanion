package com.nashss.se.creativecompanion.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.creativecompanion.requests.DeleteWordPoolRequest;
import com.nashss.se.creativecompanion.results.DeleteWordPoolResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class DeleteWordPoolLambda extends LambdaActivityRunner<DeleteWordPoolRequest, DeleteWordPoolResult>
        implements RequestHandler<AuthenticatedLambdaRequest<DeleteWordPoolRequest>, LambdaResponse>{

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteWordPoolRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    DeleteWordPoolRequest unauthenticatedRequest = input.fromPath(path ->
                            DeleteWordPoolRequest.builder()
                                    .withWordPoolId(path.get("wordPoolId"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            DeleteWordPoolRequest.builder()
                                    .withWordPoolId(unauthenticatedRequest.getWordPoolId())
                                    .withUserId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteWordPoolActivity().handleRequest(request)
        );
    }


}
