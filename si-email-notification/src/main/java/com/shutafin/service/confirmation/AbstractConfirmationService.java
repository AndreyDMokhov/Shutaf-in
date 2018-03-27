package com.shutafin.service.confirmation;

import com.shutafin.model.entity.BaseConfirmation;
import com.shutafin.model.exception.exceptions.ActionExpiresAtException;
import com.shutafin.model.exception.exceptions.ActionIsConfirmedException;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;

import java.util.Date;

/**
 * Created by evgeny on 3/28/2018.
 */
abstract public class AbstractConfirmationService {
    public BaseConfirmation getConfirmed(BaseConfirmation baseConfirmation) {
        Date date = new Date();
        if (baseConfirmation == null){
            throw new ResourceNotFoundException();
        } else if (baseConfirmation.getIsConfirmed()){
            throw new ActionIsConfirmedException();
        } else if (date.after(baseConfirmation.getExpiresAt())){
            throw new ActionExpiresAtException();
        }
        return baseConfirmation;
    }
}
