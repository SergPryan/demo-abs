package com.abs.demoabs.controller;

import com.abs.demoabs.dto.TransferMoneyDto;
import com.abs.demoabs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account_operation")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/transfer")
    public void transferMoney(TransferMoneyDto transferMoneyDto){
        accountService.transferMoney(transferMoneyDto);
    }
}
