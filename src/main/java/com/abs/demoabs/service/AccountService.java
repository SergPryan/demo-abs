package com.abs.demoabs.service;

import com.abs.demoabs.entity.Account;
import com.abs.demoabs.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountService {

    private static Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountRepository;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public String transferMoney(Long sourceId, Long targetId, Double amount) {
        StringBuilder sb = new StringBuilder();
        Optional<Account> source = accountRepository.findById(sourceId);
        Optional<Account> target = accountRepository.findById(targetId);
        if (source.isPresent() && !source.get().isAvailable()) {
            sb.append("Account with id = ").append(sourceId).append(" not available");
            log.error(sb.toString());
            return sb.toString();
        }
        if (target.isPresent() && !target.get().isAvailable()) {
            sb.append("Account with id = ").append(sourceId).append(" not available");
            log.error(sb.toString());
            return sb.toString();
        }
        if (source.isPresent() && target.isPresent()) {
            if (source.get().getAmount() < amount) {
                sb.append("Not enough money in bank account with id = ").append(sourceId);
                log.error(sb.toString());
                return sb.toString();
            } else {
                source.get().setAmount(source.get().getAmount() - amount);
                target.get().setAmount(target.get().getAmount() + amount);
                accountRepository.save(source.get());
                accountRepository.save(target.get());
                accountRepository.flush();
                sb.append("success transfer money, sourceId = ").append(sourceId).append(", targetId = ").append(targetId)
                        .append(", amount").append(amount);
                log.info(sb.toString());
                return sb.toString();
            }
        } else {
            sb.append("Not find accounts with targetId = ").append(targetId).append(" and sourceId = ").append(sourceId);
            log.error(sb.toString());
            return sb.toString();
        }
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public boolean putMoney(Long id, Double count) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            if (account.get().isAvailable()) {
                account.get().setAmount(account.get().getAmount() + count);
                accountRepository.saveAndFlush(account.get());
                return true;
            } else {
                log.error("Account not available, id = " + id);
                return false;
            }
        } else {
            log.error("Account not find, id = " + id);
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public boolean takeMoney(Long id, Double count) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            if (account.get().getAmount() < count) {
                log.error("Account with id = " + id + " not enough money");
                return false;
            } else {
                if (account.get().isAvailable()) {
                    account.get().setAmount(account.get().getAmount() - count);
                    accountRepository.saveAndFlush(account.get());
                    return true;
                } else {
                    log.error("Account not available, id = " + id);
                    return false;
                }
            }
        } else {
            log.error("Account not find, id = " + id);
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean blockAccount(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            account.get().setAvailable(false);
            accountRepository.saveAndFlush(account.get());
            return true;
        } else {
            log.error("Account not find, id = " + id);
            return false;
        }
    }

}
