package ua.kh.butov.ishop.service;

import ua.kh.butov.ishop.model.SocialAccount;

public interface SocialService {

	String getAuthorizeUrl();

	SocialAccount getSocialAccount(String authToken);
}
