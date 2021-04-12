package com.project.moroz.glazes_market.entity.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueLoginValidation.class)
@Retention(RetentionPolicy.RUNTIME) //доступ после компиляции
@Target({ElementType.FIELD}) //применение на поле
public @interface UniqueLogin {
    //public String message() default "{duplicate.customerForm.name}";
    public String message() default "User with such login already exist.";
    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default{};
}