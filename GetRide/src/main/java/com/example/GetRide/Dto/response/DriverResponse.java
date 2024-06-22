package com.example.GetRide.Dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DriverResponse{

    String name;
    long mobNo;
    CabResponse cab;
}
