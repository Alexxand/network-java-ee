package com.github.alexxand.model.validation;

import com.github.alexxand.model.BasicManagerInf;
import com.github.alexxand.model.CompanyInf;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BasicValidator implements Validator {

    private static FieldValidation validate(String field, Pattern pattern){
        if (field == null || field.isEmpty()){
            return FieldValidation.builder().isEmptyField(true).build();
        } else if (pattern != null) {
            final Matcher matcher = pattern.matcher(field);
            if (!matcher.matches()){
                return FieldValidation.builder().isIncorrect(true).build();
            }
        }

        return new FieldValidation(false,false);
    }

    private static FormValidation validate(BasicManagerInf basicManagerInf, FormValidation formValidation){
        final String name = basicManagerInf.getName();
        final String lastName = basicManagerInf.getLastName();
        final String email = basicManagerInf.getEmail();
        final String password = basicManagerInf.getPassword();

        final Pattern namePattern = Pattern.compile("([A-Z][a-z]{0,39}|[А-Я][а-я]{0,39})");
        final Pattern emailPattern = Pattern.compile("[A-Za-z0-9_-]+@[A-Za-z0-9_-]+.[A-Za-z]{2,6}");
        final Map<String,FieldValidation> fields = formValidation.getFields();
        fields.put(
                "name",
                validate(name,namePattern));
        fields.put(
                "lastName",
                validate(lastName,namePattern));
        fields.put(
                "email",
                validate(email,emailPattern));
        fields.put(
                "password",
                validate(password,null));

        /*final Pattern sameLanguagePattern = Pattern.compile("([A-Za-z]*|[А-Яа-я]*)");
        final Matcher sameLanguageMatcher = sameLanguagePattern.matcher(name + lastName);
        if (!sameLanguageMatcher.matches())
            formValidation.getErrors().put("DIFF_LANGUAGE",true);*/
        return formValidation;
    }

    private static FormValidation validate(CompanyInf companyInf, FormValidation formValidation){
        final String company = companyInf.getCompany();
        final String position = companyInf.getPosition();

        final Pattern companyPattern = Pattern.compile("[A-Za-zА-Яа-я0-9 \"\']{1,40}");
        final Pattern positionPattern = Pattern.compile("[A-Za-zА-Яа-я \"\']{1,40}");
        final Map<String,FieldValidation> fields = formValidation.getFields();
        fields.put(
                "company",
                validate(company,companyPattern));
        fields.put(
                "position",
                validate(position,positionPattern));

        return formValidation;
    }

    @Override
    public FormValidation validate(Object obj) {
        FormValidation formValidation = new FormValidation();

        if (obj instanceof BasicManagerInf)
            return validate((BasicManagerInf) obj, formValidation);
        if (obj instanceof CompanyInf)
            return validate((CompanyInf) obj, formValidation);

        return formValidation;
    }
}
