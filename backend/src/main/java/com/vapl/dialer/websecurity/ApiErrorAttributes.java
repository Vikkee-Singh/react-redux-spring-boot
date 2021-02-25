package com.vapl.dialer.websecurity;

/*import java.util.Map;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@Component
class ApiErrorAttributes extends DefaultErrorAttributes {
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> attributes = super.getErrorAttributes((WebRequest) requestAttributes, includeStackTrace);
        attributes.remove("exception");

        return attributes;
}
}*/
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class ApiErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

        // Let Spring handle the error first, we will modify later :)
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);

        errorAttributes.remove("trace");
        errorAttributes.remove("timestamp");
        errorAttributes.remove("path");
        errorAttributes.remove("error");
        errorAttributes.remove("status");
        return errorAttributes;

    }

}