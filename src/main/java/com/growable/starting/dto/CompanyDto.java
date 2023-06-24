package com.growable.starting.dto;

import com.growable.starting.model.Company;
import lombok.Data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyDto {

    private Long id;
    private String companyName;
    private String workType;
    private String position;
    private String startDate;
    private String endDate;

    public Company toEntity() {
        return Company.builder()
                .companyName(companyName)
                .workType(workType)
                .position(position)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}