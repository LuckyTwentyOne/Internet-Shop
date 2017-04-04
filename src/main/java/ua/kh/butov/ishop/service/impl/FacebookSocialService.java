package ua.kh.butov.ishop.service.impl;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.scope.ExtendedPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.User;

import ua.kh.butov.framework.annotation.Component;
import ua.kh.butov.framework.annotation.Value;
import ua.kh.butov.ishop.model.SocialAccount;
import ua.kh.butov.ishop.service.SocialService;

@Component
public class FacebookSocialService implements SocialService {
	@Value("social.facebook.idClient")
	private String idClient;
	@Value("social.facebook.secret")
	private String secret;
	@Value("app.host")
	private String host;
	
	private String getRedirectUrl() {
		return host + "/from-social";
	}

	@Override
	public String getAuthorizeUrl() {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder.addPermission(ExtendedPermissions.EMAIL);
		FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_5);
		return client.getLoginDialogUrl(idClient, getRedirectUrl(), scopeBuilder);
	}

	@Override
	public SocialAccount getSocialAccount(String authToken) {
		FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_5);
		AccessToken accessToken = client.obtainUserAccessToken(idClient, secret, getRedirectUrl(), authToken);
		client = new DefaultFacebookClient(accessToken.getAccessToken(), Version.VERSION_2_5);
		User user = client.fetchObject("me", User.class, Parameter.with("fields", "name,email,first_name,last_name"));
		String avatarUrl = String.format("https://graph.facebook.com/v2.7/%s/picture?type=small", user.getId());
		return new SocialAccount(user.getFirstName(), user.getEmail(), avatarUrl);
	}
}
