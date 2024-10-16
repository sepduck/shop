package com.qlyshopphone_backend.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GenderRequest {
    private String genderName;
}
