package com.abs.demoabs.controller;

import com.abs.demoabs.repository.AccountRepository;
import com.abs.demoabs.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/account_operation")
public class AccountController {

    private static Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping("/transfer")
    public ResponseEntity transferMoney(@RequestBody Map<String, String> data) {
        log.info("transfer money " + data);
        String result = accountService.transferMoney(Long.valueOf(data.get("source_id")), Long.valueOf(data.get("target_id")),
                Double.valueOf(data.get("count")));
        return ResponseEntity.ok(result);
    }

    @PostMapping("/put_money")
    public ResponseEntity putMoney(@RequestBody Map<String, String> data) {
        log.info(String.format("put money id=%s, count=%s", data.get("id"), data.get("count")));
        boolean isSuccess = accountService.putMoney(Long.valueOf(data.get("id")), Double.valueOf(data.get("count")));
        if (isSuccess) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/take_money")
    public ResponseEntity takeMoney(@RequestBody Map<String, String> data) {
        log.info(String.format("take money id=%s, count=%s", data.get("id"), data.get("count")));
        boolean isSuccess = accountService.takeMoney(Long.valueOf(data.get("id")), Double.valueOf(data.get("count")));
        if (isSuccess) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/block")
    public ResponseEntity blockAccount(@RequestParam Long id) {
        boolean isSuccess = accountService.blockAccount(id);
        if (isSuccess) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
