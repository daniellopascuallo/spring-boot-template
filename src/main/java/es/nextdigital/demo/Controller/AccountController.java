package es.nextdigital.demo.Controller;

import es.nextdigital.demo.Model.Account;
import es.nextdigital.demo.Model.Movement;
import es.nextdigital.demo.Model.Withdrawal;
import es.nextdigital.demo.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Account>> getAccountsByUser(@PathVariable String userId) {
        return new ResponseEntity<>(accountService.listAccountsByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{accountId}/movements")
    public ResponseEntity<List<Movement>> getMovementsByAccount(@PathVariable String accountId) {

        Account account = accountService.getAccountById(accountId);

        if (account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(account.getMovements(), HttpStatus.OK);
    }

    @PostMapping("/{cardId}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable String cardId, @RequestBody Withdrawal withdrawal) {
        try {
            double amount = withdrawal.getAmount();
            boolean result = accountService.withdraw(cardId, amount);

            if (result) {
                return new ResponseEntity<>("successfully withdrawal.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("insufficient funds.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error with withdrawal: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
