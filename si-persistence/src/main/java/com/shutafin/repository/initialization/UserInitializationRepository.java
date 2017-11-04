package com.shutafin.repository.initialization;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponseDTO;

/**
 * Created by Alex on 03.07.2017.
 */
public interface UserInitializationRepository {

    UserInfoResponseDTO getUserInitializationData(User user);
}
