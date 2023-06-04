package com.pet.server.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


public class UrlUtils {

	/**
	 * Returns the viewName to return for coming back to the sender url
	 *
	 * @param request Instance of {@link HttpServletRequest} or use an injected instance
	 * @return Optional with the view name. Recomended to use an alternativa url with
	 * {@link Optional#orElse(Object)}
	 */
	public static Optional<String> getPreviousPageByRequest(HttpServletRequest request)
	{
		return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
	}
}
