package com.khaphp.userservice.dto;

import com.khaphp.userservice.util.validdata.status.ValidStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateStatusParam {
    private String id;
    @ValidStatus
    private String status;
}
