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
        if (source.isPresent() & target.isPresent()) {
            if (!source.get().isAvailable()) {
                throw new RuntimeException("Account not available, id = " + source.get().getId());
            }
            if (!target.get().isAvailable()) {
                throw new RuntimeException("Account not available, id = " + target.get().getId());
            }
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

    @Transactional
    public void putMoney(Long id, Double count) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            if (account.get().isAvailable()) {
                account.get().setAmount(account.get().getAmount() + count);
                accountRepository.saveAndFlush(account.get());
            } else {
                throw new RuntimeException("Account not available, id = " + id);
            }
        } else {
            throw new RuntimeException("Account not find, id = " + id);
        }
    }

    @Transactional
    public void takeMoney(Long id, Double count) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            if (account.get().getAmount() < count) {
                throw new RuntimeException("Account with id = " + id + " not enough money");
            } else {
                if (account.get().isAvailable()) {
                    account.get().setAmount(account.get().getAmount() - count);
                    accountRepository.saveAndFlush(account.get());
                } else {
                    throw new RuntimeException("Account not available, id = " + id);
                }
            }
        } else {
            throw new RuntimeException("Account not find, id = " + id);
        }
    }

    @Transactional
    public void blockAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            account.get().setAvailable(false);
            accountRepository.saveAndFlush(account.get());
        } else {
            throw new RuntimeException("Account not find, id = " + id);
        }
    }

}
