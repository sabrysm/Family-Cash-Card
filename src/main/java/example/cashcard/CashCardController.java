package example.cashcard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cashcards")
class CashCardController {
    private CashCardRepository cashCardRepository;

    private CashCardController(CashCardRepository cashCardRepository) {
        this.cashCardRepository = cashCardRepository;
    }

    @GetMapping("/{id}")
    private ResponseEntity<CashCard> findById(@PathVariable Long id) {
        Optional<CashCard> cashCardOptional = cashCardRepository.findById(id);
        return cashCardOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody CashCard newCashCardRequest, UriComponentsBuilder ucb) {
        CashCard newCashCard = cashCardRepository.save(newCashCardRequest);
        URI locationOfNewCashCard = ucb.path("/cashcards/{id}").buildAndExpand(newCashCard.id()).toUri();

        return ResponseEntity.created(locationOfNewCashCard).build();
    }

    @GetMapping
    private ResponseEntity<Iterable<CashCard>> findAll() {
        return ResponseEntity.ok(cashCardRepository.findAll());
    }
}