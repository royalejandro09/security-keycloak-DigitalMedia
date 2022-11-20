package com.msbills.service;

import com.msbills.models.Bill;
import com.msbills.models.UserResponseDto;
import com.msbills.models.dto.BillDto;
import com.msbills.repositories.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillService {

    private final UsersFeignClient feignClientUsers;
    private final BillRepository repository;

    public Optional<BillDto> findByUserName(String userName) {
        Optional<Bill> bill = repository.findByCustomerBill(userName);
        Optional<UserResponseDto> userDto = feignClientUsers.findByUserName(userName);

        if (bill.isPresent()) {
            return Optional.of(toBillDto(bill.get().getIdBill(), bill.get().getBillingDate(), bill.get().getTotalPrice(), userDto.get()));
        } else {
            return Optional.empty();
        }
    }

    public List<Bill> getAllBill() {
        return repository.findAll();
    }

    public Bill saveBill(Bill bill) {
        return repository.save(bill);
    }

    public Optional<Bill> findByCustomer(String customer) {
        return repository.findByCustomerBill(customer);
    }

    private BillDto toBillDto(String idBill, Date billingDate, double totalPrice, UserResponseDto user) {
        BillDto dto = new BillDto(idBill, billingDate, totalPrice, user);
        return dto;
    }

}
