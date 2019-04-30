package com.abs.demoabs.service;

import com.abs.demoabs.dto.TransferMoneyDto;
import com.abs.demoabs.entity.Account;
import com.abs.demoabs.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Transactional
    public void transferMoney(TransferMoneyDto transferMoneyDto) {
        transferMoney(transferMoneyDto.getSourceId(), transferMoneyDto.getTargetId(), transferMoneyDto.getAmount());
    }

    @Transactional
    public void transferMoney(Long sourceId, Long targetIt, Double amount) {
        Optional<Account> source = accountRepository.findById(sourceId);
        Optional<Account> target = accountRepository.findById(targetIt);
        if (source.isPresent()) {
            if (!source.get().isAvailable()) {
                throw new RuntimeException("Account with id = " + sourceId + " not available");
            }
        }
        if (source.isPresent() && target.isPresent()) {
            if (source.get().getAmount() < amount) {
                throw new RuntimeException("Not enough money in bank account with id = " + sourceId);
            } else {
                source.get().setAmount(source.get().getAmount() - amount);
                target.get().setAmount(target.get().getAmount() + amount);
                accountRepository.save(source.get());
                accountRepository.save(target.get());
                accountRepository.flush();
            }
        } else {
            throw new RuntimeException("Not find accounts with targetId = " + targetIt + " and sourceId = " + sourceId);
        }
    }

}
