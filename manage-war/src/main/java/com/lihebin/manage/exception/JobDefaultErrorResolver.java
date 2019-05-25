package com.lihebin.manage.exception;

import com.googlecode.jsonrpc4j.ErrorData;

/**
 * Created by lihebin on 2018/10/19.
 */
//public class JobDefaultErrorResolver implements ErrorResolver {

public class JobDefaultErrorResolver{
//    private final Logger log= LoggerFactory.getLogger(getClass());
//
//    @Override
//    public JsonError resolveError(Throwable t, Method method, List<JsonNode> arguments) {
//        log.error("resolveError:{},{},{}", t, method, arguments);
//        if (t.getClass().equals(BackendException.class)) {
//            ConstraintViolationException error = (ConstraintViolationException) t;
//            Set<ConstraintViolation<?>> violations = error.getConstraintViolations();
//            final StringBuilder stringBuilder = new StringBuilder();
//            for (ConstraintViolation constraintViolation : violations) {
//                stringBuilder.append(constraintViolation.getMessage());
//            }
//            String message = stringBuilder.toString();
//            return new JsonError(400, message,
//                    new ErrorData(error.getClass().getName(), message));
//        }
//        ErrorData errorData = new ErrorData(t.getClass().getName(), t.getMessage());
//        return new JsonError(1111, "未知错误", errorData);
//    }
}
