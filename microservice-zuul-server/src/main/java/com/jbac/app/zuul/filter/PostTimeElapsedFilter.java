package com.jbac.app.zuul.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTimeElapsedFilter extends ZuulFilter{
	
	private static Logger log = LoggerFactory.getLogger(PostTimeElapsedFilter.class);
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		log.info("Entrando a post filter");
		Long startTime = (Long)request.getAttribute("startTime");
		Long finalTime = System.currentTimeMillis();
		Long elapsedTime = finalTime - startTime;
		
		log.info(String.format("Time elapsed in seconds %s seg.", elapsedTime.doubleValue()/1000.00));
		log.info(String.format("Time elapsed in milliseconds %s ms.", elapsedTime));
		return null;
	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}
	
	
	
}
