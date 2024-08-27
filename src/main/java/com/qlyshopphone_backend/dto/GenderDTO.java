package com.qlyshopphone_backend.dto;
import static com.qlyshopphone_backend.constant.ErrorMessage.*;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenderDTO {
    private String genderName;
}
