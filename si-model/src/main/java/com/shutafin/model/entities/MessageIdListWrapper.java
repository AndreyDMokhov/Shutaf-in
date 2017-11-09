package com.shutafin.model.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Rogov on 17.10.2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageIdListWrapper {

    @NotNull
    private List<Long> messageIdList;
}
