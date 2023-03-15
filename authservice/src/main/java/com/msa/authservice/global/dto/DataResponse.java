package com.msa.authservice.global.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {
    private Map<String, Object> jsonList;

    public static DataResponse of(Map jsonList) {
        return DataResponse.builder()
                .jsonList(jsonList)
                .build();
    }

}
