package org.macula.cloud.core.command;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.macula.engine.core.domain.OAuth2User;
import org.macula.engine.core.domain.UserSocial;

@Getter
@Setter
@Builder
public class CreateSocialUserCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	private OAuth2User user;

	private UserSocial social;
}
