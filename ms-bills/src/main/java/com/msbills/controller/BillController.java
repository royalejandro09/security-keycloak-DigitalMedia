package com.msbills.controller;

import com.msbills.models.Bill;
import com.msbills.models.dto.BillDto;
import com.msbills.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService service;

    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAll() {
        return ResponseEntity.ok().body(service.getAllBill());
    }

    @GetMapping("/findBy")
    public ResponseEntity<Bill> findByCustomer(@RequestParam String customer) {
        Bill bill = service.findByCustomer(customer).get();
        if (bill != null) {
            return ResponseEntity.ok().body(bill);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/detail")
    @PreAuthorize("hasAuthority('GROUP_admin')")
    public ResponseEntity<BillDto> getAllBillsByUserName(@RequestParam String username) {
        Optional<BillDto> bill = service.findByUserName(username);

        if (bill.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(bill.get());
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('GROUP_provider') AND hasAuthority('SCOPE_facturacion:gestion')")
    public ResponseEntity<Bill> saveBill(@RequestBody Bill bill) {
        return ResponseEntity.ok().body(service.saveBill(bill));
    }
}
