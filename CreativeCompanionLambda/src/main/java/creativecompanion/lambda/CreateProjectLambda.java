package creativecompanion.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import creativecompanion.requests.CreateProjectRequest;
import creativecompanion.results.CreateProjectResult;

public class CreateProjectLambda {
     extends LambdaActivityRunner<CreateProjectRequest, CreateProjectResult>
        implements RequestHandler<AuthenticatedLambdaRequest<CreateProjectRequest>, LambdaResponse> {
        @Override
        public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateProjectRequest> input, Context context) {
            return super.runActivity(
                    () -> {
                        CreateProjectRequest unauthenticatedRequest = input.fromBody(CreateProjectRequest.class);
                        return input.fromUserClaims(claims ->
                                CreateProjectRequest.builder()
                                        .withProjectName(unauthenticatedRequest.getProjectName())
                                        .withUserId(claims.get("email"))
                                        .build());
                    },
                    (request, serviceComponent) ->
                            serviceComponent.provideCreateProjectActivity().handleRequest(request)
            );
        }
}
