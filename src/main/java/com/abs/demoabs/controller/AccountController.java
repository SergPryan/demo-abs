package com.abs.demoabs.controller;

import com.abs.demoabs.dto.TransferMoneyDto;
import com.abs.demoabs.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/account_operation")
public class AccountController {

    private static Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountService accountService;

    @PostMapping("/transfer")
    public void transferMoney(TransferMoneyDto transferMoneyDto) {
        accountService.transferMoney(transferMoneyDto);
    }

    @PostMapping("/put_money")
    public void putMoney(@RequestBody Map<String, String> data) {
        log.info(String.format("put money id=%s, count=%s", data.get("id"), data.get("count")));
        accountService.putMoney(Long.valueOf(data.get("id")), Double.valueOf(data.get("count")));
    }

    @PostMapping("/take_money")
    public void takeMoney(@RequestBody Map<String, String> data) {
        log.info(String.format("take money id=%s, count=%s", data.get("id"), data.get("count")));
        accountService.takeMoney(Long.valueOf(data.get("id")), Double.valueOf(data.get("count")));
    }

    @PostMapping("/block")
    public void blockAccount(@RequestParam Long id) {
        accountService.blockAccount(id);
    }
}
