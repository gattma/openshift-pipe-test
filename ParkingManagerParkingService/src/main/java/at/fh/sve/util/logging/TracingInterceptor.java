package at.fh.sve.util.logging;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Trace
@Interceptor
public class TracingInterceptor {

    //@Inject
    //private Logger LOG;

    @AroundInvoke
    public Object intercept(InvocationContext context) throws Exception {

        //LOG.trace(String.format("entering method %s", context.getMethod().getName()));
        //Arrays.stream(context.getParameters()).forEach(p -> LOG.trace(String.format("%s: %s", p.getClass(), p)));
        Object obj = context.proceed();
        //LOG.trace("leaving method " + context.getMethod().getName());
        return obj;
    }
}
