package org.macula.cloud.core.command;

import java.io.Serializable;

import org.macula.cloud.core.domain.OAuth2User;
import org.macula.cloud.core.domain.UserSocial;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateSocialUserCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	private OAuth2User user;

	private UserSocial social;
}
