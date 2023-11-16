package com.nashss.se.creativecompanion.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.creativecompanion.requests.GetProjectRequest;
import com.nashss.se.creativecompanion.requests.GetWordPoolRequest;
import com.nashss.se.creativecompanion.results.GetProjectResult;
import com.nashss.se.creativecompanion.results.GetWordPoolResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class GetWordPoolLambda extends LambdaActivityRunner<GetWordPoolRequest, GetWordPoolResult>
        implements RequestHandler<AuthenticatedLambdaRequest<GetWordPoolRequest>, LambdaResponse>{

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetWordPoolRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    GetWordPoolRequest unauthenticatedRequest = input.fromPath(path ->
                            GetWordPoolRequest.builder()
                                    .withWordPoolId(path.get("wordPoolId"))
                                    .build());
                    return input.fromUserClaims(claims ->
                            GetWordPoolRequest.builder()
                                    .withWordPoolId(unauthenticatedRequest.getWordPoolId())
                                    .withUserId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetWordPoolActivity().handleRequest(request)
        );
    }

}
