package es.nextdigital.demo.Service;

import es.nextdigital.demo.Model.Account;
import es.nextdigital.demo.Model.Card;
import es.nextdigital.demo.Model.CardType;
import es.nextdigital.demo.Repository.AccountRepository;
import es.nextdigital.demo.Repository.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    public void testWithdrawDebitSuccess() {
        // given
        String cardId = "debit-card-123";
        double amount = 100.0;

        Card debitCard = new Card();
        debitCard.setType(CardType.DEBIT);
        debitCard.setAccountId("account-456");

        Account account = new Account();
        account.setId("account-456");
        account.setBalance(200.0);

        // when
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(debitCard));
        when(accountRepository.findById("account-456")).thenReturn(Optional.of(account));

        boolean success = accountService.withdraw(cardId, amount);

        // then
        assertTrue(success);
        assertEquals(100.0, account.getBalance(), 0.01);
        verify(accountRepository, times(1)).save(account);
        verify(cardRepository, times(1)).save(debitCard);
    }

    @Test
    public void testWithdrawCreditSuccess() {
        // given
        String cardId = "credit-card-789";
        double amount = 100.0;

        Account account = new Account();
        account.setId("account-789");
        account.setBalance(500.0);

        Card creditCard = new Card();
        creditCard.setType(CardType.CREDIT);
        creditCard.setAccountId("account-789");
        creditCard.setCreditLimit(1000.0);

        // when
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(creditCard));
        when(accountRepository.findById("account-789")).thenReturn(Optional.of(account));

        boolean success = accountService.withdraw(cardId, amount);

        // then
        assertTrue(success);
        assertEquals(900.0, creditCard.getCreditLimit(), 0.01);
        verify(cardRepository, times(1)).save(creditCard);
    }

    @Test
    public void testWithdrawDebitInsufficientBalance() {
        // given
        String cardId = "debit-card-123";
        double amount = 300.0;

        Card debitCard = new Card();
        debitCard.setType(CardType.DEBIT);
        debitCard.setAccountId("account-456");

        Account account = new Account();
        account.setId("account-456");
        account.setBalance(200.0);

        // then
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(debitCard));
        when(accountRepository.findById("account-456")).thenReturn(Optional.of(account));

        boolean success = accountService.withdraw(cardId, amount);

        // when
        assertFalse(success);
        assertEquals(200.0, account.getBalance(), 0.01);
        verify(accountRepository, never()).save(any());
        verify(cardRepository, never()).save(any());
    }
}