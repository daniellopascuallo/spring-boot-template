package es.nextdigital.demo.Service;

import es.nextdigital.demo.Controller.Exceptions.AccountNotFoundException;
import es.nextdigital.demo.Controller.Exceptions.NonAttachedCardException;
import es.nextdigital.demo.Model.Account;
import es.nextdigital.demo.Model.Card;
import es.nextdigital.demo.Model.CardType;
import es.nextdigital.demo.Repository.AccountRepository;
import es.nextdigital.demo.Repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;

    public List<Account> listAccountsByUserId(String userId) {

        return accountRepository.findByUserId(userId);
    }

    public Account getAccountById(String accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public boolean withdraw(final String cardId, final double amount) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NonAttachedCardException("The account does not have any cards attached"));

        Account account = accountRepository.
                findById(card.getAccountId()).
                orElseThrow(() -> new AccountNotFoundException("The account with ID " + card.getAccountId() + " does not exist"));

        boolean success = card.getType() == CardType.DEBIT ?
                debitWithdraw(account, amount) :
                creditWithdraw(card, amount);

        if (success) {
            accountRepository.save(account);
            cardRepository.save(card);
        }

        return success;
    }

    private boolean debitWithdraw(Account account, double amount) {
        return Optional.ofNullable(account)
                .filter(c -> c.getBalance() >= amount)
                .map(c -> {
                    c.setBalance(c.getBalance() - amount);
                    return true;
                })
                .orElse(false);
    }

    private boolean creditWithdraw(Card card, double amount) {
        return Optional.ofNullable(card)
                .filter(t -> amount <= t.getCreditLimit())
                .map(t -> {
                    t.setCreditLimit(t.getCreditLimit() - amount);
                    return true;
                })
                .orElse(false);
    }
}
