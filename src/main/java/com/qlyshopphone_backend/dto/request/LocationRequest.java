package com.qlyshopphone_backend.dto.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationRequest {
    private String locationName;
}
