package com.abs.demoabs.controller;

import com.abs.demoabs.dto.TransferMoneyDto;
import com.abs.demoabs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account_operation")
public class AccountController {

    @Autowired
    AccountService accountService;

    @PostMapping("/transfer")
    public void transferMoney(TransferMoneyDto transferMoneyDto){
        accountService.transferMoney(transferMoneyDto);
    }

    @PostMapping("/put_money")
    public void putMoney(@RequestParam Long id, @RequestParam Double count){
        accountService.putMoney(id, count);
    }

    @PostMapping("/take_money")
    public void takeMoney(@RequestParam Long id, @RequestParam Double count){
        accountService.takeMoney(id, count);
    }

    @PostMapping("/block_account")
    public void blockAccount(@RequestParam Long id){
        accountService.blockAccount(id);
    }
}
