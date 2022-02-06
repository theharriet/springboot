package com.godcoder.myhome.validator;

import com.godcoder.myhome.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BoardValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz){
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if(b.getTitle().length()<=2 || b.getTitle().length()>=30){
            errors.rejectValue("title", "key", "제목은 2자 이상 30자 이하입니다");
        }
        if(!StringUtils.hasText(b.getContent())){
            errors.rejectValue("content", "key", "내용을 입력하세요");
        }

        
// isEmpty가 deprecated 되어 대체로는 StringUtils.hasText 또는 ObjectUtils.isEmpty로 대체 가능합니다.
// 사용상 주의점은 hasText의 겨우 조건이 반대로 됩니다.
// StringUtils.isEmpty(null) = true;
// StringUtils.hasText(null) = false;
    }

}
