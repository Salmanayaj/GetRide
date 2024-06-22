package com.example.GetRide.Transformer;

import com.example.GetRide.Dto.request.CabRequest;
import com.example.GetRide.Dto.response.CabResponse;
import com.example.GetRide.Model.Cab;

public class CabTransformer {
    public static Cab cabRequestToCab(CabRequest cabRequest) {
        return Cab.builder()
                .cabType(cabRequest.getCabType())
                .cabNumber(cabRequest.getCabNumber())
                .farePerKm(cabRequest.getFarePerKm())
                .booked(false)
                .build();
    }

    public static CabResponse cabToCabResponse(Cab cab) {
        return CabResponse.builder()
                .booked(cab.isBooked())
                .cabNumber(cab.getCabNumber())
                .farePerKm(cab.getFarePerKm())
                .cabType(cab.getCabType())
                .build();
    }
}
