package mx.com.truper.springboot.practica26.zuul.apigateway.filter.post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServiceUnavailablePostFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return RequestContext.getCurrentContext().getResponseStatusCode() == 500 ? true : false;
	}

	@Override
	public Object run() throws ZuulException {

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest servletRequest = ctx.getRequest();
		
		@SuppressWarnings("unused")
		HttpServletResponse servletResponse = ctx.getResponse();

		log.info("[{} ServiceUnavailablePostFilter {}] Request Method: {}, Request URL: {}", filterType(),
				filterOrder(), servletRequest.getMethod(), servletRequest.getRequestURL().toString());

		if (ctx.getResponseStatusCode() == 500) {

			System.out.println(ctx.get("serviceId"));

			ctx.setResponseStatusCode(HttpStatus.SERVICE_UNAVAILABLE.value());

			// Service: uppercase-microservice returning 500
			ctx.setResponseBody("Service: " + ctx.get(FilterConstants.SERVICE_ID_KEY) + " returning 500");
		}

		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
	}

}
