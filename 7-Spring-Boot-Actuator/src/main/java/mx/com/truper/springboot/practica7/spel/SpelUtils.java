package mx.com.truper.springboot.practica7.spel;

import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.context.expression.BeanExpressionContextAccessor;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Component
public class SpelUtils {

	private StandardEvaluationContext springEvaluationContext;
	private ExpressionParser spelParser;
	private SpelBeanResolver spelBeanResolver;
	private BeanExpressionContext springRootObject;

	private static SpelUtils instance;

	public SpelUtils(SpelBeanResolver spelBeanResolver) {
		this.spelBeanResolver = spelBeanResolver;

		this.springEvaluationContext = new StandardEvaluationContext();
		//this.springEvaluationContext.setBeanResolver(this.spelBeanResolver);
		this.springEvaluationContext.setBeanResolver(new BeanFactoryResolver(this.spelBeanResolver.getApplicationContext()));
		
		this.springEvaluationContext.addPropertyAccessor(new BeanExpressionContextAccessor());
		this.springRootObject = new BeanExpressionContext(this.spelBeanResolver.getApplicationContext().getBeanFactory(), null);

		this.spelParser = new SpelExpressionParser();

		instance = this;
	}

	public <T> T resolveExpression(String expression, Class<T> type) {
		return spelParser.parseExpression(expression).getValue(springEvaluationContext, type);
	}

	public static <T> T resolveBean(String expression, Class<T> type) {
		return instance.spelParser.parseExpression(expression).getValue(instance.springEvaluationContext, type);
	}
	
	public static <T> T resolve(String expression, Class<T> type) {
		return instance.spelParser.parseExpression(expression).getValue(instance.springEvaluationContext, instance.springRootObject, type);
	}

}
