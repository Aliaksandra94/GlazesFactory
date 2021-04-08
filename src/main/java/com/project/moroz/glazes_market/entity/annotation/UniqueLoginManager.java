package com.project.moroz.glazes_market.entity.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueLoginManagerValidation.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UniqueLoginManager {
    public String message() default "There is already manager with this login!";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};
}