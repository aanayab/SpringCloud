package mx.com.truper.springboot.practica26.zuul.apigateway.filter.route;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mx.com.truper.springboot.practica26.zuul.apigateway.fallback.GatewayClientResponse;

@Slf4j
//@Component
public class CustomRouteFilter extends ZuulFilter {

	@Autowired
	private ProxyRequestHelper helper;

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@SneakyThrows
	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		log.info("[{} CustomRouteFilter {}] Request Method: {}, Request URL: {}", filterType(), filterOrder(),
				request.getMethod(), request.getRequestURL().toString());

		ClientHttpResponse resp = new GatewayClientResponse(HttpStatus.OK, "Hola");

		ctx.set("zuulResponse", resp);
		ctx.set("sendZuulResponse", false); 
		this.helper.setResponse(resp.getRawStatusCode(), resp.getBody() == null ? null : resp.getBody(),
				resp.getHeaders());

		return resp;
	}

	@Override
	public String filterType() {
		return FilterConstants.ROUTE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.RIBBON_ROUTING_FILTER_ORDER - 1;
	}

}
