package com.oyster.core.controller;

import com.oyster.core.controller.annotation.PARAMETER;
import com.oyster.core.controller.command.Context;
import com.oyster.core.controller.exception.InvalidCommandParameterException;
import com.oyster.core.controller.utils.ControllerAnnotationUtils;

/**
 * @author bamboo
 * @since 4/21/14 11:31 PM
 */
public class Validator {

    public static boolean validate(Class command, Context context) throws InvalidCommandParameterException {

        PARAMETER[] parameters = ControllerAnnotationUtils
                .getParameterList(command);
        if (parameters == null)
            return false;

        for (PARAMETER parameter : parameters) {
            String key = parameter.key();


            Object ObjectContext = context.get(key);
            if (parameter.optional() && ObjectContext == null) {
                continue;
            }

            if (ObjectContext == null) {
                throw new InvalidCommandParameterException("Cannot find [" + key + "] parameter");
            }

            Class<? extends Object> classCurrentParameter = ObjectContext.getClass();
            if (parameter.type() != classCurrentParameter) {
                throw new InvalidCommandParameterException("Value [" + key + "] is of type " + classCurrentParameter.getSimpleName()
                        + ", must be of type " + parameter.type().getSimpleName());
            }
        }

        return true;
    }
}
