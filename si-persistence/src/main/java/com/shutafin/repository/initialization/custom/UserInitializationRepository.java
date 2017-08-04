package com.shutafin.repository.initialization.custom;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInit;

/**
 * Created by Alex on 03.07.2017.
 */
public interface UserInitializationRepository {

    UserInit getUserInitializationData(User user);
}
