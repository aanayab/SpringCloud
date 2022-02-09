package mx.com.truper.springboot.practica7.spel;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class SpelBeanResolver implements BeanResolver, ApplicationContextAware {

	private @Getter ConfigurableApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = (ConfigurableApplicationContext) applicationContext;
	}

	@Override
	public Object resolve(EvaluationContext context, String beanName) throws AccessException {
		return applicationContext.getBean(beanName); // @nombreDelBean
	}

}
