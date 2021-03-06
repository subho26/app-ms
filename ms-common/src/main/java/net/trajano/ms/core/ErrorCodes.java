package net.trajano.ms.core;

/**
 * Error codes used in OAuth 2.0 Responses or {@link ErrorResponse}.
 */
public final class ErrorCodes {

    public static final String FORBIDDEN = "forbidden";

    public static final String INVALID_REQUEST = "invalid_request";

    public static final String SERVER_ERROR = "server_error";

    public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";

    public static final String UNSUPPORT_GRANT_TYPE = "unsupported_grant_type";

    public static final String UNSUPPORTED_TOKEN_TYPE = "unsupported_token_type";

    private ErrorCodes() {

    }
}
