package com.msbills.models.dto;

import com.msbills.models.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {

    private String idBill;

    private Date billingDate;

    private Double totalPrice;

    private UserResponseDto userDTO;
}
