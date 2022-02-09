package mx.com.truper.springboot.practica26.zuul.apigateway.filter.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomErrorFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		log.info("[{} CustomErrorFilter {}] Request Method: {}, Request URL: {}", filterType(), filterOrder(),
				request.getMethod(), request.getRequestURL().toString());

		System.out.println( ctx.get("throwable") );
		
		// remove error code to prevent further error handling in follow up filters
		ctx.remove("throwable");
		
		// nueva respuesta
		ctx.setResponseBody("Overriding Zuul Response Body");
		ctx.getResponse().setContentType("application/json");
		
		// error code
		ctx.setResponseStatusCode(503);
		
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.ERROR_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.SEND_ERROR_FILTER_ORDER - 1;
	}

}
