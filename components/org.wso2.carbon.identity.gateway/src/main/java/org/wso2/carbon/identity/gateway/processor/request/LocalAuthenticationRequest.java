package org.wso2.carbon.identity.gateway.processor.request;

import org.wso2.carbon.identity.gateway.api.FrameworkRuntimeException;


public class LocalAuthenticationRequest extends CallbackAuthenticationRequest {

    private String authenticatorName;
    private String identityProviderName;

    protected LocalAuthenticationRequest(FrameworkLoginBuilder builder) {
        super(builder);
        authenticatorName = builder.authenticatorName;
        identityProviderName = builder.identityProviderName ;
    }

    public String getAuthenticatorName() {
        return authenticatorName;
    }

    public String getIdentityProviderName() {
        return identityProviderName;
    }

    public static class FrameworkLoginBuilder extends CallbackAuthenticationRequestBuilder {

        private String authenticatorName;
        private String identityProviderName;

        public FrameworkLoginBuilder() {
            super();
        }



        public FrameworkLoginBuilder setAuthenticatorName(String authenticatorName) {
            this.authenticatorName = authenticatorName;
            return this;
        }
        public FrameworkLoginBuilder setIdentityProviderName(String identityProviderName) {
            this.identityProviderName = identityProviderName;
            return this;
        }

        @Override
        public LocalAuthenticationRequest build() throws FrameworkRuntimeException {
            return new LocalAuthenticationRequest(this);
        }
    }

    public static class FrameworkLoginRequestConstants extends CallbackAuthenticationRequest.LocalAuthenticationRequestConstants {
        public static final String AUTHENTICATOR_NAME = "authenticator";
        public static final String IDP_NAME = "idp";
    }
}