package com.micro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The purpose of this class is to override the default spring error page so
 * return a more "jsonish" response
 * 
 * containing: timestamp status error message path
 * 
 * @author developer
 *
 */
@RestController
@RequestMapping("/error")
public class SimpleErrorController implements ErrorController {
    /**
     * represents the error attributes that were set
     */
    private final ErrorAttributes errorAttributes;

    /**
     * creates a new instace of the controller
     * 
     * @param errorAttributes
     */
    @Autowired
    public SimpleErrorController(ErrorAttributes errorAttributes) {
        Assert.notNull(errorAttributes, "ErrorAttributes must not be null");
        this.errorAttributes = errorAttributes;
    }

    /**
     * the path of the error handler
     */
    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * handles the error and returns the error object as a JSON object
     * 
     * @param aRequest
     * @return
     */
    @RequestMapping
    public Map<String, Object> error(HttpServletRequest aRequest) {
        Map<String, Object> body = getErrorAttributes(aRequest, getTraceParameter(aRequest));
        return body;
    }

    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest aRequest,
        boolean includeStackTrace
    ) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(aRequest);
        Map<String, Object> attributes = errorAttributes.getErrorAttributes(
            requestAttributes,
            includeStackTrace
        );
        // we don't want the name of the exception to be public
        attributes.remove("exception");
        return attributes;
    }
}