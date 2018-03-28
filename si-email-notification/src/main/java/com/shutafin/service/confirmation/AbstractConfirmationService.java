package com.shutafin.service.confirmation;

import com.shutafin.model.entity.BaseConfirmation;
import com.shutafin.model.exception.exceptions.LinkExpiredException;
import com.shutafin.model.exception.exceptions.LinkAlreadyConfirmedException;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Created by evgeny on 3/28/2018.
 */
@Slf4j
public abstract class AbstractConfirmationService {
    public BaseConfirmation getConfirmed(BaseConfirmation baseConfirmation) {
        Date date = new Date();
        if (baseConfirmation == null){
            log.warn("Resource not found exception");
            throw new ResourceNotFoundException();
        } else if (baseConfirmation.getIsConfirmed()){
            log.warn("Link {} is already confirmed. Confirmation time: {}",
                    baseConfirmation.getConfirmationUUID(),
                    baseConfirmation.getUpdatedDate());

            throw new LinkAlreadyConfirmedException();
        } else if (date.after(baseConfirmation.getExpiresAt())){
            log.warn("Link {} is already expired. Expiration time: {}. Current time: {}",
                    baseConfirmation.getConfirmationUUID(),
                    baseConfirmation.getExpiresAt(),
                    new Date());
            throw new LinkExpiredException();
        }
        return baseConfirmation;
    }
}
