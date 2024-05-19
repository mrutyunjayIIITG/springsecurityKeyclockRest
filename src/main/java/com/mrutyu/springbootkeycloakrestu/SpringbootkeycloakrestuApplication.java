//package com.mrutyu.springbootkeycloakrestu;
//
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//@SecurityScheme(
//		name = "Keycloak"
//		, openIdConnectUrl = "https://13.232.144.174:8443/realms/dive-dev/.well-known/openid-configuration"
//		, scheme = "bearer"
//		, type = SecuritySchemeType.OPENIDCONNECT
//		, in = SecuritySchemeIn.HEADER
//)
//public class SpringbootkeycloakrestuApplication {
//
//	public static void main(String[] args) {
//
//		SpringApplication.run(SpringbootkeycloakrestuApplication.class, args);
//		System.out.println("Hello World!");
//
//	}
//
//}
package com.mrutyu.springbootkeycloakrestu;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@SpringBootApplication
@SecurityScheme(
		name = "Keycloak"
		, openIdConnectUrl = "https://key.enfyjobs.com/realms/dive-dev/.well-known/openid-configuration"
		, scheme = "bearer"
		, type = SecuritySchemeType.OPENIDCONNECT
		, in = SecuritySchemeIn.HEADER
)
public class SpringbootkeycloakrestuApplication {

	public static void main(String[] args) {
		disableSSLVerification();
		SpringApplication.run(SpringbootkeycloakrestuApplication.class, args);
		System.out.println("Hello World!");
	}

	private static void disableSSLVerification() {
		try {
			// Create a trust manager that trusts all certificates
			TrustManager[] trustAllCerts = new TrustManager[]{
					new X509TrustManager() {
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}

						public void checkClientTrusted(X509Certificate[] certs, String authType) {
						}

						public void checkServerTrusted(X509Certificate[] certs, String authType) {
						}
					}
			};

			// Create an SSL context that disables certificate verification
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

			// Set the default SSL context to use the one we just created
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

			// Optionally, disable hostname verification
			HostnameVerifier hostnameVerifier = (hostname, session) -> true;
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}
	}
}
