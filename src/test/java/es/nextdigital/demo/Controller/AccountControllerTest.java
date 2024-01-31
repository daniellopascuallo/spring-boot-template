package es.nextdigital.demo.Controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import es.nextdigital.demo.Model.Account;
import es.nextdigital.demo.Model.Movement;
import es.nextdigital.demo.Model.Withdrawal;
import es.nextdigital.demo.Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void getAccountsByUserTest() throws Exception {
        String userId = "user-123";
        List<Account> accounts = Arrays.asList(new Account(), new Account());

        when(accountService.listAccountsByUserId(userId)).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(accountService).listAccountsByUserId(userId);
    }

    @Test
    void getMovementsByAccountTest() throws Exception {
        String accountId = "account-456";
        Account account = new Account();
        account.setMovements(Arrays.asList(new Movement(), new Movement()));

        when(accountService.getAccountById(accountId)).thenReturn(account);

        mockMvc.perform(get("/api/accounts/{accountId}/movements", accountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(accountService).getAccountById(accountId);
    }

    @Test
    void getMovementsByAccountNotFoundTest() throws Exception {
        String accountId = "account-789";

        when(accountService.getAccountById(accountId)).thenReturn(null);

        mockMvc.perform(get("/api/accounts/{accountId}/movements", accountId))
                .andExpect(status().isNotFound());

        verify(accountService).getAccountById(accountId);
    }

    @Test
    void withdrawSuccessTest() throws Exception {
        String cardId = "card-123";
        double amount = 100.0;
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);

        when(accountService.withdraw(cardId, amount)).thenReturn(true);

        mockMvc.perform(post("/api/accounts/{cardId}/withdraw", cardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":100.0}"))
                .andExpect(status().isOk())
                .andExpect(content().string("successfully withdrawal."));

        verify(accountService).withdraw(cardId, amount);
    }

    @Test
    void withdrawInsufficientFundsTest() throws Exception {
        String cardId = "card-456";
        double amount = 200.0;
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);

        when(accountService.withdraw(cardId, amount)).thenReturn(false);

        mockMvc.perform(post("/api/accounts/{cardId}/withdraw", cardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":200.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("insufficient funds."));

        verify(accountService).withdraw(cardId, amount);
    }

    @Test
    void withdrawErrorTest() throws Exception {
        String cardId = "card-789";
        double amount = 300.0;
        Withdrawal withdrawal = new Withdrawal();
        withdrawal.setAmount(amount);

        when(accountService.withdraw(cardId, amount)).thenThrow(new RuntimeException("Withdrawal error"));

        mockMvc.perform(post("/api/accounts/{cardId}/withdraw", cardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":300.0}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error with withdrawal: Withdrawal error"));

        verify(accountService).withdraw(cardId, amount);
    }
}